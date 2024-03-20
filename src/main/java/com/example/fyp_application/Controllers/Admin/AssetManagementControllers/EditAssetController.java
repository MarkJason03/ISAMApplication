package com.example.fyp_application.Controllers.Admin.AssetManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.AlertNotificationHandler;
import com.example.fyp_application.Utils.AttachmentHandler;
import com.example.fyp_application.Utils.DateTimeHandler;
import com.example.fyp_application.Utils.SharedButtonUtils;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EditAssetController implements Initializable {

    @FXML
    private Button addPhoto_btn;

    @FXML
    private ChoiceBox<String> assetCondition_CB;

    @FXML
    private TextField assetName_TF;

    @FXML
    private ChoiceBox<String> assetStatus_CB;

    @FXML
    private Button cancel_btn;

    @FXML
    private Button createAsset_btn;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private DatePicker estimatedEOL_DP;

    @FXML
    private Button exitWindowBtn;

    @FXML
    private ChoiceBox<String> osSpec_CB;

    @FXML
    private TextField photoPath_TF;

    @FXML
    private DatePicker purchaseDate_DP;

    @FXML
    private ChoiceBox<String> ramSpec_CB;

    @FXML
    private Button randomSerialNo_btn;

    @FXML
    private Button resetFormBtn;

    @FXML
    private TextField serialNo_TF;

    @FXML
    private ChoiceBox<String> storageSpec_CB;

    @FXML
    private DatePicker warrantyEnd_DP;

    @FXML
    private DatePicker warrantyStart_DP;

    @FXML
    private TextField assetPrice_TF;

    @FXML
    private ChoiceBox<AssetManufacturerModel> manufacturer_CB;

    @FXML
    private ChoiceBox<AssetCategoryModel> category_CB;

    @FXML
    private int assetID;


    @FXML
    public void loadSelectedAssetDetails(AssetModel selectedItem) {

        this.assetID = selectedItem.getAssetID();

        //Asset information
        assetName_TF.setText(selectedItem.getAssetName());
        serialNo_TF.setText(selectedItem.getSerialNumber());
        assetPrice_TF.setText(String.valueOf(selectedItem.getAssetPrice()));

        for (AssetCategoryModel assetCategoryModel : category_CB.getItems()) {
            if (assetCategoryModel.getAssetCategoryID() == selectedItem.getAssetCategoryID()) {
                category_CB.setValue(assetCategoryModel);
            }
        }

        for (AssetManufacturerModel assetManufacturerModel : manufacturer_CB.getItems()) {
            if (assetManufacturerModel.getManufacturerID() == selectedItem.getManufacturerID()) {
                manufacturer_CB.setValue(assetManufacturerModel);
            }
        }


        osSpec_CB.setValue(selectedItem.getOsSpec());
        ramSpec_CB.setValue(selectedItem.getRamSpec());
        storageSpec_CB.setValue(selectedItem.getStorageSpec());
        photoPath_TF.setText(selectedItem.getAssetPhotoPath());

        //Asset status information
        assetCondition_CB.setValue(selectedItem.getAssetCondition());
        assetStatus_CB.setValue(selectedItem.getAssetStatus());

        //Date information
        purchaseDate_DP.setValue(LocalDate.parse(selectedItem.getPurchaseDate()));
        estimatedEOL_DP.setValue(LocalDate.parse(selectedItem.getEolDate()));
        warrantyStart_DP.setValue(LocalDate.parse(selectedItem.getWarrantyDate()));
        warrantyEnd_DP.setValue(LocalDate.parse(selectedItem.getWarrantyEndDate()));


    }




    @FXML
    private void updateAssetDetails() {

        if(isFormValid()) {
            AlertNotificationHandler.showErrorMessageAlert("Invalid Entry", "Please fill in all fields and select a valid date");
        } else {
            AssetDAO.updateAssetDetails(
                    assetID,
                    category_CB.getValue().getAssetCategoryID(),
                    manufacturer_CB.getValue().getManufacturerID(),
                    assetName_TF.getText(),
                    serialNo_TF.getText(),
                    Integer.parseInt(assetPrice_TF.getText()),
                    storageSpec_CB.getValue(),
                    ramSpec_CB.getValue(),
                    osSpec_CB.getValue(),
                    DateTimeHandler.setSQLiteDateFormat(purchaseDate_DP.getValue()),
                    DateTimeHandler.setSQLiteDateFormat(estimatedEOL_DP.getValue()),
                    DateTimeHandler.setSQLiteDateFormat(warrantyStart_DP.getValue()),
                    DateTimeHandler.setSQLiteDateFormat(warrantyEnd_DP.getValue()),
                    assetCondition_CB.getValue(),
                    assetStatus_CB.getValue(),
                    photoPath_TF.getText()
            );
            AlertNotificationHandler.showInformationMessageAlert("Update Completed", "Asset information updated successfully");
            cancel_btn.getScene().getWindow().hide();
        }
    }


    @FXML

    private boolean isFormValid() {
        // check if all fields are filled
        return !assetName_TF.getText().isEmpty() &&
                !serialNo_TF.getText().isEmpty() &&
                osSpec_CB.getValue() != null && !osSpec_CB.getValue().isEmpty() &&
                ramSpec_CB.getValue() != null && !ramSpec_CB.getValue().isEmpty() &&
                storageSpec_CB.getValue() != null && !storageSpec_CB.getValue().isEmpty() &&
                !photoPath_TF.getText().isEmpty() &&
                assetCondition_CB.getValue() != null && !assetCondition_CB.getValue().isEmpty() &&
                assetStatus_CB.getValue() != null && !assetStatus_CB.getValue().isEmpty() &&
                purchaseDate_DP.getValue() != null &&
                estimatedEOL_DP.getValue() != null &&
                warrantyStart_DP.getValue() != null &&
                warrantyEnd_DP.getValue() != null;
    }



    @FXML
    private void updatePhotoPath() {
        //Todo update the photo path
        photoPath_TF.setText(AttachmentHandler.addIndividualAttachment());
    }


    @FXML
    private void refreshInformation(){
        try {
            ObservableList<AssetModel> assetDetails = AssetDAO.getSelectedAsset(assetID);

            //Asset information
            assetName_TF.setText(assetDetails.get(0).getAssetName());
            serialNo_TF.setText(assetDetails.get(0).getSerialNumber());
            assetPrice_TF.setText(String.valueOf(assetDetails.get(0).getAssetPrice()));
            osSpec_CB.setValue(assetDetails.get(0).getOsSpec());
            ramSpec_CB.setValue(assetDetails.get(0).getRamSpec());
            storageSpec_CB.setValue(assetDetails.get(0).getStorageSpec());
            photoPath_TF.setText(assetDetails.get(0).getAssetPhotoPath());

            //Asset status information
            assetCondition_CB.setValue(assetDetails.get(0).getAssetCondition());
            assetStatus_CB.setValue(assetDetails.get(0).getAssetStatus());

            //Date information
            purchaseDate_DP.setValue(LocalDate.parse(assetDetails.get(0).getPurchaseDate()));
            estimatedEOL_DP.setValue(LocalDate.parse(assetDetails.get(0).getEolDate()));
            warrantyStart_DP.setValue(LocalDate.parse(assetDetails.get(0).getWarrantyDate()));
            warrantyEnd_DP.setValue(LocalDate.parse(assetDetails.get(0).getWarrantyEndDate()));
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }




    @FXML
    private void cancelUpdate() {

        SharedButtonUtils.closeMenu(cancel_btn);
        SharedButtonUtils.closeMenu(exitWindowBtn);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<AssetCategoryModel> assetCategoryList = AssetCategoryDAO.getAllAssetCategories();
        category_CB.getItems().addAll(assetCategoryList);
        List<AssetManufacturerModel> assetManufacturerList = AssetManufacturerDAO.getAllAssetManufacturers();
        manufacturer_CB.getItems().addAll(assetManufacturerList);


        assetCondition_CB.getItems().addAll("Excellent", "Good", "Fair", "Poor");
        assetStatus_CB.getItems().addAll("Available", "In Use", "In Repair", "Retired", "Disposed");
        osSpec_CB.getItems().addAll("N/A", "Windows", "iOS", "MacOS", "Android", "Linux");
        ramSpec_CB.getItems().addAll("N/A", "8GB", "16GB", "32GB", "64GB");
        storageSpec_CB.getItems().addAll("N/A", "128GB", "256GB", "512GB", "1TB", "2TB");

        DateTimeHandler.dateValidator(purchaseDate_DP);
        DateTimeHandler.dateValidator(warrantyStart_DP);
        DateTimeHandler.warrantyEndDateValidator(warrantyStart_DP, warrantyEnd_DP);
        DateTimeHandler.endOfLifeValidator(purchaseDate_DP, estimatedEOL_DP);


        DateTimeHandler.dateTimeUpdates(dateTimeHolder);

    }

}
