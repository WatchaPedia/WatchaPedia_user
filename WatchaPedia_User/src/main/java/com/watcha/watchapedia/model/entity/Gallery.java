package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tbGallery")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Gallery {
    @Id
    private Long galIdx;
    @Column(length = 10)
    private String galContent;
    private Long galContentIdx;
}

