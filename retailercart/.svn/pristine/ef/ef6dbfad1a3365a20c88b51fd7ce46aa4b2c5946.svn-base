/*
 * Copyright (C) 2016 Botree Software International Private Limited

 */

package com.botree.retailerssfa.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class DateUtil {

    private static final String CONS_SDFPATTERN = "dd-MM-yyyy";
    private static final String CONS_YEAR_MONTH_DATE = "yyyy-MM-dd";
    private static final String CONS_DATE_MONTH_YEAR = "dd MMMM yyyy";
    private static final String TAG = DateUtil.class.getSimpleName();
    private static DateUtil ourInstance;
    private static final String CONS_YEAR_MONTH_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final String CODE_GENERATION_TIME_FORMAT = "yyyyMMdd";
    private DateUtil() {
    }

    public static void initDateUtils() {
        if (ourInstance == null) {
            ourInstance = new DateUtil();
        }
    }

    public static DateUtil getOurInstance() {
        return ourInstance;
    }

    /**
     * This method is used to format the current date
     *
     * @return current date format
     */
    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(CONS_SDFPATTERN, Locale.getDefault());
        return format.format(date);
    }

    /**
     * This method is used to format the current date
     *
     * @return current date format
     */
    public static String getCurrentYearMonthDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(CONS_YEAR_MONTH_DATE, Locale.getDefault());
        return format.format(date);
    }

    /**
     * This method is used to format the current date
     *
     * @return current date format
     */
    public static String getCurrentDateMonthYear() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(CONS_DATE_MONTH_YEAR, Locale.getDefault());
        return format.format(date);
    }

    /**
     * This method is used to subtract one day with the current date
     *
     * @return previous date format
     */
    public static String getPreviousDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat format = new SimpleDateFormat(CONS_SDFPATTERN, Locale.getDefault());
        return format.format(calendar.getTime());
    }

    public static long convertStringDateToTimestamp(String inputDate, String dateFormat) {
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = formatter.parse(inputDate);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        if (date != null)
            return date.getTime();

        return 0;
    }

    public static String convertTimestampToStringDate(long millis) {
        SimpleDateFormat format = new SimpleDateFormat(CONS_SDFPATTERN);
        return format.format(millis);
    }

    public static String getCurrentMonth(String date) {

        if (date == null) {
            return "";
        }

        String[] stringTokens = date.split("-");


        int month = Integer.parseInt(stringTokens[1]);
        return String.valueOf(month);
    }

    public static String getCurrentDate(String date) {

        if (date == null) {
            return "";
        }
        String[] stringTokens = date.split("-");
        int day = Integer.parseInt(stringTokens[2]);
        return String.valueOf(day);
    }

    public static String getCurrentYear(String date) {

        if (date == null) {
            return "";
        }

        String[] stringTokens = date.split("-");
        int year = Integer.parseInt(stringTokens[0]);
        return String.valueOf(year);
    }

    public static Date covertStringDateIntoDate(String strDate, String formatPattern) {
        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat(formatPattern, Locale.US);
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            Log.e(TAG, "covertStringDateIntoDate: " + e.getMessage(), e);
        }
        return date;
    }

    public static String covertDateToString(Date date, String formatPattern) {
        String strDate;
        DateFormat dateFormat = new SimpleDateFormat(formatPattern);
        strDate = dateFormat.format(date);
        return strDate;
    }

    public static Integer calcDaysBetweenDates(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static Date covertLongValueIntoDate(long invoiceDate) {
        return new Date(invoiceDate);
    }

    public static String covertTimeStampIntoData(String invoiceDate, String pattern) {
        try {
            SimpleDateFormat df2 = new SimpleDateFormat(pattern, Locale.getDefault());
            return df2.format(covertLongValueIntoDate(Long.parseLong(invoiceDate)));
        } catch (Exception e) {
            Log.e(TAG, "covertTimeStampIntoData: " + e.getMessage(), e);
            return "";
        }
    }

    public static String covertLongValueIntoDateString(long invoiceDate, String pattern) {
        SimpleDateFormat df2 = new SimpleDateFormat(pattern, Locale.getDefault());
        return df2.format(covertLongValueIntoDate(invoiceDate));
    }

    public static String convertTime(String endTime, String startTime) {

        Date sTime;

        if (startTime.equalsIgnoreCase("0")) {
            sTime = covertLongValueIntoDate(Long.parseLong(endTime));
        } else {
            sTime = covertLongValueIntoDate(Long.parseLong(startTime));
        }

        Date eTime = covertLongValueIntoDate(Long.parseLong(endTime));
        return convertTime(eTime.getTime() - sTime.getTime());
    }


    public static String convertTime(long millis) {
        @SuppressLint("DefaultLocale") String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return hms;
    }


    public static String convertStringToDate(String datetime) {

        String strDate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
        try {
            Date date = format.parse(datetime);
            strDate = "" + date.getTime();
        } catch (ParseException e) {
            Log.e(TAG, "convertStringToDate: " + e.getMessage(), e);
        }
        return strDate;
    }


    public static  String parseDateToddMMyyyy(String time) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(CONS_SDFPATTERN);
        SimpleDateFormat outputFormat = new SimpleDateFormat(CONS_YEAR_MONTH_DATE);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return str;
    }

    public String covertUtilDateIntoDatebaseDateString(Date date) {
        String dateString = null;
        try {
            DateFormat formatter = new SimpleDateFormat(CONS_YEAR_MONTH_DATE, Locale.getDefault());
            dateString = formatter.format(date);
        } catch (Exception e) {
            Log.e(TAG, "covertUtilDateIntoDatebaseDateString: " + e.getMessage(), e);
        }
        return dateString;
    }

    public String covertUtilDateIntoDatebaseDateString(Date date, String pattern) {
        String dateString = null;
        try {
            DateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
            dateString = formatter.format(date);
        } catch (Exception e) {
            Log.e(TAG, "covertUtilDateIntoDatebaseDateString: " + e.getMessage(), e);
        }
        return dateString;
    }

    public static String formatDate(String date, String currentPattern, String requiredPattern) {
        String requiredDate = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat(currentPattern);
            Date d = format.parse(date);

            requiredDate = covertDateToString(d,requiredPattern);
        } catch (Exception e) {
            Log.e(TAG, "formatDate: " + e.getMessage(), e);
        }
        return requiredDate;
    }

    public static String getNameOfDay(int year, int dayOfYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);

        return days[dayIndex - 1];
    }

    public static String getNameOfDayUsingDate(String dateString) {
        SimpleDateFormat inFormat = new SimpleDateFormat(CONS_SDFPATTERN);
        Date date;
        try {
            date = inFormat.parse(dateString);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage(), e);
            return "";
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        return outFormat.format(date);
    }

    public static String convertTimestampToStringTime(long millis) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(millis);
    }

    public static String getCurrentDateAndTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(CONS_YEAR_MONTH_DATE_TIME, Locale.getDefault());
        return format.format(date);
    }

    public static String getCurrentDateAndTimeForCodeGen() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(CODE_GENERATION_TIME_FORMAT, Locale.getDefault());
        return format.format(date);
    }

    public static String convertDateStringFormat(String datetime) {

        String strDate = "";
        SimpleDateFormat format = new SimpleDateFormat(CONS_YEAR_MONTH_DATE, Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(CONS_SDFPATTERN, Locale.ENGLISH);
        try {
            Date date = format.parse(datetime);
            strDate = outputFormat.format(date);
            Log.e("Current start time Date", "" + strDate);
        } catch (ParseException e) {
            Log.e(TAG, "convertStringToDate: " + e.getMessage(), e);
        }
        return strDate;
    }
}
