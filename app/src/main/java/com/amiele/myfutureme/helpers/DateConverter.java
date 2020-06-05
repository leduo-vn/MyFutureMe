package com.amiele.myfutureme.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Helper class
 * Use for convert format of date and calculate the date different given 2 dates
 */
public class DateConverter {

    /**
     * Convert from year, month, day into date
     * @param year the year
     * @param month the month
     * @param day the day
     * @return give date based on year month day
     */
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

    /**
     * Return day of Week (Monday, Tuesday,...) based on the date
     * @param date : given date
     * @return String represent the date of week
     */
    public static String GetDayOfWeekFromDate(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE", Locale.US);
        return dateFormat.format(date);
    }

    /**
     * Return day month year follow format dd MMM yy based on the date
     * @param date : given date
     * @return String represent the day month year
     */
    public static String GetDayMonthYearFromDate(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy", Locale.US);
        return dateFormat.format(date);
    }


    /**
     * Return format string of date
     * @param date given date
     * @return String represent the date
     */
    // Ex:Sun, 27 May 20
    public static String ConvertFromDateToString(Date date)
    {
        return GetDayOfWeekFromDate(date) + "-" + GetDayMonthYearFromDate(date);
    }

    /**
     * Get the number of days different given two Strings represent for 2 days
     * @param endString  the String represent for end day
     * @param startString the String represent for the start day
     * @return the number of days different between 2 dates
     */
    public static long getDaysDifferentFromStringDate(String endString, String startString)
    {
        Date first = GetDateFromString(endString);
        Date second = GetDateFromString(startString);
        long differenceDates;
        long difference = first.getTime() - second.getTime();
        if (difference<0) differenceDates =0;
        else
            differenceDates = difference / (24 * 60 * 60 * 1000);

        return differenceDates;
    }

    /**
     * Return date from String
     * @param dateString String represent date
     * @return the date
     */
    private static Date GetDateFromString(String dateString)
    {
        SimpleDateFormat format = new SimpleDateFormat("EE-dd MMM yy", Locale.US);
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
