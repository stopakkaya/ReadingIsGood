package com.topakkaya.reading.controller;

import com.topakkaya.reading.builder.ResponseBuilder;
import com.topakkaya.reading.enums.ReturnType;
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
@RequestMapping(value = "retail/v1/book")
@AllArgsConstructor
public class BookController {
    private final IBookService bookService;

    @PostMapping("/create-book")
    public ResponseEntity<Map<String, Object>> createBook(@Valid @RequestBody BookDTO bookDTO){
        bookService.createBook(bookDTO);
        return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).build();
    }

    @GetMapping("/books")
    public ResponseEntity<Map<String, Object>> getAllBooks(@PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable){
        Page<BookDTO> allBooks = bookService.getAllBooks(pageable);
        return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).withPagination(allBooks).build();
    }

    @PutMapping("/update-book")
    public ResponseEntity<Map<String, Object>> updateBook(@RequestBody BookDTO bookDTO){
        try {
            bookService.updateBook(bookDTO);
            return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).build();
        }catch (BookNotFoundException | IdNotValidException exception){
            return new ResponseBuilder(exception.getHttpStatus(), ReturnType.FAIL).withError(exception.getMessage()).build();
        } catch (Exception e){
            return new ResponseBuilder(HttpStatus.BAD_REQUEST, ReturnType.FAIL).build();
        }
    }

    @PutMapping("/update-stock")
    public ResponseEntity<Map<String, Object>> updateBookStock(@RequestBody UpdateStockDTO bookDTO){
        try {
            bookService.updateBookStock(bookDTO);
            return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).build();
        }catch (BookNotFoundException exception){
            return new ResponseBuilder(exception.getHttpStatus(), ReturnType.FAIL).withError(exception.getMessage()).build();
        }catch (Exception e){
            return new ResponseBuilder(HttpStatus.BAD_REQUEST, ReturnType.FAIL).build();
        }
    }

}
