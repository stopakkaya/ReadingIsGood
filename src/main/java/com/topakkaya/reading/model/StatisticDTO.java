package com.topakkaya.reading.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDTO {
    private String month;
    private Integer totalOrderCount;
    private Integer totalBookCount;
    private Double totalPurchasedAmount;
}
