package com.watchapedia.watchpedia_user.model.network.response;

public record Ratings (
        Long idx,
        String thumbnail,
        String title,
        boolean netflix,
        boolean watcha,
        Long starPoint
){
    public static Ratings of(
            Long idx, String thumbnail, String title, boolean netflix, boolean watcha, Long starPoint
    ){
        return new Ratings(
                idx, thumbnail, title, netflix, watcha, starPoint);
    }
}
