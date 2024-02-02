package com.storyblocks.storyblocksservice.stories;

import com.storyblocks.storyblocksservice.users.User;

public interface StoriesService {

    Story saveStory(Story story);

    Story getStoryByIdAndAuthor(User author, long id);

    Story updateStory(User author, Story story, long id);

    void deleteStory(User author, long id);

}
