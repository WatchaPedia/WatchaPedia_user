package com.watchapedia.watchpedia_user.service.content;

import com.watchapedia.watchpedia_user.model.dto.comment.CommentDto;
import com.watchapedia.watchpedia_user.model.dto.content.MovieDto;
import com.watchapedia.watchpedia_user.model.dto.comment.RecommentDto;
import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.comment.Spoiler;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.MovieResponse;
import com.watchapedia.watchpedia_user.model.network.response.comment.RecommentResponse;
import com.watchapedia.watchpedia_user.model.repository.comment.*;
import com.watchapedia.watchpedia_user.model.repository.content.MovieRepository;
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
    private final RelikeRepository relikeRepository;

    @Transactional(readOnly = true)
    public List<CommentResponse> commentList(Long movieIdx, Long user){
        List<CommentResponse> commentResponseList =
                commentRepository.findByCommContentTypeAndCommContentIdxOrderByCommIdxDesc("movie",movieIdx)
                    .stream().map(CommentDto::from).map(
                            comm -> {
                                Comment com = commentRepository.getReferenceById(comm.idx());
                                Spoiler spo = spoilerRepository.findBySpoCommentIdx(com.getCommIdx());
                                if(spo != null){
                                    return CommentResponse.from(comm,true,
                                            likeRepository.findByLikeCommentIdx(com.getCommIdx()).size(),
                                            recommentRepository.findByRecommCommIdx(com.getCommIdx()).size(),
                                            likeRepository.findByLikeCommentIdxAndLikeUserIdx(com.getCommIdx(),user) != null ?
                                                    true : false
                                            ,false,false
                                    );
                                }else{
                                    return CommentResponse.from(comm,false,
                                            likeRepository.findByLikeCommentIdx(com.getCommIdx()).size(),
                                            recommentRepository.findByRecommCommIdx(com.getCommIdx()).size(),
                                            likeRepository.findByLikeCommentIdxAndLikeUserIdx(com.getCommIdx(),user) != null ?
                                                    true : false
                                            , false,false
                                    );
                                }
                            }
                        ).toList();
        return commentResponseList;
    }
}
