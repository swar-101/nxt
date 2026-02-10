package com.nxt.user_service.service.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailSender {

    public void sendPasswordReset(String email, String resetLink) {
        log.info("Sending request for Password Reset Link {} for User with Email {} ", resetLink, email);
    }
}
