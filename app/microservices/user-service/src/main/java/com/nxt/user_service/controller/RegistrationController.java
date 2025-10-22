package com.nxt.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class RegistrationController {
    // new users to create an account using their email or social media profiles

    @PostMapping
    public ResponseEntity<CreateUserRespDTO> createUser(CreateUserReqDTO request) {}

}
