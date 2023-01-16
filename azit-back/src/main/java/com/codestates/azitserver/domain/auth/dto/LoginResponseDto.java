package com.codestates.azitserver.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String email;
    private String nickname;
    // private String profileUrl;
}
