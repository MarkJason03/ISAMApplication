package com.example.fyp_application.Controllers.Shared;

import com.example.fyp_application.Model.SupplierDAO;
import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Utils.DatabaseConnectionUtils;
import javafx.application.Platform;
import javafx.scene.control.Label;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

import java.util.Objects;
public class SplashScreenController {

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label progressbarInfo_lbl;

    @FXML
    private Label dbStatusCheck_lbl;

    private final Object lock = new Object();


    private Runnable onDatabaseUpdateCompleteListener;

    public void setOnDatabaseUpdateCompleteListener(Runnable onDatabaseUpdateCompleteListener) {
        this.onDatabaseUpdateCompleteListener = onDatabaseUpdateCompleteListener;
    }


    public void checkDatabaseConnection() {

        // Check if the database is connected
        if (DatabaseConnectionUtils.isDbConnected(Objects.requireNonNull(DatabaseConnectionUtils.getConnection()))){
            dbStatusCheck_lbl.setText("Updating Back-end");
        } else {
            // If the database is not connected, display an error message and exit the application
            System.out.println("Database Connection Failed");
            System.exit(0);
        }
    }


    public void startDatabaseUpdates() {
        Thread accountUpdateThread = new Thread(() -> {
            synchronized (lock) {



                // Updating for inactive accounts
                updateDatabaseStep(UserDAO::checkAndUpdateInactiveAccountStatus, 0.5);

                // Updating for expired accounts
                updateDatabaseStep(UserDAO::checkAndUpdateExpiredAccountStatus, 1.0);


                // Updating for contract status
                updateDatabaseStep(SupplierDAO::checkAndUpdateContractStatus, 1.0);

            }

            // Complete the process and possibly transition to the main app
            Platform.runLater(this::onDatabaseUpdateComplete);
        });

        accountUpdateThread.start();
    }


    private void updateDatabaseStep(Runnable step, double progressAfterStep) {
        step.run();  // Execute the database step

        updateProgress(progressAfterStep);  // Update progress on completion of the step
    }

    public void updateProgress(double progress) {
        Platform.runLater(() -> {
            checkDatabaseConnection();
            progressBar.setProgress(progress);
            progressbarInfo_lbl.setText("Initializing " + String.format("%.0f%%", progress * 100));

        });
    }

    private void onDatabaseUpdateComplete() {
        // Close splash and open main application window
        if (onDatabaseUpdateCompleteListener != null) {
            onDatabaseUpdateCompleteListener.run();
        }
    }
}