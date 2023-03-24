package com.watchapedia.watchpedia_user.controller.content;


import com.watchapedia.watchpedia_user.model.dto.UserSessionDto;
import com.watchapedia.watchpedia_user.model.dto.comment.CommentDto;
import com.watchapedia.watchpedia_user.model.dto.content.BookDto;
import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.PersonResponse;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.BookResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.StarResponse;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.SpoilerRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import com.watchapedia.watchpedia_user.service.PersonService;
import com.watchapedia.watchpedia_user.service.comment.CommentService;
import com.watchapedia.watchpedia_user.service.content.BookService;
import com.watchapedia.watchpedia_user.service.content.ajax.HateService;
import com.watchapedia.watchpedia_user.service.content.ajax.StarService;
import com.watchapedia.watchpedia_user.service.content.ajax.WatchService;
import com.watchapedia.watchpedia_user.service.content.ajax.WishService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



//메소드들에 responsebody가 안붙은이유
//페이지 처음 로드시 출력만 해주면 되는 페이지라서
//데이터를 주고받는 페이지같은경우만 메소드에 responsebody 어노테이션을 붙이거나 클래스에 controller대신 restcontroller사용

//HTTP통신이란 브라우저와 서버가 통신하기 위한 여러 프로토콜 가운데 한 종류로
//웹 브라우저와 웹 서버 사이에 HTML(하이퍼텍스트) 문서를 주고받는 데 사용되는 통신 프로토콜이다
//HTTP의 통신 방식은 기본적으로 '요청과 응답(request, response)'으로 이루어져 있다.
//클라이언트가 요청(HttpRequest)을 서버에 보내면 서버는 클라이언트에게 응답(HttpResponse)하는 구조이다
//httpRequest는 start line, headers, body 세 부분으로 나누어져 있으며,
//HttpResponse 역시 비슷하게 status line, headers, body 세 부분으로 나누어져 있다.
//body에 담기는 데이터 형식은 대표적으로 json,xml형식이다

//get 방식같은 경우는 body를 통해 전달하지않고
//url 또는 uri 의 파라미터로 전달되는 형식이기때문에
//@PathVariable, @RequestParam 을 사용해야함

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    final StarService starService;
    final BookService bookService;
    final PersonService personService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final WishService wishService;
    private final WatchService watchService;
    private final HateService hateService;
    private final CommentService commentService;
    private final SpoilerRepository spoilerRepository;


    @GetMapping(path="/main")
    public String book(
            ModelMap map,
            HttpSession session
    ){
        UserSessionDto userSessionDto = (UserSessionDto) session.getAttribute("userSession");
        map.addAttribute("userSession", userSessionDto);
        List<BookDto> books = bookService.books();
        map.addAttribute("books", books);

        Long totalCnt = starService.getTotalCnt();
        map.addAttribute("totalCnt",totalCnt);
        return "book/bookMain";
    }



    @GetMapping("/{bookIdx}") // http://localhost:8080/movie/1
    public String bookDetail(
            @PathVariable Long bookIdx,
            @PageableDefault(size = 5, sort = "commIdx", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map,
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        BookResponse book = bookService.bookView(bookIdx);

//      평균 별점
        double sum = 0;
        double avgStar = 0;
        if(book.starList().size() == 1){
            avgStar = book.starList().get(0).getStarPoint();
        }else if(book.starList().size() > 0){
            for(int i=0; i<book.starList().size(); i++){
                sum += book.starList().get(i).getStarPoint();
            }
            avgStar = Math.round((sum / book.starList().size()) * 10.0) / 10.0;
        }
        StarResponse hasStar = null;
        if (dto != null) {
//        해당 유저가 별점을 매겼는지
            hasStar = starService.findStar("book", book.idx(), dto.userIdx());
        }

//        인물 리스트
        List<String> peopleList = new ArrayList<>();

        List<String> people = new ArrayList<>();
        List<PersonResponse> personList = new ArrayList<>();
        if(book.people() != null){
            peopleList = List.of(book.people().split(","));
            for(String per : peopleList){
                people.add(per.split("\\(")[0] + "," + per.split("\\(")[1].split("\\)")[0]);
            }
        }
        try{
            personList = personService.personList(people);
        }catch (Exception e){
            System.out.println("** 인물정보가 없습니다 **");
        }

        CommentResponse hasComm = null;
        boolean hasWish = false;
        boolean hasWatch = false;
        boolean hasHate = false;
        Page<CommentResponse> commentList = commentService.commentList("book", book.idx(), dto != null ? dto.userIdx() : null, pageable);
        if (dto != null) {
            Comment comment = commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx(
                    "book", book.idx(), userRepository.getReferenceById(dto.userIdx())
            );
            if(comment != null){
                hasComm = CommentResponse.from(CommentDto.from(comment),spoilerRepository.findBySpoCommentIdx(comment.getCommIdx())!=null?true:false,
                        0,0L,false);
            }

            hasWish = wishService.findWish("book", book.idx(), dto.userIdx());
            hasWatch = watchService.findWatch("book", book.idx(), dto.userIdx());
            hasHate = hateService.findHate(dto.userIdx(), "book", book.idx());
        }
//        별점 그래프
        HashMap<Long, Integer> starGraph = new HashMap<Long,Integer>(){{
            put(1L,0);put(2L,0);put(3L,0);put(4L,0);put(5L,0);
        }};
        if(book.starList().size() > 0){
            int graphPx = 88 / book.starList().size();
            for(Star star : book.starList()){
                for(Long i=1L; i<=5L; i++){
                    if(star.getStarPoint() == i){
                        starGraph.put(i,starGraph.get(i) + graphPx);
                    }
                }
            }
        }
        Long bigStar = starGraph.entrySet().stream().max((m1, m2) -> m1.getValue() > m2.getValue() ? 1 : -1).get().getKey();

//        비슷한 장르 영화
        List<BookResponse> similarGenre = bookService.similarGenre(book.category(), book.idx());

        map.addAttribute("book", book);
        map.addAttribute("avg", avgStar);
        map.addAttribute("people", personList);
        map.addAttribute("comment", commentList);
        map.addAttribute("hasStar", hasStar);
        map.addAttribute("hasComm", hasComm);
        map.addAttribute("hasWish", hasWish);
        map.addAttribute("hasWatch", hasWatch);
        map.addAttribute("hasHate", hasHate);
        map.addAttribute("graph", starGraph);
        map.addAttribute("bigStar", bigStar);
        map.addAttribute("userSession", dto);
        map.addAttribute("similarGenre", similarGenre);

        Long totalCnt = starService.getTotalCnt();
        map.addAttribute("totalCnt",totalCnt);
        return "book/bookDetail";
    }


    @GetMapping("/{bookIdx}/info")
    public String bookInfo(
            @PathVariable Long bookIdx,
            ModelMap map,
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        BookResponse book = bookService.bookView(bookIdx);

        map.addAttribute("book", book);
        map.addAttribute("userSession", dto);
        return "book/detailInfoBook";
    }

    @GetMapping("/{bookIdx}/bookStory")
    public String bookStory(
            @PathVariable Long bookIdx,
            ModelMap map,
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        BookResponse book = bookService.bookView(bookIdx);

        map.addAttribute("book", book);
        map.addAttribute("userSession", dto);
        return "book/bookStory";
    }
}
