package com.example.fyp_application.Utils;

import com.example.fyp_application.Model.*;
import javafx.collections.FXCollections;
import javafx.scene.control.*;

import java.util.List;

public class TextFieldListenerUtils {


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

                // Removes non digit numbers from the input
                String numberOnlyValue = newInput.replaceAll("\\D", "");

                // Assuming arbitrary value - 1_000_000
                // check the length of the phone number
                if (numberOnlyValue.length() > 6) {
                    // stop the user from entering more than 7 characters
                    assetPriceTextField.setText(numberOnlyValue.substring(0, 6));
                }

                if(!newInput.equals(numberOnlyValue)){
                    assetPriceTextField.setText(numberOnlyValue);
                }
            });
       }


       public static void userSearchBarListener(TextField searchBar, ComboBox<UserModel> userList_CB, List<UserModel> userList) {
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                // Filter the list of users based on the search query
                List<UserModel> filteredList = userList.stream()
                        .filter(user -> user.getFirstName().toLowerCase().contains(newValue.toLowerCase()) ||
                                user.getLastName().toLowerCase().contains(newValue.toLowerCase()) ||
                                user.getUsername().toLowerCase().contains(newValue.toLowerCase()))
                        .toList();

                // Update the list of users in the ComboBox
                userList_CB.setItems(FXCollections.observableArrayList(filteredList));
            });
       }



    public static void ticketSearchBarListener(TextField searchBar, ComboBox<UserModel> userList_CB, List<UserModel> userList) {
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            // Filter the list of users based on the search query
            List<UserModel> filteredList = userList.stream()
                    .filter(user -> user.getFirstName().toLowerCase().contains(newValue.toLowerCase()) ||
                            user.getLastName().toLowerCase().contains(newValue.toLowerCase()) ||
                            user.getUsername().toLowerCase().contains(newValue.toLowerCase()))
                    .toList();

            // Update the list of users in the ComboBox
            userList_CB.setItems(FXCollections.observableArrayList(filteredList));
        });
    }



    public static void refreshUserTicketForm(TextField ticketTitle, TextArea ticketDetails, ListView<String> attachmentListView, CheckBox attachmentCheckbox) {
        ticketTitle.clear();
        ticketDetails.clear();
        attachmentListView.getItems().clear();
        attachmentCheckbox.setSelected(false);
    }
}
