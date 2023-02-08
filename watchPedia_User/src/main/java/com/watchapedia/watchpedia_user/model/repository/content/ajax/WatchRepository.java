package com.watchapedia.watchpedia_user.model.repository.content.ajax;

import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchRepository extends JpaRepository<Watch, Long> {
    Watch findByWatchContentTypeAndWatchContentIdxAndWatchUserIdx(
            String contentType, Long contentIdx, Long user
    );
}
