package com.storyblocks.storyblocksservice.users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ProfileResponse {

    private final String username;

    private final String displayName;

    private final String email;

    private final boolean emailConfirmed;

    private final UserRole role;

}
