package com.example.fyp_application.Controllers.Admin.SupplierManagementControllers;

import com.example.fyp_application.Model.SupplierDAO;
import com.example.fyp_application.Model.SupplierModel;
import com.example.fyp_application.Utils.AlertNotificationUtils;
import com.example.fyp_application.Utils.DateTimeUtils;
import com.example.fyp_application.Utils.SharedButtonUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    private TextField supEmail_TF;

    @FXML
    private TextField supName_TF;

    @FXML
    private TextField supPhone_TF;

    @FXML
    private ChoiceBox<String> supStatus_CB;

    @FXML
    private TextArea supplierAddress_TA;
    private int supplierID;


    private static final SupplierDAO SUPPLIER_DAO = new SupplierDAO();

    @FXML
    private void saveProfileChanges () {

        if(!isEmptyFields()){

            SUPPLIER_DAO.updateSupplier(
                    supplierID, supName_TF.getText(),
                    supplierAddress_TA.getText(),
                    supPhone_TF.getText(),
                    supEmail_TF.getText(),
                    supStatus_CB.getValue(),
                    DateTimeUtils.setYearMonthDayFormat(expiryDate_DP.getValue()));
            AlertNotificationUtils.showInformationMessageAlert("Success", "Supplier edited successfully");
            cancel_btn.getScene().getWindow().hide();



        } else {
            AlertNotificationUtils.showErrorMessageAlert("Invalid Entry", "Please fill in all fields and select a valid date");
        }

    }


    public void loadSelectedSupplierDetails(SupplierModel supplierModel) {

        this.supplierID = supplierModel.getSupplierID();

        supName_TF.setText(supplierModel.getSupplierName());
        supplierAddress_TA.setText(supplierModel.getSupplierAddress());
        supPhone_TF.setText(supplierModel.getSupplierContact());
        supEmail_TF.setText(supplierModel.getSupplierEmail());
        supStatus_CB.setValue(supplierModel.getSupplierContractStatus());
        expiryDate_DP.setValue(LocalDate.parse(supplierModel.getContractEndDate()));

    }

    @FXML
    private void cancelAction() {
        SharedButtonUtils.closeMenu(cancel_btn);
    }

    @FXML
    private void refreshInformation(){
        //Todo reload the information from the database
        ObservableList<SupplierModel> supplierModel = SupplierDAO.getSupplierDetails(supplierID);

        this.supplierID = supplierModel.get(0).getSupplierID();

        supName_TF.setText(supplierModel.get(0).getSupplierName());
        supplierAddress_TA.setText(supplierModel.get(0).getSupplierAddress());
        supPhone_TF.setText(supplierModel.get(0).getSupplierContact());
        supEmail_TF.setText(supplierModel.get(0).getSupplierEmail());
        supStatus_CB.setValue(supplierModel.get(0).getSupplierContractStatus());
        expiryDate_DP.setValue(LocalDate.parse(supplierModel.get(0).getContractEndDate()));

    }


    private boolean isEmptyFields() {
        // Trim each text field value before checking if it's empty
        return supName_TF.getText().trim().isEmpty() ||
                supplierAddress_TA.getText().trim().isEmpty() ||
                supPhone_TF.getText().trim().isEmpty() ||
                supEmail_TF.getText().trim().isEmpty() ||
                supStatus_CB.getValue() == null ||
                expiryDate_DP.getValue() == null;

    }






        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        supStatus_CB.getItems().addAll("Active", "Expired");
        DateTimeUtils.dateTimeUpdates(dateTimeHolder);
        DateTimeUtils.dateValidator(expiryDate_DP);
    }
}
