package com.example.fyp_application.Utils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javafx.scene.control.Label;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHandler {


    private static final DateTimeFormatter CURRENT_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm a");
    private static final DateTimeFormatter CURRENT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static  final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("LLL dd, yyyy");

    private static final DateTimeFormatter SQLITE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private static final DateTimeFormatter DATE_CREATED_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");
    public static String getCurrentTime(){

        return LocalTime.now().format(CURRENT_TIME_FORMAT);
    }


    public static String getCurrentDate(){

        return LocalDate.now().format(CURRENT_DATE_FORMAT);
    }


    public static String getMonthDayYearFormat(){
        return LocalDate.now().format(DATE_FORMAT);
    }


    public  static String getSQLiteDate(){

        return LocalDate.now().format(SQLITE_DATE_FORMAT);
    }


    public static String setSQLiteDateFormat(LocalDate date){
        return date.format(SQLITE_DATE_FORMAT);
    }


    public static void dateTimeUpdates(Label timeHolder){

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            String currentTime = getCurrentTime();
            String currentDate = getMonthDayYearFormat();

            timeHolder.setText("Today " + currentDate + " | " + currentTime);
        }),

        new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play(

        );


    }

    public static String getCurrentDateTime() {
        return getSQLiteDate() + " " + getCurrentTime();
    }



    public static String dateParser(String dateCreated){



        // parse the date
        LocalDateTime dateTime = LocalDateTime.parse(dateCreated,DATE_CREATED_DATE_FORMAT);

        LocalDateTime twoWeeksDateTime = dateTime.plusDays(14);

        return twoWeeksDateTime.format(SQLITE_DATE_FORMAT);
    }
}
