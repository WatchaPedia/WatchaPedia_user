package com.watchapedia.watchpedia_user.service.content;

import com.watchapedia.watchpedia_user.model.dto.comment.CommentDto;
import com.watchapedia.watchpedia_user.model.dto.content.MovieDto;
import com.watchapedia.watchpedia_user.model.dto.comment.RecommentDto;
import com.watchapedia.watchpedia_user.model.dto.content.TvDto;
import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.comment.Spoiler;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.content.Movie;
import com.watchapedia.watchpedia_user.model.entity.content.Tv;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.MovieResponse;
import com.watchapedia.watchpedia_user.model.network.response.comment.RecommentResponse;
import com.watchapedia.watchpedia_user.model.repository.comment.*;
import com.watchapedia.watchpedia_user.model.repository.content.MovieRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MovieService {
    final StarRepository starRepository;
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

//    @Transactional(readOnly = true) //index 페이지 출력
//    public List<Movie> searchMovies() {
//        return movieRepository.findAll();
//    }

    // 무비 메인페이지 별점 등록 및 출력
    @Transactional(readOnly = true)
    public List<MovieDto> movies() {
        //빈 웹툰리스폰스 리스트
        List<MovieDto> result = new ArrayList<>();

        List<Movie> movieList = movieRepository.findAll();

        for(Movie movie : movieList){
            double sum = 0;
            int starCount = 0;
            for(Star star : movie.getStar()){
                sum += star.getStarPoint();
                starCount = movie.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(MovieDto.from(movie, avg));
        }
        return result;
    }


    @Transactional(readOnly = true)
    public List<MovieDto> movieDtos() {
        //빈 웹툰리스폰스 리스트
        List<MovieDto> result = new ArrayList<>();

        List<Movie> movieDtos = movieRepository.findAll(Sort.by(Sort.Direction.DESC,"movIdx"));

        for(Movie m : movieDtos){
            double sum = 0;
            int starCount = 0;
            for(Star star : m.getStar()){
                sum += star.getStarPoint();
                starCount = m.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(MovieDto.from(m, avg));
        }
        return result;
    }


    //영화이름 기준 출력(나홀로 집에)
    @Transactional(readOnly = true)
    public List<MovieDto> movies2(String movieTitle) {
        //빈 웹툰리스폰스 리스트
        List<MovieDto> result = new ArrayList<>();

        List<Movie> movieList2 = movieRepository.findByMovTitleContaining(movieTitle);

        for(Movie m : movieList2){
            double sum = 0;
            int starCount = 0;
            for(Star star : m.getStar()){
                sum += star.getStarPoint();
                starCount = m.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(MovieDto.from(m, avg));
        }
        return result;
    }
    //영화이름 기준 출력(아이언 맨)
    @Transactional(readOnly = true)
    public List<MovieDto> Irons(String movieTitle) {
        //빈 웹툰리스폰스 리스트
        List<MovieDto> result = new ArrayList<>();

        List<Movie> movieList2 = movieRepository.findByMovTitleContaining(movieTitle);

        for(Movie m : movieList2){
            double sum = 0;
            int starCount = 0;
            for(Star star : m.getStar()){
                sum += star.getStarPoint();
                starCount = m.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(MovieDto.from(m, avg));
        }
        return result;
    }

    //년도 기준 출력
    @Transactional(readOnly = true)
    public List<MovieDto> movies3(String movieMakingDate) {
        //빈 웹툰리스폰스 리스트
        List<MovieDto> result = new ArrayList<>();

        List<Movie> movieList2 = movieRepository.findByMovMakingDate(movieMakingDate);

        for(Movie m : movieList2){
            double sum = 0;
            int starCount = 0;
            for(Star star : m.getStar()){
                sum += star.getStarPoint();
                starCount = m.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(MovieDto.from(m, avg));
        }
        return result;
    }
    // 나라기준 출력
    @Transactional(readOnly = true)
    public List<MovieDto> searchCountry(String country) {
        //빈 웹툰리스폰스 리스트
        List<MovieDto> result = new ArrayList<>();

        List<Movie> movieList2 = movieRepository.findByMovCountryContaining(country);

        for(Movie m : movieList2){
            double sum = 0;
            int starCount = 0;
            for(Star star : m.getStar()){
                sum += star.getStarPoint();
                starCount = m.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(MovieDto.from(m, avg));
        }
        return result;
    }
    // 컨텐츠 -드라마
    @Transactional(readOnly = true)
    public List<MovieDto> searchCri(String genre, String country) {
        //빈 웹툰리스폰스 리스트
        List<MovieDto> result = new ArrayList<>();

        List<Movie> movieList2 = movieRepository.findByMovGenreContainingAndMovCountryContaining(genre, country);

        for(Movie m : movieList2){
            double sum = 0;
            int starCount = 0;
            for(Star star : m.getStar()){
                sum += star.getStarPoint();
                starCount = m.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(MovieDto.from(m, avg));
        }
        return result;
    }

    //컨텐츠 -범죄
    @Transactional(readOnly = true)
    public List<MovieDto> searchDrama(String genre) {
        //빈 웹툰리스폰스 리스트
        List<MovieDto> result = new ArrayList<>();

        List<Movie> movieList2 = movieRepository.findByMovGenreContaining(genre);

        for(Movie m : movieList2){
            double sum = 0;
            int starCount = 0;
            for(Star star : m.getStar()){
                sum += star.getStarPoint();
                starCount = m.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(MovieDto.from(m, avg));
        }
        return result;
    }



    //사용자-인물페이지--------------------------------------------------------------------------------------------------------

    @Transactional(readOnly = true)
    public MovieResponse movieWithRole(Long movieIdx, Long perIdx){
        MovieDto mov = movieRepository.findById(movieIdx).map(MovieDto::fromis).get();

        List<Star> starResponseList = starRepository.findByStarContentTypeAndStarContentIdx("movie", mov.movIdx());
        int starCount= starResponseList.size();
        float starPoint = 0;
        for(Star star : starResponseList){
            starPoint = starPoint + (star.getStarPoint()).intValue();
        }
        float starAvg = 0;
        if(starCount != 0){
            float avg = (starPoint / starCount);
            float avg2 = (float) ((avg*100)/100.0);
            starAvg = (float)(Math.round(avg2*10)/10.0);
        }

        boolean isWatcha = false;
        boolean isNetflix = false;

        try{
            if(!mov.movWatch().isEmpty()){
                String[] movieWatch = mov.movWatch().split(",");
                for(String movWatch : movieWatch){
                    if(movWatch.contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhdGNoL21")){
                        isWatcha = true;
                    }
                    if(movWatch.contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0bGUvOD")){
                        isNetflix = true;
                    }
                }

            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }


        int num = mov.movPeople().indexOf(String.valueOf(perIdx));  // 특정문자 => 문자열로 변환 => 동일한 부분을 찾기(영화 사람에서) => 결과를 인덱스로 반환 => 다시 숫자형으로 변환
        String moviePersonRole = mov.movPeople().substring(num+1);  // 숫자 + ( 이후이므로 +1를 함 => (시작번을 포함해서)부터 자르기 => 00 | 00), 숫자(00 | 00)
        String role2 = "";
        String role3 = "";
        String role = "";
        if(moviePersonRole.length() > -1){
            String[] movieRoles = moviePersonRole.split("\\)");     // String 안에 이스케이프 문자인 \를 써 주려면 \\라고 써 줘야 한다. 따라서 \\라고 쓰는 것이다. 그래서 \\)이라고 쓰면 정규식 쪽에서는 \)라고 인식을 하고 실제 )을 찾게 되는 것
            role2 = movieRoles[0];
            String[] movieRoles2 = role2.split("\\|");
            role3 = movieRoles2[0];
            String[] movieRoles3 = role3.split("\\(");
            role = movieRoles3[1];
        }

        return MovieResponse.fromis(mov, role, starAvg, isWatcha, isNetflix);
    }

}
