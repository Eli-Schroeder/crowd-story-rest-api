package com.storyblocks.storyblocksservice.repository;

import com.storyblocks.storyblocksservice.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class UsersRepositoryTests {

    @Autowired
    private UsersRepository repository;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setUsername("felix");
        repository.save(user);
        User schultz = new User();
        schultz.setUsername("schultz");
        repository.save(schultz);
    }

    @Test
    void getById() {
        Optional<User> findUserResult = repository.findById(user.getUser_id());
        assertTrue(findUserResult.isPresent());
        assertEquals(user, findUserResult.get());
    }

    @Test
    void getByUsername() {
        Optional<User> findUserResult = repository.findByUsername("felix");
        assertTrue(findUserResult.isPresent());
        assertEquals(user, findUserResult.get());
    }

    @Test
    void getByUsernameNotFound() {
        Optional<User> findUserResult = repository.findByUsername("opal");
        assertTrue(findUserResult.isEmpty());
    }

    @Test
    void deleteUser(){
        Long id = user.getUser_id();
        repository.delete(user);
        Optional<User> findUserResult = repository.findById(id);
        assertTrue(findUserResult.isEmpty());
    }

}
