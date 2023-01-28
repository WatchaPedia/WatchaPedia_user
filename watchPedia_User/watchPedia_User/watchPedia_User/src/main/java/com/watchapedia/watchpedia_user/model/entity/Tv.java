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
    private String tvTitle;
    private String tvTitleOrg;
    private String tvMakingDate;
    private String tvChannel;
    private String tvGenre;
    private String tvCountry;
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