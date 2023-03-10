package com.codestates.azitserver.domain.member.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

	private final ClubRepository clubRepository;
	//?????? ??????
	public Member createMember(Member tempMember, MultipartFile profileImage, List<Long> categorySmallIdList) {
		// ????????? ?????? ??????
		verifyExistNickname(tempMember);
		// ????????? ?????? ??????
		verifyExistEmail(tempMember);
		// password ?????????
		String encryptedPassword = passwordEncoder.encode(tempMember.getPassword());

		// ???????????? ???????????? ?????????
		tempMember.setPassword(encryptedPassword);
		tempMember.setReputation(10);
		tempMember.setMemberStatus(Member.MemberStatus.ACTIVE);

		Member member = memberRepository.save(tempMember);

		List<MemberCategory> memberCategoryList = new ArrayList<>();

		// ???????????? ??????
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
		if (!profileImage.isEmpty() || profileImage != null) {
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

	//?????? ?????? ??????
	public Page<Member> getMembers(int page, int size) {
		return memberRepository.findAll(PageRequest.of(page - 1, size));

	}

	//1?????? ?????? ??????
	public Member getMemberById(Long memberId) {
		return findExistingMember(memberId);
	}

	//?????? ??????
	public Member patchMember(Member member, List<Long> categorySmallIdList) {

		// ???????????? ??????
		Member existingMember = findExistingMember(member.getMemberId());

		// ????????? ???????????? ?????? ??????( = ?????? ????????? ????????? patch ?????? ????????? ?????? ??????????????? ???????????????)
		if (!existingMember.getNickname().equals(member.getNickname())) {
			// ????????? ???????????? ?????? ????????? ?????? ??????
			verifyExistNickname(member);
		}

		// ???????????? ???
		Member updatedMember = beanUtils.copyNonNullProperties(member, existingMember);
		updatedMember.setMemberCategoryList(null);

		// // password ?????????
		// String encryptedPassword = passwordEncoder.encode(updatedMember.getPassword());
		// updatedMember.setPassword(encryptedPassword);

		// MemberCategory ?????? ????????? ????????? ??????
		memberCategoryRepository.deleteAllByMember(member);

		// ???????????? ??????
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

	//????????? ?????? ??????
	public Member updateMemberImage(Long memberId, MultipartFile profileImage) {
		Member member = getMemberById(memberId);

		// banner image ??????
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

	// ?????? ??????(??????)
	public Member deleteMember(Long memberId) {
		Member member = findExistingMember(memberId);
		member.setMemberStatus(Member.MemberStatus.DELETED);
		return memberRepository.save(member);
	}


	//?????????, ????????????
	public Member followMember(Member member) {
		return null; //TODO
	}

	//?????? ??????
	public Member reportMember(Member member) {
		return null; //TODO
	}

	//?????? ??????
	public Member blockMember(Member member) {
		return null; //TODO
	}

	// ????????? ?????? ?????? when sign-up
	public void verifyExistNickname(Member member) {
		String nickname = member.getNickname();
		Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
		if (optionalMember.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.NICKNAME_EXIST_SIGNUP);
		}
	}


	// ????????? ?????? ?????? when sign-up
	public void verifyExistEmail(Member member) {
		String email = member.getEmail();
		Optional<Member> optionalMember = memberRepository.findByEmail(email);
		if (optionalMember.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.EMAIL_EXIST_SIGNUP);
		}
	}

	// ???, ????????? ???????????????
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

	// 'password ?????? ???' ??????(post)
	public void passwordConfirmer(MemberDto.Post memberPostDto) {
		if (!Objects.equals(memberPostDto.getPassword(), memberPostDto.getPasswordCheck())) {
			throw new BusinessLogicException(ExceptionCode.PASSWORD_VALIDATION_FAILED);
		}
	}

	// 'password ?????? ???' ??????(patch)
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
			case 0:
				break;
			case 1:
				status = ClubMember.ClubMemberStatus.CLUB_JOINED;
				break;
			case 3:
				status = ClubMember.ClubMemberStatus.CLUB_REJECTED;
				break;
			case 4:
				status = ClubMember.ClubMemberStatus.CLUB_KICKED;
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
			// ======Club Info ??? ???????????? ?????? ??????=======
			List<ClubMember> preParticipants = club.getClubMembers();
			List<MemberDto.ParticipantResponse> participantResponses = new ArrayList<>();
			for (ClubMember pre : preParticipants) {
				if (pre.getClubMemberStatus() == ClubMember.ClubMemberStatus.CLUB_JOINED) {
					MemberDto.ParticipantResponse
						preResponse = memberMapper.memberToParticipantResponse(pre.getMember());
						preResponse.setClubMemberId(pre.getClubMemberId());
					participantResponses.add(preResponse);
				}
			}
			clubInfoResponse.setParticipantList(participantResponses);
			// ============????????? ???????????? ???=============

			response.setClubInfoResponse(clubInfoResponse);

			// Host ??????
			response.setIsHost(clubMember.getClub().getHost().getMemberId() == clubMember.getMember().getMemberId());
			// ??????????????????
			response.setIsHidden(false);
			// ?????? ?????? ?????????
			Member member = clubMember.getMember();
			List<Review> reviewList = reviewRepository.findAllReviewsByReviewerAndClub(member, club);
			if (reviewList != null && !reviewList.isEmpty()) {
				response.setIsReviewed(true);
			} else
				response.setIsReviewed(false);

			clubMemberStatusResponseList.add(response);
		}

		return clubMemberStatusResponseList;
	}
	public List<ClubMemberDto.ClubMemberStatusResponse>
	responseSorter(List<ClubMemberDto.ClubMemberStatusResponse> responses) {
		Collections.sort(responses, new MeetingTimeComparator().reversed());
		for (ClubMemberDto.ClubMemberStatusResponse response : responses) {
			List<MemberDto.ParticipantResponse> list = response.getClubInfoResponse().getParticipantList();
			Collections.sort(list, new ParticipationTimeComparator());
			ClubDto.ClubMemberStatusClubInfoResponse infoResponse = response.getClubInfoResponse();
			infoResponse.setParticipantList(list);
			response.setClubInfoResponse(infoResponse);
		}
		return responses;
	}
	public class ParticipationTimeComparator implements Comparator<MemberDto.ParticipantResponse> {
		@Override
		public int compare(MemberDto.ParticipantResponse response1, MemberDto.ParticipantResponse response2) {
			return Integer.valueOf(response1.getClubMemberId().compareTo(response2.getClubMemberId()));
		};
	}

	public class MeetingTimeComparator implements Comparator<ClubMemberDto.ClubMemberStatusResponse> {
		@Override
		public int compare(ClubMemberDto.ClubMemberStatusResponse response1,
			ClubMemberDto.ClubMemberStatusResponse response2) {
			return Integer.valueOf(getTime(response1).compareTo(getTime(response2)));
		}
	}

	public LocalDateTime getTime(ClubMemberDto.ClubMemberStatusResponse response) {
		LocalDate meetingDate = response.getClubInfoResponse().getMeetingDate();
		LocalTime meetingTime = response.getClubInfoResponse().getMeetingTime();

		return LocalDateTime.of(meetingDate, meetingTime);
	}
}
