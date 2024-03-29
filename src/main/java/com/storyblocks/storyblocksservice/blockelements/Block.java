package com.storyblocks.storyblocksservice.blockelements;

import com.storyblocks.storyblocksservice.stories.Story;
import com.storyblocks.storyblocksservice.users.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name="blocks")
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long blockId;

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

    @OneToMany(mappedBy = "firstBlock")
    private Set<BlockAssociation> getPrimaryAssociations;

    @OneToMany(mappedBy = "secondBlock")
    private Set<BlockAssociation> getSecondaryAssociations;

}
