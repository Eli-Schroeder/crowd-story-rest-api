package com.storyblocks.storyblocksservice.controller;

import com.storyblocks.storyblocksservice.model.Story;
import com.storyblocks.storyblocksservice.repository.StoriesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/stories")
public class StoryblocksStoriesController {

    private final StoriesRepository repository;

    public StoryblocksStoriesController(StoriesRepository repository){
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Story> getStory(Long id){
        Optional<Story> story = repository.findById(id);
        if(story.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(story.get(), HttpStatus.OK);
        }
    }
}
