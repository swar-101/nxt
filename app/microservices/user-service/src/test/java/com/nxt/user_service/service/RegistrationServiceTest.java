package com.nxt.user_service.service;

import com.nxt.user_service.dto.CreateUserReqDTO;
import com.nxt.user_service.dto.CreateUserRespDTO;
import com.nxt.user_service.entity.User;
import com.nxt.user_service.exception.UserAlreadyExistsException;
import com.nxt.user_service.model.RegistrationType;
import com.nxt.user_service.model.ResponseStatus;
import com.nxt.user_service.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegistrationService registrationService;

    private CreateUserReqDTO validRequest;

    @BeforeEach
    void setup() {
        validRequest = new CreateUserReqDTO();
        validRequest.setFirstName("John");
        validRequest.setLastName("Doe");
        validRequest.setEmail("john.doe@example.com");
        validRequest.setRegistrationType("GOOGLE");
    }

    @Test
    void shouldCreateUserSuccessfully() {
        when(userRepository.existsByEmail(validRequest.getEmail())).thenReturn(false);

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setFirstName("John");
        savedUser.setLastName("Doe");
        savedUser.setEmail("john.doe@example.com");
        savedUser.setRegistrationType(RegistrationType.GOOGLE);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        CreateUserRespDTO response = registrationService.createUser(validRequest);

        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo(1L);
        assertThat(response.getResponseStatus()).isEqualTo(ResponseStatus.SUCCESSFUL);

        verify(userRepository).existsByEmail("john.doe@example.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // given
        when(userRepository.existsByEmail(validRequest.getEmail())).thenReturn(true);

        // when / then
        assertThatThrownBy(() -> registrationService.createUser(validRequest))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("Email already registered");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionForInvalidRegistrationType() {
        // given
        validRequest.setRegistrationType("INVALID_TYPE");
        when(userRepository.existsByEmail(validRequest.getEmail())).thenReturn(false);

        // when / then
        assertThatThrownBy(() -> registrationService.createUser(validRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant");

        verify(userRepository, never()).save(any(User.class));
    }
}