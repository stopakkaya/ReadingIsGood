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
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = BookServiceImpl.class)
public class BookServiceImplTest {
    BookRepository repository = Mockito.mock(BookRepository.class);
    BookDTOMapper mapper = Mockito.mock(BookDTOMapper.class);
    BookServiceImpl service = new BookServiceImpl(repository, mapper);

    @Test
    public void getAllBooks() {
        PageRequest pageable = PageRequest.of(0, 10);
        List<Book> bookList = List.of(getBook());
        Page<Book> bookPageList = new PageImpl<>(bookList, pageable, bookList.size());

        when(repository.findAll(pageable)).thenReturn(bookPageList);
        when(mapper.toDtoList(bookPageList.getContent())).thenReturn(List.of(getBookDTO()));

        Page<BookDTO> allBooks = service.getAllBooks(pageable);
        assertEquals(allBooks.getTotalElements(), bookPageList.getTotalElements());
        assertEquals(allBooks.getContent().get(0).getStockSize(), bookPageList.getContent().get(0).getStockSize());

    }

    @Test
    public void getBookById() {
        Book book = getBook();
        BookDTO dto = getBookDTO();

        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(book));
        when(mapper.toBookDTO(any(Book.class))).thenReturn(dto);
        BookDTO bookById = service.getBookById(1L);
        assertEquals(bookById.getAuthor(), book.getAuthor());
        assertEquals(bookById.getIsbn(), book.getISBN());
        assertEquals(bookById.getPrice(), book.getPrice());
        assertEquals(bookById.getName(), book.getName());
        assertEquals(bookById.getStockSize(), book.getStockSize());
    }

    private BookDTO getBookDTO() {
        return BookDTO.builder().id(1L)
                .author("TestAuthor")
                .isbn("isbn")
                .name("TestName")
                .stockSize(5)
                .publishYear(2022)
                .price(100d)
                .publisher("TestPublisher").build();
    }

    private Book getBook() {
        Book book = new Book();
        book.setStockSize(5);
        book.setPublisher("TestPublisher");
        book.setPrice(100d);
        book.setPublishYear(2022);
        book.setIsAvailable(true);
        book.setAuthor("TestAuthor");
        book.setISBN("isbn");
        book.setName("TestName");
        return book;
    }

    @Test
    public void createBookSuccessfully() {
        Book book = getBook();
        when(mapper.toBook(any(BookDTO.class))).thenReturn(book);
        when(repository.save(any(Book.class))).thenReturn(book);
        Book savedBook = service.createBook(getBookDTO());

        assertEquals(savedBook.getAuthor(), book.getAuthor());
    }

    @Test
    public void throwExceptionWhenBookCreateBookIsAlreadyExist() {
        when(repository.findBookByAuthorAndName(anyString(), anyString())).thenReturn(getBook());
        assertThrows(BookAlreadyExistException.class, () -> service.createBook(getBookDTO()));
    }

    @Test
    public void throwExceptionWhenBookUpdateIdIsNull() {
        assertThrows(IdNotValidException.class, () -> service.updateBook(BookDTO.builder().id(null).build()));
    }

    @Test
    public void throwExceptionWhenBookUpdateBookIsNotFound() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> service.updateBook(BookDTO.builder().id(1L).build()));
    }

    @Test
    public void updateBook() {
        when(repository.findById(any())).thenReturn(Optional.of(getBook()));
        when(mapper.toBook(any(), any())).thenReturn(getBook());
        service.updateBook(getBookDTO());
    }

    @Test
    public void throwExceptionWhenIdNotValidBookStockUpdate() {
        assertThrows(IdNotValidException.class, () -> service.updateBookStock(UpdateStockDTO.builder().build()));
    }

    @Test
    public void updateStockSize() {
        when(repository.findById(any())).thenReturn(Optional.of(getBook()));
        when(repository.save(any())).thenReturn(getBook());
        Integer stockSize = service.updateBookStock(UpdateStockDTO.builder().bookId(1L).stockSize(10).build());
        assertEquals(stockSize, getBook().getStockSize());
    }

    @Test
    public void throwsBookNotFoundExceptionDecreaseStock() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> service.decreaseStock(1L, 10));
    }

    @Test
    public void throwsStockSizeExceedExceptionWhenAmountGreaterThanStockSize() {
        when(repository.findById(any())).thenReturn(Optional.of(getBook()));
        assertThrows(ExceedStockSizeException.class, () -> service.decreaseStock(1L, 10));
    }

    @Test
    public void decreaseStock() {
        when(repository.findById(any())).thenReturn(Optional.of(getBook()));
        Integer newStockSize = service.decreaseStock(1L, 4);
        assertEquals(newStockSize, Integer.valueOf(1));

    }
}