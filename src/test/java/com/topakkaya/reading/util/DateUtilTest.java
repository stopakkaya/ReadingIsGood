package com.topakkaya.reading.util;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = DateUtil.class)
public class DateUtilTest {
    @Test
    public void getMonthNameTest() throws ParseException {
        String dateString = "07/01/2022";
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        String monthName = DateUtil.getMonthName(date1);
        assertEquals(monthName, "January");
    }
}
