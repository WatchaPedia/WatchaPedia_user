package com.watchapedia.watchpedia_user.model.dto.content;

import com.watchapedia.watchpedia_user.model.entity.content.Book;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;

import java.util.List;

public record BookDto(
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
        String bookBuy,
        List<Star> starList
) {
    public static BookDto of(
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
            String bookBuy,
            List<Star> starList
    ){

        return new BookDto(bookIdx, bookThumbnail, bookTitle, bookTitleSub,
                bookWriter, bookCategory, bookAtDate, bookPage, bookAge, bookSummary, bookPeople, bookContentIdx, bookPubSummary, bookBackImg, bookBuy,
                starList
        );
    }

    public static BookDto from(Book entity){
        return new BookDto(
                entity.getBookIdx(),
                entity.getBookThumbnail(),
                entity.getBookTitle(),
                entity.getBookTitleSub(),
                entity.getBookWriter(),
                entity.getBookCategory(),
                entity.getBookAtDate(),
                entity.getBookPage(),
                entity.getBookAge(),
                entity.getBookSummary(),
                entity.getBookPeople(),
                entity.getBookContentIdx(),
                entity.getBookPubSummary(),
                entity.getBookBackImg(),
                entity.getBookBuy(),
                entity.getStar()
        );
    }
}




