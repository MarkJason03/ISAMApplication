package com.example.fyp_application.Controllers.Admin.AssetManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.AlertNotificationHandler;
import com.example.fyp_application.Utils.TableSearchHandler;
import com.example.fyp_application.Views.ViewConstants;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ManageAssetController implements Initializable {


    @FXML
    private TextField storageSpec_TF;

    @FXML
    private TextField manufacturer_TF;

    @FXML
    private TextField warrantStartDate_TF;

    @FXML
    private TextField warrantEndDate_TF;

    @FXML
    private TextField ramSpec_TF;

    @FXML
    private TextField opSystem_TF;
    @FXML
    private TableColumn<?, ?> allocHistoryAssetID_col;

    @FXML
    private TableColumn<?, ?> allocHistoryComment_col;

    @FXML
    private TableColumn<?, ?> allocHistoryDueDate_col;

    @FXML
    private TableColumn<?, ?> allocHistoryID_col;

    @FXML
    private TableColumn<?, ?> allocHistoryRetDate_col;

    @FXML
    private TableColumn<?, ?> allocHistoryStartDate_col;

    @FXML
    private TableColumn<?, ?> allocHistoryStatus_col;

    @FXML
    private TableView<?> allocHistoryTable;

    @FXML
    private TableColumn<?, ?> allocHistoryType_col;

    @FXML
    private TableColumn<?, ?> allocHistoryUserID_col;
    @FXML
    private AnchorPane contentAP;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private Label serialNumber_lbl;

    @FXML
    private Label deptHolder_lbl1;

    @FXML
    private Button editProfile_btn1;

    @FXML
    private Button editProfile_btn11;

    @FXML
    private TextField email_TF;

    @FXML
    private TextField email_TF1;

    @FXML
    private TextField firstName_TF;

    @FXML
    private TextField firstName_TF1;

    @FXML
    private Label assetFullname_lbl;

    @FXML
    private Label fullNameHolder_lbl1;

    @FXML
    private TextField lastName_TF;

    @FXML
    private TextField lastName_TF1;

    @FXML
    private Label lastUpdate_lbl;

    @FXML
    private Label lastUpdate_lbl1;

    @FXML
    private Button newRequest;

    @FXML
    private Button newRequest1;


    @FXML
    private Button reload_btn;

    @FXML
    private Button reload_btn1;

    @FXML
    private TextField searchBar_TF;

    @FXML
    private TextField searchBar_TF1;


    @FXML
    private Label userCounter_lbl;

    @FXML
    private Label userCounter_lbl1;

    @FXML
    private Label userInactiveCounter_lbl;

    @FXML
    private TextField username_TF;

    @FXML
    private TextField username_TF1;

    @FXML
    private Button viewRequest;

    @FXML
    private Button viewRequest1;

    @FXML
    private Button test;

    @FXML
    private TableColumn<?, ?> assetCategory_col;

    @FXML
    private TableColumn<?, ?> assetCondition_col;

    @FXML
    private TableColumn<?, ?> assetID_Col;

    @FXML
    private TableColumn<?, ?> assetName_col;

    @FXML
    private TableColumn<?, ?> assetStatus_col;

    @FXML
    private TableView<AssetModel> assetTable;

    @FXML
    private TableColumn<?, ?> photoPath_col;
    @FXML
    private TableColumn<?, ?> serialNo_col;
    @FXML
    private TableColumn<AssetModel, ImageView> photoDisplay_col;

    private ObservableList<AssetModel> assetList;

    private static final String DEFAULT_PHOTO_PATH = "/CataloguePhotos/DefaultPlaceholder.PNG";
    @FXML
    private void loadAssetTable() {

        assetList = AssetDAO.getAllAssets();

        assetID_Col.setCellValueFactory(new PropertyValueFactory<>("assetID"));
        photoDisplay_col.setCellValueFactory(cellData -> {
            AssetModel asset = cellData.getValue();
            String photoPath = asset.getAssetPhotoPath();
            ImageView imageView = new ImageView();
            imageView.setFitHeight(150); // Set the desired height
            imageView.setFitWidth(150); // Set the desired width

            if (photoPath != null && !photoPath.isEmpty()) {
                try {
                    // Load the image from the photo path -Relative Folder:  CataloguePhotos
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(photoPath)));
                    imageView.setImage(image);
                } catch (NullPointerException e) {

                    System.err.println("Image not found: " + photoPath);
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_PHOTO_PATH)));
                    imageView.setImage(image);
                }
            } else {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_PHOTO_PATH)));
                imageView.setImage(image);
            }
            return new SimpleObjectProperty<>(imageView);
        });

        assetName_col.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        serialNo_col.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        assetCategory_col.setCellValueFactory(new PropertyValueFactory<>("assetCategoryName"));
        assetStatus_col.setCellValueFactory(new PropertyValueFactory<>("assetStatus"));
        assetCondition_col.setCellValueFactory(new PropertyValueFactory<>("assetCondition"));
        photoPath_col.setCellValueFactory(new PropertyValueFactory<>("assetPhotoPath"));

        assetTable.setItems(assetList);
    }


    private void tableListener(){
        assetTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                AssetModel selectedItem = assetTable.getSelectionModel().getSelectedItem();


                //asset info
                assetFullname_lbl.setText(selectedItem.getAssetName());
                serialNumber_lbl.setText("Serial Number: " + selectedItem.getSerialNumber());
                manufacturer_TF.setText(selectedItem.getManufacturerName());
                ramSpec_TF.setText(selectedItem.getRamSpec());
                storageSpec_TF.setText(selectedItem.getStorageSpec());
                opSystem_TF.setText(selectedItem.getOsSpec());
                warrantStartDate_TF.setText(selectedItem.getWarrantyDate());
                warrantEndDate_TF.setText(selectedItem.getWarrantyEndDate());
            }
        });
    }

    @FXML
    private void searchAssetDetails(){
/*        searchBar_TF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                assetTable.setItems(assetList); // Reset to show all data
                return;
            }
            ObservableList<AssetModel> filteredList = FXCollections.observableArrayList();
            for (AssetModel assetModel : assetList) {
                if(assetModel.getSerialNumber().toLowerCase().contains(newValue.toLowerCase()) || String.valueOf(assetModel.getAssetID()).contains(newValue)) {
                    filteredList.add(assetModel);
                }

            }
            assetTable.setItems(filteredList);
        });*/

        TableSearchHandler.searchTableDetails(searchBar_TF, assetTable, assetList, (asset, search) ->
                asset.getSerialNumber().toLowerCase().contains(search.toLowerCase()) ||
                        String.valueOf(asset.getAssetID()).contains(search));
    }


    @FXML
    private void viewAssetInformation(){

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) assetTable.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage


        AssetModel selectedItem = assetTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            AlertNotificationHandler.showErrorMessageAlert("Error Loading Asset Information", "Please select an  asset to load");
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect
        } else {
            try {
                //Load the supplier menu
                //modal pop-up dialogue box
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_EDIT_ASSET_POP_UP));
                Parent root = modalViewLoader.load();

                EditAssetController editAssetController = modalViewLoader.getController();
                editAssetController.loadSelectedAssetDetails(selectedItem);


                // New window setup as modal
                Stage editAssetModalWindow = new Stage();
                editAssetModalWindow.initOwner(currentDashboardStage);
                editAssetModalWindow.initModality(Modality.WINDOW_MODAL);
                editAssetModalWindow.initStyle(StageStyle.TRANSPARENT);


                Scene scene = new Scene(root);
                editAssetModalWindow.setScene(scene);

                editAssetModalWindow.showAndWait(); // Blocks interaction with the main stage

            } catch (IOException e) {
                e.printStackTrace();
            }  finally {
                currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close

                Platform.runLater(this::loadAssetTable);


                Platform.runLater(this::countDeployedAsset);
                Platform.runLater(this::countAvailableAsset);

            }

        }


    }

    private void countAvailableAsset() {
    }

    private void countDeployedAsset() {
    }

    @FXML
    private void insertNewAsset(){
        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) assetTable.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage

        try {
            //Load the supplier menu
            //modal pop-up dialogue box
            FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_ADD_ASSET_POP_UP));
            Parent root = modalViewLoader.load();

            AddAssetController addAssetController = modalViewLoader.getController();



            // New window setup as modal
            Stage addAssetModalWindow = new Stage();
            addAssetModalWindow.initOwner(currentDashboardStage);
            addAssetModalWindow.initModality(Modality.WINDOW_MODAL);
            addAssetModalWindow.initStyle(StageStyle.TRANSPARENT);


            Scene scene = new Scene(root);
            addAssetModalWindow.setScene(scene);

            addAssetModalWindow.showAndWait(); // Blocks interaction with the main stage

        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close

            Platform.runLater(this::loadAssetTable);
            Platform.runLater(this::countAvailableAsset);
            Platform.runLater(this::countAvailableAsset);

        }


    }



    @FXML
    private void insertAssetAllocation(){

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) assetTable.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage


        AssetModel selectedItem = assetTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            AlertNotificationHandler.showErrorMessageAlert("Error Loading Asset Information", "Please select an  asset to allocate");
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect
        } else {
            try {
                //Load the supplier menu
                //modal pop-up dialogue box
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_ADD_ALLOCATION_POP_UP));
                Parent root = modalViewLoader.load();

                AllocateAssetController allocateAssetController = modalViewLoader.getController();
                allocateAssetController.loadSelectedAssetDetails(selectedItem);


                // New window setup as modal
                Stage editAssetModalWindow = new Stage();
                editAssetModalWindow.initOwner(currentDashboardStage);
                editAssetModalWindow.initModality(Modality.WINDOW_MODAL);
                editAssetModalWindow.initStyle(StageStyle.TRANSPARENT);


                Scene scene = new Scene(root);
                editAssetModalWindow.setScene(scene);

                editAssetModalWindow.showAndWait(); // Blocks interaction with the main stage

            } catch (IOException e) {
                e.printStackTrace();
            }  finally {
                currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close

                Platform.runLater(this::loadAssetTable);


                Platform.runLater(this::countDeployedAsset);
                Platform.runLater(this::countAvailableAsset);

            }

        }



    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAssetTable();
        tableListener();
        searchAssetDetails();
    }
}
