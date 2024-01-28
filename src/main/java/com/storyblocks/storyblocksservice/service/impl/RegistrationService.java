package com.storyblocks.storyblocksservice.service.impl;

import com.storyblocks.storyblocksservice.controller.RegistrationRequest;
import com.storyblocks.storyblocksservice.model.ConfirmationToken;
import com.storyblocks.storyblocksservice.model.User;
import com.storyblocks.storyblocksservice.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    @Autowired
    private JpaUserDetailsManager userService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    private String validator = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public String register(RegistrationRequest request){
        boolean isValidEmail = request.getPassword().matches(validator);
        if(!isValidEmail){
            throw new IllegalStateException("Invalid email");
        }
        String token = userService.signUpUser(new User(request.getUsername(), request.getEmail(), request.getPassword(), UserRole.USER, request.getDisplayName()));
        //TODO: Form confirmation link and email to user
        return token;
    }

    public void confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);
        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("Email already confirmed.");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Token is expired.");
        }
        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(confirmationToken.getUser());
    }

}
