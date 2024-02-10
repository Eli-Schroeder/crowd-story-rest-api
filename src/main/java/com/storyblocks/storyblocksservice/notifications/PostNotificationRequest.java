package com.storyblocks.storyblocksservice.notifications;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PostNotificationRequest {

    private String recipient;

    private String message;
}
