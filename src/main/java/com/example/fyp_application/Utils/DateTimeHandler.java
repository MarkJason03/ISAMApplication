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

public class DateTimeHandler {


    private static final DateTimeFormatter CURRENT_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm a");
    private static final DateTimeFormatter CURRENT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static  final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("LLL dd, yyyy");

    private static final DateTimeFormatter SQLITE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private static final DateTimeFormatter DATE_CREATED_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");


    private DateTimeHandler (){}
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




    public static void dateValidator(DatePicker datePicker){

        Platform.runLater(() -> {
            datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && newValue.isBefore(LocalDate.now())) {

                    System.out.println("Date is before current date");

                    AlertNotificationHandler.showInformationMessageAlert("Invalid date",
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

                    AlertNotificationHandler.showInformationMessageAlert("Invalid date",
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

                    AlertNotificationHandler.showInformationMessageAlert("Invalid date",
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
