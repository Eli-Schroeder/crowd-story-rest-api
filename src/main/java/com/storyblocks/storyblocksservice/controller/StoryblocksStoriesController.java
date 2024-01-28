package com.storyblocks.storyblocksservice.controller;

import com.storyblocks.storyblocksservice.model.Story;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stories")
public class StoryblocksStoriesController {

    @GetMapping("/{id}")
    public ResponseEntity<Story> getStory(Long id){
        return null;
    }
}
