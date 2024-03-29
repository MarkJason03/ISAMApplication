package com.example.fyp_application.Controllers.Admin.AssetManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.AlertNotificationUtils;
import com.example.fyp_application.Utils.TableListenerUtils;
import com.example.fyp_application.Views.ViewConstants;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private TableColumn<?, ?> allocAssetName_col;

    @FXML
    private Label allocAssetName_lbl;

    @FXML
    private TableColumn<?, ?> allocCurrentStatus_col;

    @FXML
    private TableColumn<?, ?> allocHistoryDueDate_col;

    @FXML
    private TableColumn<?, ?> allocHistoryID_col;

    @FXML
    private TableColumn<?, ?> allocHistoryRetDate_col;

    @FXML
    private TableColumn<?, ?> allocHistoryStartDate_col;

    @FXML
    private TableView<AssetAllocationModel> allocHistoryTable;

    @FXML
    private TableColumn<?, ?> allocLoanType_col;

    @FXML
    private TableColumn<?, ?> allocOverdueInfo_col;

    @FXML
    private TextField allocSearchBar_TF;

    @FXML
    private ComboBox<String> allocationStatusFilter_CB;

    @FXML
    private TableColumn<?, ?> assetCategory_col;

    @FXML
    private TableColumn<?, ?> assetCondition_col;

    @FXML
    private Label assetFullname_lbl;

    @FXML
    private TableColumn<?, ?> assetID_Col;

    @FXML
    private TableColumn<?, ?> assetName_col;

    @FXML
    private ComboBox<String> assetStatusFilter_CB;

    @FXML
    private TableColumn<?, ?> assetStatus_col;

    @FXML
    private TableView<AssetModel> assetTable;

    @FXML
    private TextField building_TF;

    @FXML
    private AnchorPane contentAP;

    @FXML
    private Label currentStatus_lbl;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private Button editProfile_btn11;

    @FXML
    private Label lastUpdate_lbl;

    @FXML
    private Label lastUpdate_lbl1;

    @FXML
    private TextField manufacturer_TF;

    @FXML
    private Button newRequest;

    @FXML
    private TextField office_TF;

    @FXML
    private TextField opSystem_TF;

    @FXML
    private TableColumn<AssetModel, ImageView> photoDisplay_col;

    @FXML
    private TableColumn<?, ?> photoPath_col;

    @FXML
    private TextField ramSpec_TF;

    @FXML
    private Button reload_btn;

    @FXML
    private Button reload_btn1;

    @FXML
    private TextField searchBar_TF;

    @FXML
    private TableColumn<?, ?> serialNo_col;

    @FXML
    private Label serialNumber_lbl;

    @FXML
    private TextField storageSpec_TF;

    @FXML
    private Button test;

    @FXML
    private Button updateAllocation_TF;

    @FXML
    private Label userCounter_lbl;

    @FXML
    private Label userCounter_lbl1;

    @FXML
    private TextField userDept_TF;

    @FXML
    private TextField userEmail_TF;

    @FXML
    private TextField userFullname_TF;

    @FXML
    private Label userInactiveCounter_lbl;

    @FXML
    private TextField userPhone_TF;

    @FXML
    private Button viewAllocation_btn;

    @FXML
    private Button viewRequest;

    @FXML
    private TextField warrantEndDate_TF;

    @FXML
    private TextField warrantStartDate_TF;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab assetManager_tab;

    @FXML
    private Tab assetAllocation_tab;

    @FXML
    private TableColumn<?, ?> allocOverDueDays;

    private ObservableList<AssetModel> assetList;

    private ObservableList<AssetAllocationModel> allocationList;
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



    @FXML
    private void loadFilteredAssetTable(String status) {

        assetList = AssetDAO.getFilteredAsset(status);

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



    private void loadAssetAllocationTable() {
        allocationList = AssetAllocationDAO.getAssetAllocations();

        allocHistoryID_col.setCellValueFactory(new PropertyValueFactory<>("allocationID"));
        allocAssetName_col.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        allocLoanType_col.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        allocCurrentStatus_col.setCellValueFactory(new PropertyValueFactory<>("allocationStatus"));
        allocHistoryStartDate_col.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        allocHistoryDueDate_col.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        allocHistoryRetDate_col.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        allocOverdueInfo_col.setCellValueFactory(new PropertyValueFactory<>("overdueStatus"));
        allocOverDueDays.setCellValueFactory(new PropertyValueFactory<>("overdueDays"));
        allocHistoryTable.setItems(allocationList);
    }

    @FXML
    private void loadFilteredAllocationTable(String status) {

        allocationList = AssetAllocationDAO.getFilteredAllocations(status);

        allocHistoryID_col.setCellValueFactory(new PropertyValueFactory<>("allocationID"));
        allocAssetName_col.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        allocLoanType_col.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        allocCurrentStatus_col.setCellValueFactory(new PropertyValueFactory<>("allocationStatus"));
        allocHistoryStartDate_col.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        allocHistoryDueDate_col.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        allocHistoryRetDate_col.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        allocOverdueInfo_col.setCellValueFactory(new PropertyValueFactory<>("overdueStatus"));
        allocOverDueDays.setCellValueFactory(new PropertyValueFactory<>("overdueDays"));
        allocHistoryTable.setItems(allocationList);
    }
    /*@FXML
    private void loadAllocationTable(){

            ObservableList<AssetAllocationModel> allocationList = AssetAllocationDAO.getAssetAllocations();

            allocHistoryID_col.setCellValueFactory(new PropertyValueFactory<>("allocationID"));
            allocHistoryAssetID_col.setCellValueFactory(new PropertyValueFactory<>("assetID"));
            allocHistoryUserID_col.setCellValueFactory(new PropertyValueFactory<>("userID"));
            allocHistoryStartDate_col.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            allocHistoryDueDate_col.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
            allocHistoryRetDate_col.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
            allocHistoryStatus_col.setCellValueFactory(new PropertyValueFactory<>("status"));
            allocHistoryType_col.setCellValueFactory(new PropertyValueFactory<>("allocationType"));
            allocHistoryComment_col.setCellValueFactory(new PropertyValueFactory<>("comment"));

            allocHistoryTable.setItems(allocationList);
    }*/
    private void assetTableListener(){
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

    private void allocationTableListener(){
        allocHistoryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                AssetAllocationModel selectedItem = allocHistoryTable.getSelectionModel().getSelectedItem();

                //asset info
                allocAssetName_lbl.setText(selectedItem.getAssetName());
                currentStatus_lbl.setText(selectedItem.getAllocationStatus());
                building_TF.setText(selectedItem.getBuildingName());
                office_TF.setText(selectedItem.getOfficeName());
                userFullname_TF.setText(selectedItem.getFirstName() + " " + selectedItem.getLastName());
                userDept_TF.setText(selectedItem.getDepartment());
                userEmail_TF.setText(selectedItem.getEmail());
                userPhone_TF.setText(selectedItem.getPhone());
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

        TableListenerUtils.searchTableDetails(searchBar_TF, assetTable, assetList, (asset, search) ->
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
            AlertNotificationUtils.showErrorMessageAlert("Error Loading Asset Information", "Please select an  asset to load");
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
    private void recordNewAllocationRequest(){

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) assetTable.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage


        AssetModel selectedItem = assetTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            AlertNotificationUtils.showErrorMessageAlert("Error Loading Asset Information", "Please select an  asset to allocate");
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


    @FXML
    private void viewAllocationDetails(){

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) allocHistoryTable.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage


        AssetAllocationModel selectedItem = allocHistoryTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            AlertNotificationUtils.showErrorMessageAlert("Error Loading Allocation Information", "Please select an allocation history to view");
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect
        } else {
            try {
                //Load the supplier menu
                //modal pop-up dialogue box
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_VIEW_ALLOCATION_POP_UP));
                Parent root = modalViewLoader.load();

                ReturnAllocatedAssetController returnAllocatedAssetController = modalViewLoader.getController();
                returnAllocatedAssetController.loadAllocationInformation(selectedItem,true);


                // New window setup as modal
                Stage viewAllocationForm = new Stage();
                viewAllocationForm.initOwner(currentDashboardStage);
                viewAllocationForm.initModality(Modality.WINDOW_MODAL);
                viewAllocationForm.initStyle(StageStyle.TRANSPARENT);


                Scene scene = new Scene(root);
                viewAllocationForm.setScene(scene);

                viewAllocationForm.showAndWait(); // Blocks interaction with the main stage

            } catch (IOException e) {
                e.printStackTrace();
            }  finally {
                currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close
            }

        }



    }



    @FXML
    private void updateAllocationDetails(){

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) allocHistoryTable.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage


        AssetAllocationModel selectedItem = allocHistoryTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            AlertNotificationUtils.showErrorMessageAlert("Error Loading Allocation Information", "Please select an allocation history to view");
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect
        } else {
            try {
                //Load the supplier menu
                //modal pop-up dialogue box
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_VIEW_ALLOCATION_POP_UP));
                Parent root = modalViewLoader.load();

                ReturnAllocatedAssetController returnAllocatedAssetController = modalViewLoader.getController();
                returnAllocatedAssetController.loadAllocationInformation(selectedItem,false);


                // New window setup as modal
                Stage viewAllocationForm = new Stage();
                viewAllocationForm.initOwner(currentDashboardStage);
                viewAllocationForm.initModality(Modality.WINDOW_MODAL);
                viewAllocationForm.initStyle(StageStyle.TRANSPARENT);


                Scene scene = new Scene(root);
                viewAllocationForm.setScene(scene);

                viewAllocationForm.showAndWait(); // Blocks interaction with the main stage

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close


                // Reload the table
                Task<Void>initTask = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        setupAllocationFilterComboBox();
                        assetTableListener();
                        searchAssetDetails();

                        loadAssetTable();

                        loadAssetAllocationTable();
                        setupAssetFilterComboBox();
                        allocationTableListener();
                        return null;
                    }
                };
                new Thread(initTask).start();

            }

        }
    }

    @FXML
    private void setupAssetFilterComboBox(){
        assetStatusFilter_CB.getItems().addAll("All","Available", "In Use", "In Repair", "Retired", "Disposed");
        assetStatusFilter_CB.setValue("All");

        assetStatusFilter_CB.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection.equals("All")) {
                test.setDisable(true);
                loadAssetTable();
                }
            else if(newSelection.equals("In Use")) {
                test.setDisable(true);
                loadFilteredAssetTable(newSelection);
            }else {
                test.setDisable(false);
                loadFilteredAssetTable(newSelection);
            }
        });
    }

    @FXML
    private void setupAllocationFilterComboBox(){
        allocationStatusFilter_CB.getItems().addAll("All", "In Use", "Returned");
        allocationStatusFilter_CB.setValue("All");


        allocationStatusFilter_CB.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection.equals("All")) {

                loadAssetAllocationTable();
            }
            else if(newSelection.equals("In Use")) {

                loadFilteredAllocationTable(newSelection);
            }else {

                loadFilteredAllocationTable(newSelection);
            }
        });
    }

    @FXML
    private void reloadAssetTable(){
        loadFilteredAllocationTable(allocationStatusFilter_CB.getValue());
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Task<Void>initTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                setupAllocationFilterComboBox();
                assetTableListener();
                searchAssetDetails();

                loadAssetTable();

                loadAssetAllocationTable();
                        setupAssetFilterComboBox();
                allocationTableListener();
                return null;
            }
        };
        new Thread(initTask).start();



/*
        Platform.runLater(this::loadAssetAllocationTable);
        Platform.runLater(this::setupAssetFilterComboBox);
        Platform.runLater(this::allocationTableListener);*/
    }
}
