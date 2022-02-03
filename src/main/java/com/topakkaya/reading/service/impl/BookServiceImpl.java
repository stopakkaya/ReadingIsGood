package com.topakkaya.reading.service.impl;

import com.topakkaya.reading.model.Book;
import com.topakkaya.reading.service.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Override
    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(Book.builder().name("test").author("SametTopakkaya").id(10L).year(1994).build());
        return bookList;
    }

    @Override
    public Book createBook(Book book) {
        return book;
    }
}
