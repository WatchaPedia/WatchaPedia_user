package com.watchapedia.watchpedia_user.service.content;

import com.watchapedia.watchpedia_user.model.dto.comment.CommentDto;
import com.watchapedia.watchpedia_user.model.dto.content.MovieDto;
import com.watchapedia.watchpedia_user.model.dto.comment.RecommentDto;
import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.comment.Spoiler;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.content.Movie;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.MovieResponse;
import com.watchapedia.watchpedia_user.model.network.response.comment.RecommentResponse;
import com.watchapedia.watchpedia_user.model.repository.comment.*;
import com.watchapedia.watchpedia_user.model.repository.content.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MovieService {

    final MovieRepository movieRepository;
    @Transactional(readOnly = true)
    public MovieResponse movieView(Long movieIdx){
        MovieDto mov = movieRepository.findById(movieIdx).map(MovieDto::from).get();
        return MovieResponse.from(mov);
    }

    @Transactional(readOnly = true)
    public List<MovieResponse> similarGenre(String genre, Long movieIdx){
        List<MovieResponse> result = new ArrayList<>();

        List<Long> movieIdxList = new ArrayList<>(16);
        if(genre.contains("/")){
            List<String> genreList = Arrays.stream(genre.split("/")).toList();
            HashMap<Long, Integer> containMovie = new HashMap<>();
            for(String idx: genreList){
                for(Movie mov: movieRepository.findByMovGenreContaining(idx)){
                    containMovie.put(mov.getMovIdx(),
                            containMovie.get(mov.getMovIdx()) != null ?
                                    containMovie.get(mov.getMovIdx()) + 1 : 1
                    );
                }
            }
            List<Map.Entry<Long, Integer>> entryList = new LinkedList<>(containMovie.entrySet());
            entryList.sort(((o1, o2) -> o2.getValue() - o1.getValue()));
            for(Map.Entry<Long, Integer> entry : entryList){
                movieIdxList.add(entry.getKey());
            }
        }else{
            for(Movie idx: movieRepository.findByMovGenreContaining(genre)){
                movieIdxList.add(idx.getMovIdx());
            }
        }

        for(Long idx: movieIdxList){
            if(idx == movieIdx) continue;
            MovieDto dto = MovieDto.from(movieRepository.getReferenceById(idx));
            double sum = 0;

            if(dto.starList().size() > 0){
                for(Star star: dto.starList()){
                    sum += star.getStarPoint();
                }
                result.add(MovieResponse.of(dto.movIdx(), dto.movThumbnail(),dto.movTitle(),dto.movWatch(),Math.round((sum / dto.starList().size()) * 10.0) / 10.0));
            }else{
                result.add(MovieResponse.of(dto.movIdx(), dto.movThumbnail(),dto.movTitle(),dto.movWatch(),0.0));
            }
        }
        return result;
    }

}
