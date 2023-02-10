package com.watchapedia.watchpedia_user.model.network.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record NoticeResponse(
        String title,
        String text,
        String image,
        String btnColor,
        String btnText,
        String btnLink,
        LocalDateTime regDate
) {
    public static NoticeResponse of(
            String title, String text, String image,
            String btnColor, String btnText, String btnLink, LocalDateTime regDate
    ){
        return new NoticeResponse(
                title, text, image,
                btnColor, btnText, btnLink, regDate
        );
    }
}
