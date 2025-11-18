package com.nxt.user_service.service;

import com.nxt.user_service.dto.LoginReqDTO;
import com.nxt.user_service.dto.LoginRespDTO;
import com.nxt.user_service.dto.TokenPair;
import com.nxt.user_service.entity.User;
import com.nxt.user_service.exception.InvalidCredentialsException;
import com.nxt.user_service.exception.UserNotFoundException;
import com.nxt.user_service.repo.RefreshTokenRepository;
import com.nxt.user_service.repo.UserRepository;
import com.nxt.user_service.service.token.JwtTokenService;
import com.nxt.user_service.service.token.RefreshTokenFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenFactory refreshTokenFactory;

    @Autowired
    public AuthService(UserRepository userRepository,
                       JwtTokenService jwtTokenService,
                       RefreshTokenRepository refreshTokenRepository,
                       PasswordEncoder passwordEncoder,
                       RefreshTokenFactory refreshTokenFactory) {

        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenFactory = refreshTokenFactory;
    }

    public LoginRespDTO login(LoginReqDTO req) {
        log.info("Login attempt email={}", req.getEmail());

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> {
                    log.warn("Login failed: email={} reason=user not found", req.getEmail());
                    return new UserNotFoundException("User not found with email: " + req.getEmail());
                });

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            log.warn("Login failed: userId={} reason=bad password", user.getId());
            throw new InvalidCredentialsException("Password mismatch for userId: " + user.getId());
        }

        TokenPair tokens = jwtTokenService.generateTokens(user);
        var rt = refreshTokenFactory.fromRaw(tokens.refreshToken(), user.getId());
        refreshTokenRepository.save(rt);

        LoginRespDTO resp = new LoginRespDTO();
        resp.setUserId(user.getId());
        resp.setAccessToken(tokens.accessToken());
        resp.setRefreshToken(tokens.refreshToken());
        resp.setMessage("Login successful");


        return resp;
    }

    public LoginRespDTO refresh(String refreshToken) {
        var decoded = jwtTokenService.decode(refreshToken);
        Long userId = Long.parseLong(decoded.getSubject());

        User user = userRepository.findById(userId).orElseThrow();

        TokenPair newTokens = jwtTokenService.generateTokens(user);

        refreshTokenRepository.save(refreshTokenFactory.fromRaw(newTokens.refreshToken(), user.getId()));

        LoginRespDTO resp = new LoginRespDTO();
        resp.setUserId(user.getId());
        resp.setAccessToken(newTokens.refreshToken());
        resp.setMessage("Token refreshed");
        return resp;
    }
}