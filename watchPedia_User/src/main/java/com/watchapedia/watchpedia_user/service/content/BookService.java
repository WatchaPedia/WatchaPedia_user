package com.watchapedia.watchpedia_user.service.content;


import com.watchapedia.watchpedia_user.model.dto.content.BookDto;
import com.watchapedia.watchpedia_user.model.entity.content.Book;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.content.BookResponse;
import com.watchapedia.watchpedia_user.model.repository.content.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookService {

    final BookRepository bookRepository;
    @Transactional(readOnly = true)
    public BookResponse bookView(Long bookIdx){
        BookDto book = bookRepository.findById(bookIdx).map(BookDto::from).get();
        return BookResponse.from(book);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> similarGenre(String bookGenre, Long bookIdx){
        List<BookResponse> result = new ArrayList<>();

        List<Long> bookIdxList = new ArrayList<>(16);
        if(bookGenre.contains("/")){
            List<String> genreList = Arrays.stream(bookGenre.split("/")).toList();
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
            for(Book idx: bookRepository.findByBookCategoryContaining(bookGenre)){
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
}
