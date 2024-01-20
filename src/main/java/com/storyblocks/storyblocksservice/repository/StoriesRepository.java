package com.storyblocks.storyblocksservice.repository;

import com.storyblocks.storyblocksservice.model.Story;
import com.storyblocks.storyblocksservice.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoriesRepository extends PagingAndSortingRepository<Story, Long>, CrudRepository<Story, Long> {

    Optional<Story> findByIdAndAuthor(User author, Long id);

    List<Story> findByAuthor(User author, Pageable pageable);

}
