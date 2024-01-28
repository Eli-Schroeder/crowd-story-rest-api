package com.storyblocks.storyblocksservice.repository;

import com.storyblocks.storyblocksservice.model.Block;
import com.storyblocks.storyblocksservice.model.BlockAssociation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BlockAssociationsRepository extends CrudRepository<BlockAssociation, Long> {

    Set<BlockAssociation> getAssociationsFromBlock(Block firstBlock);

    Set<BlockAssociation> getAssociationsToBlock(Block secondBlock);

}
