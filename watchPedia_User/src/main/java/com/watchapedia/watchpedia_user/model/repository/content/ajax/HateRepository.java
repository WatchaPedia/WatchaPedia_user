package com.watchapedia.watchpedia_user.model.repository.content.ajax;

import com.watchapedia.watchpedia_user.model.entity.content.ajax.Hate;
import com.watchapedia.watchpedia_user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HateRepository extends JpaRepository<Hate, Long> {
    Hate findByHateUserIdxAndHateContentTypeAndHateContentIdx(
            Long user, String contentType, Long contentIdx
    );
    List<Hate> findByHateUserIdx(Long userIdx);
}
