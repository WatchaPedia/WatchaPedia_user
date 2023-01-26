package com.watcha.watchapedia.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "tbWebtoon")
@Builder
@Data
@ToString(callSuper = true)
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

    public Webtoon() {

    }
    private Webtoon(Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
               String webWriter, String webGenre, String webSerDetail,
               String webSerDay, String webSerPeriod, String webAge, String webSummary,
               String webPeople, String webWatch) {
        this.webIdx = webIdx; // 번호
        this.webThumbnail = webThumbnail; // 섬네일
        this.webTitle = webTitle; // 제목
        this.webTitleOrg = webTitleOrg;
        this.webWriter = webWriter; // 만들어진 날짜
        this.webGenre = webGenre; // 채널
        this.webSerDetail = webSerDetail; // 장르
        this.webSerDay = webSerDay; // 국가
        this.webSerPeriod = webSerPeriod;
        this.webAge = webAge; // 출연진
        this.webSummary = webSummary; // 요약
        this.webPeople = webPeople; // 시청
        this.webWatch = webWatch; // 사진
    }

    public static Webtoon of(Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
                             String webWriter, String webGenre, String webSerDetail,
                             String webSerDay, String webSerPeriod, String webAge, String webSummary,
                             String webPeople, String webWatch) {
        return new Webtoon(webIdx, webThumbnail, webTitle, webTitleOrg,
                webWriter, webGenre, webSerDetail, webSerDay, webSerPeriod, webAge, webSummary, webPeople, webWatch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(webIdx);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Tv tv)) return false;
        return webIdx != null;
    }
}
