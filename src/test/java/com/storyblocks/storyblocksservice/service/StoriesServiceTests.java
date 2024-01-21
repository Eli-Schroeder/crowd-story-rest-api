package com.storyblocks.storyblocksservice.service;

import com.storyblocks.storyblocksservice.exception.ResourceNotFoundException;
import com.storyblocks.storyblocksservice.model.Story;
import com.storyblocks.storyblocksservice.model.User;
import com.storyblocks.storyblocksservice.repository.StoriesRepository;
import com.storyblocks.storyblocksservice.service.impl.StoriesServiceImplementation;
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
        Mockito.when(repository.findByIdAndAuthor(user, story.getStory_id())).thenReturn(Optional.of(story));
        Story retrieved = service.getStoryByIdAndAuthor(user,story.getStory_id());
        assertSame(story, retrieved);
    }

    @Test
    void getStoryByIdNotFound() {
        Mockito.when(repository.findByIdAndAuthor(user, 1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getStoryByIdAndAuthor(user, 1L));
    }

    @Test
    void updateStory() {
        Story existingStory = new Story();
        existingStory.setTitle("Felix the Cat");
        Mockito.when(repository.findByIdAndAuthor(user, existingStory.getStory_id())).thenReturn(Optional.of(existingStory));
        Mockito.when(repository.save(existingStory)).thenReturn(existingStory);
        Story updatedStory = new Story();
        updatedStory.setTitle("Felix the Very Cool Cat");
        Story savedUpdatedStory = service.updateStory(user, updatedStory, existingStory.getStory_id());
        assertEquals(updatedStory, savedUpdatedStory);
        assertSame(existingStory, savedUpdatedStory);
    }

    @Test
    void updateStoryNotFound(){
        Mockito.when(repository.findByIdAndAuthor(user, 1L)).thenReturn(Optional.empty());
        Story updatedStory = new Story();
        updatedStory.setTitle("Felix the Very Cool Cat");
        assertThrows(ResourceNotFoundException.class, () -> service.updateStory(user, updatedStory, 1L));
    }

    @Test
    void deleteStory(){
        Story story = new Story();
        story.setTitle("Felix the Cat");
        Mockito.when(repository.findByIdAndAuthor(user, story.getStory_id())).thenReturn(Optional.of(story));
        service.deleteStory(user, story.getStory_id());
        Mockito.verify(repository).deleteById(story.getStory_id());
    }

    @Test
    void deleteStoryNotFound(){
        Mockito.when(repository.findByIdAndAuthor(user, 1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteStory(user, 1L));
    }
}
