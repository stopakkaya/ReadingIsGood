package com.topakkaya.reading.service;

import com.topakkaya.reading.entity.Book;
import com.topakkaya.reading.model.BookDTO;
import com.topakkaya.reading.model.UpdateStockDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookService {
    Page<BookDTO> getAllBooks(Pageable pageable);

    Book createBook(BookDTO bookDTO);

    void updateBook(BookDTO bookDTO);

    Integer updateBookStock(UpdateStockDTO stockDTO);

    BookDTO getBookById(Long id);

    Integer decreaseStock(Long bookId, int amount);
}
