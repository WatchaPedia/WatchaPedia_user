package com.watchapedia.watchpedia_user.service.content;


import com.watchapedia.watchpedia_user.model.dto.content.BookDto;
import com.watchapedia.watchpedia_user.model.entity.content.Book;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.content.BookResponse;
import com.watchapedia.watchpedia_user.model.repository.content.BookRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookService {
    final StarRepository starRepository;
    final BookRepository bookRepository;
    @Transactional(readOnly = true)
    public BookResponse bookView(Long bookIdx){
        BookDto book = bookRepository.findById(bookIdx).map(BookDto::from).get();
        return BookResponse.from(book);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> similarGenre(String bookCategory, Long bookIdx){
        List<BookResponse> result = new ArrayList<>();

        List<Long> bookIdxList = new ArrayList<>(16);
        if(bookCategory.contains("/")){
            List<String> genreList = Arrays.stream(bookCategory.split("/")).toList();
            HashMap<Long, Integer> containBook = new HashMap<>();
            for(String idx: genreList){
                for(Book book: bookRepository.findByBookCategoryContaining(idx)){
                    containBook.put(book.getBookIdx(),
                            containBook.get(book.getBookIdx()) != null ?
                                    containBook.get(book.getBookIdx()) + 1 : 1
                    );
                }
            }
            List<Map.Entry<Long, Integer>> entryList = new LinkedList<>(containBook.entrySet());
            entryList.sort(((o1, o2) -> o2.getValue() - o1.getValue()));
            for(Map.Entry<Long, Integer> entry : entryList){
                bookIdxList.add(entry.getKey());
            }
        }else{
            for(Book idx: bookRepository.findByBookCategoryContaining(bookCategory)){
                bookIdxList.add(idx.getBookIdx());
            }
        }

        for(Long idx: bookIdxList){
            if(idx == bookIdx) continue;
            BookDto dto = BookDto.from(bookRepository.getReferenceById(idx));
            double sum = 0;

            if(dto.starList().size() > 0){
                for(Star star: dto.starList()){
                    sum += star.getStarPoint();
                }
                result.add(BookResponse.of(dto.bookIdx(), dto.bookThumbnail(),dto.bookTitle(),dto.bookBuy(),Math.round((sum / dto.starList().size()) * 10.0) / 10.0));
            }else{
                result.add(BookResponse.of(dto.bookIdx(), dto.bookThumbnail(),dto.bookTitle(),dto.bookBuy(),0.0));
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<BookDto> books() {
        //빈 웹툰리스폰스 리스트
        List<BookDto> result = new ArrayList<>();

        List<Book> bookList = bookRepository.findAll();

        for(Book book : bookList){
            double sum = 0;
            int starCount = 0;
            for(Star star : book.getStar()){
                sum += star.getStarPoint();
                starCount = book.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(BookDto.from(book, avg));
        }
        return result;
    }


    @Transactional(readOnly = true)
    public BookResponse bookWithRole(Long bookIdx, Long perIdx){
        BookDto book = bookRepository.findById(bookIdx).map(BookDto::from).get();

        List<Star> starResponseList = starRepository.findByStarContentTypeAndStarContentIdx("book", book.bookIdx());
        int starCount= starResponseList.size();
        float starPoint = 0;
        for(Star star : starResponseList){
            starPoint = starPoint + (star.getStarPoint()).intValue();
        }
        float starAvg = 0;
        if(starCount != 0){
            float avg = (starPoint / starCount);
            float avg2 = (float) ((avg*100)/100.0);
            starAvg = (float)(Math.round(avg2*10)/10.0);
        }

        int num = book.bookPeople().indexOf(String.valueOf(perIdx));  // 특정문자 => 문자열로 변환 => 동일한 부분을 찾기(영화 사람에서) => 결과를 인덱스로 반환 => 다시 숫자형으로 변환
        String bookPersonRole = book.bookPeople().substring(num+1);  // 숫자 + ( 이후이므로 +1를 함 => (시작번을 포함해서)부터 자르기 => (00 | 00), 숫자(00 | 00)
        String role2 = "";
        String role = "";
        if(bookPersonRole.length() > -1){

            String[] bookRoles = bookPersonRole.split("\\)");
            role2 = bookRoles[0];
            String[] bookRoles2 = role2.split("\\(");
            role = bookRoles2[1];
        }

        return BookResponse.fromis(book, role, starAvg);
    }
}
