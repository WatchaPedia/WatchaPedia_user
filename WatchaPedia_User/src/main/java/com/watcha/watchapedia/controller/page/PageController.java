package com.watcha.watchapedia.controller.page;

import com.watcha.watchapedia.model.dto.BookDto;
import com.watcha.watchapedia.model.dto.TvDto;
import com.watcha.watchapedia.model.dto.WebtoonDto;
import com.watcha.watchapedia.model.entity.Book;
import com.watcha.watchapedia.model.entity.Tv;
import com.watcha.watchapedia.model.entity.Webtoon;
import com.watcha.watchapedia.model.entity.type.FormStatus;
import com.watcha.watchapedia.model.network.response.BookResponse;
import com.watcha.watchapedia.model.network.response.TvResponse;
import com.watcha.watchapedia.model.network.response.WebtoonResponse;
import com.watcha.watchapedia.model.repository.BookRepository;
import com.watcha.watchapedia.model.repository.TvRepository;
import com.watcha.watchapedia.model.repository.WebtoonRepository;
import com.watcha.watchapedia.service.BookService;
import com.watcha.watchapedia.service.TvService;
import com.watcha.watchapedia.service.WebtoonService;
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
        map.addAttribute("tvs", tvService.searchTvs());
        return "/tv/tvDetail";
    }

    @GetMapping(path="/tv/{tvIdx}/tvview")
    public String allTv(@PathVariable Long tvIdx, ModelMap map){
        Optional<Tv> tv = tvRepository.findById(tvIdx);
        TvResponse tvResponse = TvResponse.from(TvDto.from(tv.get()));
        map.addAttribute("tv", tvResponse);
        map.addAttribute("tvs", tvService.searchTvs());
        return "/tv/detailInfoTv";
    }

    private final BookService bookService;
    @GetMapping(path="/book/main")
    public String bookMain(ModelMap map){
        map.addAttribute("books", bookService.searchBooks());
        return "/book/bookMain";
    }

    final BookRepository bookRepository;
    @GetMapping(path="/book/{bookIdx}")
    public String bookDetail(@PathVariable Long bookIdx, ModelMap map){
        Optional<Book> book = bookRepository.findById(bookIdx);
        BookResponse bookResponse = BookResponse.from(BookDto.from(book.get()));
        map.addAttribute("book", bookResponse);
        return "/book/bookDetail";
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

    private final WebtoonService webtoonService;
    @GetMapping(path="/webtoon/main")
    public String webtoonMain(ModelMap map){
        map.addAttribute("webtoons", webtoonService.searchWebtoons());
        return "/webtoon/webtoonMain";
    }

    final WebtoonRepository webtoonRepository;
    @GetMapping(path="/webtoon/{webIdx}")
    public String webtoonDetail(@PathVariable Long webIdx, ModelMap map){
        Optional<Webtoon> webtoon = webtoonRepository.findById(webIdx);
        WebtoonResponse webtoonResponse = WebtoonResponse.from(WebtoonDto.from(webtoon.get()));
        map.addAttribute("webtoon", webtoonResponse);
        return "/webtoon/webtoonDetail";
    }

    @GetMapping(path="/webtoon/{webIdx}/webview")
    public String allWebtooon(@PathVariable Long webIdx, ModelMap map){
        Optional<Webtoon> webtoon = webtoonRepository.findById(webIdx);
        WebtoonResponse webtoonResponse = WebtoonResponse.from(WebtoonDto.from(webtoon.get()));
        map.addAttribute("webtoon", webtoonResponse);
        map.addAttribute("webtoons", webtoonService.searchWebtoons());
        return "/webtoon/detailInfoWebtoon";
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