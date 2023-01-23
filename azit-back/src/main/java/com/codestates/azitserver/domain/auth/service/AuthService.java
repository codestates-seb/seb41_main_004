package com.codestates.azitserver.domain.auth.service;

import static java.util.stream.Collectors.*;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.azitserver.domain.auth.dto.AuthDto;
import com.codestates.azitserver.domain.auth.entity.AuthNumber;
import com.codestates.azitserver.domain.auth.jwt.JwtTokenizer;
import com.codestates.azitserver.domain.auth.repository.AuthNumberRepository;
import com.codestates.azitserver.domain.auth.utils.RedisUtils;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.repository.MemberRepository;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final RedisUtils redisUtils;
	private final JwtTokenizer jwtTokenizer;
	private final JavaMailSender javaMailSender;
	private final AuthNumberRepository authNumberRepository;

	/**
	 * 인증번호 발송 메서드
	 * @param request 회원 eamil
	 * @throws Exception
	 */
	@Transactional
	public void sendAuthEmail(AuthDto.SendEmail request) throws Exception {
		// request의 email로 회원 찾기. (회원 email인지 확인)
		String email = request.getEmail();
		Member member = findVerifiedMemberByEmail(email);

		// 인증번호 생성
		String authNum = createAuthNumber();

		// email, authNum DB에 저장 (같은 email 존재하면 덮어쓰기)
		AuthNumber authNumber = new AuthNumber();
		if (authNumberRepository.findByEmail(email).isPresent()) {
			authNumber = findVerifiedAuthNumberByEmail(email);
		} else {
			authNumber.setEmail(email);
		}
		authNumber.setAuthNum(authNum);
		authNumberRepository.save(authNumber);

		// 메일 작성, 전송
		sendMessage(createAuthEmail(email, authNum));
	}

	/**
	 * 임시 비밀번호 발급 메서드
	 * @param request email, 인증번호
	 * @throws Exception
	 */
	@Transactional
	public void resetPassword(AuthDto.SendPWEmail request) throws Exception {
		// 입력값으로 email, authNum 받음
		String email = request.getEmail();
		String authNum = request.getAuthNum();

		// email로 db에서 AuthNumber authDBNum 찾아오기
		AuthNumber findAuthNumber = findVerifiedAuthNumberByEmail(email);
		String DBNumber = findAuthNumber.getAuthNum();

		// 둘이 일치하는지 확인
		stringConfirmer(authNum, DBNumber);

		// 랜덤 비밀번호 생성
		String tempPassword = createTempPassword();

		// 랜덤 비번 Member DB 저장
		Member member = findVerifiedMemberByEmail(email);
		member.setPassword(passwordEncoder.encode(tempPassword));
		memberRepository.save(member);

		// db에서 authNumber 정보 삭제
		authNumberRepository.delete(findAuthNumber);

		// 메일 작성, 메일 전송
		sendMessage(createPWEmail(email, tempPassword));
	}

	@Transactional
	public void logout(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		accessToken = accessToken.split(" ")[1];

		String ATKemail = jwtTokenizer.getATKemail(accessToken);
		redisUtils.deleteData(ATKemail);

		Long expiration = jwtTokenizer.getATKExpiration(accessToken);
		redisUtils.setData(accessToken, "blackList", expiration);
	}

	/**
	 * 비밀번호 인증
	 * @param memberId 회원 고유 식별자
	 * @param request 입력 password
	 */
	public void passwordMatcher(Long memberId, AuthDto.MatchPassword request) {
		// 입력받은 memberId로 memberRepository 안의 member를 찾는다
		Member findMember = findVerifiedMember(memberId);

		// 입력받은 password와 findMember의 password가 일치하는지 확인(matches 사용하면 암호화한 값과 비교해준다)
		if (!passwordEncoder.matches(request.getPassword(), findMember.getPassword())) {
			throw new BusinessLogicException(ExceptionCode.STRING_VALIDATION_FAILED);
		}
	}

	/**
	 * 비밀번호 변경
	 * @param memberId 회원 고유 식별자
	 * @param request 입력 newPassword, newPasswordCheck
	 * @param member 요청 회원 정보
	 */
	public void updatePassword(Long memberId, AuthDto.PatchPassword request, Member member) {
		// 요청주체의 memberId가 api memberId와 일치하는지 확인 -> 일치하지 않으면 예외 발생
		if (!member.getMemberId().equals(memberId)) {
			throw new BusinessLogicException(ExceptionCode.MEMBER_VERIFICATION_FAILED);
		}

		// password와 passwordCheck가 같은지 확인해서 같지 않으면 exception 보냄
		stringConfirmer(request.getNewPassword(), request.getNewPasswordCheck());

		// member 찾고, 새 비밀번호를 암호화해서 넣고 저장 (소셜은 비밀번호가 없으니 비밀번호가 있는 경우에만 수정)
		Member findMember = findVerifiedMember(memberId);

		try {
			Optional.ofNullable(findMember.getPassword())
				.ifPresent(password -> findMember.setPassword(passwordEncoder.encode(request.getNewPassword())));
			memberRepository.save(findMember);

		} catch (EntityNotFoundException e) {
			log.warn("Failed to change password. Existing password not found: {}", e.getLocalizedMessage());
		}
	}

	/**
	 * 토큰 재발급
	 * @param request refreshToken, 만료 accessToken
	 * @param response 재발급 토큰 보낼 response
	 */
	@Transactional
	public void reIssueToken(HttpServletRequest request, HttpServletResponse response) {
		String accessToken = request.getHeader("Authorization");
		String refreshToken = request.getHeader("Refresh");
		accessToken = accessToken.split(" ")[1];

		String ATKemail = jwtTokenizer.getATKemail(accessToken);

		//TODO : 재발급 되어도 발급 이전 ATK 살아있음
		// ATK 만료시간 남아있으면 재발급 못하게 코드 작성하고 테스트동안에는 편하게 테스트하기위해 주석처리해두기

		//ATK, RTK 같은 유저거인지 검증
		if (!redisUtils.getValuebyKey(ATKemail).equals(refreshToken)) {
			throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
		}

		// logout되면 redis에서 refreshToken 삭제되어 재발급 안 됨
		if (redisUtils.isExists(ATKemail)) {
			Member findMember = findVerifiedMemberByEmail(ATKemail);
			accessToken = delegateAccessToken(findMember);

			response.setHeader("Authorization", "Bearer " + accessToken);
			response.setHeader("Refresh", refreshToken);
		} else {
			throw new BusinessLogicException(ExceptionCode.TOKEN_NOT_FOUND);
		}
	}

	/**
	 * 이메일 인증 메일 작성
	 * @param email 이메일 주소
	 * @return 이메일 본문
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public MimeMessage createAuthEmail(String email, String authNum) throws
		MessagingException,
		UnsupportedEncodingException {
		// 메일 작성
		MimeMessage message = javaMailSender.createMimeMessage();

		message.addRecipients(MimeMessage.RecipientType.TO, email); // 받는 이
		message.setSubject("azit 이메일 인증 코드"); // 이메일 제목

		String msg = "";
		msg += "<div style='margin:20px;'>";
		msg += "<p>이메일 인증 메일입니다.<p>";
		msg += "<br>";
		msg += "<div align='center' style='border:1px dashed black; font-family:verdana';>";
		msg += "<br>";
		msg += "<h3 style='color:navy;'>인증번호를 azit 화면에 입력해주세요.</h3>";
		msg += "<div style='font-size:130%'>";
		msg += "인증번호 : <strong>";
		msg += authNum + "</strong><div><br/> ";
		msg += "</div>";
		msg += "</div>";
		msg += "</div>";
		msg += "<br>";
		msg += "<p>당신의 취미, 우리도 알고 싶습니다. 취미를 통해 만드는 우리만의 공간 azit<p>";

		message.setText(msg, "utf-8", "html");//내용
		message.setFrom(new InternetAddress("azit004main@naver.com", "azit"));//보내는 사람

		return message;
	}

	/**
	 * 임시 비밀번호 발급 메일 작성
	 * @param email 이메일 주소
	 * @param tempPassword 임시 비밀번호
	 * @return 이메일 본문
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public MimeMessage createPWEmail(String email, String tempPassword) throws
		MessagingException,
		UnsupportedEncodingException {
		// 메일 작성
		MimeMessage message = javaMailSender.createMimeMessage();

		message.addRecipients(MimeMessage.RecipientType.TO, email); // 받는 이
		message.setSubject("azit 임시 비밀번호 발급"); // 이메일 제목

		String msg = "";
		msg += "<div style='margin:20px;'>";
		msg += "<p>임시 비밀번호 발급 메일입니다.<p>";
		msg += "<br>";
		msg += "<div align='center' style='border:1px dashed black; font-family:verdana';>";
		msg += "<br>";
		msg += "<h3 style='color:navy;'>로그인 후 비밀번호를 변경해 주세요.</h3>";
		msg += "<div style='font-size:130%'>";
		msg += "임시 비밀번호 : <strong>";
		msg += tempPassword + "</strong><div><br/> ";
		msg += "</div>";
		msg += "</div>";
		msg += "</div>";
		msg += "<br>";
		msg += "<p>당신의 취미, 우리도 알고 싶습니다. 취미를 통해 만드는 우리만의 공간 azit<p>";

		message.setText(msg, "utf-8", "html");//내용
		message.setFrom(new InternetAddress("azit004main@naver.com", "azit"));//보내는 사람

		return message;
	}

	/**
	 * 이메일 전송
	 * @param message 이메일 본문
	 * @throws Exception
	 */
	public void sendMessage(MimeMessage message) throws Exception {
		try {
			javaMailSender.send(message);
		} catch (MailException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	private String delegateAccessToken(Member member) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", member.getEmail());
		claims.put("roles", member.getRoles());

		String subject = member.getEmail();
		Date expiration = jwtTokenizer.getTokenExpiration(
			jwtTokenizer.getAccessTokenExpirationMinutes());

		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(
			jwtTokenizer.getSecretKey());

		String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration,
			base64EncodedSecretKey);

		return accessToken;
	}

	// memberId로 Repository에서 해당하는 멤버 찾아서 정보 반환
	private Member findVerifiedMember(Long memberId) {
		Optional<Member> optionalMember = memberRepository.findById(memberId);
		Member findMember = optionalMember.orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

		return findMember;
	}

	/**
	 * Email로 Member 찾아서 반환
	 * @param Email 회원 email
	 * @return Member
	 */
	private Member findVerifiedMemberByEmail(String Email) {
		Optional<Member> optionalMember = memberRepository.findByEmail(Email);
		Member findMember = optionalMember.orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

		return findMember;
	}

	/**
	 * Email로 AuthNumber 찾아서 반환
	 * @param Email 회원 email
	 * @return AuthNumber
	 */
	private AuthNumber findVerifiedAuthNumberByEmail(String Email) {
		Optional<AuthNumber> optionalMember = authNumberRepository.findByEmail(Email);
		AuthNumber findAuthNumber = optionalMember.orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.AUTH_NUMBER_NOT_FOUND));

		return findAuthNumber;
	}

	/**
	 * 두 값 일치하는지 비교
	 * @param s1
	 * @param s2
	 */
	public void stringConfirmer(String s1, String s2) {
		if (!Objects.equals(s1, s2)) {
			throw new BusinessLogicException(ExceptionCode.STRING_VALIDATION_FAILED);
		}
	}

	/**
	 * 이메일 인증번호 생성
	 * @return 6자리 숫자로 구성된 랜덤 인증번호 (String)
	 */
	public String createAuthNumber() {
		return randomGenerater(0, 0, 6, 0);
	}

	/**
	 * 임시 비밀번호 생성
	 * @return 12자리 랜덤 비밀번호 생성
	 */
	public String createTempPassword() {
		return randomGenerater(3, 3, 4, 2);
	}

	/**
	 * 랜덤 문자열 만드는 메서드
	 * @param numberOfUpperCaseLetters 대문자 수
	 * @param numberOfLowerCaseLetters 소문자 수
	 * @param numberOfNumeric 숫자 수
	 * @param numberOfSpecialChars 특수문자 수 (#, $, %, &)
	 * @return 랜덤 문자열
	 */
	public String randomGenerater(int numberOfUpperCaseLetters,
		int numberOfLowerCaseLetters,
		int numberOfNumeric,
		int numberOfSpecialChars) {
		String upperCaseLetters = RandomStringUtils.random(numberOfUpperCaseLetters, 65, 90, true, false);
		String lowerCaseLetters = RandomStringUtils.random(numberOfLowerCaseLetters, 97, 122, true, false);
		String numbers = RandomStringUtils.randomNumeric(numberOfNumeric);
		String specialChars = RandomStringUtils.random(numberOfSpecialChars, 35, 38, false, false);

		String combinedLetters = combineLetters(upperCaseLetters, lowerCaseLetters, numbers, specialChars);
		List<Character> shuffledLetters = shuffleLetters(combinedLetters);
		return shuffledLetters.stream()
			.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
			.toString();
	}

	private static List<Character> shuffleLetters(String combinedLetters) {
		List<Character> shuffledLetters = combinedLetters.chars().mapToObj(c -> (char)c).collect(toList());
		Collections.shuffle(shuffledLetters);
		return shuffledLetters;
	}

	private static String combineLetters(String upperCaseLetters, String lowerCaseLetters, String numbers,
		String specialChars) {
		return upperCaseLetters.concat(lowerCaseLetters).concat(numbers).concat(specialChars);
	}
}
