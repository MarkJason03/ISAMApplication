package com.example.fyp_application.Controllers.Admin.SupplierManagementControllers;

import com.example.fyp_application.Model.SupplierDAO;
import com.example.fyp_application.Model.SupplierModel;
import com.example.fyp_application.Utils.AlertNotificationUtils;
import com.example.fyp_application.Utils.DateTimeUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifiedEditSupplierController implements Initializable {

    @FXML
    private Button cancel_btn;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private DatePicker expiryDate_DP;

    @FXML
    private Label dateChecker_lbl;

    @FXML
    private Button saveProfileChanges_btn;

    @FXML
    private TextField supAddress_TF;

    @FXML
    private TextField supEmail_TF;

    @FXML
    private TextField supName_TF;

    @FXML
    private TextField supPhone_TF;

    @FXML
    private ChoiceBox<String> supStatus_CB;


    private int supplierID;


    private static final SupplierDAO SUPPLIER_DAO = new SupplierDAO();

    @FXML
    private void saveProfileChanges () {

        LocalDate currentSelectedDate = expiryDate_DP.getValue();
        DateTimeUtils.setYearMonthDayFormat(currentSelectedDate);

        System.out.println(currentSelectedDate);

        if(checkForm()){

            SUPPLIER_DAO.updateSupplier(supplierID, supName_TF.getText(), supAddress_TF.getText(), supPhone_TF.getText(), supEmail_TF.getText(), supStatus_CB.getValue(), DateTimeUtils.setYearMonthDayFormat(currentSelectedDate));
            AlertNotificationUtils.showInformationMessageAlert("Success", "Supplier edited successfully");
            cancel_btn.getScene().getWindow().hide();



        } else {
            AlertNotificationUtils.showErrorMessageAlert("Invalid Entry", "Please fill in all fields and select a valid date");
        }

    }


    public void loadSelectedSupplierDetails(SupplierModel supplierModel) {

        this.supplierID = supplierModel.getSupplierID();

        supName_TF.setText(supplierModel.getSupplierName());
        supAddress_TF.setText(supplierModel.getSupplierAddress());
        supPhone_TF.setText(supplierModel.getSupplierContact());
        supEmail_TF.setText(supplierModel.getSupplierEmail());
        supStatus_CB.setValue(supplierModel.getSupplierContractStatus());
        expiryDate_DP.setValue(LocalDate.parse(supplierModel.getContractEndDate()));

    }

    @FXML
    private void cancelAction() {
        cancel_btn.getScene().getWindow().hide();

  /*
        if( isValidDate() && !isEmptyFields()){
            System.out.println("valid information");
            System.out.println(isValidDate());
            System.out.println(!isEmptyFields());
        } else {
            System.out.println("Invalid info");
            System.out.println(isValidDate());
            System.out.println(!isEmptyFields());
        }*/

    }

    @FXML
    private void refreshInformation(){
        //Todo reload the information from the database
    }

    private boolean checkForm(){
        return isValidDate() && !isEmptyFields();
    }

    private boolean isEmptyFields() {
        // Trim each text field value before checking if it's empty
        return supName_TF.getText().trim().isEmpty() ||
                supAddress_TF.getText().trim().isEmpty() ||
                supPhone_TF.getText().trim().isEmpty() ||
                supEmail_TF.getText().trim().isEmpty();
    }



    private boolean isValidDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = expiryDate_DP.getValue();

        if (selectedDate == null || selectedDate.isBefore(currentDate)) {
            dateChecker_lbl.setText(selectedDate == null ? "Date is required" : "Invalid Date");
            dateChecker_lbl.setStyle("-fx-text-fill: red");
            return false;
        } else {
            dateChecker_lbl.setText("Valid Date");
            dateChecker_lbl.setStyle("-fx-text-fill: green");
            return true;
        }
    }




        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        supStatus_CB.getItems().addAll("Active", "Inactive");
        DateTimeUtils.dateTimeUpdates(dateTimeHolder);
    }
}
