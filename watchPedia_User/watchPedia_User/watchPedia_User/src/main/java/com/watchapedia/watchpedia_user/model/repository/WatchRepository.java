package com.watchapedia.watchpedia_user.model.repository;

import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchRepository extends JpaRepository<Watch, Long> {
    Watch findByWatchContentTypeAndWatchContentIdxAndWatchUserIdx(
            String contentType, Long contentIdx, User user
    );
}
