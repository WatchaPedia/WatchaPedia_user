package com.watchapedia.watchpedia_user.model.repository.comment;

import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<Comment> findByCommContentTypeAndCommContentIdx(
            String contentType, Long contentIdx, Pageable pageable
    );

    List<Comment> findByCommUserIdx(Long userIdx);
}
