package com.nxt.user_service.controller;

import com.nxt.user_service.dto.LoginReqDTO;
import com.nxt.user_service.dto.LoginRespDTO;
import com.nxt.user_service.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger apiLogger = LoggerFactory.getLogger("API_LOGGER");

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRespDTO> login(@RequestBody @Valid LoginReqDTO req) {
        long start = System.currentTimeMillis();

        apiLogger.info("Login start | email={}", req.getEmail());

        LoginRespDTO resp = authService.login(req);

        long end = System.currentTimeMillis();
        apiLogger.info("Login success | userId={} | timeMs={}", resp.getUserId(), (end - start));

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginRespDTO> refresh(@RequestParam("refreshToken") String refreshToken) {
        long start = System.currentTimeMillis();

        String tokenPrefix;
        if (refreshToken == null || refreshToken.isBlank()) {
            tokenPrefix = "<empty>";
        } else {
            int prefixLen = Math.min(6, refreshToken.length());
            tokenPrefix = refreshToken.substring(0, prefixLen);
        }

        apiLogger.info("RefreshToken start | tokenHash={}...", tokenPrefix);

        LoginRespDTO resp = authService.refresh(refreshToken);
        long end = System.currentTimeMillis();
        apiLogger.info("RefreshToken success | userId={} | timeMs={}", resp.getUserId(), (start - end));

        return ResponseEntity.ok(resp);
    }
}