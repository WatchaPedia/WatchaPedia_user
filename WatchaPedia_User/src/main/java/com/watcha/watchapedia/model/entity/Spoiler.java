package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "tbSpoiler")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Spoiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spoIdx;
    @Column(length =10)
    private String spoContentsType;
    private Long spoContentsIdx;
    private Long relikeRecommIdx;

}