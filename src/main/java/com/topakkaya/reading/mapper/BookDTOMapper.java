package com.topakkaya.reading.mapper;

import com.topakkaya.reading.entity.Book;
import com.topakkaya.reading.model.BookDTO;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookDTOMapper {
    public BookDTO toBookDTO(Book book){
        BookDTO response = new BookDTO();
        response.setAuthor(book.getAuthor());
        response.setIsbn(book.getISBN());
        response.setName(book.getName());
        response.setId(book.getId());
        response.setPublishYear(book.getPublishYear());
        response.setPublisher(book.getPublisher());
        return response;
    }

    public List<BookDTO> toDtoList(List<Book> bookList){
        List<BookDTO> dtoList = new ArrayList<>();
        bookList.forEach(book -> {
            dtoList.add(toBookDTO(book));
        });
        return dtoList;
    }

    public Book toBook(BookDTO dto){
        Book book = new Book();
        book.setCreateDate(Date.from(Instant.now()));
        book.setRecordDate(Date.from(Instant.now()));
        return setBookValues(book, dto);
    }

    public Book toBook(Book book, BookDTO bookDTO){
        return setBookValues(book, bookDTO);
    }

    private Book setBookValues(Book book, BookDTO bookDTO) {
        book.setUpdateDate(Date.from(Instant.now()));
        book.setStockSize(bookDTO.getStockSize());
        book.setAuthor(bookDTO.getAuthor());
        book.setISBN(bookDTO.getIsbn());
        book.setName(bookDTO.getName());
        book.setPublishYear(bookDTO.getPublishYear());
        book.setIsAvailable(true);
        book.setPublisher(bookDTO.getPublisher());
        return book;
    }
}
