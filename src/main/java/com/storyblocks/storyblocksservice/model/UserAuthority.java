package com.storyblocks.storyblocksservice.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class UserAuthority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "auth_user_detail_id")
    private User authUserDetails;

    private final String authority;

    public UserAuthority(String authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}
