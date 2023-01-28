package com.watchapedia.watchpedia_user.model.repository;

import com.watchapedia.watchpedia_user.model.entity.Comment;
import com.watchapedia.watchpedia_user.model.entity.Recomment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommentRepository extends JpaRepository<Recomment,Long> {
    List<Recomment> findByRecommCommIdx(Comment comment);
}
