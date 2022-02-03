package com.topakkaya.reading.controller;

import com.topakkaya.reading.builder.ResponseBuilder;
import com.topakkaya.reading.enums.ReturnType;
import com.topakkaya.reading.model.Book;
import com.topakkaya.reading.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "retail/v1/book")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/create-book")
    public ResponseEntity<Map<String, Object>> createBook(Book book){
        Book createdBook = bookService.createBook(book);
        return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).withData(createdBook).build();
    }

    @GetMapping("/books")
    public ResponseEntity<Map<String, Object>> getAllBooks(){
        List<Book> bookList = bookService.getAllBooks();
        return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).withDataList(Collections.singletonList(bookList)).build();
    }
}
