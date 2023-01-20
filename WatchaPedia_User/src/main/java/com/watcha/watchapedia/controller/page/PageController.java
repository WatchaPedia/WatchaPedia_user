package com.watcha.watchapedia.controller.page;

import com.watcha.watchapedia.model.dto.TvDto;
import com.watcha.watchapedia.model.entity.Tv;
import com.watcha.watchapedia.model.network.response.TvResponse;
import com.watcha.watchapedia.model.repository.TvRepository;
import com.watcha.watchapedia.service.TvService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class PageController {

    @GetMapping(path="")      // localhost:9090/
    public ModelAndView index(){
        return new ModelAndView("/index") ;
    }

    @GetMapping(path="/mypage/valueContent")    // localhost:9090/mypage/valueContent
    public ModelAndView valueContent(){
        return new ModelAndView("/mypage/valueContent") ;
    }

    //


    @GetMapping(path="/mypage/valuedPlus")    // localhost:9090/mypage/valuedPlus
    public ModelAndView valuedPlus(){
        return new ModelAndView("/mypage/valuedPlus") ;
    }

    @GetMapping(path="/mypage/valuedDesc")    // localhost:9090/mypage/valuedDesc
    public ModelAndView valuedDesc(){
        return new ModelAndView("/mypage/valuedDesc") ;
    }

    // Movie Main
    @GetMapping(path="/movie/main")      // localhost:9090/movie/main
    public ModelAndView movieMain(){
        return new ModelAndView("/movie/movieMain") ;
    }

    @GetMapping(path="/movie/movieDetail")  // localhost:9090/movie/movieDetail
    public ModelAndView movieDetail(){
        return new ModelAndView("/movie/movieDetail") ;
    }


    @GetMapping(path="/Gallery")    // localhost:9090/Gallery
    public ModelAndView gallery(){
        return new ModelAndView("/Gallery") ;
    }

//    // TV Main
//    @GetMapping(path="/tv/main")      // localhost:9090/tv/main
//    public ModelAndView tvMain(){
//        return new ModelAndView("/tv/tvMain") ;
//    }

    // Tv 리스트 출력
    private final TvService tvService;
    @GetMapping(path="/tv/main")
    public String tv(ModelMap map){
        map.addAttribute("tvs", tvService.searchTvs());
        return "/tv/tvMain";
    }

    final TvRepository tvRepository;
    @GetMapping(path="/tv/{tvIdx}")
    public String qnadetail(@PathVariable Long tvIdx, ModelMap map){
        Optional<Tv> tv = tvRepository.findById(tvIdx);
        TvResponse tvResponse = TvResponse.from(TvDto.from(tv.get()));
        map.addAttribute("tv", tvResponse);
        return "/tv/tvDetail";
    }


//    @GetMapping(path="/tv/tvDetail")  // localhost:9090/tv/tvDetail
//    public ModelAndView tvDetail(){
//        return new ModelAndView("/tv/tvDetail") ;
//    }

    // Book Main
    @GetMapping(path="/book/main")      // localhost:9090/book/main
    public ModelAndView bookMain(){
        return new ModelAndView("/book/bookMain") ;
    }

    @GetMapping(path="/book/bookDetail")  // localhost:9090/book/bookDetail
    public ModelAndView bookDetail(){
        return new ModelAndView("/book/bookDetail") ;
    }

    @GetMapping(path="/book/bookStory")  // localhost:9090/book/bookStory
    public ModelAndView bookStory(){
        return new ModelAndView("/book/bookStory") ;
    }

    @GetMapping(path="/mypage/myPage")  // localhost:9090/mypage/myPage
    public ModelAndView myPage(){
        return new ModelAndView("/mypage/myPage") ;
    }

//    @GetMapping(path="/mypage/faq")  // localhost:9090/mypage/faq
//    public ModelAndView faq(){
//        return new ModelAndView("/mypage/faq") ;
//    }

    @GetMapping(path="/mypage/analysis")  // localhost:9090/mypage/analysis
    public ModelAndView analysis(){
        return new ModelAndView("/mypage/analysis") ;
    }

    @GetMapping(path="/webtoon/main")  // localhost:9090/webtoon/main
    public ModelAndView webtoon(){
        return new ModelAndView("/webtoon/webtoonMain");
    }

    @GetMapping(path="/webtoon/webtoonDetail")  // localhost:9090/webtoon/webtooDetail
    public ModelAndView webtoonDetail(){
        return new ModelAndView("/webtoon/webtoonDetail");
    }

    // 콘텐츠 공통
    @GetMapping(path="/detailInfo")  // localhost:9090/detailInfo
    public ModelAndView detailInfo(){
        return new ModelAndView("/detailInfo");
    }

    @GetMapping(path="/personDetail")  // localhost:9090/personDetail
    public ModelAndView personDetail(){
        return new ModelAndView("/personDetail");
    }

    @GetMapping(path="/comment")  // localhost:9090/comment
    public ModelAndView commentPlus(){
        return new ModelAndView("/comment");
    }

    @GetMapping(path="/recomment")  // localhost:9090/recomment
    public ModelAndView recomment(){
        return new ModelAndView("/recomment");
    }






}