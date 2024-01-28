package com.storyblocks.storyblocksservice.service;

import com.storyblocks.storyblocksservice.model.Story;
import com.storyblocks.storyblocksservice.model.User;

public interface StoriesService {

    Story saveStory(Story story);

    Story getStoryByIdAndAuthor(User author, long id);

    Story updateStory(User author, Story story, long id);

    void deleteStory(User author, long id);

}
