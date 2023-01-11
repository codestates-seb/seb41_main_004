package com.codestates.azitserver.domain.member.dto;

import com.codestates.azitserver.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberDto {

    @Getter //인증 파트 테스트를 위해 회원 생성만 유효성 검증 적용
    public static class Post {
        @NotBlank(message = "이메일을 입력해 주세요")
        @Email(message = "유효한 이메일 형식이 아닙니다")
        private String email;

        @NotBlank(message = "별명을 입력해 주세요")
        @Length(min = 2, max = 8, message = "닉네임은 2글자 이상 8글자 이하여야 합니다")
        private String nickname;

        @NotBlank(message = "비밀번호를 입력해 주세요")
        /* 비밀번호 유효성 일단 제외해놨습니다
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
                message = "비밀번호는 최소 1개의 문자와 1개의 숫자를 포함하여 최소 8자 이상어야 합니다.")
         */
        private String password;

        @NotBlank(message = "비밀번호를 한번 더 입력해 주세요")
        private String passwordCheck;

        @NotBlank(message = "성별을 선택해 주세요")
        // 이부분 사용자가 직접 타이핑하는게 아니라 선택해서 저절로 입력되는거라
        // 유효성 검증 적용 안해도 될까요?
        private Member.Gender gender;

        @Length(min = 4, max = 4, message = "생년월일은 4자리 숫자여야 합니다")
        @Pattern(regexp = "\\d{4}", message = "생년월일은 4자리 숫자여야 합니다")
        private String birthYear;

        @Length(max = 128, message = "자기소개는 128자 이하여야 합니다")
        private String aboutMe;

    }

    @Getter
    public static class Patch {
        @NotBlank(message = "이메일을 입력해 주세요")
        @Email(message = "유효한 이메일 형식이 아닙니다")
        private String email;

        @NotBlank(message = "별명을 입력해 주세요")
        @Length(min = 2, max = 16, message = "닉네임은 2글자 이상 16글자 이하여야 합니다")
        private String nickname;

        @NotBlank(message = "비밀번호를 입력해 주세요")
        /* 비밀번호 유효성 일단 제외해놨습니다
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
                message = "비밀번호는 최소 1개의 문자와 1개의 숫자를 포함하여 최소 8자 이상어야 합니다.")
         */
        private String password;

        @NotBlank(message = "비밀번호 확인")
        private String passwordCheck;

        @Length(max = 128, message = "자기소개는 128자 이하여야 합니다")
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
        private String birthYear;
        private String aboutMe;
        private Integer reputation;
        private Member.MemberStatus memberStatus;
    }
}
