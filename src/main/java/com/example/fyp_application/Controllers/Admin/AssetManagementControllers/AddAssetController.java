package com.example.fyp_application.Controllers.Admin.AssetManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddAssetController implements Initializable {

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
    private ChoiceBox<AssetCategoryModel> category_CB;

    @FXML
    private Button createAsset_btn;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private DatePicker estimatedEOL_DP;

    @FXML
    private TextField assetPrice_TF;


    @FXML
    private Button exitWindowBtn;

    @FXML
    private ChoiceBox<AssetManufacturerModel> manufacturer_CB;

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
    private void addAsset(){
        if(isEmptyFields()){
            AlertNotificationUtils.showErrorMessageAlert("Invalid Entry", "Please fill in all fields");
        }else{
            // Add Asset
            AssetDAO.addAsset(
                    category_CB.getValue().getAssetCategoryID(),
                    manufacturer_CB.getValue().getManufacturerID(),
                    assetName_TF.getText(),
                    serialNo_TF.getText(),
                    Integer.parseInt(assetPrice_TF.getText()),
                    storageSpec_CB.getValue(),
                    ramSpec_CB.getValue(),
                    osSpec_CB.getValue(),
                    DateTimeUtils.setYearMonthDayFormat(purchaseDate_DP.getValue()),
                    DateTimeUtils.setYearMonthDayFormat(estimatedEOL_DP.getValue()),
                    DateTimeUtils.setYearMonthDayFormat(warrantyStart_DP.getValue()),
                    DateTimeUtils.setYearMonthDayFormat(warrantyEnd_DP.getValue()),
                    assetCondition_CB.getValue(),
                    assetStatus_CB.getValue(),
                    photoPath_TF.getText()
            );
            AlertNotificationUtils.showInformationMessageAlert("Success", "Asset added successfully");
            resetForm();
        }
    }


    @FXML
    private void resetForm() {
        assetName_TF.clear();
        serialNo_TF.clear();
        assetPrice_TF.clear();
        purchaseDate_DP.getEditor().clear();
        warrantyStart_DP.getEditor().clear();
        warrantyEnd_DP.getEditor().clear();
        estimatedEOL_DP.getEditor().clear();
        assetStatus_CB.setValue(null);
        assetCondition_CB.setValue(null);
        category_CB.setValue(null);
        manufacturer_CB.setValue(null);
        osSpec_CB.setValue(null);
        ramSpec_CB.setValue(null);
        storageSpec_CB.setValue(null);
        photoPath_TF.clear();
    }



    @FXML
    private boolean isEmptyFields(){
        return assetName_TF.getText().isEmpty() || serialNo_TF.getText().isEmpty() || purchaseDate_DP.getEditor().getText().isEmpty() || warrantyStart_DP.getEditor().getText().isEmpty() || warrantyEnd_DP.getEditor().getText().isEmpty() || estimatedEOL_DP.getEditor().getText().isEmpty() || assetStatus_CB.getValue() == null || assetCondition_CB.getValue() == null || category_CB.getValue() == null || manufacturer_CB.getValue() == null || osSpec_CB.getValue() == null || ramSpec_CB.getValue() == null || storageSpec_CB.getValue() == null || photoPath_TF.getText().isEmpty();
    }

    @FXML
    private void setPhotoPath(){
        photoPath_TF.setText(AttachmentUtils.addIndividualAttachment());

    }

    @FXML
    private void setSerialNumber(){
        serialNo_TF.setText(InformationGeneratorUtils.generateSerialNumber(8));
    }

    @FXML
    private void closeWindow(){
        SharedButtonUtils.closeMenu(exitWindowBtn);
        SharedButtonUtils.closeMenu(cancel_btn);
    }


    @FXML
    private void initialFormSetup(){

        assetCondition_CB.setValue("Excellent");
        assetStatus_CB.setValue("Available");

        purchaseDate_DP.setValue(LocalDate.now());

        //arbitrary eol date
        estimatedEOL_DP.setValue(LocalDate.now().plusYears(5));

        //arbitrary warranty start date
        warrantyStart_DP.setValue(LocalDate.now());

        //arbitrary warranty end date
        warrantyEnd_DP.setValue(LocalDate.now().plusYears(2));

        osSpec_CB.setValue("N/A");
        storageSpec_CB.setValue("N/A");
        ramSpec_CB.setValue("N/A");

        SearchBarListenerUtils.assetPriceTextFieldListener(assetPrice_TF);
    }


    @FXML
    private void initializeComboBoxes(){
        assetStatus_CB.getItems().addAll("Available","In Use", "In Repair", "Retired", "Disposed");
        assetCondition_CB.getItems().addAll("Excellent", "Good", "Fair", "Poor");
        osSpec_CB.getItems().addAll("N/A","Windows", "iOS", "MacOS", "Android", "Linux");
        ramSpec_CB.getItems().addAll("N/A","8GB", "16GB", "32GB", "64GB");
        storageSpec_CB.getItems().addAll("N/A","128GB", "256GB", "512GB", "1TB", "2TB");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //initialize queried combo boxes
        List<AssetCategoryModel> assetCategoryList = AssetCategoryDAO.getAllAssetCategories();
        category_CB.getItems().addAll(assetCategoryList);
        List<AssetManufacturerModel> assetManufacturerList = AssetManufacturerDAO.getAllAssetManufacturers();
        manufacturer_CB.getItems().addAll(assetManufacturerList);

        initializeComboBoxes();


        DateTimeUtils.dateValidator(purchaseDate_DP);
        DateTimeUtils.dateValidator(warrantyStart_DP);
        DateTimeUtils.warrantyEndDateValidator(warrantyStart_DP, warrantyEnd_DP);
        DateTimeUtils.endOfLifeValidator(purchaseDate_DP, estimatedEOL_DP);


        DateTimeUtils.dateTimeUpdates(dateTimeHolder);


        Platform.runLater(this::initialFormSetup);

    }

}
