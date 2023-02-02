package com.watchapedia.watchpedia_user.service.content;



import com.watchapedia.watchpedia_user.model.dto.comment.CommentDto;
import com.watchapedia.watchpedia_user.model.dto.content.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.comment.Spoiler;
import com.watchapedia.watchpedia_user.model.entity.content.Webtoon;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.WebtoonResponse;
import com.watchapedia.watchpedia_user.model.repository.comment.*;
import com.watchapedia.watchpedia_user.model.repository.content.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class WebtoonService {

    final WebtoonRepository webtoonRepository;

    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public List<Webtoon> searchWebtoons() {
        return webtoonRepository.findAll();
    }
    @Transactional(readOnly = true)
    public WebtoonResponse webtoonView(Long webIdx){
        WebtoonDto webtoon = webtoonRepository.findById(webIdx).map(WebtoonDto::from).get();
        return WebtoonResponse.from(webtoon);
    }

    final CommentRepository commentRepository;
    final SpoilerRepository spoilerRepository;
    final LikeRepository likeRepository;
    final RecommentRepository recommentRepository;
    private final RelikeRepository relikeRepository;

    @Transactional(readOnly = true)
    public List<CommentResponse> commentList(Long webIdx, Long user){
        List<CommentResponse> commentResponseList =
                commentRepository.findByCommContentTypeAndCommContentIdxOrderByCommIdxDesc("webtoon",webIdx)
                        .stream().map(CommentDto::from).map(
                                comm -> {
                                    Comment com = commentRepository.getReferenceById(comm.idx());
                                    Spoiler spo = spoilerRepository.findBySpoCommentIdx(com.getCommIdx());
                                    if(spo != null){
                                        return CommentResponse.from(comm,true,
                                                likeRepository.findByLikeCommentIdx(com.getCommIdx()).size(),
                                                (long) recommentRepository.findByRecommCommIdx(com.getCommIdx()).size(),
                                                likeRepository.findByLikeCommentIdxAndLikeUserIdx(com.getCommIdx(),user) != null ?
                                                        true : false
                                                ,false,false
                                        );
                                    }else{
                                        return CommentResponse.from(comm,false,
                                                likeRepository.findByLikeCommentIdx(com.getCommIdx()).size(),
                                                (long) recommentRepository.findByRecommCommIdx(com.getCommIdx()).size(),
                                                likeRepository.findByLikeCommentIdxAndLikeUserIdx(com.getCommIdx(),user) != null ?
                                                        true : false
                                                , false,false
                                        );
                                    }
                                }
                        ).toList();
        return commentResponseList;
    }
    public List<Webtoon> Genre(Long WebIdx){
        Webtoon webG = webtoonRepository.findByWebIdx(WebIdx);
        return webtoonRepository.findByWebGenre(webG.getWebGenre());
    }
}