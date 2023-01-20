package com.watcha.watchapedia.model.network.request;

import com.watcha.watchapedia.model.dto.BookDto;

public record BookRequest(
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
)
{
    public static BookRequest of(Long bookIdx, String bookThumbnail, String bookTitle, String bookTitleSub,
                               String bookWriter, String bookCategory, String bookAtDate,
                               String bookPage, String bookAge, String bookSummary, String bookPeople,
                               String bookContentIdx, String bookPubSummary, String bookBackImg,
                               String bookBuy) {
        return new BookRequest(bookIdx, bookThumbnail, bookTitle, bookTitleSub,
                bookWriter, bookCategory, bookAtDate, bookPage, bookAge, bookSummary, bookPeople, bookContentIdx, bookPubSummary, bookBackImg, bookBuy);
    }

    public BookDto toDto() {
        return BookDto.of(
                bookIdx, bookThumbnail, bookTitle, bookTitleSub,
                bookWriter, bookCategory, bookAtDate, bookPage, bookAge, bookSummary, bookPeople, bookContentIdx, bookPubSummary, bookBackImg, bookBuy
        );
    }
}
