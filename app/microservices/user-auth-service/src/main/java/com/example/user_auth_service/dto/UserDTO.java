package com.example.user_auth_service.dto;

import com.example.user_auth_service.entity.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO implements DTO {
    private String email;
    private Set<Role> roles = new HashSet<>();
}