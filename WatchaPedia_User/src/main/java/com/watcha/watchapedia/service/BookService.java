package com.watcha.watchapedia.service;
import com.watcha.watchapedia.model.entity.Book;
import com.watcha.watchapedia.model.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



// 책 리스트 뽑아오는 api 로직 서비스 단입니다.
@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class BookService {

     final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<Book> searchBooks() {
        return bookRepository.findAll();
    }
}

