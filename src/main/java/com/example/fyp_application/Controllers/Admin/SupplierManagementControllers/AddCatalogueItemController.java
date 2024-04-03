package com.example.fyp_application.Controllers.Admin.SupplierManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddCatalogueItemController implements Initializable {

    @FXML
    private Button addPhoto_btn;

    @FXML
    private ChoiceBox<AssetCategoryModel> assetCategory_CB;

    @FXML
    private TextField assetName_TF;

    @FXML
    private TextField assetPhoto_TF;

    @FXML
    private TextField assetPrice_TF;

    @FXML
    private Button cancel_btn;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private ChoiceBox<AssetManufacturerModel> manufacturer_CB;

    @FXML
    private ChoiceBox<String> ramSpec_CB;

    @FXML
    private Button saveProfileChanges_btn;

    @FXML
    private ChoiceBox<String> storageSpec_CB;

    @FXML
    private ChoiceBox<SupplierModel> supplier_CB;


    @FXML
    private void closeWindow() {
        SharedButtonUtils.closeMenu(cancel_btn);
    }

    @FXML
    private void addCatalogueItem(){
        if(isFormValid()){
            //add catalogue item
            ProcurementCatalogueDAO.addCatalogueItem(
                    supplier_CB.getValue().getSupplierID(),
                    manufacturer_CB.getValue().getManufacturerID(),
                    assetCategory_CB.getValue().getAssetCategoryID(),
                    assetName_TF.getText(),
                    storageSpec_CB.getValue(),
                    ramSpec_CB.getValue(),
                    Integer.parseInt(assetPrice_TF.getText()),
                    assetPhoto_TF.getText()
            );
            AlertNotificationUtils.showInformationMessageAlert("Success", "Catalogue Item Added Successfully");
            closeWindow();
        }else{
            AlertNotificationUtils.showErrorMessageAlert("Cannot Proceed", "Please fill in all fields");
        }
    }


    @FXML
    private void setPhotoPath(){
        assetPhoto_TF.setText(AttachmentUtils.addIndividualAttachment());

    }

    @FXML
    private void setupForm(){
        //setup form
        List<AssetCategoryModel> assetCategoryList = AssetCategoryDAO.getAllAssetCategories();
        assetCategory_CB.getItems().addAll(assetCategoryList);
        List<AssetManufacturerModel> assetManufacturerList = AssetManufacturerDAO.getAllAssetManufacturers();
        manufacturer_CB.getItems().addAll(assetManufacturerList);
        List<SupplierModel> supplierList = SupplierDAO.getAllSupplierNameList();
        supplier_CB.getItems().addAll(supplierList);

        ramSpec_CB.getItems().addAll("N/A","8GB", "16GB", "32GB", "64GB");
        storageSpec_CB.getItems().addAll("N/A","128GB", "256GB", "512GB", "1TB", "2TB");

        TextFieldListenerUtils.assetPriceTextFieldListener(assetPrice_TF);
    }


    @FXML
    private void resetForm(){
        assetName_TF.clear();
        assetPrice_TF.clear();
        assetPhoto_TF.clear();
        manufacturer_CB.setValue(null);
        ramSpec_CB.setValue(null);
        storageSpec_CB.setValue(null);
        assetCategory_CB.setValue(null);
        supplier_CB.setValue(null);
    }

    @FXML
    private boolean  isFormValid(){
        return  !assetName_TF.getText().isEmpty() &&
                !assetPrice_TF.getText().isEmpty() &&
                !assetPhoto_TF.getText().isEmpty() &&
                manufacturer_CB.getValue() != null &&
                ramSpec_CB.getValue() != null &&
                storageSpec_CB.getValue() != null &&
                assetCategory_CB.getValue() != null &&
                supplier_CB.getValue() != null;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupForm();
        DateTimeUtils.dateTimeUpdates(dateTimeHolder);

    }
}
