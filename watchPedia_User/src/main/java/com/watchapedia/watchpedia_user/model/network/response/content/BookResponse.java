package com.watchapedia.watchpedia_user.model.network.response.content;

import com.watchapedia.watchpedia_user.model.dto.content.BookDto;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;

import java.util.List;

public record BookResponse(
        Long idx,String thumbnail,String title,String titleSub,
        String writer,String category,String atDate,String page,
        String age,String summary,String people,String contentIdx,String pubSummary,
        String backImg,String buy,List<Star> starList, double avgStar
){
    public static BookResponse of(
            Long idx, String thumbnail, String title, String titleSub,
            String writer, String category, String atDate,
            String page, String age, String summary, String people,
            String contentIdx, String pubSummary, String backImg,
            String buy,List<Star> starList
    ){

        return new BookResponse(idx, thumbnail, title, titleSub,
                writer, category, atDate, page, age, summary,
                people, contentIdx, pubSummary, backImg, buy,starList,0.0);
    }

    public static BookResponse of(
            Long idx, String thumbnail, String title, String buy, Double avgStar
    ){
        return new BookResponse(
                idx, thumbnail, title, null, null, null, null, null, null,
                null, null, null, null , null,buy, null, avgStar
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
                dto.bookBackImg(),
                dto.bookBuy(),
                dto.starList(),
                0
        );
    }
}
