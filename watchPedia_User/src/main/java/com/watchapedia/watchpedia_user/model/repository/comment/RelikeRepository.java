package com.watchapedia.watchpedia_user.model.repository.comment;

import com.watchapedia.watchpedia_user.model.entity.comment.Recomment;
import com.watchapedia.watchpedia_user.model.entity.comment.Relike;
import com.watchapedia.watchpedia_user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelikeRepository extends JpaRepository<Relike, Long> {
    Relike findByRelikeRecommIdxAndRelikeUserIdx(Long recomment, Long user);
    List<Relike> findByRelikeRecommIdx(Long recomment);
    List<Relike> findByRelikeUserIdx(Long userIdx);
}
