package com.watchapedia.watchpedia_user.model.repository.content.ajax;

import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    Wish findByWishContentTypeAndWishContentIdxAndWishUserIdx(
            String contentType, Long contentIdx, Long user
    );

    Page<Wish> findByWishContentTypeAndWishUserIdx(
            String contentType, Long userIdx, Pageable pageable
    );
    List<Wish> findByWishContentTypeAndWishUserIdx(
            String contentType, Long userIdx
    );
    List<Wish> findByWishUserIdx(Long userIdx);
}
