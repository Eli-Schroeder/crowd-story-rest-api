package com.storyblocks.storyblocksservice.users;

import com.storyblocks.storyblocksservice.users.RegistrationRequest;
import com.storyblocks.storyblocksservice.users.ConfirmationToken;
import com.storyblocks.storyblocksservice.users.User;
import com.storyblocks.storyblocksservice.users.ConfirmationTokenService;
import com.storyblocks.storyblocksservice.users.JpaUserDetailsManager;
import com.storyblocks.storyblocksservice.users.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTests {

    @Autowired
    @InjectMocks
    private RegistrationService service;

    @Mock
    private JpaUserDetailsManager userService;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    private RegistrationRequest registrationRequest;

    private RegistrationRequest registrationRequestInvalidEmail;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private User user;

    @BeforeEach
    void setup(){
        registrationRequest = new RegistrationRequest("felix123", "felix@abc.com", "Felix", "superSecurePassword");
        registrationRequestInvalidEmail = new RegistrationRequest("felix123", "this is an invalid email address", "Felix", "superSecurePassword");
        user = new User();
        user.setUsername("Felix");
        user.setPassword("password");
        user.setEmail("felix@abc.com");
        user.setEnabled(false);
        LocalDateTime mockNow = LocalDateTime.of(2020, 12, 1, 0, 0);
        try(MockedStatic<LocalDateTime> mockedLocalDateTime = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS)){
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(mockNow);
        }
    }

    @Test
    void register() {
        when(userService.signUpUser(userCaptor.capture())).thenReturn("thisisatoken");
        String token = service.register(registrationRequest);
        User created = userCaptor.getValue();
        assertEquals("thisisatoken", token);
        assertEquals("felix123", created.getUsername());
        assertEquals("felix@abc.com", created.getEmail());
        assertEquals("Felix", created.getDisplayName());
        assertEquals("superSecurePassword", created.getPassword());
    }

    @Test
    void registerInvalidEmail() {
        assertThrows(IllegalStateException.class, () -> service.register(registrationRequestInvalidEmail));
    }

    @Test
    void confirmToken() {
        ConfirmationToken token = new ConfirmationToken("token",
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.of(2030, 1, 12, 0, 0),
                user);
        when(confirmationTokenService.getToken("token")).thenReturn(token);
        service.confirmToken(token.getToken());
        verify(confirmationTokenService).setConfirmedAt("token");
        verify(userService).enableUser(user);
    }

    @Test
    void confirmTokenExpired() {
        ConfirmationToken token = new ConfirmationToken("token",
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.of(2010, 1, 12, 0, 0),
                user);
        when(confirmationTokenService.getToken("token")).thenReturn(token);
        assertThrows(IllegalStateException.class, () -> service.confirmToken("token"));
    }

    @Test
    void confirmTokenAlreadyConfirmed() {
        ConfirmationToken token = new ConfirmationToken("token",
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.of(2030, 1, 12, 0, 0),
                user);
        token.setConfirmedAt(LocalDateTime.of(2010, 1, 12, 0, 0));
        when(confirmationTokenService.getToken("token")).thenReturn(token);
        assertThrows(IllegalStateException.class, () -> service.confirmToken("token"));
    }
}
