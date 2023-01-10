package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="tbRecommentLike")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecommentLike{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relikeIdx;
    private Long relikeRecommIdx;
    private Long relikeUserIdx;
}