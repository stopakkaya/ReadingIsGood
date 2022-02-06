package com.topakkaya.reading.model;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    @NotEmpty(message = ("{book.service.book.name.cannot.be.empty}"))
    private String name;
    @NotEmpty(message = ("{book.service.author.cannot.be.empty}"))
    private String author;
    @NotEmpty(message = ("{book.service.isbn.cannot.be.empty}"))
    private String isbn;
    @NotEmpty(message = ("{book.service.publish.year.cannot.be.empty}"))
    @Min(value = 0, message = ("{book.service.publish.year.min}"))
    @Max(value = 2022, message = ("{book.service.publish.year.max}"))
    private Integer publishYear;
    @NotEmpty(message = ("{book.service.publisher.cannot.be.empty}"))
    private String publisher;
    @NotEmpty(message = ("{book.service.stock.size.cannot.be.empty}"))
    @Min(value = 0, message = ("{book.service.stock.size.min.value}"))
    private Integer stockSize;
}
