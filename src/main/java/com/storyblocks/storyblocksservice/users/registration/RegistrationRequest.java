package com.storyblocks.storyblocksservice.users.registration;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String username;

    private final String email;

    private final String displayName;

    private final String password;

}
