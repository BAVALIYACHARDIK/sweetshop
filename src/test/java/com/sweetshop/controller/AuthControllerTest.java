package com.sweetshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweetshop.dto.AuthResponse;
import com.sweetshop.dto.RegisterRequest;
import com.sweetshop.service.AuthService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Clean, focused controller test for `AuthController`.
 * - Uses @WebMvcTest to load the controller slice
 * - Mocks the `AuthService` so tests are fast and deterministic
 */
@WebMvcTest(AuthController.class)
@Import(com.sweetshop.config.SecurityConfig.class)
public class AuthControllerTest {
    private static final Logger log = LoggerFactory.getLogger(AuthControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    void postRegister_shouldReturnOkAndAuthResponse() throws Exception {
        RegisterRequest req = new RegisterRequest("Charlie", "charlie@example.com", "pwd");

        AuthResponse resp = new AuthResponse("id-1", "Charlie", "charlie@example.com", "pwd", "token-xyz",
                "registration successful");

        when(authService.register(any(RegisterRequest.class))).thenReturn(resp);

        String json = objectMapper.writeValueAsString(req);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(resp.getId()))
                .andExpect(jsonPath("$.email").value(resp.getEmail()));

        log.info("Performed POST /api/auth/register with payload: {}", json);
    }

    @Test
    void postRegister_shouldReturnBadRequest_whenBodyMissing() throws Exception {
        // send empty body => controller should respond 400 (malformed json / validation
        // not present but empty body is invalid)
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
