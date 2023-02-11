package com.watchapedia.watchpedia_user.model.repository.comment;

import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.comment.Recomment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecommentRepository extends JpaRepository<Recomment,Long> {
    Page<Recomment> findByRecommCommIdx(Long comment, Pageable pageable);
    List<Recomment> findByRecommCommIdx(Long comment);
    List<Recomment> findByRecommUserIdx(Long userIdx);
}
