package com.watchapedia.watchpedia_user.model.repository.content;

import com.watchapedia.watchpedia_user.model.entity.content.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
