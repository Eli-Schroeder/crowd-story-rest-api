package com.storyblocks.storyblocksservice.service.impl;

import com.storyblocks.storyblocksservice.model.User;
import com.storyblocks.storyblocksservice.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsManager implements UserDetailsManager {

    @Autowired
    private UsersRepository repository;

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
}