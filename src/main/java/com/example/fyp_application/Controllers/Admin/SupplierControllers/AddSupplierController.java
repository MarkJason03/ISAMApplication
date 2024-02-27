package com.example.fyp_application.Controllers.Admin.SupplierControllers;

import com.example.fyp_application.Model.SupplierDAO;
import com.example.fyp_application.Utils.AlertHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddSupplierController {

    @FXML
    private Button cancel_btn;

    @FXML
    private Button save_btn;

    @FXML
    private TextArea supAddress_TA;

    @FXML
    private TextField supEmail_TF;

    @FXML
    private TextField supName_TF;

    @FXML
    private TextField supPhone_TF;

    private static final AlertHandler ALERT_HANDLER = new AlertHandler();

    @FXML
    private void addSupplier(){


        SupplierDAO supplierDAO = new SupplierDAO();

        if (isEmptyFields()){

            ALERT_HANDLER.showErrorMessageAlert("Empty Fields", "Please fill in all fields");
        }
        else {
                supplierDAO.addSupplier(supName_TF.getText(), supAddress_TA.getText(), supPhone_TF.getText(), supEmail_TF.getText());
                ALERT_HANDLER.showInformationMessageAlert("Success", "Supplier added successfully");
                resetForm();
        }

    }


/*
    public void editSupplier(Integer supID, String supName, String supAddress, String supPhone, String supEmail){

        SupplierDAO supplierDAO = new SupplierDAO();
*//*if (alertHandlerController.confirmationAlert("Exit Confirmation","Are you sure you want to exit?"))*//*
        if (ALERT_HANDLER.confirmationAlert("Edit Confirmation","Are you sure you want to edit this supplier?")) {
            supplierDAO.updateSupplier(supID, supName_TF.getText(), supAddress_TA.getText(), supPhone_TF.getText(), supEmail_TF.getText());
            ALERT_HANDLER.showInformationMessage("Success", "Supplier edited successfully");
        }
        heelodd

    }*/


    @FXML
    private boolean isEmptyFields(){

        return supName_TF.getText().isEmpty() || supAddress_TA.getText().isEmpty() || supPhone_TF.getText().isEmpty() || supEmail_TF.getText().isEmpty();
    }

    @FXML
    private void exitWindow(){
        cancel_btn.getScene().getWindow().hide();
    }

    @FXML
    private void resetForm(){
        supName_TF.clear();
        supAddress_TA.clear();
        supPhone_TF.clear();
        supEmail_TF.clear();
    }



}
