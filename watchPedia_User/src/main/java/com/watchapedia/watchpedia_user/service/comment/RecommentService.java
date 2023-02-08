package com.watchapedia.watchpedia_user.service.comment;

import com.watchapedia.watchpedia_user.model.dto.comment.RecommentDto;
import com.watchapedia.watchpedia_user.model.dto.comment.RelikeDto;
import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.comment.Recomment;
import com.watchapedia.watchpedia_user.model.entity.comment.Relike;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.comment.Report;
import com.watchapedia.watchpedia_user.model.network.request.comment.RecommentRequest;
import com.watchapedia.watchpedia_user.model.network.request.comment.RelikeRequest;
import com.watchapedia.watchpedia_user.model.network.response.comment.RecommentResponse;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.RecommentRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.RelikeRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommentService {
    final RecommentRepository recommentRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final RelikeRepository relikeRepository;
    private final ReportRepository reportRepository;

    public RecommentResponse recommentSave(RecommentRequest request){
        User user = userRepository.findById(request.userIdx()).get();
        Long recomment = recommentRepository.save(RecommentDto.of(request.commIdx(),user,user.getUserName(), request.text()).toEntity()).getRecommIdx();
        RecommentDto dto = RecommentDto.from(recommentRepository.getReferenceById(recomment));
        return RecommentResponse.of(dto.idx(),dto.commIdx(),dto.userIdx().getUserIdx(),dto.name(),dto.text(),dto.regDate(),null,false,false);
    }

    public boolean recommentLikeSave(RelikeRequest request){
        Relike check = relikeRepository.findByRelikeRecommIdxAndRelikeUserIdx(request.recommIdx(),request.userIdx());
        if(check == null){
            relikeRepository.save(RelikeDto.of(request.recommIdx(), request.userIdx()).toEntity());
            return true;
        }else{
            recommentLikeDelete(check);
            return false;
        }
    }

    public void recommentLikeDelete(Relike recomment){
        relikeRepository.delete(recomment);
    }

    public void recommentEdit(RecommentRequest request){
        Recomment recomment = recommentRepository.getReferenceById(request.idx());
        recomment.setRecommText(request.text());
        recommentRepository.save(recomment);
    }

    public void recommentDelete(Long recommentIdx){
        recommentRepository.deleteById(recommentIdx);
        likeAndReportDel(recommentIdx);
    }

    public void likeAndReportDel(Long recommentIdx){
        try{
        List<Relike> relikeList = relikeRepository.findByRelikeRecommIdx(recommentIdx);
        for(Relike re: relikeList){
            relikeRepository.delete(re);
        }}catch(Exception e){
            System.out.println("라이크가 없음");
        }
        try {
            reportRepository.delete(reportRepository.findByReportCommTypeAndReportCommIdx("re", recommentIdx));
        }catch (Exception e) {
            System.out.println("리포트가 없음");
        }
    }

}
