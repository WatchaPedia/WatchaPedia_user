package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.dto.CommentDto;
import com.watchapedia.watchpedia_user.model.dto.MovieDto;
import com.watchapedia.watchpedia_user.model.entity.Comment;
import com.watchapedia.watchpedia_user.model.entity.Spoiler;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.response.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.MovieResponse;
import com.watchapedia.watchpedia_user.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    final MovieRepository movieRepository;
    @Transactional(readOnly = true)
    public MovieResponse movieView(Long movieIdx){
        MovieDto mov = movieRepository.findById(movieIdx).map(MovieDto::from).get();
        return MovieResponse.from(mov);
    }

    final CommentRepository commentRepository;
    final SpoilerRepository spoilerRepository;
    final LikeRepository likeRepository;
    final RecommentRepository recommentRepository;
    @Transactional(readOnly = true)
    public List<CommentResponse> commentList(Long movieIdx, User user){
        List<CommentResponse> commentResponseList =
                commentRepository.findByCommContentTypeAndCommContentIdxOrderByCommIdxDesc("영화",movieIdx)
                    .stream().map(CommentDto::from).map(
                            comm -> {
                                Comment com = commentRepository.getReferenceById(comm.idx());
                                Spoiler spo = spoilerRepository.findBySpoCommentIdx(com);
                                if(spo != null){
                                    return CommentResponse.from(comm,true,
                                            likeRepository.findByLikeCommentIdx(com),
                                            recommentRepository.findByRecommCommIdx(com),
                                            likeRepository.findByLikeCommentIdxAndLikeUserIdx(com,user) != null ?
                                                    true : false
                                    );
                                }else{
                                    return CommentResponse.from(comm,false,
                                            likeRepository.findByLikeCommentIdx(com),
                                            recommentRepository.findByRecommCommIdx(com),
                                            likeRepository.findByLikeCommentIdxAndLikeUserIdx(com,user) != null ?
                                                    true : false
                                    );
                                }
                            }
                        ).toList();
        return commentResponseList;
    }
}
