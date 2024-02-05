package com.storyblocks.storyblocksservice.users;

import com.storyblocks.storyblocksservice.exception.ResourceNotFoundException;
import com.storyblocks.storyblocksservice.users.registration.ConfirmationToken;
import com.storyblocks.storyblocksservice.users.registration.ConfirmationTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class JpaUserDetailsManager implements UserDetailsManager {

    @Autowired
    private UsersRepository repository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("No user found with username " + username));
    }

    @Override
    public void createUser(UserDetails user) {
        repository.save((User) user);
    }

    @Override
    public void updateUser(UserDetails user) {
        repository.save((User) user);
    }

    @Override
    public void deleteUser(String username) {
        User userDetails = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No User found with username " + username));
        repository.delete(userDetails);
    }

    public String signUpUser(User user){
        boolean usernameInUse = repository.findByUsername(user.getUsername()).isPresent();
        if(usernameInUse){
            throw new IllegalStateException("Username already in use.");
        }
        boolean emailInUse = repository.findByEmail(user.getEmail()).isPresent();
        if(emailInUse){
            throw new IllegalStateException("Email already in use.");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        repository.save(user);
        String tokenString = UUID.randomUUID().toString();
        ConfirmationToken token = new ConfirmationToken(tokenString, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
        confirmationTokenService.saveConfirmationToken(token);
        return tokenString;
    }

    @Transactional
    public void enableUser(User user){
        user.setEnabled(true);
        repository.save(user);
    }

    @Override
    @Transactional
    public void changePassword(String oldPassword, String newPassword) {
        User userDetails = repository.findByPassword(oldPassword)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid password."));
        userDetails.setPassword(newPassword);
        repository.save(userDetails);
    }

    @Override
    public boolean userExists(String username) {
        return repository.findByUsername(username).isPresent();
    }

    public ProfileResponse getUserProfile(long userid) {
        User user = repository.findById(userid).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userid));
        return new ProfileResponse(user.getUsername(), user.getDisplayName(), user.getEmail(), user.isEnabled(), user.getRole());
    }
}