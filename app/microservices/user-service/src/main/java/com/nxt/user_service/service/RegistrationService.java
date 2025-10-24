package com.nxt.user_service.service;

import com.nxt.user_service.dto.CreateUserReqDTO;
import com.nxt.user_service.dto.CreateUserRespDTO;
import com.nxt.user_service.entity.User;
import com.nxt.user_service.exception.UserAlreadyExistsException;
import com.nxt.user_service.model.ResponseStatus;
import com.nxt.user_service.repo.UserRepository;
import com.nxt.user_service.util.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class RegistrationService {

    private final UserRepository repository;

    @Autowired
    public RegistrationService(UserRepository repository) {
        this.repository = repository;
    }

    public CreateUserRespDTO createUser(CreateUserReqDTO request) {
        String email = request.getEmail();
        if (repository.existsByEmail(email))
            throw new UserAlreadyExistsException("Email already registered");

        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String registrationType = request.getRegistrationType();
        Map<String, String> metadata = request.getMetadata();

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setRegistrationType(Util.fromString(registrationType));
        user.setMetadata(metadata);
        user = repository.save(user);

        CreateUserRespDTO response = new CreateUserRespDTO();
        response.setFirstName(firstName);
        response.setLastName(lastName);
        response.setUserId(user.getId());
        response.setResponseStatus(ResponseStatus.SUCCESSFUL);
        response.setEmail(email);
        response.setEmailVerified(false);
        response.setCreatedAt(new Date().toString());
        response.setRefreshToken("123");
        response.setAccessToken("abc");

        return response;
    }
}