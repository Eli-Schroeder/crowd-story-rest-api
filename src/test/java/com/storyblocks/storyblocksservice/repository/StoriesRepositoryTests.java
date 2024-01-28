package com.storyblocks.storyblocksservice.repository;

import com.storyblocks.storyblocksservice.model.Story;
import com.storyblocks.storyblocksservice.model.User;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class StoriesRepositoryTests {

    @Autowired
    private StoriesRepository storiesRepository;

    @Autowired
    private UsersRepository usersRepository;

    private Story first_story;

    private Story second_story;

    private User author;

    @BeforeEach
    void setup(){
        author = new User();
        author.setUsername("felix");
        first_story = new Story();
        first_story.setTitle("Felix the Cat");
        first_story.setAuthor(author);
        storiesRepository.save(first_story);
        second_story = new Story();
        second_story.setTitle("Schultz the Cat");
        second_story.setAuthor(author);
        usersRepository.save(author);
        storiesRepository.save(second_story);
    }

    @Test
    void getById() {
        Optional<Story> findStoryResult = storiesRepository.findById(second_story.getStoryId());
        assertTrue(findStoryResult.isPresent());
        Story response = findStoryResult.get();
        Hibernate.initialize(response);
        assertEquals(second_story, response);
    }

    @Test
    void getByIdNotFound() {
        Optional<Story> findStoryResult = storiesRepository.findById(1000L);
        assertTrue(findStoryResult.isEmpty());
    }

    @Test
    void getByUser() {
        List<Story> stories = storiesRepository.findByAuthor(author, PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "title")));
        assertEquals(2, stories.size());
        assertEquals(first_story, stories.get(0));
        assertEquals(second_story, stories.get(1));
    }

    @Test
    void getByUserPagingAndSorting(){
        List<Story> stories = storiesRepository.findByAuthor(author, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "title")));
        assertEquals(1, stories.size());
        assertEquals(second_story, stories.get(0));
    }

    @Test
    void saveStory(){
        Long id = first_story.getStoryId();
        first_story.setTitle("Felix and His Lesser Sidekick, Schultz");
        storiesRepository.save(first_story);
        Optional<Story> retrieved = storiesRepository.findById(id);
        assertTrue(retrieved.isPresent());
        assertEquals("Felix and His Lesser Sidekick, Schultz", retrieved.get().getTitle());
    }

    @Test
    void deleteStory() {
        Long id = first_story.getStoryId();
        storiesRepository.delete(first_story);
        Optional<Story> findStoryResult = storiesRepository.findById(id);
        assertTrue(findStoryResult.isEmpty());
    }
}
