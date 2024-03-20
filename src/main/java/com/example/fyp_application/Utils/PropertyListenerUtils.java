package com.example.fyp_application.Utils;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.*;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.application.Platform;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PropertyListenerUtils {

  /*  // Listen for changes in the phone number field
        userWorkPhone_TF.textProperty().addListener((observable, oldValue, newInput) -> {

        // Remove spaces from the new value and set it to the TextField.
        String numberOnlyValue = newInput.replaceAll("\\D", "");

        // check the length of the phone number
        if(numberOnlyValue.length() > 11){
            // stop the user from entering more than 11 characters
            userWorkPhone_TF.setText(numberOnlyValue.substring(0, 11));


        } else if (!numberOnlyValue.equals(newInput)) {
            //if the new value is not a digit, replace it with the sanitized value
            userWorkPhone_TF.setText(numberOnlyValue);
        }

        if (numberOnlyValue.length() == 11 ){
            System.out.println("Valid phone number");
        }
    });*/

        public static void phoneNumberTextFieldListener(TextField phoneNumberTextField) {
            phoneNumberTextField.textProperty().addListener((observable, oldValue, newInput) -> {

                // Remove spaces from the new value and set it to the TextField.
                String numberOnlyValue = newInput.replaceAll("\\D", "");

                // check the length of the phone number
                if(numberOnlyValue.length() > 11){
                    // stop the user from entering more than 11 characters
                    phoneNumberTextField.setText(numberOnlyValue.substring(0, 11));


                } else if (!numberOnlyValue.equals(newInput)) {
                    //if the new value is not a digit, replace it with the sanitized value
                    phoneNumberTextField.setText(numberOnlyValue);
                }

                if (numberOnlyValue.length() == 11 ){
                    System.out.println("Valid phone number");
                }
            });
       }



       public static void assetPriceTextFieldListener(TextField assetPriceTextField) {
            assetPriceTextField.textProperty().addListener((observable, oldValue, newInput) -> {

                // Remove spaces from the new value and set it to the TextField.
                String numberOnlyValue = newInput.replaceAll("\\D", "");

                // Assuming arbitrary value - 1_000_000
                // check the length of the phone number
                if (numberOnlyValue.length() > 6) {
                    // stop the user from entering more than 7 characters
                    assetPriceTextField.setText(numberOnlyValue.substring(0, 6));
                }
            });
       }
}
