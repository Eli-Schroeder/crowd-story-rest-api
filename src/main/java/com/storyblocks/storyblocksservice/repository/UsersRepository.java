package com.storyblocks.storyblocksservice.repository;

import com.storyblocks.storyblocksservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByPassword(String password);

    Optional<User> findByEmail(String email);

}
