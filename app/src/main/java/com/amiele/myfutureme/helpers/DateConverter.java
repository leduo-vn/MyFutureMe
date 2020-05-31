package com.amiele.myfutureme.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    public static Date ConvertFromYearMonthDayToDate(int year, int month, int day)
    {
        String txt_date= year +"-"+ (month + 1) +"-"+ day;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date = null;
        try {
            date = format.parse(txt_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String GetDayOfWeekFromDate(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE", Locale.US);
        return dateFormat.format(date);
    }

    public static String GetDayMonthYearFromDate(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy", Locale.US);
        return dateFormat.format(date);
    }


    // Ex:Sun, 27 May 20
    public static String ConvertFromDateToString(Date date)
    {
        return GetDayOfWeekFromDate(date) + "-" + GetDayMonthYearFromDate(date);
    }



}
