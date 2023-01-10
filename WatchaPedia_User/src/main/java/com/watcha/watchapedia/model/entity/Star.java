package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tbStar")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Star {
    @Id
    private Long starIdx;
    @Column(length = 10)
    private String starContentType;
    private Long starContentIdx;
    private Long starUserIdx;
    private double starPoint;

}

