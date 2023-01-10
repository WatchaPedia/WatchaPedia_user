package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "tbMovie")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movIdx;
    private String movThumbnail;
    @Column(length =100)
    private String movTitle;
    @Column(length =100)
    private String movTitleOrg;
    @Column(length =20)
    private String movMakingDate;
    @Column(length =20)
    private String movCountry;
    @Column(length =20)
    private String movGenre;
    @Column(length =20)
    private String movTime;
    @Column(length =10)
    private String movAge;
    private String movPeople;
    private String movSummary;
    private String movGallery;
    private String movVideo;
    private String movWatch;
    private String movBackImg;

    @OneToOne(mappedBy = "movie")
    private BoxOffice boxOffice;

}
