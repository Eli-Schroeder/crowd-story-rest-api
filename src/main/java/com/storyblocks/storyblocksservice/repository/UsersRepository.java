package com.storyblocks.storyblocksservice.repository;

import com.storyblocks.storyblocksservice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UsersRepository extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
}
