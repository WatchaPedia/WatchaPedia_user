package com.watchapedia.watchpedia_user.model.repository.content.ajax;

import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
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

    @Query(value = "select avg(starPoint) from tb_star where starContentIdx=:contentIdx and starContentType=:contentType")
    double findByContentStarAvg(@Param("contentIdx") Long contentIdx, @Param("contentType") String contentType);
    @Query(value = "select avg(starPoint) from tb_star where starUserIdx=:idx")
    double findByStarAvg(@Param("idx") Long idx);
    @Query(value = "select count(starPoint) from tb_star where starUserIdx=:idx")
    int findByStarCount(@Param("idx") Long idx);
    @Query(value = "select starContentIdx from tb_star where starUserIdx=:idx and starContentType='movie'")
    List<Long> findStarMovie(@Param("idx") Long idx);
    @Query(value = "select count(starPoint) from tb_star where starUserIdx=:idx and starPoint=:point")
    double findByStarPointCount(@Param("idx") Long idx, @Param("point") Long point);
    @Query(value = "select max(starPoint) from tb_star where starUserIdx=:idx")
    int findByStarMax(@Param("idx") Long idx);
}
