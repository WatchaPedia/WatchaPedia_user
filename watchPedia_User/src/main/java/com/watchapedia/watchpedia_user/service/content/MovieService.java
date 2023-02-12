package com.watchapedia.watchpedia_user.service.content;

import com.jayway.jsonpath.internal.function.numeric.Max;
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
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class MovieService {

    final StarRepository starRepository;
    final MovieRepository movieRepository;
    @Transactional(readOnly = true)
    public MovieResponse movieView(Long movieIdx){
        MovieDto mov = movieRepository.findById(movieIdx).map(MovieDto::from).get();
//        MovieDto mov = MovieDto.from(movieRepository.findByMovIdx(movieIdx));
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


    // 무비 메인페이지 별점 등록 및 출력
    @Transactional(readOnly = true)
    public List<MovieDto> movies() {
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

    /* 별점 높은순 출력, 어느정도 인원이 평가해야(타임리프 적용) 0명이 평가한 경우 한명이 바로 5점을 주면
        1등이 나오기 때문*/
    @Transactional(readOnly = true)
    public List<MovieDto> movieStar() {
        //평균 구하기
        List<MovieDto> result = new ArrayList<>();
        // 평균점수가 최소 4.2 이상인 : 설정을 안할 경우 데이터가 없을경우 1점도 들어갈 수 있음
        List<MovieDto> result2 = new ArrayList<>();
        // 평가한 인원이 최소 5명인 : 평가자가 0명인 경우 처음 평가한 이가 5점을 주면 바로 최고 점수
        List<MovieDto> result3 = new ArrayList<>();
        List<Movie> movieStar = movieRepository.findAll();
        for(Movie m : movieStar){//평균
            double sum = 0;
            int starCount = 0;
            for(Star star : m.getStar()){
                sum += star.getStarPoint();
                starCount = m.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(MovieDto.from(m, avg));
        }// 최소 점수
        for(MovieDto m : result){
            if(m.avg() >= 4.2){
                result2.add(m);
            }
        }// 최소 인원
        for(MovieDto m : result2){
            if(m.starList().size()>=3){
                result3.add(m);

            }
        }// 평균 순으로 1위부터 뽑기

        result3 = result3.stream().sorted(Comparator.comparing(MovieDto::avg)
                .reversed()).collect(Collectors.toList());
        return result3;
    }

    // 관리자 등록순 리스트 출력

    @Transactional(readOnly = true)
    public List<MovieDto> movieDtos() {
        List<MovieDto> result = new ArrayList<>();
        List<MovieDto> result2 = new ArrayList<>(10);
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
        for(int i = 0; i < 10; i++){
            result2.add(result.get(i));
        }
        return result2;
    }

    // 별점 매기지 않은 경우 : 내가 아직 보지 않은 영화 또는 평가하지 않은 영화
    @Transactional(readOnly = true)
    public List<MovieDto> movieZero() {
        List<MovieDto> result = new ArrayList<>();
        List<MovieDto> result2 = new ArrayList<>();
        List<MovieDto> result3 = new ArrayList<>(10);

        List<Movie> movieStar = movieRepository.findAll();

        for(Movie m : movieStar){
            double sum = 0;
            int starCount = 0;
            for(Star star : m.getStar()){
                sum += star.getStarPoint();
                starCount = m.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(MovieDto.from(m, avg));
        }
        for(MovieDto m : result){
            if(m.avg() == 0.0){
                result2.add(m);
            }
        }
        for(int i = 0; i < 10; i++){
            result3.add(result2.get(i));
        }
        return result3;
    }

    //영화이름 기준 출력(나홀로 집에)
    @Transactional(readOnly = true)
    public List<MovieDto> movies2(String movieTitle) {
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

    //최근개봉영화
    @Transactional(readOnly = true)
    public List<MovieDto> movies3() {
        //빈 웹툰리스폰스 리스트
        List<MovieDto> result = new ArrayList<>();
        List<MovieDto> result2 = new ArrayList<>();
        List<MovieDto> result3 = new ArrayList<>(10);
        List<Movie> movieList2 = movieRepository.findAll(Sort.by(Sort.Direction.DESC,"movMakingDate"));

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
        for(MovieDto m : result){
            if(m.avg() == 0.0){
                result2.add(m);
            }
        }
        for(int i = 0; i < 10; i++){
            result3.add(result2.get(i));
        }
        return result3;
    }
    // 나라기준 출력
    @Transactional(readOnly = true)
    public List<MovieDto> searchCountry(String country) {
        //빈 웹툰리스폰스 리스트
        List<MovieDto> result = new ArrayList<>();
        List<MovieDto> result2 = new ArrayList<>(10);
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
        for(int i = 0; i < 10; i++){
            result2.add(result.get(i));
        }
        return result2;
    }
    // 나라&장르 랜덤출력 하기위한 부분
    @Transactional(readOnly = true)
    public List<MovieDto> searchCri(String genre, String country) {
        //빈 웹툰리스폰스 리스트
        List<MovieDto> result = new ArrayList<>();
        List<MovieDto> result2 = new ArrayList<>(10);

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

    //장르기준 출력
    @Transactional(readOnly = true)
    public List<MovieDto> searchDrama(String genre) {
        //빈 웹툰리스폰스 리스트
        List<MovieDto> result = new ArrayList<>();
        List<MovieDto> result2 = new ArrayList<>(10);
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
        for(int i = 0; i < 10; i++){
            result2.add(result.get(i));
        }
        return result2;
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
            if(mov.movWatch() != null){
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
