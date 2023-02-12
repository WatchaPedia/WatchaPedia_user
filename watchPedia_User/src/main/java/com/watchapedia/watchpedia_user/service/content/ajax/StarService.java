package com.watchapedia.watchpedia_user.service.content.ajax;

import com.watchapedia.watchpedia_user.model.dto.content.MovieDto;
import com.watchapedia.watchpedia_user.model.dto.content.ajax.StarDto;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.content.Book;
import com.watchapedia.watchpedia_user.model.entity.content.Movie;
import com.watchapedia.watchpedia_user.model.entity.content.Tv;
import com.watchapedia.watchpedia_user.model.entity.content.Webtoon;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Watch;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Wish;
import com.watchapedia.watchpedia_user.model.network.request.ajax.StarRequest;
import com.watchapedia.watchpedia_user.model.network.response.content.EstimateContent;
import com.watchapedia.watchpedia_user.model.network.response.content.MovieResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.StarResponse;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.SpoilerRepository;
import com.watchapedia.watchpedia_user.model.repository.content.BookRepository;
import com.watchapedia.watchpedia_user.model.repository.content.MovieRepository;
import com.watchapedia.watchpedia_user.model.repository.content.TvRepository;
import com.watchapedia.watchpedia_user.model.repository.content.WebtoonRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.HateRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.WatchRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StarService {
    final MovieRepository movieRepository;
    final StarRepository starRepository;

    //@Transactional은 클래스나 메서드에 붙여줄 경우, 해당 범위 내 메서드가 트랜잭션이 되도록 보장해준다.
    //일련의 작업들을 묶어서 하나의 단위로 처리하고 싶다면 @Transactional을 활용하자
    //트랜잭션의 4가지 특성
    //원자성,독립성,영속성,일관성
    //원자성->전부 다 성공하거나 아님 아예 안하거나
    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public Map<String, Object> movieList(User user, Pageable pageable){
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        Map<String, Object> map = new HashMap<>();
        map.put("last",moviePage.isLast());
        List<EstimateContent> movieList = moviePage.stream().map(
                mov -> {
                    Long movIdx = mov.getMovIdx();
                    Comment comm = commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx("movie",movIdx,user);
                    return EstimateContent.of(movIdx,mov.getMovTitle(),mov.getMovMakingDate(),mov.getMovCountry(),mov.getMovThumbnail(),
                            wishRepository.findByWishContentTypeAndWishContentIdxAndWishUserIdx("movie",movIdx,user.getUserIdx()) != null ? true : false,
                            watchRepository.findByWatchContentTypeAndWatchContentIdxAndWatchUserIdx("movie",movIdx,user.getUserIdx()) != null ? true : false,
                            comm,
                            comm != null?
                                    (spoilerRepository.findBySpoCommentIdx(comm.getCommIdx()) != null ? true :false) :
                                    false
                    );
                }
        ).collect(Collectors.toList());
//        평점을 남겼던 영화, 관심없어요 한 영화라면 인덱스 삭제
        movieList.removeIf(mov-> starRepository.findByStarContentTypeAndStarContentIdxAndStarUserIdx("movie",mov.idx(),user.getUserIdx())!=null
                || hateRepository.findByHateUserIdxAndHateContentTypeAndHateContentIdx(user.getUserIdx(),"movie",mov.idx())!=null);
        map.put("contentList",movieList);
        return map;
    }
    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public Map<String, Object> tvList(User user, Pageable pageable){
        Page<Tv> tvPage = tvRepository.findAll(pageable);
        Map<String, Object> map = new HashMap<>();
        map.put("last",tvPage.isLast());
        List<EstimateContent> tvList = tvPage.stream().map(
                tv -> {
                    Long tvIdx = tv.getTvIdx();
                    Comment comm = commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx("tv",tvIdx,user);
                    return EstimateContent.of(tvIdx,tv.getTvTitle(),tv.getTvMakingDate(),tv.getTvCountry(),tv.getTvThumbnail(),
                            wishRepository.findByWishContentTypeAndWishContentIdxAndWishUserIdx("tv",tvIdx,user.getUserIdx()) != null ? true : false,
                            watchRepository.findByWatchContentTypeAndWatchContentIdxAndWatchUserIdx("tv",tvIdx,user.getUserIdx()) != null ? true : false,
                            comm,
                            comm != null?
                                    (spoilerRepository.findBySpoCommentIdx(comm.getCommIdx()) != null ? true :false) :
                                    false
                    );
                }
        ).collect(Collectors.toList());
//        평점을 남겼던 콘텐츠, 관심없어요 한 콘텐츠라면 인덱스 삭제
        tvList.removeIf(tv-> starRepository.findByStarContentTypeAndStarContentIdxAndStarUserIdx("tv",tv.idx(),user.getUserIdx())!=null
                || hateRepository.findByHateUserIdxAndHateContentTypeAndHateContentIdx(user.getUserIdx(),"tv",tv.idx())!=null);
        map.put("contentList",tvList);

        return map;
    }
    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public Map<String, Object> bookList(User user, Pageable pageable){
        Page<Book> bookPage = bookRepository.findAll(pageable);
        Map<String, Object> map = new HashMap<>();
            map.put("last",bookPage.isLast());
        List<EstimateContent> bookList = bookPage.stream().map(
                book -> {
                    Long bookIdx = book.getBookIdx();
                    return EstimateContent.of(bookIdx,book.getBookTitle(),book.getBookAtDate(),book.getBookWriter(),book.getBookThumbnail(),
                            wishRepository.findByWishContentTypeAndWishContentIdxAndWishUserIdx("book",bookIdx,user.getUserIdx()) != null ? true : false,
                            watchRepository.findByWatchContentTypeAndWatchContentIdxAndWatchUserIdx("book",bookIdx,user.getUserIdx()) != null ? true : false,
                            commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx("book",bookIdx,user),
                            commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx("book",bookIdx,user) != null?
                                    (spoilerRepository.findBySpoCommentIdx(commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx("book",bookIdx,user).getCommIdx()) != null ? true :false) :
                                    false
                    );
                }
        ).collect(Collectors.toList());
//        평점을 남겼던 콘텐츠, 관심없어요 한 콘텐츠라면 인덱스 삭제
        bookList.removeIf(book-> starRepository.findByStarContentTypeAndStarContentIdxAndStarUserIdx("book",book.idx(),user.getUserIdx())!=null
                || hateRepository.findByHateUserIdxAndHateContentTypeAndHateContentIdx(user.getUserIdx(),"book",book.idx())!=null);
        map.put("contentList", bookList);
        return map;
    }
    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public Map<String, Object> webList(User user, Pageable pageable){
        Page<Webtoon> webPage = webtoonRepository.findAll(pageable);
        Map<String, Object> map = new HashMap<>();
        map.put("last",webPage.isLast());
        List<EstimateContent> webtoonList = webPage.stream().map(
                web -> {
                    Long webIdx = web.getWebIdx();
                    Comment comm = commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx("webtoon",webIdx,user);
                    return EstimateContent.of(webIdx,web.getWebTitle(),web.getWebSerDetail(),web.getWebWriter(),web.getWebThumbnail(),
                            wishRepository.findByWishContentTypeAndWishContentIdxAndWishUserIdx("webtoon",web.getWebIdx(),user.getUserIdx())!=null?true:false,
                            watchRepository.findByWatchContentTypeAndWatchContentIdxAndWatchUserIdx("webtoon",web.getWebIdx(),user.getUserIdx())!=null?true:false,
                            comm,
                            comm != null? (spoilerRepository.findBySpoCommentIdx(comm.getCommIdx()) != null ? true :false) :
                                    false
                    );
                }
        ).collect(Collectors.toList());
//        평점을 남겼던 콘텐츠, 관심없어요 한 콘텐츠라면 인덱스 삭제
        webtoonList.removeIf(web-> starRepository.findByStarContentTypeAndStarContentIdxAndStarUserIdx("webtoon",web.idx(),user.getUserIdx())!=null
                || hateRepository.findByHateUserIdxAndHateContentTypeAndHateContentIdx(user.getUserIdx(),"webtoon",web.idx())!=null);
        map.put("contentList", webtoonList);

        return map;
    }

    final UserRepository userRepository;
    private final HateRepository hateRepository;
    private final WishRepository wishRepository;
    private final WatchRepository watchRepository;
    private final CommentRepository commentRepository;
    private final SpoilerRepository spoilerRepository;
    private final TvRepository tvRepository;
    private final WebtoonRepository webtoonRepository;
    private final BookRepository bookRepository;

    public StarResponse starSave(StarRequest request){
        Star starEntity = starRepository.findByStarContentTypeAndStarContentIdxAndStarUserIdx(
                request.starContentType(),request.starContentIdx(), request.starUserIdx()
        );
        if(starEntity==null){
            StarDto starDto = StarDto.of(request.starContentType(), request.starContentIdx(),
                    request.starUserIdx(), request.starPoint());
            Star starSave = Star.of(starDto.starContentType(), starDto.starContentIdx(),
                    starDto.starUserIdx(), starDto.starPoint());
            starRepository.save(starSave);
            return StarResponse.from(starSave);
        }else{
            starEntity.setStarPoint(request.starPoint());
            starRepository.save(starEntity);
            return StarResponse.from(starEntity);
        }
    }

    public void starDelete(StarRequest request){
        Star starEntity = starRepository.findByStarContentTypeAndStarContentIdxAndStarUserIdx(
                request.starContentType(),request.starContentIdx(), request.starUserIdx()
        );
        starRepository.delete(starEntity);
    }

    public StarResponse findStar(
            String starContentType, Long starContentIdx, Long starUserIdx
    ){
        try {
            Star hasStar = starRepository.findByStarContentTypeAndStarContentIdxAndStarUserIdx(
                    starContentType, starContentIdx, starUserIdx
            );
            return StarResponse.from(StarDto.from(hasStar));
        }catch(Exception e){
            return null;
        }
    }

    public Long getTotalCnt(){
        Long totalCnt = Long.valueOf(starRepository.findAll().size());
        return totalCnt;

    }
}
