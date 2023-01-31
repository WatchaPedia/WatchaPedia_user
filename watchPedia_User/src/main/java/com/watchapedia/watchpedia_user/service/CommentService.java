package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.dto.CommentDto;
import com.watchapedia.watchpedia_user.model.dto.LikeDto;
import com.watchapedia.watchpedia_user.model.dto.SpoilerDto;
import com.watchapedia.watchpedia_user.model.entity.Comment;
import com.watchapedia.watchpedia_user.model.entity.Like;
import com.watchapedia.watchpedia_user.model.entity.Spoiler;
import com.watchapedia.watchpedia_user.model.network.request.CommentRequest;
import com.watchapedia.watchpedia_user.model.network.request.LikeRequest;
import com.watchapedia.watchpedia_user.model.network.response.CommentResponse;
import com.watchapedia.watchpedia_user.model.repository.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.LikeRepository;
import com.watchapedia.watchpedia_user.model.repository.SpoilerRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    final CommentRepository commentRepository;
    final UserRepository userRepository;
    private final SpoilerRepository spoilerRepository;
    private final LikeRepository likeRepository;

    public void commentSave(CommentRequest request){
        Comment findComment = commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx(
                request.contentType(), request.contentIdx(), userRepository.getReferenceById(request.userIdx())
        );
        if(findComment == null){
            Comment comm = CommentDto.of(userRepository.getReferenceById(request.userIdx()),request.text(),request.contentType(),request.contentIdx())
                    .toEntity();
            commentRepository.save(comm);
        }else{
            findComment.setCommText(request.text());
            commentRepository.save(findComment);
        }
    }

    public void commentDelete(CommentRequest request){
        Comment comm = commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx(
                request.contentType(), request.contentIdx(), userRepository.getReferenceById(request.userIdx())
        );
        commentRepository.delete(comm);
        Spoiler spoCheck = spoilerRepository.findBySpoCommentIdx(comm);
        if(spoCheck != null) spoilerRepository.delete(spoCheck);
    }

    public void spoilerSave(CommentRequest request){
        Spoiler check = spoilerRepository.findBySpoCommentIdx(commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx(
                request.contentType(), request.contentIdx(), userRepository.getReferenceById(request.userIdx())
        ));
        if(check == null){
            if(request.spoiler() == true){
                Spoiler spoiler = SpoilerDto.of(commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx(
                        request.contentType(), request.contentIdx(), userRepository.getReferenceById(request.userIdx())
                )).toEntity();
                spoilerRepository.save(spoiler);
            }
        }else{
            if(request.spoiler() == false){
                spoilerRepository.delete(check);
            }
        }
    }

    public boolean likeSave(LikeRequest request){
        Comment comm = commentRepository.getReferenceById(request.commentIdx());
        Like check = likeRepository.findByLikeCommentIdxAndLikeUserIdx(
                comm,userRepository.getReferenceById(request.userIdx())
        );
        if(check == null){
            Like like = LikeDto.of(userRepository.getReferenceById(request.userIdx()),comm)
                    .toEntity();
            likeRepository.save(like);
            return true;
        }else{
            likeDelete(check);
            return false;
        }
    }

    public void likeDelete(Like like){
        likeRepository.delete(like);
    }
}
