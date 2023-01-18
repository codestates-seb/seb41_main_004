package com.codestates.azitserver.domain.auth.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codestates.azitserver.domain.auth.dto.LoginDto;
import com.codestates.azitserver.domain.auth.jwt.JwtTokenizer;
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

	public boolean passwordMatcher(Long memberId, LoginDto.MatchPassword request) {
		// 입력받은 memberId로 memberRepository 안의 member를 찾는다
		Member findMember = findVerifiedMember(memberId);

		// 입력받은 password와 findMember의 password가 일치하는지 확인(matches 사용하면 암호화한 값과 비교해준다)
		boolean matchingResult = passwordEncoder.matches(request.getPassword(), findMember.getPassword());

		return matchingResult;
	}

	//비밀번호 변경(patchPassword)
	public void updatePassword(Long memberId, LoginDto.PatchPassword request, Member member) {
		// 요청주체의 memberId가 api memberId와 일치하는지 확인 -> 일치하지 않으면 예외 발생
		if (!member.getMemberId().equals(memberId)) {
			throw new BusinessLogicException(ExceptionCode.MEMBER_VERIFICATION_FAILED);
		}

		// password와 passwordCheck가 같은지 확인해서 같지 않으면 exception 보냄
		passwordConfirmer(request);

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
	 * @param request : refreshToken, 만료 accessToken
	 * @param response : 재발급 토큰 보낼 response
	 */
	public void reIssueToken(HttpServletRequest request, HttpServletResponse response) {
		String accessToken = request.getHeader("Authorization");
		String refreshToken = request.getHeader("Refresh");

		if (redisUtils.getExpiration(refreshToken) > 0) {
			String Email = redisUtils.getEmail(refreshToken); //**
			Member findMember = findVerifiedMemberByEmail(Email);
			// accessToken = delegateAccessToken(findVerifiedMember(memberId));
			accessToken = delegateAccessToken(findMember);

			response.setHeader("Authorization", "Bearer " + accessToken);
			response.setHeader("Refresh", refreshToken);
		} else {
			throw new BusinessLogicException(ExceptionCode.TOKEN_NOT_FOUND);
		}
	}

	private String delegateAccessToken(Member member) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", member.getEmail());
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
	 * @param Email : 회원 unique email
	 * @return : Member
	 */
	private Member findVerifiedMemberByEmail(String Email) {
		Optional<Member> optionalMember = memberRepository.findByEmail(Email);
		Member findMember = optionalMember.orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

		return findMember;
	}

	// 입력 password 확인
	public void passwordConfirmer(LoginDto.PatchPassword request) {
		if (!Objects.equals(request.getNewPassword(), request.getNewPasswordCheck())) {
			throw new BusinessLogicException(ExceptionCode.PASSWORD_VALIDATION_FAILED);
		}
	}
}
