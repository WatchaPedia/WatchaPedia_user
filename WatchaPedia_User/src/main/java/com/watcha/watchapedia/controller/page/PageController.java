package com.watcha.watchapedia.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("") // localhost:9090
public class PageController {

    @GetMapping(path="")      // localhost:9090/
    public ModelAndView index(){
        return new ModelAndView("/index") ;
    }

    @GetMapping(path="/mypage/valueContent")    // localhost:9090/mypage/valueContent
    public ModelAndView valueContent(){
        return new ModelAndView("/mypage/valueContent") ;
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

    // TV Main
    @GetMapping(path="/tv/main")      // localhost:9090/tv/main
    public ModelAndView tvMain(){
        return new ModelAndView("/tv/tvMain") ;
    }

    @GetMapping(path="/tv/tvDetail")  // localhost:9090/tv/tvDetail
    public ModelAndView tvDetail(){
        return new ModelAndView("/tv/tvDetail") ;
    }

    // Book Main
    @GetMapping(path="/book/main")      // localhost:9090/book/main
    public ModelAndView bookMain(){
        return new ModelAndView("/book/bookMain") ;
    }

    @GetMapping(path="/book/bookDetail")  // localhost:9090/book/bookDetail
    public ModelAndView bookDetail(){
        return new ModelAndView("/book/bookDetail") ;
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








}