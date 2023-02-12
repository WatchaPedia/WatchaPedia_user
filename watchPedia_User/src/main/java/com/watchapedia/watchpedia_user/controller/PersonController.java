package com.watchapedia.watchpedia_user.controller;

import com.watchapedia.watchpedia_user.model.dto.PersonDto;
import com.watchapedia.watchpedia_user.model.dto.PersonLikeDto;
import com.watchapedia.watchpedia_user.model.dto.UserSessionDto;
import com.watchapedia.watchpedia_user.model.entity.Person;
import com.watchapedia.watchpedia_user.model.entity.PersonLike;
import com.watchapedia.watchpedia_user.model.network.response.PersonResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.BookResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.MovieResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.TvResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.WebtoonResponse;
import com.watchapedia.watchpedia_user.model.repository.PersonLikeRepository;
import com.watchapedia.watchpedia_user.service.PersonService;
import com.watchapedia.watchpedia_user.service.content.BookService;
import com.watchapedia.watchpedia_user.service.content.MovieService;
import com.watchapedia.watchpedia_user.service.content.TvService;
import com.watchapedia.watchpedia_user.service.content.WebtoonService;
import com.watchapedia.watchpedia_user.service.content.ajax.StarService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class PersonController {

    final PersonService personService;
    final MovieService movieService;
    final TvService tvService;
    final WebtoonService webtoonService;
    final BookService bookService;
    final StarService starService;


    @GetMapping("/personDetail/{perIdx}")
    public String personDetail(@PathVariable Long perIdx, ModelMap map, HttpSession session){
        PersonResponse personResponse = null;
        personResponse = personService.personView(perIdx);

        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        map.addAttribute("userSession", dto);

        map.addAttribute("person", personResponse);
//------------------------------------------------------Movie-----------------------------------------------------------
        if(!StringUtils.isEmpty(personResponse.mov())){
            List<MovieResponse> movieResponseList = new ArrayList<>();
            String[] movies = personResponse.mov().split(",");

            boolean movMoreShow = false;
            boolean movShow = false;

            if(movies.length > 6){
                movMoreShow = true;
            }
            if(movies.length != 0){
                movShow = true;
            }

            int i = 0;
            for(String movieIdx : movies){
                i ++ ;
                if(i > 6){
                    break;
                }
                MovieResponse movieResponse = movieService.movieWithRole(Long.parseLong(movieIdx), perIdx);
                movieResponseList.add(movieResponse);
            }
            map.addAttribute("movMoreShow", movMoreShow);
            map.addAttribute("movShow", movShow);
            map.addAttribute("movie", movieResponseList);
        }
//--------------------------------------------------------tv------------------------------------------------------------
        if(!StringUtils.isEmpty(personResponse.tv())){
            List<TvResponse> tvResponseList = new ArrayList<>();
            String[] tvs = personResponse.tv().split(",");

            boolean tvMoreShow = false;
            boolean tvShow = false;
            if(tvs.length > 6){
                tvMoreShow = true;
            }
            if(tvs.length != 0){
                tvShow = true;
            }

            int i = 0;
            for(String tvIdx : tvs){
                i ++ ;
                if(i > 6){
                    break;
                }
                TvResponse tvResponse = tvService.tvWithRole(Long.parseLong(tvIdx), perIdx);
                tvResponseList.add(tvResponse);
            }
            map.addAttribute("tvMoreShow", tvMoreShow);
            map.addAttribute("tvShow", tvShow);
            map.addAttribute("tv", tvResponseList);
        }
//-------------------------------------------------------웹툰------------------------------------------------------------
        if(!StringUtils.isEmpty(personResponse.webtoon())){
            List<WebtoonResponse> webtoonResponseList = new ArrayList<>();
            String[] webtoons = personResponse.webtoon().split(",");

            boolean webtoonMoreShow = false;
            boolean webtoonShow = false;
            if(webtoons.length > 6){
                webtoonMoreShow = true;
            }
            if(webtoons.length != 0){
                webtoonShow = true;
            }

            int i = 0;
            for(String webIdx : webtoons){
                i ++ ;
                if(i > 6){
                    break;
                }
                WebtoonResponse webtoonResponse = webtoonService.webtoonWithRole(Long.parseLong(webIdx), perIdx);
                webtoonResponseList.add(webtoonResponse);
            }
            map.addAttribute("webtoonMoreShow", webtoonMoreShow);
            map.addAttribute("webtoonShow", webtoonShow);
            map.addAttribute("webtoon", webtoonResponseList);
        }

//-------------------------------------------------------책-------------------------------------------------------------
        if(!StringUtils.isEmpty(personResponse.book())){
            List<BookResponse> bookResponseList = new ArrayList<>();
            String[] books = personResponse.book().split(",");

            boolean bookMoreShow = false;
            boolean bookShow = false;
            if(books.length > 6){
                bookMoreShow = true;
            }
            if(books.length != 0){
                bookShow = true;
            }

            int i = 0;
            for(String bookIdx : books){
                i ++ ;
                if(i > 6){
                    break;
                }
                BookResponse bookResponse = bookService.bookWithRole(Long.parseLong(bookIdx), perIdx);
                bookResponseList.add(bookResponse);
            }
            map.addAttribute("bookMoreShow", bookMoreShow);
            map.addAttribute("bookShow", bookShow);
            map.addAttribute("book", bookResponseList);
        }
        //인물좋아요 갯수
        Long perlikeCnt = personService.likePersonCnt(perIdx);
        map.addAttribute("perlikeCnt",perlikeCnt);
//---------------------------------------------------------------------------------------------

        Long totalCnt = starService.getTotalCnt();
        map.addAttribute("totalCnt",totalCnt);
//---------------------------------------------------------------------------------------------

        Long useridx;
        boolean chklike;
        try{
            useridx = dto.userIdx();
            System.out.println("useridx"+ useridx);
            System.out.println("perIdx"+ perIdx);

            chklike=personService.isLikePerson(useridx,perIdx);
            System.out.println("chklike"+chklike);
            map.addAttribute("chklike",chklike);

        }catch (Exception e){
            useridx = null;
            chklike =false;
            map.addAttribute("chklike",chklike);
        }

            return "/personDetail";
    }

    //---------------------------------------------------------------------------------------------
   //인물좋아요
    @PostMapping("personLike/{perIdx}")
    @ResponseBody
    public int likePerson(@RequestParam("perIdx") int perIdx,
                           HttpSession session){
        UserSessionDto userSessionDto;
        try{
            System.out.println("실행");
            userSessionDto = (UserSessionDto)session.getAttribute("userSession");
            Long userIdx = userSessionDto.userIdx();
            personService.likePerson(userIdx,Long.valueOf(perIdx));

            return 1;
        }catch (Exception e){
            System.out.println("에러");
            return 0;
        }




    }
    //---------------------------------------------------------------------------------------------
    //인물좋아요취소
    @PostMapping("personDelLike/{perIdx}")
    @ResponseBody
    public int delLikePerson(@RequestParam("perIdx") Long perIdx,
                           HttpSession session){
        UserSessionDto userSessionDto;
        Long userIdx;
        try {
            userSessionDto = (UserSessionDto) session.getAttribute("userSession");
            userIdx = userSessionDto.userIdx();
            personService.delLikePerson(userIdx,perIdx);
            return 1;
        }catch(Exception e){
            userSessionDto=null;
            userIdx=null;
            return 0;
        }

    }

//------------------------------------------------------Movie-----------------------------------------------------------
    @PostMapping("movieMore")
    @ResponseBody
    public HashMap<String, Object> movieMore(@RequestParam("perIdx") int perIdx, @RequestParam("count") int count){
        HashMap<String, Object> result = new HashMap <String,Object>();

        PersonResponse personResponse = null;
        personResponse = personService.personView(Long.valueOf(perIdx));
        List<MovieResponse> movieResponseList = new ArrayList<>();
        boolean isEnd = false;

        if(!StringUtils.isEmpty(personResponse.mov())){
            String[] movies = personResponse.mov().split(",");
            if(movies.length <= (count+1)*10+6){
                isEnd = true;
            }
            if(movies.length > count*10 + 6){
                for(int i = count*10 + 6 ; i < movies.length ; i ++){
                    MovieResponse movieResponse = movieService.movieWithRole(Long.parseLong(movies[i]), Long.valueOf(perIdx));
                    movieResponseList.add(movieResponse);
                }
            }
        }
        result.put("movieList", movieResponseList);
        result.put("isEnd", isEnd);
        System.out.println(result);
        return result;
    }
//--------------------------------------------------------tv------------------------------------------------------------
    @PostMapping("tvMore")
    @ResponseBody
    public HashMap<String, Object> tvMore(@RequestParam("perIdx") int perIdx, @RequestParam("count") int count){
        HashMap<String, Object> result = new HashMap <String,Object>();

        PersonResponse personResponse = null;
        personResponse = personService.personView(Long.valueOf(perIdx));
        List<TvResponse> tvResponseList = new ArrayList<>();
        boolean isEnd = false;

        if(!StringUtils.isEmpty(personResponse.tv())){
            String[] tvs = personResponse.tv().split(",");
            if(tvs.length <= (count+1)*10+6){
                isEnd = true;
            }
            if(tvs.length > count*10 + 6){
                for(int i = count*10 + 6 ; i < tvs.length ; i ++){
                    TvResponse tvResponse = tvService.tvWithRole(Long.parseLong(tvs[i]), Long.valueOf(perIdx));
                    tvResponseList.add(tvResponse);
                }
            }
        }
        result.put("tvList", tvResponseList);
        result.put("isEnd", isEnd);
        System.out.println(result);
        return result;
    }
//--------------------------------------------------------웹툰------------------------------------------------------------
    @PostMapping("webtoonMore")
    @ResponseBody
    public HashMap<String, Object> webtoonMore(@RequestParam("perIdx") int perIdx, @RequestParam("count") int count){
        HashMap<String, Object> result = new HashMap <String,Object>();

        PersonResponse personResponse = null;
        personResponse = personService.personView(Long.valueOf(perIdx));
        List<WebtoonResponse> webtoonResponseList = new ArrayList<>();
        boolean isEnd = false;

        if(!StringUtils.isEmpty(personResponse.webtoon())){
            String[] webtoons = personResponse.webtoon().split(",");
            if(webtoons.length <= (count+1)*10+6){
                isEnd = true;
            }
            if(webtoons.length > count*10 + 6){
                for(int i = count*10 + 6 ; i < webtoons.length ; i ++){
                    WebtoonResponse webtoonResponse = webtoonService.webtoonWithRole(Long.parseLong(webtoons[i]), Long.valueOf(perIdx));
                    webtoonResponseList.add(webtoonResponse);
                }
            }
        }
        result.put("webtoonList", webtoonResponseList);
        result.put("isEnd", isEnd);
        System.out.println(result);
        return result;
    }
//--------------------------------------------------------책-------------------------------------------------------------
    @PostMapping("bookMore")
    @ResponseBody
    public HashMap<String, Object> bookMore(@RequestParam("perIdx") int perIdx, @RequestParam("count") int count){
        HashMap<String, Object> result = new HashMap <String,Object>();

        PersonResponse personResponse = null;
        personResponse = personService.personView(Long.valueOf(perIdx));
        List<BookResponse> bookResponseList = new ArrayList<>();
        boolean isEnd = false;

        if(!StringUtils.isEmpty(personResponse.book())){
            String[] books = personResponse.book().split(",");
            if(books.length <= (count+1)*10+6){
                isEnd = true;
            }
            if(books.length > count*10 + 6){
                for(int i = count*10 + 6 ; i < books.length ; i ++){
                    BookResponse bookResponse = bookService.bookWithRole(Long.parseLong(books[i]), Long.valueOf(perIdx));
                    bookResponseList.add(bookResponse);
                }
            }
        }
        result.put("bookList", bookResponseList);
        result.put("isEnd", isEnd);
        System.out.println(result);
        return result;
    }
}
