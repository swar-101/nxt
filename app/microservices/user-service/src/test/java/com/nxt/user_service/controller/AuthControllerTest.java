package com.nxt.user_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nxt.user_service.config.TestSecurityConfig;
import com.nxt.user_service.dto.LoginReqDTO;
import com.nxt.user_service.dto.LoginRespDTO;
import com.nxt.user_service.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = AuthController.class,
        useDefaultFilters = false
)
@Import({
        AuthController.class,
        TestSecurityConfig.class
})
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldLoginSuccessfully() throws Exception {

        LoginRespDTO mockResp = new LoginRespDTO();
        mockResp.setUserId(10L);
        mockResp.setAccessToken("access-token-123");
        mockResp.setRefreshToken("refresh-token-456");
        mockResp.setMessage("Login successful");

        Mockito.when(authService.login(any(LoginReqDTO.class)))
                .thenReturn(mockResp);

        String req = """
                    { "email": "john@example.com", "password": "pass123" }
                """;

        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(req)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(10L));
    }

    @Test
    void shouldRefreshSuccessfully() throws Exception {

        LoginRespDTO mockResp = new LoginRespDTO();
        mockResp.setUserId(10L);
        mockResp.setAccessToken("new-access");
        mockResp.setRefreshToken("new-refresh");
        mockResp.setMessage("Token refreshed");

        Mockito.when(authService.refresh("valid-refresh"))
                .thenReturn(mockResp);

        mockMvc.perform(
                        post("/api/v1/auth/refresh")
                                .param("refreshToken", "valid-refresh")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("new-access"));
    }
}
