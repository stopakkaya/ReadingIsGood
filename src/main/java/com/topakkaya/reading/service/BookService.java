package com.topakkaya.reading.service;

import com.topakkaya.reading.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book createBook(Book book);
}
