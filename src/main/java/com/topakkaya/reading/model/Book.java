package com.topakkaya.reading.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Book {
    private Long id;
    private String name;
    private String author;
    private String ISBN;
    private Integer year;
    private String publisher;
}
