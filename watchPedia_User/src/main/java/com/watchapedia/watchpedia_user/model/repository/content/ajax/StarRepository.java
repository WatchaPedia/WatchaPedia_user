package com.watchapedia.watchpedia_user.model.repository.content.ajax;

import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StarRepository extends JpaRepository<Star, Long> {
    Star findByStarContentTypeAndStarContentIdxAndStarUserIdx(
            String starContentType, Long starContentIdx, Long starUserIdx
    );

    List<Star> findByStarContentTypeAndStarUserIdx(String contentType, Long userIdx);

    List<Star> findByStarContentTypeAndStarContentIdx(
            String starContentType, Long starContentIdx
    );
    List<Star> findAllByStarUserIdx(Long starUserIdx);
}
