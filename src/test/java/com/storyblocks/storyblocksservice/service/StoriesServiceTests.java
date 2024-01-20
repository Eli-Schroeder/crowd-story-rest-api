package com.storyblocks.storyblocksservice.service;

import com.storyblocks.storyblocksservice.exception.ResourceNotFoundException;
import com.storyblocks.storyblocksservice.model.Story;
import com.storyblocks.storyblocksservice.model.User;
import com.storyblocks.storyblocksservice.repository.StoriesRepository;
import com.storyblocks.storyblocksservice.service.impl.StoriesServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        Story story = new Story();
        story.setTitle("Felix the Cat");
        User user = new User();
        user.setUsername("Felix");
        Mockito.when(repository.findByIdAndAuthor(user, 1L)).thenReturn(Optional.of(story));
        Story retrieved = service.getStoryByIdAndAuthor(user,1L);
        assertEquals(story, retrieved);
    }

    @Test
    void getStoryByIdNotFound() {
        User user = new User();
        user.setUsername("Felix");
        Mockito.when(repository.findByIdAndAuthor(user, 1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getStoryByIdAndAuthor(user, 1L));
    }
}
