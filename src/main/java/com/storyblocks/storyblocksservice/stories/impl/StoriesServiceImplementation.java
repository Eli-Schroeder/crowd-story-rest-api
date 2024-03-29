package com.storyblocks.storyblocksservice.stories.impl;

import com.storyblocks.storyblocksservice.exception.ResourceNotFoundException;
import com.storyblocks.storyblocksservice.stories.StoriesRepository;
import com.storyblocks.storyblocksservice.stories.StoriesService;
import com.storyblocks.storyblocksservice.stories.Story;
import com.storyblocks.storyblocksservice.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StoriesServiceImplementation implements StoriesService {

    @Autowired
    private final StoriesRepository storiesRepository;

    public StoriesServiceImplementation(StoriesRepository repository){
        this.storiesRepository = repository;
    }

    @Override
    public Story saveStory(Story story){
        return storiesRepository.save(story);
    }

    @Override
    public Story getStoryByIdAndAuthor(User author, long id) {
        return storiesRepository.findByAuthorAndStoryId(author, id).orElseThrow(() -> new ResourceNotFoundException("Story", "Id", id));
    }

    @Override
    public Story updateStory(User author, Story story, long id) {
        Story existingStory = storiesRepository.findByAuthorAndStoryId(author, id).orElseThrow(() -> new ResourceNotFoundException("Story", "Id", id));
        existingStory.setAuthor(story.getAuthor());
        existingStory.setTitle(story.getTitle());
        existingStory.setBlocks(story.getBlocks());
        existingStory.setCollaborators(story.getCollaborators());
        existingStory.setVisibility(story.getVisibility());
        storiesRepository.save(existingStory);
        return existingStory;
    }

    @Override
    public void deleteStory(User author, long id) {
        storiesRepository.findByAuthorAndStoryId(author, id).orElseThrow(() -> new ResourceNotFoundException("Story", "Id", id));
        storiesRepository.deleteById(id);
    }

}
