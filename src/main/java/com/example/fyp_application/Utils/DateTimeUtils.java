package com.example.fyp_application.Utils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.DatePicker;
import javafx.util.Duration;

import javafx.scene.control.Label;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {


    // Format the time format of "12:00 PM"
    private static final DateTimeFormatter CURRENT_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm a");

    // Format the date format of "01/01/2021"
    private static final DateTimeFormatter UK_CURRENT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Format the date format of "Jan 01, 2021"
    private static  final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("LLL dd, yyyy");

    // Format the date format of "2021-01-01"
    private static final DateTimeFormatter CURRENT_YEAR_MONTH_DAY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private static final DateTimeFormatter CURRENT_YEAR_MONTH_DAY_HOURS_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");


    private DateTimeUtils(){}
    public static String getCurrentTimeFormat(){

        return LocalTime.now().format(CURRENT_TIME_FORMAT);
    }


    public static String getUKDateFormat(){

        return LocalDate.now().format(UK_CURRENT_DATE_FORMAT);
    }


    public static String getMonthDayYearFormat(){
        return LocalDate.now().format(DATE_FORMAT);
    }


    public  static String getYearMonthDayFormat(){

        return LocalDate.now().format(CURRENT_YEAR_MONTH_DAY_FORMAT);
    }


    public static String setYearMonthDayFormat(LocalDate date){
        return date.format(CURRENT_YEAR_MONTH_DAY_FORMAT);
    }


    public static void dateTimeUpdates(Label timeHolder){

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            String currentTime = getCurrentTimeFormat();
            String currentDate = getMonthDayYearFormat();

            timeHolder.setText("Today " + currentDate + " | " + currentTime);
        }),

        new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play(

        );


    }

    public static String getCurrentDateTime() {
        return getYearMonthDayFormat() + " " + getCurrentTimeFormat();
    }



    public static String setTargetResolutionDate(String dateCreated){



        // parse the date
        LocalDate dateTime = LocalDate.parse(dateCreated, CURRENT_YEAR_MONTH_DAY_FORMAT);

        LocalDate twoWeeksDateTime = dateTime.plusDays(14);

        return twoWeeksDateTime.format(CURRENT_YEAR_MONTH_DAY_FORMAT);
    }




    public static void dateValidator(DatePicker datePicker){

        Platform.runLater(() -> {
            datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && newValue.isBefore(LocalDate.now())) {

                    System.out.println("Date is before current date");

                    AlertNotificationUtils.showInformationMessageAlert("Invalid date",
                            "The date selected is before the current date. Please select a valid date.");

                    datePicker.setValue(null);
                    datePicker.setStyle("-fx-border-color: red");

                } else{

                    datePicker.setStyle("-fx-border-color: green");
                    System.out.println("Date is valid" + newValue);

                }
            });
        });
    }


    public static void endOfLifeValidator(DatePicker purchaseDate, DatePicker eolDate){

        Platform.runLater(() -> {
            eolDate.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && newValue.isBefore(purchaseDate.getValue())) {

                    System.out.println("Date is before current date");

                    AlertNotificationUtils.showInformationMessageAlert("Invalid date",
                            "The date selected is before the current date. Please select a valid date.");

                    eolDate.setValue(null);
                    eolDate.setStyle("-fx-border-color: red");

                } else{

                    eolDate.setStyle("-fx-border-color: green");
                    System.out.println("Date is valid" + newValue);

                }
            });
        });
    }


    public static void warrantyEndDateValidator(DatePicker warrantyStartDate, DatePicker warrantyEndDate){

        Platform.runLater(() -> {
            warrantyEndDate.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && newValue.isBefore(warrantyStartDate.getValue())) {

                    System.out.println("Date is before current date");

                    AlertNotificationUtils.showInformationMessageAlert("Invalid date",
                            "The date selected is before the current date. Please select a valid date.");

                    warrantyEndDate.setValue(null);
                    warrantyEndDate.setStyle("-fx-border-color: red");

                } else{

                    warrantyEndDate.setStyle("-fx-border-color: green");
                    System.out.println("Date is valid" + newValue);

                }
            });
        });
    }
}
