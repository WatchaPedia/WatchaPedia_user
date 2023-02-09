package com.watchapedia.watchpedia_user.model.repository.content;

import com.watchapedia.watchpedia_user.model.entity.content.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByMovGenreContaining(String genre);
    List<Movie> findByMovTitleContaining(String movieTitle);
    List<Movie> findByMovMakingDate(String date);
    List<Movie> findByMovCountryContaining(String country);
}
