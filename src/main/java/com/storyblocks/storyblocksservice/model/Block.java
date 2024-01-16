package com.storyblocks.storyblocksservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="blocks")
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long block_id;

    @ManyToOne
    private Story story;

    @ManyToOne
    private User author;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "visibility")
    private String visibility;

    @OneToMany(mappedBy = "firstBlock")
    private Set<BlockAssociation> primaryAssociations = new HashSet<>();

    @OneToMany(mappedBy = "secondBlock")
    private Set<BlockAssociation> secondaryAssociations = new HashSet<>();

}
