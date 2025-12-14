package com.sweetshop.service;

import com.sweetshop.dto.AuthResponse;
import com.sweetshop.dto.RegisterRequest;
import com.sweetshop.model.User;
import com.sweetshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceTest.class);

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        // nothing special for now
    }

    @Test
    void register_shouldSaveUserAndReturnAuthResponse_whenEmailNotTaken() {
        RegisterRequest req = new RegisterRequest("Alice", "alice@example.com", "password123");

        when(userRepository.existsByEmail(req.getEmail())).thenReturn(false);

        User saved = new User("id-123", req.getName(), req.getEmail(), req.getPassword());
        when(userRepository.save(any(User.class))).thenReturn(saved);

        AuthResponse resp = authService.register(req);

        log.info("Register response: {}", resp);

        assertThat(resp).isNotNull();
        assertThat(resp.getId()).isEqualTo(saved.getId());
        assertThat(resp.getEmail()).isEqualTo(saved.getEmail());
        assertThat(resp.getMessage()).isEqualTo("registration successful");
        assertThat(resp.getToken()).isNotNull();

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(captor.capture());
        User toSave = captor.getValue();
        assertThat(toSave.getEmail()).isEqualTo(req.getEmail());
    }

    @Test
    void register_shouldReturnErrorMessage_whenEmailAlreadyExists() {
        RegisterRequest req = new RegisterRequest("Bob", "bob@example.com", "secret");

        when(userRepository.existsByEmail(req.getEmail())).thenReturn(true);

        AuthResponse resp = authService.register(req);

        log.info("Register (duplicate) response: {}", resp);

        assertThat(resp).isNotNull();
        assertThat(resp.getMessage()).isEqualTo("Email already in use");

        verify(userRepository, never()).save(any());
    }
}
