package com.watchapedia.watchpedia_user.model.repository.content;


import com.watchapedia.watchpedia_user.model.entity.content.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

    List<Webtoon> findByWebGenreContaining(String webGenre);
    List<Webtoon> findByWebTitleContaining(String searchKey);
}
