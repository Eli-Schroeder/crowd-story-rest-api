package com.storyblocks.storyblocksservice.users;

import com.storyblocks.storyblocksservice.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByPassword(String password);

    Optional<User> findByEmail(String email);

}
