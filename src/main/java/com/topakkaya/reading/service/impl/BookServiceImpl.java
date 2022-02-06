package com.topakkaya.reading.service.impl;

import com.topakkaya.reading.entity.Book;
import com.topakkaya.reading.exception.BookAlreadyExistException;
import com.topakkaya.reading.exception.BookNotFoundException;
import com.topakkaya.reading.exception.ExceedStockSizeException;
import com.topakkaya.reading.exception.IdNotValidException;
import com.topakkaya.reading.mapper.BookDTOMapper;
import com.topakkaya.reading.model.BookDTO;
import com.topakkaya.reading.model.UpdateStockDTO;
import com.topakkaya.reading.repository.BookRepository;
import com.topakkaya.reading.service.IBookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class BookServiceImpl implements IBookService {
    private final BookRepository bookRepository;
    private final BookDTOMapper dtoMapper;

    @Override
    public Page<BookDTO> getAllBooks(Pageable pageable) {
        Page<Book> allBooks = bookRepository.findAll(pageable);
        List<BookDTO> bookList = dtoMapper.toDtoList(allBooks.getContent());
        return new PageImpl<>(bookList, pageable, allBooks.getTotalElements());
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return dtoMapper.toBookDTO(book);
    }

    @Override
    public void createBook(BookDTO bookDTO) {
        log.info("Book {} is creating..", bookDTO.getName());
        Book foundBook = bookRepository.findBookByAuthorAndName(bookDTO.getAuthor(), bookDTO.getName());
        if (!Objects.isNull(foundBook))
            throw new BookAlreadyExistException();

        Book book = dtoMapper.toBook(bookDTO);
        bookRepository.save(book);
        log.info("Book created successfully : Name : {}", book.getName());
    }

    @Override
    public void updateBook(BookDTO bookDTO) {
        if (Objects.isNull(bookDTO.getId()))
            throw new IdNotValidException();

        Optional<Book> find = bookRepository.findById(bookDTO.getId());
        if (find.isEmpty())
            throw new BookNotFoundException();

        log.info("Book {} is updating..", bookDTO.getName());
        Book book = find.get();
        Book updatedBook = dtoMapper.toBook(book, bookDTO);
        bookRepository.save(updatedBook);
        log.info("Book updated successfully : Name : {}", book.getName());
    }

    @Override
    public void updateBookStock(UpdateStockDTO stockDTO) {
        log.info("Book stock updating to {} ", stockDTO.getStockSize());
        Book book = bookRepository.findById(stockDTO.getBookId()).orElseThrow(BookNotFoundException::new);
        Integer oldStockSize = book.getStockSize();
        book.setStockSize(stockDTO.getStockSize());
        bookRepository.save(book);
        log.info("Book stock updated {} to {}", oldStockSize, book.getStockSize());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void decreaseStock(Long bookId, int amount) {
        log.info("Book stock decrease bookId : {}, decreaseCount", bookId, amount);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        if (book.getStockSize() < amount) throw new ExceedStockSizeException();
        book.setStockSize(book.getStockSize() - amount);
        log.info("{} books new stockSize : {}", book.getName(), book.getStockSize());
    }
}
