package com.watchapedia.watchpedia_user.model.entity.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "tbBook")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookIdx;
    private String bookThumbnail;
    private String bookTitle;
    private String bookTitleSub;
    private String bookWriter;
    private String bookCategory;
    private String bookAtDate;
    private String bookPage;
    private String bookAge;
    private String bookSummary;
    private String bookPeople;
    private String bookContentIdx;
    private String bookPubSummary;
    private String bookBackImg;
    private String bookBuy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "starContentIdx")
    @JsonIgnore
    private List<Star> star;
}
