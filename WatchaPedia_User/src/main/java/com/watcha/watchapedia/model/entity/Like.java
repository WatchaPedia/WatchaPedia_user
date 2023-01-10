package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "tbLike")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeIdx;
    private Long likeUserIdx;
    @Column(length =10)
    private String likeContentType;
    private Long likeContentIdx;

}