package com.watcha.watchapedia.model.network.response;

import com.watcha.watchapedia.model.dto.BookDto;

import java.io.Serializable;

public record BookResponse(
        Long bookIdx,   //
        String bookThumbnail,
        String bookTitle,       //
        String bookTitleSub,
        String bookWriter,      //
        String bookCategory,   //
        String bookAtDate,      //
        String bookPage,        //
        String bookAge,
        String bookSummary,
        String bookPeople,
        String bookContentIdx,
        String bookPubSummary,
        String bookBackImg,
        String bookBuy
) implements Serializable {
    public static BookResponse of(
            Long bookIdx, String bookThumbnail, String bookTitle, String bookTitleSub,
            String bookWriter, String bookCategory, String bookAtDate,
            String bookPage, String bookAge, String bookSummary, String bookPeople,
            String bookContentIdx, String bookPubSummary, String bookBackImg,
            String bookBuy){
        return new BookResponse(bookIdx, bookThumbnail, bookTitle, bookTitleSub,
                bookWriter, bookCategory, bookAtDate, bookPage, bookAge, bookSummary, bookPeople, bookContentIdx, bookPubSummary, bookBackImg, bookBuy);
    }

    public static BookResponse from (BookDto dto){
        return new BookResponse(
                dto.bookIdx(),
                dto.bookThumbnail(),
                dto.bookTitle(),
                dto.bookTitleSub(),
                dto.bookWriter(),
                dto.bookCategory(),
                dto.bookAtDate(),
                dto.bookPage(),
                dto.bookAge(),
                dto.bookSummary(),
                dto.bookPeople(),
                dto.bookContentIdx(),
                dto.bookPubSummary(),
                dto.bookBackImg(),
                dto.bookBuy()
        );
    }
}
