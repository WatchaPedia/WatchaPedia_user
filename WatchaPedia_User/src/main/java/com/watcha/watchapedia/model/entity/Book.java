package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "tbBook")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookIdx;

    private String bookThumbnail;
    @Column(length =100)
    private String bookTitle;
    @Column(length =100)
    private String bookTitleSub;
    @Column(length =20)
    private String bookWriter;
    @Column(length =20)
    private String bookCategory;
    @Column(length =20)
    private String bookAtDate;
    @Column(length =10)
    private String bookPage;
    @Column(length =10)
    private String bookAge;
    private String bookSummary;
    private String bookPeople;
    private String bookContentIdx;
    private String bookPubSummary;
    private String bookBackImg;
    private String bookBuy;

}
