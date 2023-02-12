package com.watchapedia.watchpedia_user.model.repository.content;

import com.watchapedia.watchpedia_user.model.entity.content.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByMovGenreContainingAndMovCountryContaining(String genre, String country);

    List<Movie> findByMovGenreContaining(String genre);

    List<Movie> findByMovTitleContaining(String movieTitle);
    List<Movie> findByMovMakingDate(String date);
    List<Movie> findByMovCountryContaining(String country);

    @Query(value = "SELECT movCountry, COUNT(movCountry) FROM tbMovie WHERE movIdx in :idxs GROUP BY movCountry HAVING COUNT(movCountry) > 0")
    List<String> findCountryCnt(@Param("idxs") List<Long> idxs);
    @Query(value = "SELECT movGenre, COUNT(movGenre) FROM tbMovie WHERE movIdx in :idxs GROUP BY movGenre HAVING COUNT(movGenre) > 0")
    List<String> findGenreCnt(@Param("idxs") List<Long> idxs);
    @Query(value = "SELECT movPeople FROM tbMovie WHERE movIdx in :idxs")
    List<String> findPeopleList(@Param("idxs") List<Long> idxs);
    @Query(value = "SELECT movTime FROM tbMovie WHERE movIdx in :idxs")
    List<String> timeList(@Param("idxs") List<Long> idxs);
    @Query(value = "SELECT movTitle FROM tbMovie WHERE movPeople LIKE :idx ORDER BY movIdx DESC")
    List<String> actorMovie(@Param("idx") String idx, Pageable pageable);
}
