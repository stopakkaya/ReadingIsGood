package com.topakkaya.reading.util;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtil {

    public static String getMonthName(Date date){
        String[] monthNames = getMonths();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return monthNames[cal.get(Calendar.MONTH)];
    }

    public static String[] getMonths(){
        return new String[]{"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
    }

}
