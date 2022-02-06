package com.topakkaya.reading.service.impl;

import com.topakkaya.reading.entity.Book;
import com.topakkaya.reading.exception.BookNotFoundException;
import com.topakkaya.reading.exception.IdNotValidException;
import com.topakkaya.reading.mapper.BookDTOMapper;
import com.topakkaya.reading.model.BookDTO;
import com.topakkaya.reading.repository.BookRepository;
import com.topakkaya.reading.model.UpdateStockDTO;
import com.topakkaya.reading.service.IBookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements IBookService {
    private final BookRepository bookDao;
    private final BookDTOMapper dtoMapper;

    @Override
    public Page<BookDTO> getAllBooks(Pageable pageable) {
        Page<Book> allBooks = bookDao.findAll(pageable);
        List<BookDTO> bookList = dtoMapper.toDtoList(allBooks.getContent());
        return new PageImpl<>(bookList, pageable,allBooks.getTotalElements());
    }

    @Override
    public void createBook(BookDTO bookDTO) {
        Book book = dtoMapper.toBook(bookDTO);
        bookDao.save(book);
    }

    @Override
    public void updateBook(BookDTO bookDTO) {
        if(Objects.isNull(bookDTO.getId()))
            throw new IdNotValidException();

        Optional<Book> find = bookDao.findById(bookDTO.getId());
        if(find.isEmpty())
            throw new BookNotFoundException();

        Book book = find.get();
        Book updatedBook = dtoMapper.toBook(book, bookDTO);
        bookDao.save(updatedBook);
    }

    @Override
    public void updateBookStock(UpdateStockDTO stockDTO){
        Optional<Book> find = bookDao.findById(stockDTO.getBookId());
        if(find.isEmpty())
            throw new BookNotFoundException();
        Book book = find.get();
        book.setStockSize(stockDTO.getStockSize());
        bookDao.save(book);
    }
}
