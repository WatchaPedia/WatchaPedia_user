package com.watchapedia.watchpedia_user.controller;

import com.watchapedia.watchpedia_user.model.dto.UserSessionDto;
import com.watchapedia.watchpedia_user.model.dto.comment.CommentDto;
import com.watchapedia.watchpedia_user.model.dto.content.MovieDto;
import com.watchapedia.watchpedia_user.model.dto.content.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.content.Book;
import com.watchapedia.watchpedia_user.model.entity.content.Movie;
import com.watchapedia.watchpedia_user.model.entity.content.Tv;
import com.watchapedia.watchpedia_user.model.entity.content.Webtoon;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.request.ajax.StarRequest;
import com.watchapedia.watchpedia_user.model.network.response.PersonResponse;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.MovieResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.StarAndCommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.StarResponse;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.content.BookRepository;
import com.watchapedia.watchpedia_user.model.repository.content.MovieRepository;
import com.watchapedia.watchpedia_user.model.repository.content.TvRepository;
import com.watchapedia.watchpedia_user.model.repository.content.WebtoonRepository;
import com.watchapedia.watchpedia_user.service.comment.CommentService;
import com.watchapedia.watchpedia_user.service.content.MovieService;
import com.watchapedia.watchpedia_user.service.content.ajax.StarService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class PageController {
    final StarService starService;
    final MovieService movieService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @GetMapping(path="/")
    public String movie(ModelMap map, HttpSession session, HttpServletRequest request){
        UserSessionDto userSessionDto = (UserSessionDto) session.getAttribute("userSession");
        map.addAttribute("userSession", userSessionDto);
        List<MovieDto> movies = movieService.movies();
        map.addAttribute("movies", movies);

        //String[] gerneList = {"액션","모험","예술","코미디","블랙코미디","로멘틱","다큐멘터리","드라마","코미디","시대극","멜로드라마","교육영화","판타지","누아르","공포","뮤지컬","미스터리","성인","멜로","로멘스","재난","좀비","전쟁","애니메이션","독립","스포츠","음악","뮤지컬","틴에이저","시트콤","가족","역사","독립","스포츠","음악","뮤지컬","로맨스"};
        String[] gerneList = {"액션","액션","액션","코미디","코미디","코미디", "드라마","드라마"};
        String[] countryList = {"한국","한국","한국","한국","미국","미국", "미국"};
        Random rand = new Random();
        String randomJerne = gerneList[rand.nextInt(gerneList.length)];
        String randomCountry = countryList[rand.nextInt(countryList.length)];

        map.addAttribute("randomCountry", randomCountry);
        map.addAttribute("randomJerne",randomJerne);
        map.addAttribute("movieZero", movieService.movieZero());
        map.addAttribute("movieDtos", movieService.movieDtos());
        map.addAttribute("movies2", movieService.movies2("나 홀로"));
        map.addAttribute("movies3", movieService.movies3());
        map.addAttribute("koreanMovies", movieService.searchCountry("한국"));
        map.addAttribute("americanMovies", movieService.searchCountry("미국"));
        map.addAttribute("dramas", movieService.searchDrama("드라마"));
        map.addAttribute("cris", movieService.searchCri(randomJerne,randomCountry));
        map.addAttribute("movieStar", movieService.movieStar());
        return "/movie/movieMain";
    }

    //     별점 저장
    @PostMapping("/estimate") // http://localhost:8080/estimate
    @ResponseBody
    public StarAndCommentResponse starSave(
            @RequestBody StarRequest request
    ){
        if(request.starPoint()==0){
            starService.starDelete(request);
        }else{
            StarResponse starResponse = starService.starSave(request);
        }
        String comm = null;
        try {
            comm = commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx(
                    request.starContentType(), request.starContentIdx(), userRepository.getReferenceById(request.starUserIdx())
            ).getCommText();
        }catch(Exception e){
            System.out.println("댓글이 없습니다.");
        }
        return StarAndCommentResponse.of(request.starPoint(),comm);
    }

    private final CommentService commentService;
    private final MovieRepository movieRepository;
    private final TvRepository tvRepository;
    private final WebtoonRepository webtoonRepository;
    private final BookRepository bookRepository;

    @GetMapping("/{contentType}/{contentIdx}/comments") // http://localhost:8080/movie/1/comments
    public String commentList(
            @PathVariable String contentType,
            @PathVariable Long contentIdx,
            @PageableDefault(size = 3, sort = "commIdx", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map,
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        Page<CommentResponse> commentList = commentService.commentList(contentType,contentIdx,dto!=null?dto.userIdx():null, pageable);
        String contentTitle = "";

        switch (contentType){
            case "movie" ->{
                contentTitle = movieRepository.findById(contentIdx).get().getMovTitle();
            }
            case "tv" ->{
                contentTitle = tvRepository.findById(contentIdx).get().getTvTitle();
            }
            case "webtoon" ->{
                contentTitle = webtoonRepository.findById(contentIdx).get().getWebTitle();
            }
            case "book" ->{
                contentTitle = bookRepository.findById(contentIdx).get().getBookTitle();
            }
        }
        map.addAttribute("userSession", dto);
        map.addAttribute("commentList", commentList);
        map.addAttribute("contentTitle", contentTitle);
        return "/comments";
    }

    @GetMapping("/{contentType}/{contentIdx}/comments/new") // http://localhost:8080/movie/1/comments
    @ResponseBody
    public Map<String, Object> commentList(
            @PathVariable String contentType,
            @PathVariable Long contentIdx,
            @PageableDefault(size = 3, sort = "commIdx", direction = Sort.Direction.DESC) Pageable pageable
            ,
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        Page<CommentResponse> commentList = commentService.commentList(contentType,contentIdx,dto!=null?dto.userIdx():null, pageable);

        Map<String, Object> mv = new HashMap<>();
        mv.put("commentList", commentList);
        return mv;
    }

    @GetMapping("/{contentType}/{contentIdx}/new") // http://localhost:8080/movie/1/new
    @ResponseBody
    public Map<String, Object> movieDetail(
            @PathVariable String contentType,
            @PathVariable Long contentIdx,
            @PageableDefault(size = 5, sort = "commIdx", direction = Sort.Direction.DESC) Pageable pageable,
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        Page<CommentResponse> commentList = commentService.commentList(contentType,contentIdx,dto!=null?dto.userIdx():null,pageable);

        Map<String, Object> mv = new HashMap<>();
        mv.put("commentList", commentList);
        return mv;
    }

    @GetMapping(path="/personDetail")  // localhost:9090/personDetail
    public ModelAndView personDetail(){
        return new ModelAndView("/personDetail");
    }

    @GetMapping("/search/contents/{searchKey}")
    public ModelAndView searchContents(@PathVariable String searchKey, HttpSession session){
        System.out.println("searchContents 페이지 컨트롤러에 잘 도착함");
        System.out.println("searchKey 매개변수로 받은 값 : " + searchKey);

        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");

        //영화 addObject 만들기
        List<Movie> movies = movieRepository.findByMovTitleContaining(searchKey);
        System.out.println(movies);

        //TV addObject 만들기
        List<Tv> tvs = tvRepository.findByTvTitleContaining(searchKey);
        System.out.println(tvs);

        //책 addObject 만들기
        List<Book> books = bookRepository.findByBookTitleContaining(searchKey);
        System.out.println(books);

        //웹툰 addObject 만들기
        List<Webtoon> webtoons = webtoonRepository.findByWebTitleContaining(searchKey);
        System.out.println(webtoons);

        return new ModelAndView("/search/searchContent")
                .addObject("userSession", dto)
                .addObject("searchKey", searchKey)
                .addObject("movies", movies)
                .addObject("tvs", tvs)
                .addObject("books",books)
                .addObject("webtoons", webtoons);
    }
    @GetMapping("/search/person/{searchKey}")
    public ModelAndView searchPerson(@PathVariable String searchKey){
        System.out.println("searchPerson 페이지 컨트롤러에 잘 도착함");
        System.out.println("searchKey 매개변수로 받은 값 : " + searchKey);
        return new ModelAndView("/search/searchPerson");
    }

}
