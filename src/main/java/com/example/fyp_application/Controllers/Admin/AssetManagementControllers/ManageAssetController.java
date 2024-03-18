package com.example.fyp_application.Controllers.Admin.AssetManagementControllers;

import com.example.fyp_application.Model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
                }
            }
            return new SimpleObjectProperty<>(imageView);
        });

        assetName_col.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        serialNo_col.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        assetCategory_col.setCellValueFactory(new PropertyValueFactory<>("assetCategoryName"));
        assetCondition_col.setCellValueFactory(new PropertyValueFactory<>("assetCondition"));
        assetStatus_col.setCellValueFactory(new PropertyValueFactory<>("assetStatus"));
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
        searchBar_TF.textProperty().addListener((observable, oldValue, newValue) -> {
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
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAssetTable();
        tableListener();
        searchAssetDetails();
    }
}
