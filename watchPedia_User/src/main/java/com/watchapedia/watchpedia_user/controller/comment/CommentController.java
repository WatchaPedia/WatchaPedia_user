package com.watchapedia.watchpedia_user.controller.comment;

import com.watchapedia.watchpedia_user.model.dto.UserSessionDto;
import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.request.comment.CommentRequest;
import com.watchapedia.watchpedia_user.model.network.request.comment.LikeRequest;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.BookResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.MovieResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.TvResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.WebtoonResponse;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.service.comment.CommentService;
import com.watchapedia.watchpedia_user.service.content.BookService;
import com.watchapedia.watchpedia_user.service.content.MovieService;
import com.watchapedia.watchpedia_user.service.content.TvService;
import com.watchapedia.watchpedia_user.service.content.WebtoonService;
import com.watchapedia.watchpedia_user.service.content.ajax.StarService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//컨트롤러란 사용자의 요청이 진입하는 지점(entry point)
//요청에 따라 어떤 처리를 할지 결정해준다
//단 controller는 단지 결정만 해주고 실질적인 처리는 서비스(Layered Architecture)에서 담당
//사용자에게 View(또는 서버에서 처리된 데이터를 포함하는 View)를 응답으로 보내준다

//@Controller이 적용된 클래스는 "Controller"임을 나타나고,
//bean으로 등록되며 해당 클래스가 Controller로 사용됨을 Spring Framework에 알립

//@RequestMapping이 선언된 클래스의 모든 메소드가 하나의 요청에 대한 처리를 할경우 사용

//@RequiredArgsConstructor은 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해준다.
//주로 의존성 주입(Dependency Injection) 편의성을 위해서 사용한다
//어떠한 빈(Bean)에 생성자가 오직 하나만 있고, 생성자의 파라미터 타입이 빈으로 등록 가능한 존재라면 이 빈은 @Autowired 어노테이션 없이도 의존성 주입이 가능하다.

//추가로 이곳에선 사용되진 않지만 @RestController에 대해 설명하자면
//@RestController은 @Controller + @ResponseBody 이며, 메소드의 return(반환 결과값)을 문자열(JSON) 형태로 반환
//view가 필요없는 API만 지원하는 클래스에 사용되며, json 이나 xml 과 같은 문자열의 return이 주목적

//메소드에 @ResponseBody 로 어노테이션이 되어 있다면 메소드에서 리턴되는 값은 View 를 통해서 출력되지 않고 HTTP Response Body 에 직접 쓰여지게 된다
//이때 쓰여지기 전에 리턴되는 데이터 타입에 따라 MessageConverter 에서 변환이 이뤄진 후 쓰여지게 된다

//메소드의 메개변수앞에 @RequestBody가 붙을경우
//HTTP POST 요청에 대해 request body에 있는 request message에서 값을 얻어와 매칭하며,
//RequestData를 바로 Model이나 클래스로 매핑한다

//Map,Model,ModelMap
//이 클래스들은 요청으로 넘어온 파라미터를 받아 처리하는 것이 아닌, 모델 정보를 담는데 사용한다

//HttpSession 을 통해 세션값을 받아올수있다.

//https://develop-log-sj.tistory.com/29 참고


@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    final CommentService commentService;
    private final StarRepository starRepository;
    private final UserRepository userRepository;
    private final StarService starService;

    @PostMapping("/save")
    @ResponseBody
    public Long commentSave(
            @RequestBody CommentRequest request
    ){
        Comment comment = commentService.commentSave(request);
        commentService.spoilerSave(comment, request.spoiler());
        return comment.getCommIdx();
    }

    @PostMapping("/delete")
    @ResponseBody
    public boolean commentDelete(
            @RequestBody CommentRequest request
    ){
        commentService.commentDelete(request);
        Star star = starRepository.findByStarContentTypeAndStarContentIdxAndStarUserIdx(
                request.contentType(),request.contentIdx(),request.userIdx()
        );
        if(star != null){
            return true;
        }else {
            return false;
        }
    }

    @PostMapping("/like/save")
    @ResponseBody
    public boolean likeSave(
            @RequestBody LikeRequest request
    ){
        return commentService.likeSave(request);
    }
    final TvService tvService;
    final WebtoonService webtoonService;
    final MovieService movieService;
    final BookService bookService;
    @GetMapping("/{commentIdx}")
    public String commentView(
            @PathVariable Long commentIdx,
            @PageableDefault(size = 9, sort = "recommIdx", direction = Sort.Direction.ASC) Pageable pageable,
            ModelMap map,
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        CommentResponse comment = commentService.findComment(commentIdx, dto!=null?dto.userIdx():null, pageable);
        switch (comment.contentType()){
            case "movie" -> {
                MovieResponse content =movieService.movieView(comment.contentIdx());
                map.addAttribute("content", content);
            }
            case "tv" -> {
                TvResponse content = tvService.tvView(comment.contentIdx());
                map.addAttribute("content", content);
            }

            case "webtoon" -> {
                WebtoonResponse content = webtoonService.webtoonView(comment.contentIdx());
                map.addAttribute("content", content);
            }
            case "book" -> {
                BookResponse content = bookService.bookView(comment.contentIdx());
                map.addAttribute("content", content);
            }
        }
        map.addAttribute("comment", comment);
        map.addAttribute("userSession", dto);
        map.addAttribute("userIdx", dto!=null?dto.userIdx():null);

        Long totalCnt = starService.getTotalCnt();
        map.addAttribute("totalCnt",totalCnt);
        return "/recomment";
    }

    @GetMapping("/{commentIdx}/new")
    @ResponseBody
    public Map<String, Object> commentView(
            @PathVariable Long commentIdx,
            @PageableDefault(size = 9, sort = "recommIdx", direction = Sort.Direction.ASC) Pageable pageable,
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        CommentResponse comment = commentService.findComment(commentIdx, dto!=null? dto.userIdx():null, pageable);
        Map<String, Object> mv = new HashMap<>();

        mv.put("comment", comment.recomment());
        return mv;
    }
}
