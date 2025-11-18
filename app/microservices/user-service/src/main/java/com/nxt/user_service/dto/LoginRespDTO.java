package com.nxt.user_service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LoginRespDTO {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private String message;
}
