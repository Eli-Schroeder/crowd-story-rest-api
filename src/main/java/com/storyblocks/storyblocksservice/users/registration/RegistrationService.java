package com.storyblocks.storyblocksservice.users.registration;

import com.storyblocks.storyblocksservice.users.JpaUserDetailsManager;
import com.storyblocks.storyblocksservice.users.User;
import com.storyblocks.storyblocksservice.users.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    @Autowired
    private JpaUserDetailsManager userService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    private final String validator = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public String register(RegistrationRequest request){
        boolean isValidEmail = request.getEmail().matches(validator);
        if(!isValidEmail){
            throw new IllegalStateException("Invalid email");
        }
        String token = userService.signUpUser(new User(request.getUsername(), request.getEmail(), request.getPassword(), UserRole.ROLE_USER, request.getDisplayName()));
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
