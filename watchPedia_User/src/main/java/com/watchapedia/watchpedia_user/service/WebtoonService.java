package com.watchapedia.watchpedia_user.service;


import com.watchapedia.watchpedia_user.model.dto.CommentDto;
import com.watchapedia.watchpedia_user.model.dto.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.*;
import com.watchapedia.watchpedia_user.model.network.response.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.WebtoonResponse;
import com.watchapedia.watchpedia_user.model.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public WebtoonResponse webtoonView(Long webIdx) {
        WebtoonDto webtoon = webtoonRepository.findById(webIdx).map(WebtoonDto::from).get();
        return WebtoonResponse.from(webtoon);
    }

    final CommentRepository commentRepository;
    final SpoilerRepository spoilerRepository;
    final LikeRepository likeRepository;
    final RecommentRepository recommentRepository;

    @Transactional(readOnly = true)
    public List<CommentResponse> commentList(Long webIdx, User user) {
        List<CommentResponse> commentResponseList =
                commentRepository.findByCommContentTypeAndCommContentIdxOrderByCommIdxDesc("webtoon", webIdx)
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