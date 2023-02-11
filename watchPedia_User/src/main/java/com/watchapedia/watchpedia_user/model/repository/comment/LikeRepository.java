package com.watchapedia.watchpedia_user.model.repository.comment;

import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.comment.Like;
import com.watchapedia.watchpedia_user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByLikeCommentIdx(Long comment);
    Like findByLikeCommentIdxAndLikeUserIdx(Long comment, Long user);
    List<Like> findByLikeUserIdx(Long userIdx);
    @Query(value = "select count(likeIdx) from tb_like where likeCommentIdx=:idx")
    int findByCommCount(@Param("idx") Long idx);
}
