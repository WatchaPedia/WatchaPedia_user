package com.watchapedia.watchpedia_user.model.repository;

import com.watchapedia.watchpedia_user.model.dto.PersonLikeDto;
import com.watchapedia.watchpedia_user.model.entity.Person;
import com.watchapedia.watchpedia_user.model.entity.PersonLike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonLikeRepository extends JpaRepository<PersonLike, Long> {
    List<PersonLike> findByPerIdx(Long perIdx);
    List<PersonLike> findByUserIdx(Long userIdx);

    PersonLikeDto findByUserIdxAndPerIdx(Long userId, Long perIdx);



}
