package com.watchapedia.watchpedia_user.model.dto;

import com.watchapedia.watchpedia_user.model.entity.Qna;
import com.watchapedia.watchpedia_user.model.entity.User;

import java.time.LocalDateTime;

public record QnaDto(
        Long qnaIdx,
        String qnaText,
        User user,
        String qnaUserId,
        LocalDateTime qnaRegDate,
        String qnaAttach,
        String qnaStatus,
        Long qnaAuserIdx,
        String qnaAuserId,
        String qnaAtext,
        LocalDateTime qnaAregDate,
        String qnaAnswer,
        String qnaName,
        String qnaFile,
        String qnaDtext
) {
    public static QnaDto of(Long qnaIdx, String qnaText, User user, String qnaUserId, LocalDateTime qnaRegDate, String qnaAttach,
                     String qnaStatus, Long qnaAuserIdx, String qnaAuserId, String qnaAtext, LocalDateTime qnaAregDate,
                     String qnaAnswer, String qnaName, String qnaFile, String qnaDtext){
        return new QnaDto(
                qnaIdx,
                qnaText,
                user,
                qnaUserId,
                qnaRegDate,
                qnaAttach,
                qnaStatus,
                qnaAuserIdx,
                qnaAuserId,
                qnaAtext,
                qnaAregDate,
                qnaAnswer,
                qnaName,
                qnaFile,
                qnaDtext
        );

    }

    public static QnaDto from(Qna qna){
        return new QnaDto(
                qna.getQnaIdx(),
                qna.getQnaText(),
                qna.getUser(),
                qna.getQnaUserid(),
                qna.getQnaRegDate(),
                qna.getQnaAttach(),
                qna.getQnaStatus(),
                qna.getQnaAuserIdx(),
                qna.getQnaAuserid(),
                qna.getQnaAtext(),
                qna.getQnaAregDate(),
                qna.getQnaAnswer(),
                qna.getQnaName(),
                qna.getQnaFile(),
                qna.getQnaDtext());
    }

}
