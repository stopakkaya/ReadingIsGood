package com.topakkaya.reading.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "book")
@Data
public class Book{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name")
    private String name;

    @Column(name = "isbn_number")
    private String ISBN;

    @Column(name = "author_name")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publish_year")
    private Integer publishYear;

    @Column(name = "stock_size")
    private Integer stockSize;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "record_date")
    private Date recordDate;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

}
