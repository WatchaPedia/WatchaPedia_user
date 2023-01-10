package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "tbTv")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tvIdx;
    private String tvThumbnail;
    @Column(length =100)
    private String tvTitle;
    @Column(length =100)
    private String tvTitleOrg;
    @Column(length =20)
    private String tvMakingDate;
    @Column(length =20)
    private String tvChannel;
    @Column(length =20)
    private String tvGenre;
    @Column(length =20)
    private String tvCountry;
    @Column(length =10)
    private String tvAge;
    private String tvPeople;
    private String tvSummary;
    private String tvWatch;
    private String tvGallery;
    private String tvVideo;
    private String tvBackImg;

}
