package com.example.fyp_application.Controllers.Admin.SupplierControllers;

import com.example.fyp_application.Model.SupplierDAO;
import com.example.fyp_application.Model.SupplierModel;
import com.example.fyp_application.Utils.AlertHandlerController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditSupplierController {

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

    private int supplierID;

    private static final AlertHandlerController ALERT_HANDLER = new AlertHandlerController();

    private static final SupplierDAO SUPPLIER_DAO = new SupplierDAO();
    @FXML
    private void cancelEdit(){
        cancel_btn.getScene().getWindow().hide();

    }

    @FXML
    private void resetForm(){
        supName_TF.clear();
        supAddress_TA.clear();
        supPhone_TF.clear();
        supEmail_TF.clear();
    }


    /*

        AddSupplierController addSupplierController = new AddSupplierController();
        if(addSupplierController.isEmptyFields()){
            //alertHandlerController.showErrorMessageAlert("Empty Fields", "Please fill in all fields");
        }
        else {
            //supplierDAO.updateSupplier(supID, supName_TF.getText(), supAddress_TA.getText(), supPhone_TF.getText(), supEmail_TF.getText());
            //alertHandlerController.showInformationMessage("Success", "Supplier edited successfully");
            resetForm();
        }
*/



    @FXML
    private boolean isEmptyFields(){

        return supName_TF.getText().isEmpty() || supAddress_TA.getText().isEmpty() || supPhone_TF.getText().isEmpty() || supEmail_TF.getText().isEmpty();
    }

    public void loadSelectedSupplierInfo(SupplierModel selectedSupplier) {

        this.supplierID = selectedSupplier.getSupplierID();
        System.out.println(supplierID);
        supName_TF.setText(selectedSupplier.getSupplierName());
        supAddress_TA.setText(selectedSupplier.getSupplierAddress());
        supPhone_TF.setText(selectedSupplier.getSupplierContact());
        supEmail_TF.setText(selectedSupplier.getSupplierEmail());
    }


    @FXML
    private void saveEdit(){



        if(isEmptyFields()){
            ALERT_HANDLER.showErrorMessageAlert("Empty Fields", "Please fill in all fields");
        }
        else{
            SUPPLIER_DAO.updateSupplier(this.supplierID, supName_TF.getText(), supAddress_TA.getText(), supPhone_TF.getText(), supEmail_TF.getText());
            ALERT_HANDLER.showInformationMessageAlert("Update Completed", "Supplier information updated successfully");
            save_btn.getScene().getWindow().hide();
        }


    }

}
