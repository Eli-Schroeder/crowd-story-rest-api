package com.storyblocks.storyblocksservice.stories;

import com.storyblocks.storyblocksservice.blockelements.Block;
import com.storyblocks.storyblocksservice.blockelements.BlocksRepository;
import com.storyblocks.storyblocksservice.stories.Story;
import com.storyblocks.storyblocksservice.stories.StoriesRepository;
import com.storyblocks.storyblocksservice.users.User;
import com.storyblocks.storyblocksservice.users.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class BlocksRepositoryTests {

    @Autowired
    private BlocksRepository blocksRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private StoriesRepository storiesRepository;

    private User user;

    private Story story;

    private Block first_block;

    private Block second_block;

    private Block third_block;

    @BeforeEach
    void setup(){
        user = new User();
        user.setUsername("felix");
        usersRepository.save(user);
        usersRepository.save(user);
        story = new Story();
        story.setAuthor(user);
        story.setTitle("Felix the Cat");
        storiesRepository.save(story);
        first_block = new Block();
        first_block.setTitle("Chapter 1");
        first_block.setType("Narration");
        first_block.setStory(story);
        first_block.setAuthor(user);
        blocksRepository.save(first_block);
        User otherUser = new User();
        otherUser.setUsername("schultz");
        usersRepository.save(otherUser);
        second_block = new Block();
        second_block.setTitle("Felix Dies Tragically The End");
        second_block.setType("Event");
        second_block.setStory(story);
        second_block.setAuthor(otherUser);
        blocksRepository.save(second_block);
        Story otherStory = new Story();
        otherStory.setTitle("Schultz the Cat");
        otherStory.setAuthor(otherUser);
        storiesRepository.save(otherStory);
        third_block = new Block();
        third_block.setAuthor(user);
        third_block.setType("Narration");
        third_block.setTitle("Schultz is very stinky");
        third_block.setStory(otherStory);
        blocksRepository.save(third_block);
    }

    @Test
    void findById() {
        Optional<Block> findByIdResult = blocksRepository.findById(first_block.getBlockId());
        assertTrue(findByIdResult.isPresent());
        assertEquals(first_block, findByIdResult.get());
    }

    @Test
    void findByIdNotFound() {
        Optional<Block> findByIdResult = blocksRepository.findById(1000L);
        assertTrue(findByIdResult.isEmpty());
    }

    @Test
    void findAllByStory() {
        Set<Block> blocks = blocksRepository.findAllByStory(story);
        assertEquals(2, blocks.size());
        assertTrue(blocks.contains(first_block));
        assertTrue(blocks.contains(second_block));
    }

    @Test
    void findAllByAuthor() {
        Set<Block> blocks = blocksRepository.findAllByAuthor(user);
        assertEquals(2, blocks.size());
        assertTrue(blocks.contains(first_block));
        assertTrue(blocks.contains(third_block));
    }

    @Test
    void findAllByStoryAndAuthor() {
        Set<Block> blocks = blocksRepository.findAllByStoryAndAuthor(story, user);
        assertEquals(1, blocks.size());
        assertTrue(blocks.contains(first_block));
    }

    @Test
    void saveBlock() {
        Long id = first_block.getBlockId();
        first_block.setTitle("Chapter 1: Felix the Great");
        blocksRepository.save(first_block);
        Optional<Block> findByIdResult = blocksRepository.findById(id);
        assertTrue(findByIdResult.isPresent());
        assertEquals("Chapter 1: Felix the Great", findByIdResult.get().getTitle());
    }

    @Test
    void deleteBlock() {
        Long id = second_block.getBlockId();
        blocksRepository.delete(second_block);
        Optional<Block> findByIdResult = blocksRepository.findById(id);
        assertTrue(findByIdResult.isEmpty());
    }
}
