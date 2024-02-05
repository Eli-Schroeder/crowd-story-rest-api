package com.storyblocks.storyblocksservice.stories;

import com.storyblocks.storyblocksservice.exception.ResourceNotFoundException;
import com.storyblocks.storyblocksservice.users.User;
import com.storyblocks.storyblocksservice.stories.impl.StoriesServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
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

    private User user;

    @BeforeEach
    void setup(){
        user = new User();
        user.setUsername("Felix");
    }

    @Test
    void saveStory() {
        Story story = new Story();
        story.setTitle("Felix the Cat");
        Mockito.when(repository.save(story)).thenReturn(story);
        Story saved = service.saveStory(story);
        Mockito.verify(repository).save(story);
        assertSame(saved, story);
    }

    @Test
    void getStoryById() {
        Story story = new Story();
        story.setTitle("Felix the Cat");
        Mockito.when(repository.findByAuthorAndStoryId(user, story.getStoryId())).thenReturn(Optional.of(story));
        Story retrieved = service.getStoryByIdAndAuthor(user,story.getStoryId());
        assertSame(story, retrieved);
    }

    @Test
    void getStoryByIdNotFound() {
        Mockito.when(repository.findByAuthorAndStoryId(user, 1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getStoryByIdAndAuthor(user, 1L));
    }

    @Test
    void updateStory() {
        Story existingStory = new Story();
        existingStory.setTitle("Felix the Cat");
        Mockito.when(repository.findByAuthorAndStoryId(user, existingStory.getStoryId())).thenReturn(Optional.of(existingStory));
        Mockito.when(repository.save(existingStory)).thenReturn(existingStory);
        Story updatedStory = new Story();
        updatedStory.setTitle("Felix the Very Cool Cat");
        Story savedUpdatedStory = service.updateStory(user, updatedStory, existingStory.getStoryId());
        assertEquals(updatedStory, savedUpdatedStory);
        assertSame(existingStory, savedUpdatedStory);
    }

    @Test
    void updateStoryNotFound(){
        Mockito.when(repository.findByAuthorAndStoryId(user, 1L)).thenReturn(Optional.empty());
        Story updatedStory = new Story();
        updatedStory.setTitle("Felix the Very Cool Cat");
        assertThrows(ResourceNotFoundException.class, () -> service.updateStory(user, updatedStory, 1L));
    }

    @Test
    void deleteStory(){
        Story story = new Story();
        story.setTitle("Felix the Cat");
        Mockito.when(repository.findByAuthorAndStoryId(user, story.getStoryId())).thenReturn(Optional.of(story));
        service.deleteStory(user, story.getStoryId());
        Mockito.verify(repository).deleteById(story.getStoryId());
    }

    @Test
    void deleteStoryNotFound(){
        Mockito.when(repository.findByAuthorAndStoryId(user, 1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteStory(user, 1L));
    }
}
