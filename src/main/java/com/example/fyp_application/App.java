package com.example.fyp_application;

import com.example.fyp_application.Controllers.Shared.SplashScreenController;
import com.example.fyp_application.Views.ViewConstants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class App extends Application{

    private double xCoordinate = 0;
    private double yCoordinate = 0;


/*
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource(ViewConstants.APP_LOGIN)));

        Scene scene = new Scene(fxmlLoader.load());

        scene.setOnMousePressed((MouseEvent event) -> {
            xCoordinate = event.getSceneX();
            yCoordinate = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xCoordinate);
            stage.setY(event.getScreenY() - yCoordinate);
        });


        stage.getIcons().add(new Image(getClass().getResourceAsStream(ViewConstants.APP_ICON)));
        stage.setTitle("FYP Application");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        stage.setResizable(false);

    }

    public static void main(String[] args) {
        launch(args);
    }*/
private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showSplashScreen();
    }
/*
    private void showSplashScreen() throws Exception {
        // Load the splash screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewConstants.APP_SPLASH_SCREEN));
        Parent root = loader.load();
        Stage splashStage = new Stage(StageStyle.UNDECORATED);
        splashStage.setScene(new Scene(root));
        splashStage.show();

        // Start database updates and progress reporting
        SplashScreenController splashController = loader.getController();
        splashController.startDatabaseUpdates();

        // Listen for completion (you might implement a completion listener in your controller)
        splashController.setOnDatabaseUpdateCompleteListener(() -> {
            splashStage.hide();
            showMainApplication();
        });
    }*/
/*
    private void showSplashScreen() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewConstants.APP_SPLASH_SCREEN));
        Parent root = loader.load();
        Stage splashStage = new Stage(StageStyle.UNDECORATED);
        splashStage.setScene(new Scene(root));
        splashStage.show();
        SplashScreenController splashController = loader.getController();


        // Start database updates
        splashController.startDatabaseUpdates();

        // Create a task for smooth progress updates
        Task<Void> smoothProgressTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                final int totalSteps = 100;
                for (int i = 1; i <= totalSteps; i++) {
                    System.out.println("Progress: " + i / (double) totalSteps);
                    if (isCancelled()) {
                        break;
                    }

                    updateProgress(i / (double) totalSteps);
                    Thread.sleep(1); // Adjust the sleep time to control the speed of progress updates
                }
                return null;
            }

            private void updateProgress(double progress) {
                javafx.application.Platform.runLater(() -> splashController.updateProgress(progress));
            }
        };

        // Listen for completion of the database update and stop the smooth progress task
        splashController.setOnDatabaseUpdateCompleteListener(() -> {
            smoothProgressTask.cancel(true);
            splashStage.hide();
            showMainApplication();
        });

        // Start the smooth progress update task in a new thread
        new Thread(smoothProgressTask).start();
    }*/


    private void showSplashScreen() throws Exception {
        //Load the splash screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewConstants.APP_SPLASH_SCREEN));
        Parent root = loader.load();

        // Create a new stage for the splash screen without decorations
        Stage splashScreenStage = new Stage(StageStyle.UNDECORATED);
        splashScreenStage.getIcons().add(new Image(getClass().getResourceAsStream(ViewConstants.APP_ICON)));
        splashScreenStage.setScene(new Scene(root));
        splashScreenStage.show();

        //Get the Splash Screen Controller
        SplashScreenController splashScreenController = loader.getController();


        //Start the database updates
        splashScreenController.startDatabaseUpdates();

        // Define minimum display time for the splash screen
        final long minimumSplashScreenDisplayTime = 5000; // 5 seconds
        final long startTime = System.currentTimeMillis();

        // Smooth progress update task
        Task<Void> splashScreenProgressBarTask = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    final int totalProgressSteps = 100; // 100 steps for 100%
                    for (int stepCounter = 0; stepCounter <= totalProgressSteps; stepCounter++) {
                        if (isCancelled()) {
                            // If the task is cancelled, stop the progress updates
                            break;
                        }
                        updateProgress(stepCounter / 100.0); // Update the progress and display it
                        Thread.sleep(30); // Sleep for a short time to simulate work on the task
                    }
                } catch (InterruptedException e) {
                    if (isCancelled()) {
                        return null;
                    }
                }
                return null;
            }

            // Update the progress on the JavaFX Application Thread
            private void updateProgress(double progress) {
                Platform.runLater(() -> splashScreenController.updateProgress(progress));
            }
        };

        new Thread(splashScreenProgressBarTask).start();

        // Wait for the database update to complete and then ensure minimum display time
        splashScreenController.setOnDatabaseUpdateCompleteListener(() -> {
            // Calculate the elapsed time
            new Thread(() -> {
                try {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    System.out.println("\n Elapsed Time: " + elapsedTime);
                    long remainingTime = minimumSplashScreenDisplayTime - elapsedTime;
                    System.out.println("Remaining Time: " + remainingTime + "\n");
                    if (remainingTime > 0) {
                        System.out.println("Waiting");
                        Thread.sleep(remainingTime); // Wait for the remaining time
                        System.out.println("Thread Resumed terminating the splash screen");
                    }
                    Platform.runLater(() -> {
                        // Hide the splash screen and show the main application
                        splashScreenStage.hide();
                        showMainApplication();
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        });
    }


    private void showMainApplication() {
        // Load and show the main application UI
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewConstants.APP_LOGIN));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Main Application");
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(ViewConstants.APP_ICON)));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

