package com.watchapedia.watchpedia_user.controller;


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





    @GetMapping(path="/book/bookStory")  // localhost:9090/book/bookStory
    public ModelAndView bookStory(){
        return new ModelAndView("/book/bookStory") ;
    }

    @GetMapping(path="/mypage/myPage")  // localhost:9090/mypage/myPage
    public ModelAndView myPage(){
        return new ModelAndView("/mypage/myPage") ;
    }


    @GetMapping(path="/mypage/analysis")  // localhost:9090/mypage/analysis
    public ModelAndView analysis(){
        return new ModelAndView("/mypage/analysis") ;
    }

//    private final WebtoonService webtoonService;
//    @GetMapping(path="/webtoon/main")
//    public String webtoonMain(ModelMap map){
//        map.addAttribute("webtoons", webtoonService.searchWebtoons());
//        return "/webtoon/webtoonMain";
//    }
//
//    final WebtoonRepository webtoonRepository;
//    @GetMapping(path="/webtoon/{webIdx}")
//    public String webtoonDetail(@PathVariable Long webIdx, ModelMap map){
//        Optional<Webtoon> webtoon = webtoonRepository.findById(webIdx);
//        WebtoonResponse webtoonResponse = WebtoonResponse.from(WebtoonDto.from(webtoon.get()));
//        map.addAttribute("webtoon", webtoonResponse);
//        return "/webtoon/webtoonDetail";
//    }
//
//    @GetMapping(path="/webtoon/{webIdx}/webview")
//    public String allWebtooon(@PathVariable Long webIdx, ModelMap map){
//        Optional<Webtoon> webtoon = webtoonRepository.findById(webIdx);
//        WebtoonResponse webtoonResponse = WebtoonResponse.from(WebtoonDto.from(webtoon.get()));
//        map.addAttribute("webtoon", webtoonResponse);
//        map.addAttribute("webtoons", webtoonService.searchWebtoons());
//        return "/webtoon/detailInfoWebtoon";
//    }

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