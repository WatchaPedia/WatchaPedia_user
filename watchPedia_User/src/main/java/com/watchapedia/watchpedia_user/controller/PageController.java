package com.watchapedia.watchpedia_user.controller;

import com.watchapedia.watchpedia_user.model.dto.UserSessionDto;
import com.watchapedia.watchpedia_user.model.dto.comment.CommentDto;
import com.watchapedia.watchpedia_user.model.dto.content.MovieDto;
import com.watchapedia.watchpedia_user.model.dto.content.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.Person;
import com.watchapedia.watchpedia_user.model.entity.content.Book;
import com.watchapedia.watchpedia_user.model.entity.content.Movie;
import com.watchapedia.watchpedia_user.model.entity.content.Tv;
import com.watchapedia.watchpedia_user.model.entity.content.Webtoon;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.request.ajax.StarRequest;
import com.watchapedia.watchpedia_user.model.network.response.PersonAppear;
import com.watchapedia.watchpedia_user.model.network.response.PersonResponse;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.MovieResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.StarAndCommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.StarResponse;
import com.watchapedia.watchpedia_user.model.repository.PersonRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.content.BookRepository;
import com.watchapedia.watchpedia_user.model.repository.content.MovieRepository;
import com.watchapedia.watchpedia_user.model.repository.content.TvRepository;
import com.watchapedia.watchpedia_user.model.repository.content.WebtoonRepository;
import com.watchapedia.watchpedia_user.service.SearchService;
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
    private final SearchService searchService;

    @GetMapping(path="/")
    public String movie(ModelMap map, HttpSession session, HttpServletRequest request){
        UserSessionDto userSessionDto = (UserSessionDto) session.getAttribute("userSession");
        map.addAttribute("userSession", userSessionDto);
        List<MovieDto> movies = movieService.movies();
        map.addAttribute("movies", movies);
        searchService.searchTop10Movie();    //modelMap.addAttribute("searchTop10", searchTop10);

        String[] gerneList = {"액션","액션","액션","코미디","코미디","코미디", "드라마","드라마"};
        String[] countryList = {"한국","한국","한국","한국","미국","미국", "미국"};
        String[] runningList = {"나 홀로","아이언","해리포터", "나 홀로","아이언","해리포터"};
        Random rand = new Random();
        String randomJerne = gerneList[rand.nextInt(gerneList.length)];
        String randomCountry = countryList[rand.nextInt(countryList.length)];
        String randomRunning = runningList[rand.nextInt(runningList.length)];

        map.addAttribute("searchTop10Movie", searchService.searchTop10Movie());
        map.addAttribute("movieStar", movieService.movieStar());
        map.addAttribute("randomCountry", randomCountry);
        map.addAttribute("movies2", movieService.movies2(randomRunning));
        map.addAttribute("randomJerne",randomJerne);
        map.addAttribute("movieDtos", movieService.movieDtos());
        map.addAttribute("movieZero", movieService.movieZero());
        map.addAttribute("movies3", movieService.movies3());
        map.addAttribute("koreanMovies", movieService.searchCountry("한국"));
        map.addAttribute("americanMovies", movieService.searchCountry("미국"));
        map.addAttribute("dramas", movieService.searchDrama("드라마"));
        map.addAttribute("cris", movieService.searchCri(randomJerne,randomCountry));

        Long totalCnt = starService.getTotalCnt();
        map.addAttribute("totalCnt",totalCnt);
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
    private final PersonRepository personRepository;

    @GetMapping("/{contentType}/{contentIdx}/comments") // http://localhost:8080/movie/1/comments
    public String commentList(
            @PathVariable String contentType,
            @PathVariable Long contentIdx,
            @PageableDefault(size = 5, sort = "commIdx", direction = Sort.Direction.DESC) Pageable pageable,
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
            @PageableDefault(size = 5, sort = "commIdx", direction = Sort.Direction.DESC) Pageable pageable
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
        Long totalCnt = starService.getTotalCnt();

        return new ModelAndView("/personDetail").addObject("totalCnt",totalCnt);
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
    public ModelAndView searchPerson(@PathVariable String searchKey, HttpSession session){
        System.out.println("searchPerson 페이지 컨트롤러에 잘 도착함");
        System.out.println("searchKey 매개변수로 받은 값 : " + searchKey);

        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");

        //검색어를 포함하는 이름의 인물List를 가져옴
        List<Person> persons= personRepository.findByPerNameContaining(searchKey);

        //최종 PersonAppear
        List<PersonAppear> personAppears = new ArrayList<>();

        //인물 List에서 perPhoto가 비어있는 사람에게 기본이미지 제공
        for(Person p : persons){
            //프로필이 없으면 기본이미지
            if(p.getPerPhoto() == null){
                p.setPerPhoto("data:image/jpeg;base64,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");
            }
            
            //출연작 2개정도를 받아서 String List에 저장
            List<String> appear = searchService.getListTitle(p);

            //Person을 매개변수로 보내서 PersonAppear을 받아옴
            PersonAppear appear1 = PersonAppear.personAppear(p, appear);

            personAppears.add(appear1);
        }

        return new ModelAndView("/search/searchPerson")
                .addObject("searchKey", searchKey)
                .addObject("userSession", dto)
                .addObject("persons", personAppears);
    }

}
