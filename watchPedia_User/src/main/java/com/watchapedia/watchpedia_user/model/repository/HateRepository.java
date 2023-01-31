package com.watchapedia.watchpedia_user.model.repository;

import com.watchapedia.watchpedia_user.model.entity.Hate;
import com.watchapedia.watchpedia_user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HateRepository extends JpaRepository<Hate, Long> {
    Hate findByHateUserIdxAndHateContentTypeAndHateContentIdx(
            User user, String contentType, Long contentIdx
    );
}
