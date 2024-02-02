package com.storyblocks.storyblocksservice.users;

import com.storyblocks.storyblocksservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    public ConfirmationToken getToken(String token){
        return confirmationTokenRepository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("Token", "token", token));
    }

    public void setConfirmedAt(String tokenString){
        ConfirmationToken token = confirmationTokenRepository.findByToken(tokenString).orElseThrow(() -> new ResourceNotFoundException("Token", "token", tokenString));
        token.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(token);
    }
}
