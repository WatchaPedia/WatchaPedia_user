package com.watchapedia.watchpedia_user.model.entity.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
//    private String webMakingDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "starContentIdx")
    @JsonIgnore
    private List<Star> star;

}
