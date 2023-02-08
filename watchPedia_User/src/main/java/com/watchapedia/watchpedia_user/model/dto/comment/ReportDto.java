package com.watchapedia.watchpedia_user.model.dto.comment;

import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.comment.Report;

import java.time.LocalDateTime;

public record ReportDto(
        Long idx,
        Long userIdx,
        String commType,
        Long commIdx,
        String text,
        Long spoiler,
        Long inap,
        String processing,
        String reporter,
        LocalDateTime regDate
) {
    public static ReportDto of(
            Long userIdx, String commType, Long commIdx, String text,
            Long spoiler, Long inap, String reporter
    ){
        return new ReportDto(
                null,userIdx,commType,commIdx,text,spoiler,inap,null,reporter,null
        );
    }
    public Report toEntity(){
        return Report.of(
                userIdx,commType,commIdx,text,spoiler,inap,reporter
        );
    }
}
