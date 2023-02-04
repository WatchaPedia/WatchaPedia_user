package com.watchapedia.watchpedia_user.controller.content;

import com.watchapedia.watchpedia_user.model.dto.BookDto;
import com.watchapedia.watchpedia_user.model.entity.Book;
import com.watchapedia.watchpedia_user.model.entity.Star;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.request.SelectType;
import com.watchapedia.watchpedia_user.model.network.request.StarRequest;
import com.watchapedia.watchpedia_user.model.network.response.PersonResponse;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.BookResponse;
import com.watchapedia.watchpedia_user.model.repository.BookRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.service.PersonService;
import com.watchapedia.watchpedia_user.service.content.ajax.StarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class BookController {
    final StarService starService;

    @GetMapping(path="/bookestimate") // http://localhost:9090/estimate
    public String bookestimate(
            SelectType selectType,
            ModelMap map
    ){
        Long userIdx = 1L;
//        리스트 인덱스 삭제 문제 때문에 new ArrayList로 감쌈
        List<BookResponse> bookList = new ArrayList<>(starService.bookList().stream().map(BookResponse::from).toList());
//        평점을 남겼던 책이라면 인덱스 삭제
        int j = 0;
        for(int i=0; i<bookList.size(); i++){
            BookResponse bok = bookList.get(j);
            if(bok.starList().size() > 0){
                for(Star star : bok.starList()){
                    System.out.println("별점 : " + star.getStarPoint());
                    if(star.getStarUserIdx().getUserIdx() == userIdx){
                        bookList.remove(j);
                        j--;
                        break;
                    }
                }
            }
            j++;
        }
        map.addAttribute("content", bookList);
        return "/valueContent";
    }

    @PostMapping("/bookestimate") // http://localhost:9090/estimate
    @ResponseBody
    public StarAndCommentResponse bookstarSave(
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

    final BookService bookService;
    final PersonService personService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final WishService wishService;
    private final WatchService watchService;
    private final HateService hateService;
    private final BookRepository bookRepository;

    @GetMapping(path="/book/main")
    public String book(ModelMap map){
        map.addAttribute("books", bookService.searchBooks());
        return "/book/bookMain";
    }
    @GetMapping("/book/{bookIdx}") // http://localhost:9090/book/1
    public String bookDetail(
            @PathVariable Long bookIdx,
            ModelMap map
    ){
        User user = userRepository.getReferenceById(3L);

       BookResponse book = bookService.bookView(bookIdx);

//      평균 별점
        double sum = 0;
        double avgStar = 0;
        if(book.starList().size() > 0){
            for(int i=0; i<book.starList().size(); i++){
                sum += book.starList().get(i).getStarPoint();
            }
            avgStar = Math.round((sum / book.starList().size()) * 10.0) / 10.0;
        }

//        해당 유저가 별점을 매겼는지
        StarResponse hasStar = starService.findStar("book",book.bookIdx(), user.getUserIdx());

//        인물 리스트
        List<String> peopleList = new ArrayList<>();

        List<String> people = new ArrayList<>();
        List<PersonResponse> personList = new ArrayList<>();
        if(book.bookPeople() != null){
            peopleList = List.of(book.bookPeople().split(","));
            for(String per : peopleList){
                people.add(per.split("\\(")[0] + "," + per.split("\\(")[1].split("\\)")[0]);
            }
        }
        try{
            personList = personService.personList(people);
        }catch (Exception e){
            System.out.println("** 인물정보가 없습니다 **");
        }

        List<CommentResponse> commentList = bookService.commentList(book.bookIdx(),user);
//      해당 유저가 코멘트를 달았는지
        CommentResponse hasComm = null;
        for(CommentResponse comm: commentList){
            if(comm.user().getUserIdx() == user.getUserIdx()){
                hasComm = comm;
            }
        };

        boolean hasWish = wishService.findWish("book",book.bookIdx(),user.getUserIdx());
        boolean hasWatch = watchService.findWatch("book",book.bookIdx(),user.getUserIdx());
        boolean hasHate = hateService.findHate(user,"book",book.bookIdx());

//        별점 그래프
        HashMap<Long, Integer> starGraph = new HashMap<Long,Integer>(){{
            put(1L,0);put(2L,0);put(3L,0);put(4L,0);put(5L,0);
        }};
        int graphPx = 88 / book.starList().size();
        for(Star star : book.starList()){
            for(Long i=0L; i<=5L; i++){
                if(star.getStarPoint() == i){
                    starGraph.put(i,starGraph.get(i) + graphPx);
                }
            }
        }
        Long bigStar = starGraph.entrySet().stream().max((m1, m2) -> m1.getValue() > m2.getValue() ? 1 : -1).get().getKey();

        map.addAttribute("books", bookService.searchBooks());
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
        map.addAttribute("user", user);
        return "/book/bookDetail";
    }
    @GetMapping(path="/book/{bookIdx}/bookview")
    public String allBook(@PathVariable Long bookIdx, ModelMap map) {
        Optional<Book> book = bookRepository.findById(bookIdx);
        BookResponse bookResponse = BookResponse.from(BookDto.from(book.get()));
        map.addAttribute("book", bookResponse);
        map.addAttribute("books", bookService.searchBooks());
        return "/book/detailInfoBook";
    }
    @GetMapping(path="/book/{bookIdx}/bookStory")  // localhost:9090/book/bookStory
    public String bookStory(@PathVariable Long bookIdx, ModelMap map){
        Optional<Book> book = bookRepository.findById(bookIdx);
        BookResponse bookResponse = BookResponse.from(BookDto.from(book.get()));
        map.addAttribute("book", bookResponse);
        map.addAttribute("books", bookService.searchBooks());
        return "/book/bookStory";
    }
    @GetMapping(path="/book/{bookIdx}/bookMockcha")  // localhost:9090/book/bookStory
    public String bookMockcha(@PathVariable Long bookIdx, ModelMap map){
        Optional<Book> book = bookRepository.findById(bookIdx);
        BookResponse bookResponse = BookResponse.from(BookDto.from(book.get()));
        map.addAttribute("book", bookResponse);
        map.addAttribute("books", bookService.searchBooks());
        return "/book/bookContents";
    }
}
