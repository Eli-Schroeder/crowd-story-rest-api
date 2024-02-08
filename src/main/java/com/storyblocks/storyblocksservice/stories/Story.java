package com.storyblocks.storyblocksservice.stories;

import com.storyblocks.storyblocksservice.blockelements.Block;
import com.storyblocks.storyblocksservice.users.User;
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
    private Story parentStory;

    private boolean canonToParent = false;

    @ManyToOne
    private User author;

    @ManyToMany
    private Set<User> collaborators = new HashSet<>();

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "story", cascade = CascadeType.REMOVE)
    private List<Block> blocks = new ArrayList<>();

    private StoryUserAccessLevel minimumSkipReviewRole = StoryUserAccessLevel.AUTHOR;

    private StoryUserAccessLevel minimumApproveReviewRole = StoryUserAccessLevel.AUTHOR;

    private StoryUserAccessLevel minimumRequestReviewRole = StoryUserAccessLevel.COAUTHOR;

    private StoryUserAccessLevel minimumAddToStoryRole = StoryUserAccessLevel.COAUTHOR;

    private StoryUserAccessLevel minimumViewStoryRole = StoryUserAccessLevel.COAUTHOR;

}
