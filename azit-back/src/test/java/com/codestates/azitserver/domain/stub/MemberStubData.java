package com.codestates.azitserver.domain.stub;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.codestates.azitserver.domain.category.entity.CategorySmall;
import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.entity.MemberCategory;

public class MemberStubData {

    /*등산 7 헬스 8 와인 13*/
    public static Member stubMember() {
        Member member = tempStubMember();
        member.setMemberCategoryList(
            List.of(tempMemberCategory(1L, tempStubMember(),7L),
                tempMemberCategory(1L, tempStubMember(),13L)));
        return member;
    }
    public static Member tempStubMember() {
        Member tempStubMember = Member.builder()
            .memberId(1L)
            .fileInfo(FileInfoStubData.getDefaultFileInfo())
            .email("stubmember@naver.com")
            .nickname("김스텁")
            .password("123456@asdf")
            .gender(Member.Gender.MALE)
            .birthYear("2001")
            .aboutMe("김스텁의 자기소개")
            .reputation(10)
            .memberStatus(Member.MemberStatus.ACTIVE)
            .build();

        return tempStubMember;
    }
    public static CategorySmall tempCategorySmall(Long categorySmallId) {
        CategorySmall categorySmall = new CategorySmall();
        categorySmall.setCategorySmallId(categorySmallId);
        return categorySmall;
    }
    public static MemberCategory tempMemberCategory(Long memberCategoryId, Member member, Long categorySmallId) {
        MemberCategory memberCategory =
            MemberCategory.builder()
                .memberCategoryId(memberCategoryId)
                .member(tempStubMember())
                .categorySmall(tempCategorySmall(categorySmallId))
                .build();
        return memberCategory;
    }

    public static MemberDto.Post stubMemberDtoPost() {
        MemberDto.Post post = new MemberDto.Post();

        post.setEmail("stubmember@naver.com");
        post.setNickname("김스텁");
        post.setPassword("123456@asdf");
        post.setPasswordCheck("123456@asdf");
        post.setGender(Member.Gender.MALE);
        post.setBirthYear("2001");
        post.setAboutMe("김스텁의 자기소개");
        post.setCategorySmallId(List.of(8L,13L));
        return post;
    }

    public static MemberDto.Patch stubMemberDtoPatch() {
        MemberDto.Patch patch = new MemberDto.Patch();

        patch.setNickname("스텁김");
        // patch.setPassword("654321@asdf");
        // patch.setPasswordCheck("654321@asdf");
        patch.setAboutMe("김스텁의 자기소개");
        patch.setCategorySmallId(List.of(7L,13L));

        return patch;
    }

    public static MemberDto.Response stubMemberDtoResponse() {
        Member member = tempStubMember();
        MemberDto.Response response = new MemberDto.Response();

        response.setMemberId(1L);
        response.setFileInfo(FileInfoStubData.getDefaultFileInfo());
        response.setEmail("stubmember@naver.com");
        response.setNickname("김스텁");
        // response.setPassword("123456@asdf");
        response.setGender(Member.Gender.MALE);
        response.setBirthYear("2001");
        response.setAboutMe("김스텁의 자기소개");
        response.setReputation(10);
        response.setMemberStatus(Member.MemberStatus.ACTIVE);
        response.setCategorySmallIdList(
            List.of(8L, 13L)
        );

        return response;
    }

    public static Page<Member> stubMemberPage() {
        return new PageImpl<>(List.of(stubMember()), PageRequest.of(0, 10), 1);
    }

    /**
     * 회원 id와 nickname만 반환하는 stub data
     * @return 회원 id와 nickname만 들어있는 MemberDto.Response
     * @author cryoon
     */
    public static MemberDto.ResponseEmailAndNickname stubMemberIdAndNickname() {
        MemberDto.ResponseEmailAndNickname response = new MemberDto.ResponseEmailAndNickname();

        response.setMemberId(1L);
        response.setEmail("hello@hello.com");
        response.setNickname("cryoon");
        response.setFileInfo(FileInfoStubData.getFileInfoDtoResponse());

        return response;
    }

    public static MockMultipartFile getMultipartJsonData(String content) {
        return new MockMultipartFile(
            "data",
            "",
            MediaType.APPLICATION_JSON_VALUE,
            content.getBytes()
        );
    }

    public static MemberDto.ClubMemberMemberResponse getClubMemberMemberResponse() {
        MemberDto.ClubMemberMemberResponse response = new MemberDto.ClubMemberMemberResponse();

        response.setMemberId(1L);
        response.setFileInfo(FileInfoStubData.getFileInfoDtoResponse());
        response.setEmail("stubmember@naver.com");
        response.setNickname("김스텁");

        return response;
    }
}
