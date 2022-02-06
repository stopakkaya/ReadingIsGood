package com.topakkaya.reading.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class OrderQueryDTO {
    @NotNull(message = "{order.service.order.query.start.date.cannot.be.null}")
    private Date startDate;

    @NotNull(message = "{order.service.order.query.end.date.cannot.be.null}")
    private Date endDate;
}
