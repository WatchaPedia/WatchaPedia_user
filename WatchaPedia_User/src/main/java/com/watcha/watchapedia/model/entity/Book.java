package com.watcha.watchapedia.model.entity;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import javax.persistence.*;
import java.util.Objects;

@Entity(name = "tbBook")
@Builder
@Data
@ToString(callSuper = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookIdx;
    private String bookThumbnail;
    @Column(length = 100)
    private String bookTitle;
    @Column(length = 100)
    private String bookTitleSub;
    @Column(length = 20)
    private String bookWriter;
    @Column(length = 20)
    private String bookCategory;
    @Column(length = 20)
    private String bookAtDate;
    @Column(length = 10)
    private String bookPage;
    @Column(length = 10)
    private String bookAge;
    private String bookSummary;
    private String bookPeople;
    private String bookContentIdx;
    private String bookPubSummary;
    private String bookBackImg;
    private String bookBuy;


    protected Book() {
    }

    private Book(Long bookIdx, String bookThumbnail, String bookTitle, String bookTitleSub,
               String bookWriter, String bookCategory, String bookAtDate,
               String bookPage, String bookAge, String bookSummary, String bookPeople,
               String bookContentIdx, String bookPubSummary, String bookBackImg,
               String bookBuy) {
        this.bookIdx = bookIdx; // 번호
        this.bookThumbnail = bookThumbnail; // 섬네일
        this.bookTitle = bookTitle; // 제목
        this.bookTitleSub = bookTitleSub;
        this.bookWriter = bookWriter; // 만들어진 날짜
        this.bookCategory = bookCategory; // 채널
        this.bookAtDate = bookAtDate; // 장르
        this.bookPage = bookPage; // 국가
        this.bookAge = bookAge;
        this.bookSummary = bookSummary; // 출연진
        this.bookPeople = bookPeople; // 요약
        this.bookContentIdx = bookContentIdx; // 시청
        this.bookPubSummary = bookPubSummary; // 사진
        this.bookBackImg = bookBackImg; // 영상
        this.bookBuy = bookBuy; // 이미지
    }
    public static Book of(
            Long bookIdx,
            String bookThumbnail,
            String bookTitle,
            String bookTitleSub,
            String bookWriter,
            String bookCategory,
            String bookAtDate,
            String bookPage,
            String bookAge,
            String bookSummary,
            String bookPeople,
            String bookContentIdx,
            String bookPubSummary,
            String bookBackImg,
            String bookBuy
    ) {
        return new Book(bookIdx, bookThumbnail, bookTitle, bookTitleSub, bookWriter, bookCategory, bookAtDate, bookPage,
                bookAge, bookSummary, bookPeople, bookContentIdx, bookPubSummary, bookBackImg, bookBuy);
    }
        @Override
        public int hashCode() { return Objects.hash(bookIdx); }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Book book)) return false;
            return bookIdx != null;
        }
    }

