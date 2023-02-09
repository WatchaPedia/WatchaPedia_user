package com.watchapedia.watchpedia_user.model.repository.content.ajax;

import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StarRepository extends JpaRepository<Star, Long> {
    Star findByStarContentTypeAndStarContentIdxAndStarUserIdx(
            String starContentType, Long starContentIdx, Long starUserIdx
    );

    List<Star> findByStarContentTypeAndStarUserIdx(String contentType, Long userIdx);
    Page<Star> findByStarContentTypeAndStarUserIdx(String contentType, Long userIdx, Pageable pageable);
    Page<Star> findByStarContentTypeAndStarUserIdxAndStarPoint(String contentType, Long userIdx, Long starPoint, Pageable pageable);
    List<Star> findByStarContentTypeAndStarContentIdx(
            String starContentType, Long starContentIdx
    );

    List<Star> findByStarUserIdx(Long userIdx);
}
