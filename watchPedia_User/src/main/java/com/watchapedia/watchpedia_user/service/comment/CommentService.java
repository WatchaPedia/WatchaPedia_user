package com.watchapedia.watchpedia_user.service.comment;

import com.watchapedia.watchpedia_user.model.dto.comment.CommentDto;
import com.watchapedia.watchpedia_user.model.dto.comment.LikeDto;
import com.watchapedia.watchpedia_user.model.dto.comment.RecommentDto;
import com.watchapedia.watchpedia_user.model.dto.comment.SpoilerDto;
import com.watchapedia.watchpedia_user.model.entity.comment.*;
import com.watchapedia.watchpedia_user.model.network.request.comment.CommentRequest;
import com.watchapedia.watchpedia_user.model.network.request.comment.LikeRequest;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.comment.RecommentResponse;
import com.watchapedia.watchpedia_user.model.repository.*;
import com.watchapedia.watchpedia_user.model.repository.comment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    final CommentRepository commentRepository;
    final UserRepository userRepository;
    private final SpoilerRepository spoilerRepository;
    private final LikeRepository likeRepository;
    private final RecommentRepository recommentRepository;

    public Comment commentSave(CommentRequest request){
        Comment findComment = commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx(
                request.contentType(), request.contentIdx(), userRepository.getReferenceById(request.userIdx())
        );
        if(findComment == null){
            Comment comm = CommentDto.of(userRepository.getReferenceById(request.userIdx()),request.text(),request.contentType(),request.contentIdx())
                    .toEntity();
            commentRepository.save(comm);
            return comm;
        }else{
            findComment.setCommText(request.text());
            commentRepository.save(findComment);
            return findComment;
        }
    }

    public void commentDelete(CommentRequest request){
        Comment comm = commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx(
                request.contentType(), request.contentIdx(), userRepository.getReferenceById(request.userIdx())
        );
        commentRepository.delete(comm);
        Spoiler spoCheck = spoilerRepository.findBySpoCommentIdx(comm.getCommIdx());
        if(spoCheck != null) spoilerRepository.delete(spoCheck);
        likeAndReportDelte(comm.getCommIdx());
    }

    final RecommentService recommentService;
    public void likeAndReportDelte(Long commIdx){
        Report report = reportRepository.findByReportCommTypeAndReportCommIdx("comm",commIdx);
        if(report != null) reportRepository.delete(report);
        try{
            List<Like> likeList = likeRepository.findByLikeCommentIdx(commIdx);
            for(Like like : likeList){
                likeDelete(like);
            }
        }catch(Exception e){
            System.out.println("라이크가 없습니다");
        }
        try{
            List<Recomment> recomments = recommentRepository.findByRecommCommIdx(commIdx);
            for(Recomment recomm : recomments){
                recommentService.recommentDelete(recomm.getRecommIdx());
            }
        }catch(Exception e){
            System.out.println("리코멘트가 없습니다");
        }
    }

    public void spoilerSave(Comment comment, boolean spoiler){
        Spoiler check = spoilerRepository.findBySpoCommentIdx(comment.getCommIdx());
        if(check == null){
            if(spoiler == true){
                Spoiler entity = SpoilerDto.of(comment.getCommIdx()).toEntity();
                spoilerRepository.save(entity);
            }
        }else{
            if(spoiler == false){
                spoilerRepository.delete(check);
            }
        }
    }

    public boolean likeSave(LikeRequest request){
        Comment comm = commentRepository.getReferenceById(request.commentIdx());
        Like check = likeRepository.findByLikeCommentIdxAndLikeUserIdx(
                comm.getCommIdx(), request.userIdx());
        if(check == null){
            Like like = LikeDto.of(request.userIdx(),comm.getCommIdx())
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

    final RelikeRepository relikeRepository;
    private final ReportRepository reportRepository;

    public CommentResponse findComment(Long commentIdx, Long userIdx, Pageable pageable){
        Comment comment = commentRepository.getReferenceById(commentIdx);

        boolean hasSpo = false;
        boolean hasInap = false;

        if(userIdx!=null) {
            Report report = reportRepository.findByReportCommTypeAndReportCommIdx("comm", comment.getCommIdx());
            if (report != null) {
                if (report.getReportReporter().contains(",")) {
                    for (String idx : report.getReportReporter().split(", ")) {
                        if (Long.parseLong(idx.split("[|]")[0]) == userIdx
                        ) {
                            if (idx.split("[|]")[1].equals("스포")) hasSpo = true;
                            if (idx.split("[|]")[1].equals("부적절")) hasInap = true;
                        }
                    }
                } else {
                    if (Long.parseLong(report.getReportReporter().split("[|]")[0]) == userIdx
                    ) {
                        if (report.getReportReporter().split("[|]")[1].equals("스포")) hasSpo = true;
                        if (report.getReportReporter().split("[|]")[1].equals("부적절")) hasInap = true;
                    }
                }
            }
        }
        return CommentResponse.from(
                CommentDto.from(comment),
                spoilerRepository.findBySpoCommentIdx(comment.getCommIdx()) != null ? true:false,
                likeRepository.findByLikeCommentIdx(comment.getCommIdx()),
                recommentRepository.findByRecommCommIdx(comment.getCommIdx(),pageable).map(RecommentDto::from).map(
                        dto -> {
                            Recomment recomment = recommentRepository.getReferenceById(dto.idx());
                            boolean hasReport = false;
                            if(userIdx != null) {
                                Report findReport = reportRepository.findByReportCommTypeAndReportCommIdx("re", dto.idx());
                                if (findReport != null) {
                                    for (String idx : findReport.getReportReporter().split(", ")) {
                                        if (Long.parseLong(idx.split("[|]")[0]) == userIdx) {
                                            hasReport = true;
                                        }
                                    }
                                }
                            }
                            return RecommentResponse.of(dto.idx(),dto.commIdx(),dto.userIdx().getUserIdx(),dto.name(),dto.text(),
                                    dto.regDate(), relikeRepository.findByRelikeRecommIdx(recomment.getRecommIdx()),
                                    userIdx!=null?(relikeRepository.findByRelikeRecommIdxAndRelikeUserIdx(recomment.getRecommIdx(),userIdx) != null ? true : false):false
                                    ,userIdx!=null?hasReport:false
                            );
                        }
                ),
                userIdx!=null?(likeRepository.findByLikeCommentIdxAndLikeUserIdx(comment.getCommIdx(),userIdx) != null ?
                        true : false) : false
                , hasSpo, hasInap
        );
    }

    public Page<CommentResponse> commentList(String contentType, Long contentIdx, Long userIdx, Pageable pageable){
        Page<CommentResponse> commentResponseList = commentRepository.findByCommContentTypeAndCommContentIdx(
                contentType,contentIdx, pageable
        ).map(CommentDto::from).map(dto -> {
            return CommentResponse.from(dto,
                    spoilerRepository.findBySpoCommentIdx(dto.idx()) != null ? true : false,
                    likeRepository.findByCommCount(dto.idx()),
                    (long) recommentRepository.findByRecommCount(dto.idx()),
                    userIdx!=null?(likeRepository.findByLikeCommentIdxAndLikeUserIdx(dto.idx(),userIdx) != null ? true : false):false
                    );
        });

        return commentResponseList;
    }
}
