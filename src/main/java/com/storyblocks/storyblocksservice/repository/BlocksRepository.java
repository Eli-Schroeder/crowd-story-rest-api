package com.storyblocks.storyblocksservice.repository;

import com.storyblocks.storyblocksservice.model.Block;
import com.storyblocks.storyblocksservice.model.Story;
import com.storyblocks.storyblocksservice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BlocksRepository extends CrudRepository<Block, Long> {

    Set<Block> findAllByStory(Story story);

    Set<Block> findAllByAuthor(User author);

    Set<Block> findAllByStoryAndAuthor(Story story, User author);

}
