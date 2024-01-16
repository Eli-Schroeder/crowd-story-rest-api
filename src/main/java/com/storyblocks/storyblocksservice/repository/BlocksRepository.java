package com.storyblocks.storyblocksservice.repository;

import com.storyblocks.storyblocksservice.model.Block;
import com.storyblocks.storyblocksservice.model.Story;
import com.storyblocks.storyblocksservice.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface BlocksRepository extends CrudRepository<Block, Long> {

    Set<Block> findAllByStory(Story story);

    Set<Block> findAllByAuthor(User author);

    Set<Block> findAllByStoryAndAuthor(Story story, User author);

}
