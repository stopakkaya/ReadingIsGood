package com.topakkaya.reading.model;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest(classes = StatisticDTO.class)
public class StatisticDTOTest {

    StatisticDTO dto;

    @Before
    public void setUp(){
        dto = new StatisticDTO();
        dto.setMonth("TestMonth");
        dto.setTotalPurchasedAmount(10d);
        dto.setTotalBookCount(10);
        dto.setTotalOrderCount(15);
    }

    @Test
    public void getMonth() {
        assertEquals("TestMonth", dto.getMonth());
    }

    @Test
    public void getTotalOrderCount() {
        assertEquals(Integer.valueOf(15), dto.getTotalOrderCount());
    }

    @Test
    public void getTotalBookCount() {
        assertEquals(Integer.valueOf(10), dto.getTotalBookCount());
    }

    @Test
    public void getTotalPurchasedAmount() {
        assertEquals(Double.valueOf(10d), dto.getTotalPurchasedAmount());
    }

    @Test
    public void setMonth() {
        dto.setMonth("Jan");
        assertEquals("Jan", dto.getMonth());
    }

    @Test
    public void setTotalOrderCount() {
        dto.setTotalOrderCount(100);
        assertEquals(Integer.valueOf(100), dto.getTotalOrderCount());
    }

    @Test
    public void setTotalBookCount() {
        dto.setTotalBookCount(100);
        assertEquals(Integer.valueOf(100), dto.getTotalBookCount());
    }

    @Test
    public void setTotalPurchasedAmount() {
        dto.setTotalPurchasedAmount(100d);
        assertEquals(Double.valueOf(100d), dto.getTotalPurchasedAmount());
    }
}