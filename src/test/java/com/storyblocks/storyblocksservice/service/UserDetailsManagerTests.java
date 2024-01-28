package com.storyblocks.storyblocksservice.service;

import com.storyblocks.storyblocksservice.model.User;
import com.storyblocks.storyblocksservice.repository.UsersRepository;
import com.storyblocks.storyblocksservice.service.impl.JpaUserDetailsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsManagerTests {

    @Autowired
    @InjectMocks
    private JpaUserDetailsManager service;

    @Mock
    private UsersRepository repository;

    private User user;

    @BeforeEach
    void setup(){
        user = new User();
        user.setUsername("Felix");
        user.setPassword("password");
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
}
