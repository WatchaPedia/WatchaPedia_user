package com.watchapedia.watchpedia_user.model.entity.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @JsonIgnore
    private List<Star> star;

}