package com.watchapedia.watchpedia_user.model.repository;

import com.watchapedia.watchpedia_user.model.entity.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

    List<Webtoon> findByWebGenre(String webGenre);

    Webtoon findByWebIdx(Long WebIdx);
}
