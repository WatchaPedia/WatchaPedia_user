package com.watchapedia.watchpedia_user.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity(name = "tbTv")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tvIdx;
    private String tvThumbnail;
    @Column(length = 100)
    private String tvTitle;
    @Column(length = 100)
    private String tvTitleOrg;
    @Column(length = 20)
    private String tvMakingDate;
    @Column(length = 20)
    private String tvChannel;
    @Column(length = 20)
    private String tvGenre;
    @Column(length = 10)
    private String tvCountry;
    @Column(length = 10)
    private String tvAge;
    private String tvPeople;
    private String tvSummary;
    private String tvWatch;
    private String tvGallery;
    private String tvVideo;
    private String tvBackImg;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "starContentIdx")
    private List<Star> star;

}