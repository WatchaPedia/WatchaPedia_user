package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.dto.UserDto;
import com.watchapedia.watchpedia_user.model.dto.content.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.content.Book;
import com.watchapedia.watchpedia_user.model.entity.content.Movie;
import com.watchapedia.watchpedia_user.model.entity.content.Tv;
import com.watchapedia.watchpedia_user.model.entity.content.Webtoon;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Wish;
import com.watchapedia.watchpedia_user.model.network.request.UserRequestDto;
import com.watchapedia.watchpedia_user.model.network.response.Ratings;
import com.watchapedia.watchpedia_user.model.network.response.UserResponse;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.content.BookRepository;
import com.watchapedia.watchpedia_user.model.repository.content.MovieRepository;
import com.watchapedia.watchpedia_user.model.repository.content.TvRepository;
import com.watchapedia.watchpedia_user.model.repository.content.WebtoonRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final StarRepository starRepository;
    private final WishRepository wishRepository;
    private final TvRepository tvRepository;
    private final BookRepository bookRepository;
    private final WebtoonRepository webtoonRepository;

    public User findUser(Long userIdx){
        return userRepository.getReferenceById(userIdx);
    }

    public User findEmail(String userEmail){
     return userRepository.findByUserEmail(userEmail);
    }

    public UserRequestDto login(UserRequestDto userRequestDto){
        Optional<User> byUserEmail = userRepository.findByUserEmailAndUserStatusAndUserPw(userRequestDto.userEmail(), "활동", userRequestDto.userPw());
        if (byUserEmail.isPresent()) {
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            User user = byUserEmail.get();

//            if (user.getUserPw().equals(userRequestDto.userPw())) {
//                // 비밀번호 일치
//                // entity -> dto -> request 변환 후 리턴
            UserRequestDto dto = UserRequestDto.from(UserDto.from(user));
            return dto;
//            } else {
//                // 비밀번호 불일치(로그인실패)
//                return null;
//            }
        } else {
            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }

    }

    public String save(UserRequestDto userRequestDto) {
        UserDto userDto = UserDto.requestReceive(userRequestDto);
        User user = User.of(userDto);
        User userEmail_check = this.userRepository.findByUserEmail(userRequestDto.userEmail());
        if (userEmail_check != null) {
            return "USEREMAILALREADYEXIST";
        } else {
            User userSsn_check = this.userRepository.findByUserSsn1AndUserSsn2(userRequestDto.userSsn1(), userRequestDto.userSsn2());
            if (userSsn_check != null) {
                return "USEREMSSNALREADYEXIST";
            } else {
                this.userRepository.save(user);
                return "SUCCESS";
            }
        }
    }

    public UserResponse myPageUser(Long userIdx){
        List<Star> starList = starRepository.findByStarUserIdx(userIdx);
        int mov = 0; int tv = 0; int book = 0; int web = 0;
        for(Star star : starList){
            switch (star.getStarContentType()){
                case "movie" -> mov++;
                case "tv" -> tv++;
                case "book" -> book++;
                case "web" -> web++;
            }
        }

        List<Wish> wishList = wishRepository.findByWishUserIdx(userIdx);
        int wishMov = 0; int wishTv = 0; int wishBook = 0; int wishWeb = 0;
        for(Wish wish : wishList){
            switch (wish.getWishContentType()){
                case "movie" -> wishMov++;
                case "tv" -> wishTv++;
                case "book" -> wishBook++;
                case "web" -> wishWeb++;
            }
        }

        return UserResponse.myPageFrom(UserDto.from(userRepository.getReferenceById(userIdx)),
                mov,tv,book,web,wishMov,wishTv,wishBook,wishWeb);
    }

    public Map<String, Object> findRatings(String contentType, Long userIdx, Pageable pageable){
        Map<String, Object> mv = new HashMap<>();
        Page<Star> starList = starRepository.findByStarContentTypeAndStarUserIdx(contentType,userIdx,pageable);
        mv.put("size",starList.getTotalElements());
        boolean last = starList.isLast();
        List<Ratings> ratingsList = new ArrayList<>();
        switch (contentType){
            case "movie" ->{
                for(Star star: starList){
                    Movie movie = movieRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(movie.getMovIdx(),movie.getMovThumbnail(),movie.getMovTitle(),
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||movie.getMovWatch().contains("netflix")?true:false):false,
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||movie.getMovWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
            }
            case "tv" ->{
                for(Star star: starList){
                    Tv tv = tvRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(tv.getTvIdx(),tv.getTvThumbnail(),tv.getTvTitle(),
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||tv.getTvWatch().contains("netflix")?true:false):false,
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||tv.getTvWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
            }
            case "book" ->{
                for(Star star: starList){
                    Book book = bookRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(book.getBookIdx(),book.getBookThumbnail(),book.getBookTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
            }
            case "webtoon" ->{
                for(Star star: starList){
                    Webtoon web = webtoonRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(web.getWebIdx(),web.getWebThumbnail(),web.getWebTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
            }
        }

        mv.put("content",ratingsList);
        mv.put("last",last);
        return mv;
    }

    public Map<String, Object> findRatingsStarPoint(String contentType, Long userIdx, Long starPoint, Pageable pageable){
        Map<String, Object> mv = new HashMap<>();
        Page<Star> starList = starRepository.findByStarContentTypeAndStarUserIdxAndStarPoint("movie",userIdx, starPoint,pageable);
        boolean last = starList.isLast();
        List<Ratings> ratingsList = new ArrayList<>();
        switch (contentType){
            case "movie" ->{
                for(Star star: starList){
                    Movie movie = movieRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(movie.getMovIdx(),movie.getMovThumbnail(),movie.getMovTitle(),
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||movie.getMovWatch().contains("netflix")?true:false):false,
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||movie.getMovWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
            }
            case "tv" ->{
                for(Star star: starList){
                    Tv tv = tvRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(tv.getTvIdx(),tv.getTvThumbnail(),tv.getTvTitle(),
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||tv.getTvWatch().contains("netflix")?true:false):false,
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||tv.getTvWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
            }
            case "book" ->{
                for(Star star: starList){
                    Book book = bookRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(book.getBookIdx(),book.getBookThumbnail(),book.getBookTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
            }
            case "webtoon" ->{
                for(Star star: starList){
                    Webtoon web = webtoonRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(web.getWebIdx(),web.getWebThumbnail(),web.getWebTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
            }
        }

        mv.put("content",ratingsList);
        mv.put("last",last);
        return mv;
    }

    public Map<String, Object> findRatingsStarPoint(String contentType, Long userIdx, Long starPoint, Pageable pageable){
        Map<String, Object> mv = new HashMap<>();
        Page<Star> starList = starRepository.findByStarContentTypeAndStarUserIdxAndStarPoint("movie",userIdx, starPoint,pageable);
        boolean last = starList.isLast();
        List<Ratings> ratingsList = new ArrayList<>();
        switch (contentType){
            case "movie" ->{
                for(Star star: starList){
                    Movie movie = movieRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(movie.getMovIdx(),movie.getMovThumbnail(),movie.getMovTitle(),
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||movie.getMovWatch().contains("netflix")?true:false):false,
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||movie.getMovWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
            }
            case "tv" ->{
                for(Star star: starList){
                    Tv tv = tvRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(tv.getTvIdx(),tv.getTvThumbnail(),tv.getTvTitle(),
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||tv.getTvWatch().contains("netflix")?true:false):false,
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||tv.getTvWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
            }
            case "book" ->{
                for(Star star: starList){
                    Book book = bookRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(book.getBookIdx(),book.getBookThumbnail(),book.getBookTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
            }
            case "webtoon" ->{
                for(Star star: starList){
                    Webtoon web = webtoonRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(web.getWebIdx(),web.getWebThumbnail(),web.getWebTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
            }
        }

        mv.put("content",ratingsList);
        mv.put("last",last);
        return mv;
    }
}
