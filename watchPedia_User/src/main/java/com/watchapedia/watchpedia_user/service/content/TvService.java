package com.watchapedia.watchpedia_user.service.content;



import com.watchapedia.watchpedia_user.model.dto.comment.CommentDto;
import com.watchapedia.watchpedia_user.model.dto.content.TvDto;
import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.comment.Spoiler;
import com.watchapedia.watchpedia_user.model.entity.content.Tv;
import com.watchapedia.watchpedia_user.model.entity.content.Webtoon;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.TvResponse;
import com.watchapedia.watchpedia_user.model.repository.comment.*;
import com.watchapedia.watchpedia_user.model.repository.content.TvRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TvService {

    final TvRepository tvRepository;

    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public List<Tv> searchTvs() {
        return tvRepository.findAll();
    }
    @Transactional(readOnly = true)
    public TvResponse tvView(Long tvIdx){
        TvDto tv = tvRepository.findById(tvIdx).map(TvDto::from).get();
        return TvResponse.from(tv);
    }

    final CommentRepository commentRepository;
    final SpoilerRepository spoilerRepository;
    final LikeRepository likeRepository;
    final RecommentRepository recommentRepository;
    private final RelikeRepository relikeRepository;

    @Transactional(readOnly = true)
    public List<CommentResponse> commentList(Long tvIdx, Long user){
        List<CommentResponse> commentResponseList =
                commentRepository.findByCommContentTypeAndCommContentIdxOrderByCommIdxDesc("tv",tvIdx)
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
    public List<Tv> Genre(Long TvIdx){
        Tv tvG = tvRepository.findByTvIdx(TvIdx);
        return tvRepository.findByTvGenre(tvG.getTvGenre());
    }
}