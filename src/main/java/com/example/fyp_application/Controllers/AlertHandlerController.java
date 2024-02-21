package com.example.fyp_application.Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertHandlerController {

    private Alert alert;


    //Showing an error message to the user
    public boolean showError(String title, String Message){
        alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(Message);

        Optional<ButtonType> option = alert.showAndWait();

        return option.get().equals(ButtonType.OK);
    }

    //Showing Alert with Confirmation
    public boolean showInformationMessage(String title, String Message) {
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(Message);
        Optional<ButtonType> option = alert.showAndWait();
        return option.get().equals(ButtonType.OK);
    }



    //Showing Alert with Confirmation
    public boolean confirmationDialogueBox(String title, String Message){
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(Message);

        Optional<ButtonType> option = alert.showAndWait();

        return option.get().equals(ButtonType.OK);
    }

}
