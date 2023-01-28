package com.watchapedia.watchpedia_user.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity(name = "tbWebtoon")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Webtoon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long webIdx;
    private String webThumbnail;
    @Column(length = 100)
    private String webTitle;
    @Column(length = 100)
    private String webTitleOrg;
    @Column(length = 20)
    private String webWriter;
    @Column(length = 20)
    private String webGenre;
    @Column(length = 20)
    private String webSerDetail;
    @Column(length = 10)
    private String webSerDay;
    private String webSerPeriod;
    @Column(length = 10)
    private String webAge;
    private String webSummary;
    private String webPeople;
    private String webWatch;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "starContentIdx")
    private List<Star> star;

}
