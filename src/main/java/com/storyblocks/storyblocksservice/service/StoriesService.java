package com.storyblocks.storyblocksservice.service;

import com.storyblocks.storyblocksservice.model.Story;

public interface StoriesService {

    Story saveStory(Story story);

    Story getStoryById(long id);

    Story updateStory(Story story, long id);

    void deleteStory(long id);

}
