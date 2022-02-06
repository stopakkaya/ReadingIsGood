package com.topakkaya.reading.service;

import com.topakkaya.reading.model.BookDTO;
import com.topakkaya.reading.model.UpdateStockDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookService {
    Page<BookDTO> getAllBooks(Pageable pageable);

    void createBook(BookDTO bookDTO);

    void updateBook(BookDTO bookDTO);

    void updateBookStock(UpdateStockDTO stockDTO);

    BookDTO getBookById(Long id);

    void decreaseStock(Long bookId, int amount);
}
