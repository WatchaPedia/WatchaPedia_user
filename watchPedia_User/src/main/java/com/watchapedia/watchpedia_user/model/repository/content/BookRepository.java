package com.watchapedia.watchpedia_user.model.repository.content;

import com.watchapedia.watchpedia_user.model.entity.content.Book;
import com.watchapedia.watchpedia_user.model.entity.content.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByBookCategoryContaining(String bookCategory);

    List<Book> findByBookTitleContaining(String searchKey);
}
