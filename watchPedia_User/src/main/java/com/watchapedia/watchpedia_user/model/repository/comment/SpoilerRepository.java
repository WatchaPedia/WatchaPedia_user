package com.watchapedia.watchpedia_user.model.repository.comment;

import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.comment.Spoiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpoilerRepository extends JpaRepository<Spoiler, Long> {
    Spoiler findBySpoCommentIdx(Long comment);
}
