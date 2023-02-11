package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.dto.UserDto;
import com.watchapedia.watchpedia_user.model.dto.content.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.comment.Recomment;
import com.watchapedia.watchpedia_user.model.entity.content.Book;
import com.watchapedia.watchpedia_user.model.entity.content.Movie;
import com.watchapedia.watchpedia_user.model.entity.content.Tv;
import com.watchapedia.watchpedia_user.model.entity.content.Webtoon;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Watch;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Wish;
import com.watchapedia.watchpedia_user.model.network.request.UserRequestDto;
import com.watchapedia.watchpedia_user.model.network.response.NoticeResponse;
import com.watchapedia.watchpedia_user.model.network.response.Ratings;
import com.watchapedia.watchpedia_user.model.network.response.UserResponse;
import com.watchapedia.watchpedia_user.model.repository.NoticeRepository;
import com.watchapedia.watchpedia_user.model.repository.SearchRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.*;
import com.watchapedia.watchpedia_user.model.repository.content.BookRepository;
import com.watchapedia.watchpedia_user.model.repository.content.MovieRepository;
import com.watchapedia.watchpedia_user.model.repository.content.TvRepository;
import com.watchapedia.watchpedia_user.model.repository.content.WebtoonRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.HateRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.WatchRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

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
    private final WatchRepository watchRepository;
    private final NoticeRepository noticeRepository;
    private final CommentRepository commentRepository;
    private final HateRepository hateRepository;
    private final LikeRepository likeRepository;
    private final RecommentRepository recommentRepository;
    private final RelikeRepository relikeRepository;
    private final ReportRepository reportRepository;
    private final SearchRepository searchRepository;
    private final SpoilerRepository spoilerRepository;

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

    public List<NoticeResponse> noticeAll(){
        List<NoticeResponse> noticeList = noticeRepository.findByNtcStatusOrderByNtcIdxDesc("등록").stream().map(
                notice ->{
                    return NoticeResponse.of(notice.getNtcTitle(),notice.getNtcText(),notice.getNtcImagepath(),
                            notice.getNtcBtnColor(),notice.getNtcBtnText(),notice.getNtcBtnLink(),notice.getRegDate());
                }
        ).collect(Collectors.toList());
        return noticeList;
    }

    public UserResponse myPageUser(Long userIdx){
        List<Star> starList = starRepository.findByStarUserIdx(userIdx);
        int mov = 0; int tv = 0; int book = 0; int web = 0;
        for(Star star : starList){
            switch (star.getStarContentType()){
                case "movie" -> mov++;
                case "tv" -> tv++;
                case "book" -> book++;
                case "webtoon" -> web++;
            }
        }

        List<Wish> wishList = wishRepository.findByWishUserIdx(userIdx);
        int wishMov = 0; int wishTv = 0; int wishBook = 0; int wishWeb = 0;
        for(Wish wish : wishList){
            switch (wish.getWishContentType()){
                case "movie" -> wishMov++;
                case "tv" -> wishTv++;
                case "book" -> wishBook++;
                case "webtoon" -> wishWeb++;
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
        mv.put("wishSize",wishRepository.findByWishContentTypeAndWishUserIdx(contentType,userIdx).size());
        mv.put("watchSize",watchRepository.findByWatchContentTypeAndWatchUserIdx(contentType,userIdx).size());
        mv.put("content",ratingsList);
        mv.put("last",last);
        return mv;
    }

    public Map<String, Object> findRatingsStarPointAll(String contentType, Long userIdx, Pageable pageable){
        Map<String, Object> mv = new HashMap<>();
        Page<Star> starList5 = starRepository.findByStarContentTypeAndStarUserIdxAndStarPoint(contentType,userIdx, 5L,pageable);
        Page<Star> starList4 = starRepository.findByStarContentTypeAndStarUserIdxAndStarPoint(contentType,userIdx, 4L,pageable);
        Page<Star> starList3 = starRepository.findByStarContentTypeAndStarUserIdxAndStarPoint(contentType,userIdx, 3L,pageable);
        Page<Star> starList2 = starRepository.findByStarContentTypeAndStarUserIdxAndStarPoint(contentType,userIdx, 2L,pageable);
        Page<Star> starList1 = starRepository.findByStarContentTypeAndStarUserIdxAndStarPoint(contentType,userIdx, 1L,pageable);
        List<Ratings> ratingsList = new ArrayList<>();
        switch (contentType){
            case "movie" ->{
                for(Star star: starList5){
                    Movie movie = movieRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(movie.getMovIdx(),movie.getMovThumbnail(),movie.getMovTitle(),
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||movie.getMovWatch().contains("netflix")?true:false):false,
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||movie.getMovWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
                for(Star star: starList4){
                    Movie movie = movieRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(movie.getMovIdx(),movie.getMovThumbnail(),movie.getMovTitle(),
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||movie.getMovWatch().contains("netflix")?true:false):false,
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||movie.getMovWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
                for(Star star: starList3){
                    Movie movie = movieRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(movie.getMovIdx(),movie.getMovThumbnail(),movie.getMovTitle(),
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||movie.getMovWatch().contains("netflix")?true:false):false,
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||movie.getMovWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
                for(Star star: starList2){
                    Movie movie = movieRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(movie.getMovIdx(),movie.getMovThumbnail(),movie.getMovTitle(),
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||movie.getMovWatch().contains("netflix")?true:false):false,
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||movie.getMovWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
                for(Star star: starList1){
                    Movie movie = movieRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(movie.getMovIdx(),movie.getMovThumbnail(),movie.getMovTitle(),
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||movie.getMovWatch().contains("netflix")?true:false):false,
                            movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||movie.getMovWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
            }
            case "tv" ->{
                for(Star star: starList5){
                    Tv tv = tvRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(tv.getTvIdx(),tv.getTvThumbnail(),tv.getTvTitle(),
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||tv.getTvWatch().contains("netflix")?true:false):false,
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||tv.getTvWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
                for(Star star: starList4){
                    Tv tv = tvRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(tv.getTvIdx(),tv.getTvThumbnail(),tv.getTvTitle(),
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||tv.getTvWatch().contains("netflix")?true:false):false,
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||tv.getTvWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
                for(Star star: starList3){
                    Tv tv = tvRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(tv.getTvIdx(),tv.getTvThumbnail(),tv.getTvTitle(),
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||tv.getTvWatch().contains("netflix")?true:false):false,
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||tv.getTvWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
                for(Star star: starList2){
                    Tv tv = tvRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(tv.getTvIdx(),tv.getTvThumbnail(),tv.getTvTitle(),
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||tv.getTvWatch().contains("netflix")?true:false):false,
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||tv.getTvWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
                for(Star star: starList1){
                    Tv tv = tvRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(tv.getTvIdx(),tv.getTvThumbnail(),tv.getTvTitle(),
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||tv.getTvWatch().contains("netflix")?true:false):false,
                            tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||tv.getTvWatch().contains("https://watcha.com/watch/")?true:false):false,
                            star.getStarPoint()
                    ));
                }
            }
            case "book" ->{
                for(Star star: starList5){
                    Book book = bookRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(book.getBookIdx(),book.getBookThumbnail(),book.getBookTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
                for(Star star: starList4){
                    Book book = bookRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(book.getBookIdx(),book.getBookThumbnail(),book.getBookTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
                for(Star star: starList3){
                    Book book = bookRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(book.getBookIdx(),book.getBookThumbnail(),book.getBookTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
                for(Star star: starList2){
                    Book book = bookRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(book.getBookIdx(),book.getBookThumbnail(),book.getBookTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
                for(Star star: starList1){
                    Book book = bookRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(book.getBookIdx(),book.getBookThumbnail(),book.getBookTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
            }
            case "webtoon" ->{
                for(Star star: starList5){
                    Webtoon web = webtoonRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(web.getWebIdx(),web.getWebThumbnail(),web.getWebTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
                for(Star star: starList4){
                    Webtoon web = webtoonRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(web.getWebIdx(),web.getWebThumbnail(),web.getWebTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
                for(Star star: starList3){
                    Webtoon web = webtoonRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(web.getWebIdx(),web.getWebThumbnail(),web.getWebTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
                for(Star star: starList2){
                    Webtoon web = webtoonRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(web.getWebIdx(),web.getWebThumbnail(),web.getWebTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
                for(Star star: starList1){
                    Webtoon web = webtoonRepository.getReferenceById(star.getStarContentIdx());
                    ratingsList.add(Ratings.of(web.getWebIdx(),web.getWebThumbnail(),web.getWebTitle(),
                            false,false, star.getStarPoint()
                    ));
                }
            }
        }

        mv.put("content",ratingsList);
        mv.put("size5",starList5.getTotalElements());
        mv.put("size4",starList4.getTotalElements());
        mv.put("size3",starList3.getTotalElements());
        mv.put("size2",starList2.getTotalElements());
        mv.put("size1",starList1.getTotalElements());
        return mv;
    }

    public Map<String, Object> findRatingsStarPoint(String contentType, Long userIdx, Long starPoint, Pageable pageable){
        Map<String, Object> mv = new HashMap<>();
        Page<Star> starList = starRepository.findByStarContentTypeAndStarUserIdxAndStarPoint(contentType,userIdx, starPoint,pageable);
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

    public Map<String, Object> findWishWatch(String contentType, Long userIdx, String type, Pageable pageable){
        Map<String, Object> mv = new HashMap<>();
        List<Ratings> ratingsList = new ArrayList<>();
        boolean last = false;
        if(type.equals("wish")){
            Page<Wish> list = wishRepository.findByWishContentTypeAndWishUserIdx(contentType,userIdx,pageable);
            mv.put("size",list.getTotalElements());
            last = list.isLast();
            switch (contentType){
                case "movie" ->{
                    for(Wish wish: list){
                        Movie movie = movieRepository.getReferenceById(wish.getWishContentIdx());
                        ratingsList.add(Ratings.of(movie.getMovIdx(),movie.getMovThumbnail(),movie.getMovTitle(),
                                movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||movie.getMovWatch().contains("netflix")?true:false):false,
                                movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||movie.getMovWatch().contains("https://watcha.com/watch/")?true:false):false,
                                0L
                        ));
                    }
                }
                case "tv" ->{
                    for(Wish wish: list){
                        Tv tv = tvRepository.getReferenceById(wish.getWishContentIdx());
                        ratingsList.add(Ratings.of(tv.getTvIdx(),tv.getTvThumbnail(),tv.getTvTitle(),
                                tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||tv.getTvWatch().contains("netflix")?true:false):false,
                                tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||tv.getTvWatch().contains("https://watcha.com/watch/")?true:false):false,
                                0L
                        ));
                    }
                }
                case "book" ->{
                    for(Wish wish: list){
                        Book book = bookRepository.getReferenceById(wish.getWishContentIdx());
                        ratingsList.add(Ratings.of(book.getBookIdx(),book.getBookThumbnail(),book.getBookTitle(),
                                false,false, 0L
                        ));
                    }
                }
                case "webtoon" ->{
                    for(Wish wish: list){
                        Webtoon web = webtoonRepository.getReferenceById(wish.getWishContentIdx());
                        ratingsList.add(Ratings.of(web.getWebIdx(),web.getWebThumbnail(),web.getWebTitle(),
                                false,false, 0L
                        ));
                    }
                }
            }
            mv.put("content",ratingsList);
        }else{
            Page<Watch> list = watchRepository.findByWatchContentTypeAndWatchUserIdx(contentType,userIdx,pageable);
            mv.put("size",list.getTotalElements());
            last = list.isLast();
            switch (contentType){
                case "movie" ->{
                    for(Watch watch: list){
                        Movie movie = movieRepository.getReferenceById(watch.getWatchContentIdx());
                        ratingsList.add(Ratings.of(movie.getMovIdx(),movie.getMovThumbnail(),movie.getMovTitle(),
                                movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||movie.getMovWatch().contains("netflix")?true:false):false,
                                movie.getMovWatch()!=null? (movie.getMovWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||movie.getMovWatch().contains("https://watcha.com/watch/")?true:false):false,
                                0L
                        ));
                    }
                }
                case "tv" ->{
                    for(Watch watch: list){
                        Tv tv = tvRepository.getReferenceById(watch.getWatchContentIdx());
                        ratingsList.add(Ratings.of(tv.getTvIdx(),tv.getTvThumbnail(),tv.getTvTitle(),
                                tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0b")||tv.getTvWatch().contains("netflix")?true:false):false,
                                tv.getTvWatch()!=null? (tv.getTvWatch().contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhd")||tv.getTvWatch().contains("https://watcha.com/watch/")?true:false):false,
                                0L
                        ));
                    }
                }
                case "book" ->{
                    for(Watch watch: list){
                        Book book = bookRepository.getReferenceById(watch.getWatchContentIdx());
                        ratingsList.add(Ratings.of(book.getBookIdx(),book.getBookThumbnail(),book.getBookTitle(),
                                false,false, 0L
                        ));
                    }
                }
                case "webtoon" ->{
                    for(Watch watch: list){
                        Webtoon web = webtoonRepository.getReferenceById(watch.getWatchContentIdx());
                        ratingsList.add(Ratings.of(web.getWebIdx(),web.getWebThumbnail(),web.getWebTitle(),
                                false,false, 0L
                        ));
                    }
                }
            }
            mv.put("content",ratingsList);
        }

        mv.put("last",last);
        return mv;
    }

    public void deleteUser(Long userIdx){
        List<Comment> commentList = new ArrayList<>();
        try{
            commentList = commentRepository.findByCommUserIdx(userIdx);
        }catch (Exception e){
            System.out.println("코멘트가 없음");
        }
        for(Comment comm : commentList){
                try{
                    likeRepository.deleteAll(likeRepository.findByLikeCommentIdx(comm.getCommIdx()));
                }catch(Exception e){
                    System.out.println("코멘트에 좋아요가 없음");
                }
                try{
                    spoilerRepository.delete(spoilerRepository.findBySpoCommentIdx(comm.getCommIdx()));
                }catch (Exception e){
                    System.out.println("스포 댓글이 아님");
                }
                List<Recomment> recomments = new ArrayList<>();
                try{
                    recomments = recommentRepository.findByRecommCommIdx(comm.getCommIdx());
                }catch(Exception e){
                    System.out.println("리코멘트가 없음");
                }
                for(Recomment recomment : recomments){
                    try{
                        relikeRepository.deleteAll(relikeRepository.findByRelikeRecommIdx(recomment.getRecommIdx()));
                    }catch(Exception e){
                        System.out.println("코멘트에 라이크가 없음");
                    }
                }
                try{
                    recommentRepository.deleteAll(recomments);
                }catch(Exception e){
                    System.out.println("리코멘트가 없음");
                }
            }
        try{
            commentRepository.deleteAll(commentRepository.findByCommUserIdx(userIdx));
        }catch (Exception e){
            System.out.println("코멘트가 없음");
        }

        try{
            likeRepository.deleteAll(likeRepository.findByLikeUserIdx(userIdx));
        }catch(Exception e){
            System.out.println("좋아요 단 적 없음");
        }
        try{
            relikeRepository.deleteAll(relikeRepository.findByRelikeUserIdx(userIdx));
        }catch(Exception e){
            System.out.println("좋아요 단 적 없음");
        }
        try{
            starRepository.deleteAll(starRepository.findByStarUserIdx(userIdx));
        }catch(Exception e){
            System.out.println("별점 준 적 없음");
        }
        try{
            watchRepository.deleteAll(watchRepository.findByWatchUserIdx(userIdx));
        }catch(Exception e){
            System.out.println("보는 중 콘텐츠 없음");
        }
        try{
            wishRepository.deleteAll(wishRepository.findByWishUserIdx(userIdx));
        }catch(Exception e){
            System.out.println("보고싶어요 콘텐츠 없음");
        }
        try{
            hateRepository.deleteAll(hateRepository.findByHateUserIdx(userIdx));
        }catch(Exception e){
            System.out.println("관심없어요 콘텐츠 없음");
        }
        try{
            searchRepository.deleteAll(searchRepository.findByUser(userIdx));
        }catch(Exception e){
            System.out.println("검색 기록 없음");
        }

        userRepository.delete(userRepository.findById(userIdx).get());

    }
}
