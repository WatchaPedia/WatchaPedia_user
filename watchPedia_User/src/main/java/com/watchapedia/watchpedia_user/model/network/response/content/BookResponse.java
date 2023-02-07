package com.watchapedia.watchpedia_user.model.network.response.content;

import com.watchapedia.watchpedia_user.model.dto.content.BookDto;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;

import java.util.List;

public record BookResponse(
        Long idx,
        String thumbnail,
        String title,
        String titleSub,
        String writer,
        String category,
        String atDate,
        String page,
        String age,
        String summary,
        String people,
        String contentIdx,
        String pubSummary,
        String makingDate,
        String backImg,
        String bookBuy,
        List<Star> starList,
        double avgStar,
        String bookRole,
        float starAvg
){
    public static BookResponse of(
            Long idx, String thumbnail, String title, String titleSub,
            String writer, String category, String atDate,
            String page, String age, String summary, String people,
            String contentIdx, String pubSummary,String makingDate, String backImg,
            String bookBuy,List<Star> starList
    ){

        return new BookResponse(idx, thumbnail, title, titleSub,
                writer, category, atDate, page, age, summary,
                people, contentIdx, pubSummary,makingDate, backImg, bookBuy,starList,0.0, null, 0);
    }

    public static BookResponse of(
            Long idx, String thumbnail, String title, String watch, Double avgStar
    ){
        return new BookResponse(
                idx, thumbnail, title, null, null, null, null, null, null,
                null, null, null, null , null, null, watch, null, avgStar, null,0
        );
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
                "",
                dto.bookBackImg(),
                dto.bookBuy(),
                dto.starList(),
                0,
                null,
                0
        );
    }
    //사용자-인물페이지--------------------------------------------------------------------------------------------------------
    public static BookResponse ofis(
            Long idx,
            String thumbnail,
            String title,
            String titleSub,
            String writer,
            String category,
            String atDate,
            String page,
            String age,
            String summary,
            String people,
            String contentIdx,
            String pubSummary,
            String makingDate,
            String backImg,
            String bookBuy,
            List<Star> starList,
            double avgStar,
            String bookRole,
            float starAvg){
        return new BookResponse(idx, thumbnail, title, titleSub,
                writer, category, atDate, page, age, summary,
                people, contentIdx, pubSummary, makingDate,null, bookBuy, starList,0 ,bookRole ,starAvg);
    }

    public static BookResponse fromis(BookDto dto){
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
                dto.bookAtDate(),
                dto.bookBackImg(),
                dto.bookBuy(),
                dto.starList(),
                0,
                null,
                0
        );
    }
    public static BookResponse fromis(BookDto dto, String bookRole, float starAvg){
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
                dto.bookAtDate(),
                dto.bookBackImg(),
                dto.bookBuy(),
                dto.starList(),
                0,
                bookRole,
                starAvg
        );
    }
}
