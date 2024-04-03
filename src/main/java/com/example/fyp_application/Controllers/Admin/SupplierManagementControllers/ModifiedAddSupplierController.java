package com.example.fyp_application.Controllers.Admin.SupplierManagementControllers;

import com.example.fyp_application.Model.SupplierDAO;
import com.example.fyp_application.Utils.AlertNotificationUtils;
import com.example.fyp_application.Utils.DateTimeUtils;
import com.example.fyp_application.Utils.InformationGeneratorUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifiedAddSupplierController implements Initializable{

    @FXML
    private Button cancel_btn;

    @FXML
    private DatePicker contractStart_DP;

    @FXML
    private Label dateChecker_lbl;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private DatePicker expiryDate_DP;

    @FXML
    private Button saveProfileChanges_btn;

    @FXML
    private TextField supEmail_TF;

    @FXML
    private TextField supName_TF;

    @FXML
    private TextField supPhone_TF;

    @FXML
    private ChoiceBox<String> supStatus_CB;

    @FXML
    private TextArea supplierAddress_TA;

    private static final SupplierDAO SUPPLIER_DAO = new SupplierDAO();
    @FXML
    private void saveProfileChanges() {

        if(!isEmptyFields()){
            SUPPLIER_DAO.addSupplier(
                    supName_TF.getText(),
                    supEmail_TF.getText(),
                    supPhone_TF.getText(),
                    supStatus_CB.getValue(),
                    supplierAddress_TA.getText(),
                    DateTimeUtils.setYearMonthDayFormat(contractStart_DP.getValue()),
                    DateTimeUtils.setYearMonthDayFormat(expiryDate_DP.getValue()));
            AlertNotificationUtils.showInformationMessageAlert("Success", "Supplier added successfully");
            resetForm();

        } else{
            AlertNotificationUtils.showErrorMessageAlert("Empty Fields", "Please fill in all the fields");
        }
    }


    @FXML
    private void resetForm() {

        // Clear all the fields
        supName_TF.clear();
        supplierAddress_TA.clear();
        supPhone_TF.clear();
        supEmail_TF.clear();

        // Set the default values
        supStatus_CB.setValue("Active");
        contractStart_DP.setValue(LocalDate.now());
        expiryDate_DP.setValue(null);
    }

    @FXML
    private boolean isEmptyFields(){

        // Check if any of the fields are empty
        return supName_TF.getText().isEmpty()
                || supplierAddress_TA.getText().isEmpty()
                || supPhone_TF.getText().isEmpty()
                || supEmail_TF.getText().isEmpty()
                || supStatus_CB.getValue() == null
                || contractStart_DP.getValue() == null
                || expiryDate_DP.getValue() == null
                ||supplierAddress_TA.getText().isEmpty();

    }



    @FXML
    private void cancelAction() {
        // Close the window
        cancel_btn.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the date and combo box values
        supStatus_CB.setItems(FXCollections.observableArrayList("Active", "Expired"));
        contractStart_DP.setValue(LocalDate.now());
        DateTimeUtils.dateTimeUpdates(dateTimeHolder);

        supPhone_TF.textProperty().addListener((observable, oldValue, newInput) -> {

            // Remove spaces from the new value and set it to the TextField.
            String numberOnlyValue = newInput.replaceAll("\\D", "");

            // check the length of the phone number
            if(numberOnlyValue.length() > 11){
                // stop the user from entering more than 11 characters
                supPhone_TF.setText(numberOnlyValue.substring(0, 11));


            } else if (!numberOnlyValue.equals(newInput)) {
                //if the new value is not a digit, replace it with the sanitized value
                supPhone_TF.setText(numberOnlyValue);
            }

            if (numberOnlyValue.length() == 11 ){
                System.out.println("Valid phone number");
            }
        });

        supName_TF.textProperty().addListener((observable, oldValue, newValue) -> {
            // Remove spaces from the new value and set it to the TextField.
            String sanitizedValue = newValue.replaceAll(" ", "");
            if (!newValue.equals(sanitizedValue)) {
                supName_TF.setText(sanitizedValue);
            }

            // Update the email TextField using the sanitized value.
            supEmail_TF.setText(InformationGeneratorUtils.generateSupplierEmail(sanitizedValue));
        });



        supStatus_CB.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (supStatus_CB.getValue().equals("Expired")) {
                expiryDate_DP.setValue(LocalDate.now());
                expiryDate_DP.setDisable(true);
            } else {
                expiryDate_DP.setDisable(false);
            }

        });

        DateTimeUtils.dateValidator(expiryDate_DP);




    }
}
