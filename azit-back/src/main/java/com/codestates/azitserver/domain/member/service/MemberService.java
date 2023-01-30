package com.codestates.azitserver.domain.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codestates.azitserver.domain.category.entity.CategorySmall;
import com.codestates.azitserver.domain.category.service.CategoryService;
import com.codestates.azitserver.domain.club.dto.ClubDto;
import com.codestates.azitserver.domain.club.dto.ClubMemberDto;
import com.codestates.azitserver.domain.club.entity.Club;
import com.codestates.azitserver.domain.club.entity.ClubMember;
import com.codestates.azitserver.domain.club.mapper.ClubMapper;
import com.codestates.azitserver.domain.club.mapper.ClubMemberMapper;
import com.codestates.azitserver.domain.club.repository.ClubMemberRepository;
import com.codestates.azitserver.domain.club.repository.ClubRepository;
import com.codestates.azitserver.domain.club.service.ClubService;
import com.codestates.azitserver.domain.common.CustomBeanUtils;
import com.codestates.azitserver.domain.fileInfo.entity.FileInfo;
import com.codestates.azitserver.domain.fileInfo.service.StorageService;
import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.entity.MemberCategory;
import com.codestates.azitserver.domain.member.mapper.MemberMapper;
import com.codestates.azitserver.domain.member.repository.MemberCategoryRepository;
import com.codestates.azitserver.domain.member.repository.MemberRepository;
import com.codestates.azitserver.domain.review.entity.Review;
import com.codestates.azitserver.domain.review.repository.ReviewRepository;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final CustomBeanUtils<Member> beanUtils;
	private final StorageService storageService;
	private final CategoryService categoryService;

	private final MemberCategoryRepository memberCategoryRepository;

	private final ClubMemberMapper clubMemberMapper;
	private final ReviewRepository reviewRepository;

	private final ClubMapper clubMapper;
	private final MemberMapper memberMapper;
	//회원 생성
	public Member createMember(Member tempMember, MultipartFile profileImage, List<Long> categorySmallIdList) {
		// 닉네임 중복 확인
		verifyExistNickname(tempMember);
		// 이메일 중복 확인
		verifyExistEmail(tempMember);
		// password 암호화
		String encryptedPassword = passwordEncoder.encode(tempMember.getPassword());

		// 자동으로 지정되는 정보들
		tempMember.setPassword(encryptedPassword);
		tempMember.setReputation(10);
		tempMember.setMemberStatus(Member.MemberStatus.ACTIVE);

		Member member = memberRepository.save(tempMember);

		List<MemberCategory> memberCategoryList = new ArrayList<>();

		// 카테고리 넣기
		if (categorySmallIdList != null) {
			List<CategorySmall> collectedCategorySmalls = categorySmallIdList.stream()
				.map(categoryService::findCategorySmallById).collect(Collectors.toList());

			for (CategorySmall categorySmall : collectedCategorySmalls) {
				memberCategoryList.add(MemberCategory.builder()
					.categorySmall(categorySmall)
					.member(member)
					.build());
			}
			member.addMemberCategorySmallList(memberCategoryList);
		}

		return profileImageCombiner(member, profileImage);
	}

	public Member profileImageCombiner(Member member, MultipartFile profileImage) {


		String prefix = "/images/member_profileImg";
		if (!profileImage.isEmpty()) {
			Map<String, String> map = storageService.upload(prefix, profileImage);

			FileInfo fileInfo = new FileInfo();
			fileInfo.setFileName(map.get("fileName"));
			fileInfo.setFileUrl(map.get("fileUrl"));

			member.setFileInfo(fileInfo);
		} else {
			FileInfo fileInfo = new FileInfo();
			fileInfo.setFileName("defaultProfileImageName");
			fileInfo.setFileUrl("defaultProfileImageUrl");

			member.setFileInfo(fileInfo);

		}
		return memberRepository.save(member);
	}

	//전체 회원 조회
	public Page<Member> getMembers(int page, int size) {
		return memberRepository.findAll(PageRequest.of(page - 1, size));

	}

	//1명의 회원 조회
	public Member getMemberById(Long memberId) {
		return findExistingMember(memberId);
	}

	//회원 수정
	public Member patchMember(Member member, List<Long> categorySmallIdList) {

		// 기존멤버 찾아
		Member existingMember = findExistingMember(member.getMemberId());

		// 닉네임 변경하지 않는 경우( = 쓰던 닉네임 그대로 patch 요청 보내는 경우 닉중복첵을 하지않는다)
		if (!existingMember.getNickname().equals(member.getNickname())) {
			// 닉네임 변경하는 경우 닉네임 중복 확인
			verifyExistNickname(member);
		}

		// 업데이트 해
		Member updatedMember = beanUtils.copyNonNullProperties(member, existingMember);
		updatedMember.setMemberCategoryList(null);

		// // password 암호화
		// String encryptedPassword = passwordEncoder.encode(updatedMember.getPassword());
		// updatedMember.setPassword(encryptedPassword);

		// MemberCategory 기존 데이터 테이블 삭제
		memberCategoryRepository.deleteAllByMember(member);

		// 카테고리 넣기
		List<MemberCategory> memberCategoryList = new ArrayList<>();

		if (categorySmallIdList != null) {
			List<CategorySmall> collectedCategorySmalls = categorySmallIdList.stream()
				.map(categoryService::findCategorySmallById).collect(Collectors.toList());

			for (CategorySmall categorySmall : collectedCategorySmalls) {
				memberCategoryList.add(MemberCategory.builder()
					.categorySmall(categorySmall)
					.member(updatedMember)
					.build());
			}
			updatedMember.setMemberCategoryList(memberCategoryList);
		}
		return memberRepository.save(updatedMember);

	}

	//프로필 사진 수정
	public Member updateMemberImage(Long memberId, MultipartFile profileImage) {
		Member member = getMemberById(memberId);

		// banner image 저장
		String prefix = "/images/member_profileImg";
		if (!profileImage.isEmpty()) {
			Map<String, String> map = storageService.upload(prefix, profileImage);

			FileInfo fileInfo = new FileInfo();
			fileInfo.setFileName(map.get("fileName"));
			fileInfo.setFileUrl(map.get("fileUrl"));

			member.setFileInfo(fileInfo);
		} else {
			FileInfo fileInfo = new FileInfo();
			fileInfo.setFileName("defaultProfileImageName");
			fileInfo.setFileUrl("defaultProfileImageUrl");

			member.setFileInfo(fileInfo);
		}
		return memberRepository.save(member);
	}

	// 회원 삭제(탈퇴)
	public Member deleteMember(Long memberId) {
		Member member = findExistingMember(memberId);
		member.setMemberStatus(Member.MemberStatus.DELETED);
		return memberRepository.save(member);
	}


	//팔로우, 언팔로우
	public Member followMember(Member member) {
		return null; //TODO
	}

	//회원 신고
	public Member reportMember(Member member) {
		return null; //TODO
	}

	//회원 차단
	public Member blockMember(Member member) {
		return null; //TODO
	}

	// 닉네임 중복 확인 when sign-up
	public void verifyExistNickname(Member member) {
		String nickname = member.getNickname();
		Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
		if (optionalMember.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.NICKNAME_EXIST_SIGNUP);
		}
	}


	// 닉네임 중복 확인 when sign-up
	public void verifyExistEmail(Member member) {
		String email = member.getEmail();
		Optional<Member> optionalMember = memberRepository.findByEmail(email);
		if (optionalMember.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.EMAIL_EXIST_SIGNUP);
		}
	}

	// 닉, 이메일 중복검사만
	public void justVerifyExistNicknameAndEmail(String nickname, String email) {
		if (nickname == null && email == null) {
			throw new BusinessLogicException(ExceptionCode.NO_TARGET_TO_CHECK);
		}

		Optional<Member> optionalMemberNickname = memberRepository.findByNickname(nickname);
		if (optionalMemberNickname.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.NICKNAME_EXIST_CHECK_ONLY);
		}
		Optional<Member> optionalMemberEmail = memberRepository.findByEmail(email);
		if (optionalMemberEmail.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.EMAIL_EXIST_CHECK_ONLY);
		}
	}

	// 'password 한번 더' 절차(post)
	public void passwordConfirmer(MemberDto.Post memberPostDto) {
		if (!Objects.equals(memberPostDto.getPassword(), memberPostDto.getPasswordCheck())) {
			throw new BusinessLogicException(ExceptionCode.PASSWORD_VALIDATION_FAILED);
		}
	}

	// 'password 한번 더' 절차(patch)
	// public void passwordConfirmer(MemberDto.Patch memberPatchDto) {
	// 	if (!Objects.equals(memberPatchDto.getPassword(), memberPatchDto.getPasswordCheck())) {
	// 		throw new BusinessLogicException(ExceptionCode.PASSWORD_VALIDATION_FAILED);
	// 	}
	// }

	public Member findExistingMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
	}

	public ClubMember.ClubMemberStatus numberToStatus(int clubMemberStatusNumber) {
		if (clubMemberStatusNumber > 4 || clubMemberStatusNumber < 0) {
			throw new BusinessLogicException(ExceptionCode.INVALID_CLUB_MEMBER_STATUS);
		}
		ClubMember.ClubMemberStatus status = ClubMember.ClubMemberStatus.CLUB_WAITING;
		switch (clubMemberStatusNumber) {
			case 0 : break;
			case 1 : status = ClubMember.ClubMemberStatus.CLUB_JOINED;
					break;
			case 3 : status = ClubMember.ClubMemberStatus.CLUB_REJECTED;
					break;
			case 4 : status = ClubMember.ClubMemberStatus.CLUB_KICKED;
					break;

		}
		return status;
	}



	public List<ClubMemberDto.ClubMemberStatusResponse>
	responseWithInfoGenerator(List<ClubMember> clubMemberList) {
		List<ClubMemberDto.ClubMemberStatusResponse> clubMemberStatusResponseList = new ArrayList<>();
		for (ClubMember clubMember : clubMemberList) {
			ClubMemberDto.ClubMemberStatusResponse response =
				clubMemberMapper.clubMemberToClubMemberDtoClubMemberStatusResponse(clubMember);
			Club club = clubMember.getClub();
			// Club Info
			ClubDto.ClubMemberStatusClubInfoResponse clubInfoResponse
				= clubMapper.clubToClubMemberStatusClubInfoResponse(club);
				// ======Club Info 에 참여자들 정보 넣기=======
			List<ClubMember> preParticipants = club.getClubMembers();
			List<MemberDto.ParticipantResponse> participantResponses= new ArrayList<>();
			for (ClubMember pre : preParticipants) {
				if (pre.getClubMemberStatus() == ClubMember.ClubMemberStatus.CLUB_JOINED) {
					participantResponses.add(memberMapper.memberToParticipantResponse(pre.getMember()));
				}
			}
			clubInfoResponse.setParticipantList(participantResponses);
				// ============참여자 정보넣기 끗=============

			response.setClubInfoResponse(clubInfoResponse);

			// Host 여부
			response.setIsHost(clubMember.getClub().getHost().getMemberId()==clubMember.getMember().getMemberId());
			// 숨김상태인지
			response.setIsHidden(false);
			// 리뷰 작성 했는지
			Member member = clubMember.getMember();
			List<Review> reviewList = reviewRepository.findAllReviewsByReviewerAndClub(member, club);
			if ( reviewList != null && !reviewList.isEmpty()) {
				response.setIsReviewed(true);
			}
			else response.setIsReviewed(false);

			clubMemberStatusResponseList.add(response);
		}
		return clubMemberStatusResponseList;
	}


}
