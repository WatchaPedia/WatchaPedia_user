package com.watchapedia.watchpedia_user.model.network.response.content;

import com.watchapedia.watchpedia_user.model.entity.comment.Comment;

public record EstimateContent(
        Long idx,
        String title,
        String makingDate,
        String country,
        String thumbnail,
        boolean hasWish,
        boolean hasWatch,
        Comment comment,
        boolean commSpo
) {
    public static EstimateContent of(
            Long idx, String title, String makingDate, String country, String thumbnail,
            boolean hasWish, boolean hasWatch, Comment comment, boolean commSpo
    ){
        return new EstimateContent(idx,title,makingDate,country,thumbnail,hasWish,hasWatch,comment,commSpo);
    }
}
