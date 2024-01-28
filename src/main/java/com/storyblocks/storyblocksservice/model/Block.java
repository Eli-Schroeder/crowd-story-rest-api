package com.storyblocks.storyblocksservice.model;

import jakarta.persistence.*;
import lombok.Data;

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

    @Column
    private String title;

    @Column
    private String type;

    @Column
    private String visibility;

    @Column(name = "protect_inclusions")
    private boolean protectInclusions = true;

    @Column(name = "protect_mentions")
    private boolean protectMentions = false;

}
