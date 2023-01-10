package com.codestates.azitserver.domain.member.dto;

import com.codestates.azitserver.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class MemberDto {

    @Getter
    public static class Post {
        private String email;
        private String nickname;
        private String password;
        private int birthYear;
        private String aboutMe;

    }

    @Getter
    public static class Patch {
        private String email;
        private String nickname;
        private String password;
        private String aboutMe;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long memberId;
        private Long avatar_image_id;
        private String email;
        private String nickname;
        private String password;
        private Member.Gender gender;
        private int birthYear;
        private String aboutMe;
        private int reputation;
        private Member.MemberStatus memberStatus;
    }
}
