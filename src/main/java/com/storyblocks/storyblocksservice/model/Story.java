package com.storyblocks.storyblocksservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "stories")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long storyId;

    @ManyToOne
    private User author;

    @ManyToMany
    private Set<User> collaborators = new HashSet<>();

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "story")
    private List<Block> blocks = new ArrayList<>();

    @Column(name = "visibility")
    private String visibility;

}
