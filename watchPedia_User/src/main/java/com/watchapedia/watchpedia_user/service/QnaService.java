package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.dto.UserDto;
import com.watchapedia.watchpedia_user.model.dto.UserSessionDto;
import com.watchapedia.watchpedia_user.model.entity.Qna;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QnaService {

    final QnaRepository qnaRepository;
    public void qnaSave(String qnaText, String qnaName, UserSessionDto userDto) { // 질문하기 등록서비스
        Qna qna = new Qna() ;
        LocalDateTime now = LocalDateTime.now();
        qna.setQnaText(qnaText);
        qna.setQnaName(qnaName);
        qna.setUser(new User());
        qna.getUser().setUserIdx(userDto.userIdx());
        qna.setQnaUserid(userDto.userName());
        qna.setQnaRegDate(now);
        qna.setQnaStatus("답변대기");
        qnaRepository.save(qna);
    }
}