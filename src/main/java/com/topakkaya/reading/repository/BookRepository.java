package com.topakkaya.reading.repository;

import com.topakkaya.reading.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookByAuthorAndNameAndPublisher(String author, String name, String publisher);
}
