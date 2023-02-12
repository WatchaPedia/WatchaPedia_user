package com.watchapedia.watchpedia_user.model.network.response;



import com.watchapedia.watchpedia_user.model.dto.QnaDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record QnaResponseDto(
        String qnaText,
        String qnaRegDateAgo,
        String qnaStatus
) {
    public static QnaResponseDto of(String qnaText, String qnaRegDateAgo, String qnaStatus){
        return new QnaResponseDto(
                qnaText,
                qnaRegDateAgo,
                qnaStatus
        );
    }

    public static QnaResponseDto from(QnaDto dto){

        LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime regDay = dto.qnaRegDate().truncatedTo(ChronoUnit.DAYS);
        Long ago = ChronoUnit.DAYS.between(regDay, today);


        String dayAgo = ago + "일 전";
        System.out.println(dayAgo);

        return new QnaResponseDto(
                dto.qnaText(),
                dayAgo,
                dto.qnaStatus()

        );
    }

}
