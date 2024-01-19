package com.storyblocks.storyblocksservice.service;

import com.storyblocks.storyblocksservice.model.Story;
import com.storyblocks.storyblocksservice.repository.StoriesRepository;
import com.storyblocks.storyblocksservice.service.impl.StoriesServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StoriesServiceTests {

    @Autowired
    @InjectMocks
    private StoriesServiceImplementation service;

    @Mock
    private StoriesRepository repository;

    @Test
    void saveStory() {
        Story story = new Story();
        story.setTitle("Felix the Cat");
        Mockito.when(repository.save(story)).thenReturn(story);
        Story saved = service.saveStory(story);
        Mockito.verify(repository).save(story);
        assertEquals(saved, story);
    }

    @Test
    void getStoryById() {

    }
}
