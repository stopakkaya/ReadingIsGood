package com.topakkaya.reading.controller;

import com.topakkaya.reading.builder.ResponseBuilder;
import com.topakkaya.reading.enums.ReturnType;
import com.topakkaya.reading.exception.BookAlreadyExistException;
import com.topakkaya.reading.exception.BookNotFoundException;
import com.topakkaya.reading.exception.IdNotValidException;
import com.topakkaya.reading.model.BookDTO;
import com.topakkaya.reading.model.UpdateStockDTO;
import com.topakkaya.reading.service.IBookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@Validated
@RequestMapping(value = "/retail/v1/book")
@AllArgsConstructor
public class BookController {
    private final IBookService bookService;

    /**
     * @author samet topakkaya
     * @apiNote persist new book for given parameters
     * @param bookDTO consist book infos
     * @throws BookAlreadyExistException when book is saved before (checks by author and bookName pair)
     */
    @PostMapping("/create-book")
    public ResponseEntity<Map<String, Object>> createBook(@Valid @RequestBody BookDTO bookDTO) {
        try {
            bookService.createBook(bookDTO);
            return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).build();
        } catch (BookAlreadyExistException exception) {
            return new ResponseBuilder(exception.getHttpStatus(), ReturnType.FAIL).withError(exception.getMessage()).build();
        }

    }

    /**
     * @author samet topakkaya
     * @apiNote return all books saved
     * @param pageable
     */
    @GetMapping("/books")
    public ResponseEntity<Map<String, Object>> getAllBooks(@PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<BookDTO> allBooks = bookService.getAllBooks(pageable);
        return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).withPagination(allBooks).build();
    }

    /**
     * @author samet topakkaya
     * @apiNote updates book
     * @param bookDTO consist book infos
     * @throws BookNotFoundException when book is not found for given id
     * @throws IdNotValidException when id is null
     */
    @PutMapping("/update-book")
    public ResponseEntity<Map<String, Object>> updateBook(@RequestBody BookDTO bookDTO) {
        try {
            bookService.updateBook(bookDTO);
            return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).build();
        } catch (BookNotFoundException | IdNotValidException exception) {
            return new ResponseBuilder(exception.getHttpStatus(), ReturnType.FAIL).withError(exception.getMessage()).build();
        } catch (Exception e) {
            return new ResponseBuilder(HttpStatus.BAD_REQUEST, ReturnType.FAIL).build();
        }
    }

    /**
     * @author samet topakkaya
     * @apiNote update specific book stock
     * @param stockDTO consist bookId and new stock value
     * @throws BookNotFoundException when book is not found for given id
     * @throws IdNotValidException when id is null
     */
    @PutMapping("/update-stock")
    public ResponseEntity<Map<String, Object>> updateBookStock(@RequestBody UpdateStockDTO stockDTO) {
        try {
            bookService.updateBookStock(stockDTO);
            return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).build();
        } catch (BookNotFoundException | IdNotValidException exception) {
            return new ResponseBuilder(exception.getHttpStatus(), ReturnType.FAIL).withError(exception.getMessage()).build();
        } catch (Exception e) {
            return new ResponseBuilder(HttpStatus.BAD_REQUEST, ReturnType.FAIL).build();
        }
    }

}
