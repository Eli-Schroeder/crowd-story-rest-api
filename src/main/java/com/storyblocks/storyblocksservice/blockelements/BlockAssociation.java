package com.storyblocks.storyblocksservice.blockelements;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "block_associations")
public class BlockAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long block_association_id;

    @ManyToOne
    @JoinColumn(name = "first_block")
    private Block firstBlock;

    @ManyToOne
    @JoinColumn(name = "second_block")
    private Block secondBlock;

    @Column(name = "type")
    private String type;

    @Column(name = "is_canon")
    private boolean isCanon;

    @Column
    private String description;

}
