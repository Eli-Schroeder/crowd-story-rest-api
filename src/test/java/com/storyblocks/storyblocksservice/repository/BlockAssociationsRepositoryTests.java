package com.storyblocks.storyblocksservice.repository;

import com.storyblocks.storyblocksservice.model.Block;
import com.storyblocks.storyblocksservice.model.BlockAssociation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class BlockAssociationsRepositoryTests {

    @Autowired
    private BlockAssociationsRepository repository;

    @Autowired
    private BlocksRepository blocksRepository;

    private Block block_a;

    private Block block_b;

    private Block block_c;

    private BlockAssociation atob;

    private BlockAssociation atoc;

    private BlockAssociation btoa;

    @BeforeEach
    void setup(){
        block_a = new Block();
        block_b = new Block();
        block_c = new Block();
        blocksRepository.save(block_a);
        blocksRepository.save(block_b);
        blocksRepository.save(block_c);
        atob = new BlockAssociation();
        atob.setFirstBlock(block_a);
        atob.setSecondBlock(block_b);
        atoc = new BlockAssociation();
        atoc.setFirstBlock(block_a);
        atoc.setSecondBlock(block_c);
        btoa = new BlockAssociation();
        btoa.setFirstBlock(block_b);
        btoa.setSecondBlock(block_a);
        repository.save(atob);
        repository.save(atoc);
        repository.save(btoa);
    }

    @Test
    void getAssociationsFromBlock(){
        Set<BlockAssociation> result = repository.getAssociationsFromBlock(block_a);
        assertEquals(2, result.size());
        assertTrue(result.contains(atob));
        assertTrue(result.contains(atoc));
    }

    @Test
    void getAssociationsFromBlockNotFound(){
        Set<BlockAssociation> result = repository.getAssociationsFromBlock(block_c);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAssociationsToBlock(){
        Set<BlockAssociation> result = repository.getAssociationsToBlock(block_b);
        assertEquals(1, result.size());
        assertTrue(result.contains((atob)));
    }
}
