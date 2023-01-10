package com.codestates.azitserver.domain.member.dto;

import com.codestates.azitserver.domain.member.entity.Member;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberDto {

    @Getter //인증 파트 테스트를 위해 회원 생성만 유효성 검증 적용
    public static class Post {
        @NotBlank(message = "이메일을 입력해 주세요")
        @Email(message = "유효한 이메일 형식이 아닙니다")
        private String email;

        @NotBlank(message = "별명을 입력해 주세요")
        private String nickname;

        @NotBlank(message = "비밀번호를 입력해 주세요")
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
    @Setter
    @AllArgsConstructor
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
