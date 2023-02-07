package com.watchapedia.watchpedia_user.model.entity.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Watch;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Wish;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String movTitle;
    private String movTitleOrg;
    private String movMakingDate;
    private String movCountry;
    private String movGenre;
    private String movTime;
    private String movAge;
    private String movPeople;
    private String movSummary;
    private String movGallery;
    private String movVideo;
    private String movWatch;
    private String movBackImg;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "starContentIdx")
    @JsonIgnore
    private List<Star> star;
}
