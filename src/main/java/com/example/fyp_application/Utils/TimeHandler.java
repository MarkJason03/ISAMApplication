package com.example.fyp_application.Utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeHandler {


    private static final DateTimeFormatter CURRENT_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm a");
    private static final DateTimeFormatter CURRENT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static  final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("LLL dd, yyyy");
    public static String getCurrentTime(){
        LocalTime currentTime = LocalTime.now();
        return currentTime.format(CURRENT_TIME_FORMAT);
    }


    public static String getCurrentDate(){
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(CURRENT_DATE_FORMAT);
    }


    public static String getMonthDayYearFormat(){
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(DATE_FORMAT);
    }

}
