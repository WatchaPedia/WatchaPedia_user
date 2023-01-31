package com.watchapedia.watchpedia_user.model.repository;

import com.watchapedia.watchpedia_user.model.entity.Comment;
import com.watchapedia.watchpedia_user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByCommContentTypeAndCommContentIdxOrderByCommIdxDesc(
            String contentType, Long contentIdx
    );

    Comment findByCommContentTypeAndCommContentIdxAndCommUserIdx(
            String contentType, Long contentIdx, User userIdx
    );
}
