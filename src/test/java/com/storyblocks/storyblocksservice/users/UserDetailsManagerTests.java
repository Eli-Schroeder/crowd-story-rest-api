package com.storyblocks.storyblocksservice.users;

import com.storyblocks.storyblocksservice.users.registration.ConfirmationToken;
import com.storyblocks.storyblocksservice.users.registration.ConfirmationTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsManagerTests {

    @Autowired
    @InjectMocks
    private JpaUserDetailsManager service;

    @Mock
    private UsersRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @Captor
    private ArgumentCaptor<ConfirmationToken> tokenCaptor;

    private User user;

    @BeforeEach
    void setup(){
        user = new User();
        user.setUsername("Felix");
        user.setPassword("password");
        user.setEmail("felix@abc.com");
        user.setEnabled(false);
    }

    @Test
    void loadUserByUsername() {
        when(repository.findByUsername("Felix")).thenReturn(Optional.of(user));
        User retrieved = service.loadUserByUsername("Felix");
        assertEquals(user, retrieved);
    }

    @Test
    void loadUserByUsernameNotFound() {
        when(repository.findByUsername("Felix")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("Felix"));
    }

    @Test
    void createUser(){
        service.createUser(user);
        verify(repository).save(user);
    }

    @Test
    void updateUser(){
        service.updateUser(user);
        verify(repository).save(user);
    }

    @Test
    void deleteUser() {
        when(repository.findByUsername("Felix")).thenReturn(Optional.of(user));
        service.deleteUser("Felix");
        verify(repository).delete(user);
    }

    @Test
    void deleteUserNotFound() {
        when(repository.findByUsername("Felix")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.deleteUser("Felix"));
    }

    @Test
    void changePassword() {
        when(repository.findByPassword("password")).thenReturn(Optional.of(user));
        service.changePassword("password", "password_abc123");
        assertEquals("password_abc123", user.getPassword());
        verify(repository).save(user);
    }

    @Test
    void changePasswordNotFound() {
        when(repository.findByPassword("password")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.changePassword("password", "password_abc123"));
    }

    @Test
    void signUpUser() {
        when(repository.findByUsername("Felix")).thenReturn(Optional.empty());
        when(repository.findByEmail("felix@abc.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("password::encoded");
        String token = service.signUpUser(user);
        assertEquals("password::encoded", user.getPassword());
        verify(repository).save(user);
        verify(confirmationTokenService).saveConfirmationToken(tokenCaptor.capture());
        assertEquals(token, tokenCaptor.getValue().getToken());
    }

    @Test
    void signUpUserUsernameTaken() {
        when(repository.findByUsername("Felix")).thenReturn(Optional.of(user));
        assertThrows(IllegalStateException.class, () -> service.signUpUser(user));
    }

    @Test
    void signUpEmailInUse() {
        when(repository.findByUsername("Felix")).thenReturn(Optional.empty());
        when(repository.findByEmail("felix@abc.com")).thenReturn(Optional.of(user));
        assertThrows(IllegalStateException.class, () -> service.signUpUser(user));
    }

    @Test
    void enableUser(){
        service.enableUser(user);
        assertTrue(user.isEnabled());
        verify(repository).save(user);
    }
}
