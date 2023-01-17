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
import com.codestates.azitserver.domain.common.CustomBeanUtils;
import com.codestates.azitserver.domain.fileInfo.entity.FileInfo;
import com.codestates.azitserver.domain.fileInfo.service.StorageService;
import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.entity.MemberCategory;
import com.codestates.azitserver.domain.member.repository.MemberRepository;
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

	//회원 생성
	public Member createMember(Member tempMember, MultipartFile profileImage, List<Long> categorySmallIdList) {
		// 닉네임 중복 확인
		verifyExistNickname(tempMember.getNickname());
		// 이메일 중복 확인
		verifyExistEmail(tempMember.getEmail());
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

		return profileImageCombiner(member.getMemberId(), profileImage);
	}

	public Member profileImageCombiner(Long memberId, MultipartFile profileImage) {
		Member member = getMemberById(memberId);

		String prefix = "images/member_profileImg";
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
	public Member patchMember(Member member) {

		// 기존멤버 찾아
		Member existingMember = findExistingMember(member.getMemberId());

		// 닉네임 변경하지 않는 경우( = 쓰던 닉네임 그대로 patch 요청 보내는 경우 닉중복첵을 하지않는다)
		if (!existingMember.getNickname().equals(member.getNickname())) {
			// 닉네임 변경하는 경우 닉네임 중복 확인
			verifyExistNickname(member.getNickname());
		}

		// 업데이트 해
		Member updatedMember = beanUtils.copyNonNullProperties(member, existingMember);

		// password 암호화
		String encryptedPassword = passwordEncoder.encode(updatedMember.getPassword());
		updatedMember.setPassword(encryptedPassword);
		return memberRepository.save(updatedMember);

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

	// 닉네임 중복 확인
	public void verifyExistNickname(String nickname) {
		Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
		if (optionalMember.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.NICKNAME_EXIST);
		}
	}

	public void verifyExistEmail(String email) {
		Optional<Member> optionalMember = memberRepository.findByEmail(email);
		if (optionalMember.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.EMAIL_EXIST);
		}
	}

	// 'password 한번 더' 절차(post)
	public void passwordConfirmer(MemberDto.Post memberPostDto) {
		if (!Objects.equals(memberPostDto.getPassword(), memberPostDto.getPasswordCheck())) {
			throw new BusinessLogicException(ExceptionCode.PASSWORD_VALIDATION_FAILED);
		}
	}

	// 'password 한번 더' 절차(patch)
	public void passwordConfirmer(MemberDto.Patch memberPatchDto) {
		if (!Objects.equals(memberPatchDto.getPassword(), memberPatchDto.getPasswordCheck())) {
			throw new BusinessLogicException(ExceptionCode.PASSWORD_VALIDATION_FAILED);
		}
	}

	public Member findExistingMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
	}

}
