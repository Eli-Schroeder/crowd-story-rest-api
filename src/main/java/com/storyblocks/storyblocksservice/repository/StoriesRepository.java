package com.storyblocks.storyblocksservice.repository;

import com.storyblocks.storyblocksservice.model.Story;
import com.storyblocks.storyblocksservice.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StoriesRepository extends PagingAndSortingRepository<Story, Long>, CrudRepository<Story, Long> {

    List<Story> findByAuthor(User author, Pageable pageable);

}
