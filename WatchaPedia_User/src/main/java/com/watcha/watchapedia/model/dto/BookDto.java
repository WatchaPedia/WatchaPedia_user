package com.watcha.watchapedia.model.dto;

import com.watcha.watchapedia.model.entity.Book;

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
        String bookBuy
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
            String bookBackImg

    ){

        return new BookDto(bookIdx, bookThumbnail, bookTitle, bookTitleSub,
                bookWriter, bookCategory, bookAtDate, bookPage, bookAge, bookSummary, bookPeople, bookContentIdx, bookPubSummary, bookBackImg, null
        );
    }

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
            String bookBuy


    ){

        return new BookDto(bookIdx, bookTitleSub, bookTitle, bookThumbnail,
                bookWriter, bookCategory, bookAtDate, bookPage, bookAge, bookSummary, bookPeople,
                bookContentIdx, bookPubSummary, bookBackImg,bookBuy
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
                entity.getBookBuy()
        );
    }
    public Book toEntity(){
        return  Book.of(
                bookIdx,bookThumbnail,bookTitle,bookTitleSub,bookWriter,bookCategory,bookAtDate, bookPage,
                bookAge,bookSummary,bookPeople,bookContentIdx,bookPubSummary,bookBackImg,bookBuy

        );
    }
}
