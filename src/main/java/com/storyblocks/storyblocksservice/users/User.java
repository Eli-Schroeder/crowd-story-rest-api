package com.storyblocks.storyblocksservice.users;

import com.storyblocks.storyblocksservice.stories.Story;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    public User(String username,
                   String email,
                   String password,
                   UserRole userRole,
                   String displayName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = userRole;
        this.displayName = displayName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(name = "username", unique = true)
    private String username;

    @OneToMany(mappedBy = "author")
    private List<Story> stories = new ArrayList<>();

    @ManyToMany(mappedBy = "collaborators")
    private Set<Story> coauthored = new HashSet<>();

    private String email;

    private String displayName;

    private String password;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean enabled = false;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
