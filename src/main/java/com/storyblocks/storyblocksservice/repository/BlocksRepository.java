package com.storyblocks.storyblocksservice.repository;

import com.storyblocks.storyblocksservice.model.Block;
import com.storyblocks.storyblocksservice.model.Story;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BlocksRepository extends PagingAndSortingRepository<Block, Long>, CrudRepository<Block, Long> {

    List<Block> findAllByStory(Story story);

}
