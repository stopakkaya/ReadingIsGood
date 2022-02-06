package com.topakkaya.reading.model;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {
    private Long id;
    private Date orderDate;
    private Date deliverDate;
    private Long customerId;
    private String bookName;
}
