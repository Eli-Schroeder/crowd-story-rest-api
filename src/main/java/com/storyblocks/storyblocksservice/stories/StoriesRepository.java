package com.storyblocks.storyblocksservice.stories;

import com.storyblocks.storyblocksservice.users.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoriesRepository extends PagingAndSortingRepository<Story, Long>, CrudRepository<Story, Long> {

    Optional<Story> findByAuthorAndStoryId(User author, Long story_id);

    List<Story> findByAuthor(User author, Pageable pageable);

}
