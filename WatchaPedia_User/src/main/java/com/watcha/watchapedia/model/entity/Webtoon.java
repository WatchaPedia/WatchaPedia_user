package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "tbWebtoon")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Webtoon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long webIdx;
    private String webThumbnail;
    @Column(length =100)
    private String webTitle;
    @Column(length =100)
    private String webTitleOrg;
    @Column(length =20)
    private String webWriter;
    @Column(length =20)
    private String webGenre;
    @Column(length =20)
    private String webSerDetail;
    @Column(length =10)
    private String webSerDay;
    @Column(length =30)
    private String webSerPeriod;
    @Column(length =10)
    private String webAge;
    private String webSummary;
    private String webPeople;
    private String webWatch;

}
