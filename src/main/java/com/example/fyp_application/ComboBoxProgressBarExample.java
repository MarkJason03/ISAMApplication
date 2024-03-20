package com.example.fyp_application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ComboBoxProgressBarExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox vBox = new VBox(10); // A VBox layout with spacing of 10

        // Create combo boxes
        ComboBox<String> comboBox1 = new ComboBox<>();
        comboBox1.getItems().addAll("Option 1", "Option 2", "Option 3");

        ComboBox<String> comboBox2 = new ComboBox<>();
        comboBox2.getItems().addAll("Option A", "Option B", "Option C");

        // Create a progress bar with initial progress set to 0
        ProgressBar progressBar = new ProgressBar(0);

        // Add combo boxes and progress bar to the VBox layout
        vBox.getChildren().addAll(comboBox1, comboBox2, progressBar);

        // Listener to update progress bar
        ChangeListener<String> listener = (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            int filledCount = 0;
            if (comboBox1.getValue() != null) filledCount++;
            if (comboBox2.getValue() != null) filledCount++;
            double progress = filledCount / 2.0; // Assuming 2 combo boxes
            progressBar.setProgress(progress);
        };

        // Attach listeners to combo boxes
        comboBox1.valueProperty().addListener(listener);
        comboBox2.valueProperty().addListener(listener);

        // Show the stage
        primaryStage.setScene(new Scene(vBox, 300, 200));
        primaryStage.setTitle("Combo Box Progress Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
