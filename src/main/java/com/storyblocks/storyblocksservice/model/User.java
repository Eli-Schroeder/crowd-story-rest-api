package com.storyblocks.storyblocksservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long user_id;

    @Column(name = "username", unique = true)
    private String username;

    @OneToMany(mappedBy = "author")
    private List<Story> stories = new ArrayList<>();

    @ManyToMany(mappedBy = "collaborators")
    private Set<Story> coauthored = new HashSet<>();

}
