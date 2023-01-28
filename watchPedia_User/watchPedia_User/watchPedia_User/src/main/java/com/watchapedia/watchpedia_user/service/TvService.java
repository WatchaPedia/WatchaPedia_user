package com.watchapedia.watchpedia_user.service;


import com.watchapedia.watchpedia_user.model.dto.CommentDto;
import com.watchapedia.watchpedia_user.model.dto.TvDto;
import com.watchapedia.watchpedia_user.model.entity.Comment;
import com.watchapedia.watchpedia_user.model.entity.Spoiler;
import com.watchapedia.watchpedia_user.model.entity.Tv;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.response.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.TvResponse;
import com.watchapedia.watchpedia_user.model.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public TvResponse tvView(Long tvIdx) {
        TvDto tv = tvRepository.findById(tvIdx).map(TvDto::from).get();
        return TvResponse.from(tv);
    }

    final CommentRepository commentRepository;
    final SpoilerRepository spoilerRepository;
    final LikeRepository likeRepository;
    final RecommentRepository recommentRepository;

    @Transactional(readOnly = true)
    public List<CommentResponse> commentList(Long tvIdx, User user) {
        List<CommentResponse> commentResponseList =
                commentRepository.findByCommContentTypeAndCommContentIdxOrderByCommIdxDesc("tv", tvIdx)
                        .stream().map(CommentDto::from).map(
                                comm -> {
                                    Comment com = commentRepository.getReferenceById(comm.idx());
                                    Spoiler spo = spoilerRepository.findBySpoCommentIdx(com);
                                    if (spo != null) {
                                        return CommentResponse.from(comm, true,
                                                likeRepository.findByLikeCommentIdx(com),
                                                recommentRepository.findByRecommCommIdx(com),
                                                likeRepository.findByLikeCommentIdxAndLikeUserIdx(com, user) != null ?
                                                        true : false
                                        );
                                    } else {
                                        return CommentResponse.from(comm, false,
                                                likeRepository.findByLikeCommentIdx(com),
                                                recommentRepository.findByRecommCommIdx(com),
                                                likeRepository.findByLikeCommentIdxAndLikeUserIdx(com, user) != null ?
                                                        true : false
                                        );
                                    }
                                }
                        ).toList();
        return commentResponseList;
    }
}