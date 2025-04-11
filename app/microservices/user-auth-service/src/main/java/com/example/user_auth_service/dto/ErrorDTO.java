package com.example.user_auth_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO implements DTO {
    private String code;
    private String message;
}
