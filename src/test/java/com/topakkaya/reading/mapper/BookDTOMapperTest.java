package com.topakkaya.reading.mapper;

import com.topakkaya.reading.entity.Book;
import com.topakkaya.reading.model.BookDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest(classes = BookDTOMapper.class)
public class BookDTOMapperTest {
    BookDTO dto;
    BookDTOMapper mapper = new BookDTOMapper();
    Book book;

    @Before
    public void setUp() {
        dto = new BookDTO();
        dto.setAuthor("test");
        dto.setIsbn("isbn");
        dto.setName("name");
        dto.setId(1L);
        dto.setPublishYear(2022);
        dto.setPublisher("publisher");
        dto.setStockSize(6);
        dto.setPrice(10d);

        book = new Book();
        book.setAuthor("test");
        book.setISBN("isbn");
        book.setName("name");
        book.setId(1L);
        book.setPublishYear(2022);
        book.setPublisher("publisher");
        book.setStockSize(6);
        book.setPrice(10d);
    }

    @Test
    public void toBookDTO() {
        BookDTO bookDTO = mapper.toBookDTO(book);
        assertEquals(bookDTO.getName(), book.getName());
        assertEquals(bookDTO.getStockSize(), book.getStockSize());
        assertEquals(bookDTO.getIsbn(), book.getISBN());
        assertEquals(bookDTO.getPrice(), book.getPrice());
        assertEquals(bookDTO.getAuthor(), book.getAuthor());
    }

    @Test
    public void toBook() {
        Book book = mapper.toBook(dto);
        assertEquals(dto.getName(), book.getName());
        assertEquals(dto.getStockSize(), book.getStockSize());
        assertEquals(dto.getIsbn(), book.getISBN());
        assertEquals(dto.getPrice(), book.getPrice());
        assertEquals(dto.getAuthor(), book.getAuthor());

    }
}