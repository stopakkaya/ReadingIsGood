package com.topakkaya.reading.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateStockDTO {
    @NotNull(message = "{book.service.book.id.cannot.be.null}")
    private Long bookId;
    @Min(value = 0, message = "{book.service.stock.size.min.value}")
    private Integer stockSize;
}
