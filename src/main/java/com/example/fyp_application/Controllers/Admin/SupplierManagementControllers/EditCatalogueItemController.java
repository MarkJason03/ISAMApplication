package com.example.fyp_application.Controllers.Admin.SupplierManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.AttachmentUtils;
import com.example.fyp_application.Utils.SharedButtonUtils;
import com.example.fyp_application.Utils.TextFieldListenerUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditCatalogueItemController implements Initializable {

    @FXML
    private Button addPhoto_btn;

    @FXML
    private ChoiceBox<AssetCategoryModel> assetCategory_CB;

    @FXML
    private TextField assetName_TF;

    @FXML
    private TextField assetPrice_TF;

    @FXML
    private Button cancel_btn;

    @FXML
    private Label dateChecker_lbl;

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
    private TextField assetPhotoPath_TF;

    @FXML
    private ChoiceBox<SupplierModel> supplier_CB;

    private int catalogueID;


    //This controller breaks DRY principles as it has a lot of repeated code from other controllers.
    //This controller can be refactored to use a superclass that contains the repeated code.




    @FXML
    private void setPhotoPath(){
        //set photo path
        AttachmentUtils.addIndividualAttachment();
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

        //TextFieldListenerUtils.assetPriceTextFieldListener(assetPrice_TF);
    }
    public void loadSelectedCatalogueItem(ProcurementCatalogueModel selectedItem) {

        this.catalogueID = selectedItem.getCatalogID();

        assetName_TF.setText(selectedItem.getAssetName());

        String formattedPrice = String.format("%.2f", selectedItem.getAssetPrice());
        assetPrice_TF.setText(formattedPrice);
        assetPhotoPath_TF.setText(selectedItem.getAssetPicture());


        for (AssetCategoryModel assetCategoryModel : assetCategory_CB.getItems()) {
            if (Objects.equals(assetCategoryModel.getAssetCategoryName(), selectedItem.getAssetCategory())) {
                assetCategory_CB.setValue(assetCategoryModel);
            }
        }

        for (AssetManufacturerModel assetManufacturerModel : manufacturer_CB.getItems()) {
            if (Objects.equals(assetManufacturerModel.getManufacturerName(), selectedItem.getManufacturerName())){
                manufacturer_CB.setValue(assetManufacturerModel);
            }
        }

        for (SupplierModel supplierModel : supplier_CB.getItems()) {
            if (Objects.equals(supplierModel.getSupplierName(), selectedItem.getSupplierName())){
                supplier_CB.setValue(supplierModel);
            }
        }

        ramSpec_CB.setValue(selectedItem.getRamSpecs());
        storageSpec_CB.setValue(selectedItem.getStorageSpecs());

    }

    @FXML
    private void closeWindow(){

        SharedButtonUtils.closeMenu(cancel_btn);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupForm();


    }

}
