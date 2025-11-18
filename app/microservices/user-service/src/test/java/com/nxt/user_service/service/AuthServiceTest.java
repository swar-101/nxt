package com.nxt.user_service.service;

import com.nxt.user_service.dto.LoginReqDTO;
import com.nxt.user_service.dto.LoginRespDTO;
import com.nxt.user_service.dto.TokenPair;
import com.nxt.user_service.entity.RefreshToken;
import com.nxt.user_service.entity.User;
import com.nxt.user_service.repo.RefreshTokenRepository;
import com.nxt.user_service.repo.UserRepository;
import com.nxt.user_service.service.token.JwtTokenService;
import com.nxt.user_service.service.token.RefreshTokenFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtTokenService jwtTokenService;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RefreshTokenFactory refreshTokenFactory;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(10L);
        user.setEmail("test@example.com");
        user.setPasswordHash("encoded-password");
    }

    @Test
    void shouldLoginSuccessfully() {
        // Given
        LoginReqDTO req = new LoginReqDTO();
        req.setEmail("test@example.com");
        req.setPassword("secret");

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("secret", "encoded-password"))
                .thenReturn(true);

        TokenPair tokens = new TokenPair("access-token", "refresh-token");
        when(jwtTokenService.generateTokens(user)).thenReturn(tokens);

        RefreshToken mockRT = new RefreshToken();
        mockRT.setUserId(10L);
        mockRT.setTokenHash("hashed");
        mockRT.setJti("jti-xyz");
        mockRT.setExpiresAt(Instant.now().plusSeconds(3600));

        when(refreshTokenFactory.fromRaw("refresh-token", 10L))
                .thenReturn(mockRT);

        when(refreshTokenRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // When
        LoginRespDTO resp = authService.login(req);

        // Then
        assertThat(resp).isNotNull();
        assertThat(resp.getUserId()).isEqualTo(10L);
        assertThat(resp.getAccessToken()).isEqualTo("access-token");
        assertThat(resp.getRefreshToken()).isEqualTo("refresh-token");
        assertThat(resp.getMessage()).isEqualTo("Login successful");

        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).matches("secret", "encoded-password");
        verify(jwtTokenService).generateTokens(user);
        verify(refreshTokenFactory).fromRaw("refresh-token", 10L);
        verify(refreshTokenRepository).save(any());
    }

    @Test
    void shouldFailLoginIfUserNotFound() {
        LoginReqDTO req = new LoginReqDTO();
        req.setEmail("missing@example.com");
        req.setPassword("pass");

        when(userRepository.findByEmail("missing@example.com"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid credentials");
    }

    @Test
    void shouldFailLoginIfPasswordDoesNotMatch() {
        LoginReqDTO req = new LoginReqDTO();
        req.setEmail("test@example.com");
        req.setPassword("wrong");

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrong", "encoded-password"))
                .thenReturn(false);

        assertThatThrownBy(() -> authService.login(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid credentials");
    }
}
