package com.example.fyp_application.Controllers.Admin.SupplierManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.AlertNotificationUtils;
import com.example.fyp_application.Utils.ConfigPropertiesUtils;
import com.example.fyp_application.Utils.DateTimeUtils;
import com.example.fyp_application.Utils.TableListenerUtils;
import com.example.fyp_application.Views.ViewConstants;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
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
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifiedManageSupplierController implements Initializable {


    @FXML
    private ComboBox<String>contractFilter_CB;
    @FXML
    private TextField searchBasket_TF;
    @FXML
    private ComboBox<AssetManufacturerModel> manufacturer_CB;

    @FXML
    private Button reload_btn1;
    @FXML
    private Label lastUpdate_lbl1;
    @FXML
    private Button addCatalogueDetails_btn;
    @FXML
    private Button editCatalogueDetails_btn;
    @FXML
    private TableView<ProcurementCatalogueModel> catalogueTable;
    @FXML
    private TableColumn<ProcurementCatalogueModel,ImageView> photo_col;
    @FXML
    private TableColumn<?,?> assetName_col;
    @FXML
    private TableColumn<?,?> manufacturer_col;
    @FXML
    private TableColumn<?,?> category_col;
    @FXML
    private TableColumn<?,?> storageSpec_col;
    @FXML
    private TableColumn<?,?> ramSpec_col;
    @FXML
    private TableColumn<?,?> unitPrice_col;
    @FXML
    private Button addSupplier_btn;

    @FXML
    private AnchorPane contentAP;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private Button deleteSupplier_btn;

    @FXML
    private Button editProfile_btn1;

    @FXML
    private Button editSupplier_btn;

    @FXML
    private Label lastUpdate_lbl;

    @FXML
    private Button reload_btn;

    @FXML
    private TextField searchBar_TF;

    @FXML
    private Button search_btn;

    @FXML
    private TextField supPhone_TF;

    @FXML
    private TextArea supAddress_TA;

    @FXML
    private TextField supContractEndDate_TF;

    @FXML
    private TextField supContractStartDate_TF;

    @FXML
    private TextField supContractStatus_TF;

    @FXML
    private TextField supEmail_TF;

    @FXML
    private TextField supID_TF;

    @FXML
    private Label supNameHolder_lbl;

    @FXML
    private TableView<SupplierModel> supTableView;

    @FXML
    private TableColumn<?, ?> supTable_col_ID;

    @FXML
    private TableColumn<?, ?> supTable_col_contractEmail;

    @FXML
    private TableColumn<?, ?> supTable_col_contractEnds;

    @FXML
    private TableColumn<?, ?> supTable_col_contractStart;

    @FXML
    private TableColumn<?, ?> supTable_col_contractStatus;

    @FXML
    private TableColumn<?, ?> supTable_col_supName;


    @FXML
    private Label activeSuppliers_lbl;

    @FXML
    private Label userCounter_lbl1;

    @FXML
    private Label InactiveSuppliers_lbl;

    private final SupplierDAO supplierDAO = new SupplierDAO();//instance of the Supplier Data Access Object

    private ObservableList<ProcurementCatalogueModel> procurementList;

    private void refreshTimer(){
        String currentTime = DateTimeUtils.getCurrentTimeFormat();
        lastUpdate_lbl.setText("Last Updated : " + currentTime);
    }

    @FXML
    private ObservableList<SupplierModel> supplierListData;

    @FXML
    private void loadSupplierTableData(){
        supplierListData = SupplierDAO.getAllSuppliers();

        supTable_col_ID.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        supTable_col_supName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supTable_col_contractEmail.setCellValueFactory(new PropertyValueFactory<>("supplierEmail"));
        supTable_col_contractStatus.setCellValueFactory(new PropertyValueFactory<>("supplierContractStatus"));
        supTable_col_contractStart.setCellValueFactory(new PropertyValueFactory<>("contractStartDate"));
        supTable_col_contractEnds.setCellValueFactory(new PropertyValueFactory<>("contractEndDate"));


        supTableView.setItems(supplierListData);


    }
    
    

    @FXML
    private void loadCatalogueTable(String filter){

        // Set the items of the catalogueTable to get all the non-expired procurement items

        if (filter.contains("All")) {
            procurementList = ProcurementCatalogueDAO.getAllCatalogueList();
        } else {
            procurementList = ProcurementCatalogueDAO.getFilteredActiveProcurementCatalogue(filter);
        }

        procurementList = ProcurementCatalogueDAO.getFilteredSupplierContractCatalogue(filter);

        photo_col.setCellValueFactory(cellData -> {
            ProcurementCatalogueModel asset = cellData.getValue();
            String photoPath = asset.getAssetPicture();
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
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ConfigPropertiesUtils.getPropertyValue("DEFAULT_ASSET_PHOTO"))));
                    imageView.setImage(image);
                }
            } else {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ConfigPropertiesUtils.getPropertyValue("DEFAULT_ASSET_PHOTO"))));
                imageView.setImage(image);
            }
            return new SimpleObjectProperty<>(imageView);
        });

        assetName_col.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        manufacturer_col.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));
        category_col.setCellValueFactory(new PropertyValueFactory<>("assetCategory"));
        storageSpec_col.setCellValueFactory(new PropertyValueFactory<>("storageSpecs"));
        ramSpec_col.setCellValueFactory(new PropertyValueFactory<>("ramSpecs"));
        unitPrice_col.setCellValueFactory(new PropertyValueFactory<>("assetPrice"));

        catalogueTable.setItems(procurementList);
    }



    @FXML
    private void searchSupplierDetails(){

        TableListenerUtils.searchTableDetails(searchBar_TF, supTableView, supplierListData, (supplier, search) ->
                supplier.getSupplierName().toLowerCase().contains(search.toLowerCase()));
    }
    private void tableListener(){
        supTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                SupplierModel selectedSupplier = supTableView.getSelectionModel().getSelectedItem();


                //supplier info
                supNameHolder_lbl.setText(selectedSupplier.getSupplierName());
                supAddress_TA.setText(selectedSupplier.getSupplierAddress());
                supEmail_TF.setText(selectedSupplier.getSupplierEmail());
                supPhone_TF.setText((selectedSupplier.getSupplierContact()));

                //account info
                supID_TF.setText(String.valueOf(selectedSupplier.getSupplierID()));
                supContractStatus_TF.setText(selectedSupplier.getSupplierContractStatus());
                supContractStartDate_TF.setText(selectedSupplier.getContractStartDate());
                supContractEndDate_TF.setText(selectedSupplier.getContractEndDate());


            }
        });
    }




    @FXML
    private void addNewSupplierDetails(){
        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) supTableView.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage

        try {
            //Load the supplier menu
            //modal pop-up dialogue box
            FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_ADD_SUPPLIER_POP_UP));
            Parent root = modalViewLoader.load();

            ModifiedAddSupplierController addSupplierController = modalViewLoader.getController();



            // New window setup as modal
            Stage supplierPopUpStage = new Stage();
            supplierPopUpStage.initOwner(currentDashboardStage);
            supplierPopUpStage.initModality(Modality.WINDOW_MODAL);
            supplierPopUpStage.initStyle(StageStyle.TRANSPARENT);


            Scene scene = new Scene(root);
            supplierPopUpStage.setScene(scene);

            supplierPopUpStage.showAndWait(); // Blocks interaction with the main stage

        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close



            Platform.runLater(this::loadSupplierTableData);


            Platform.runLater(this::countActiveSuppliers);
            Platform.runLater(this::countInactiveSuppliers);

        }




    }

    @FXML
    private void editSupplierDetails(){

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) supTableView.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage


        SupplierModel selectedSupplier = supTableView.getSelectionModel().getSelectedItem();

        if (selectedSupplier == null) {
            AlertNotificationUtils.showErrorMessageAlert("Error Loading Supplier Editor", "Please select a supplier to edit");
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect
        } else {
            try {
                //Load the supplier menu
                //modal pop-up dialogue box
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_EDIT_SUPPLIER_POP_UP));
                Parent root = modalViewLoader.load();

                ModifiedEditSupplierController editSupplierController = modalViewLoader.getController();
                editSupplierController.loadSelectedSupplierDetails(selectedSupplier);


                // New window setup as modal
                Stage supplierPopUpStage = new Stage();
                supplierPopUpStage.initOwner(currentDashboardStage);
                supplierPopUpStage.initModality(Modality.WINDOW_MODAL);
                supplierPopUpStage.initStyle(StageStyle.TRANSPARENT);


                Scene scene = new Scene(root);
                supplierPopUpStage.setScene(scene);

                supplierPopUpStage.showAndWait(); // Blocks interaction with the main stage

            } catch (IOException e) {
                e.printStackTrace();
            }  finally {
                currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close



                Platform.runLater(this::loadSupplierTableData);


                Platform.runLater(this::countActiveSuppliers);
                Platform.runLater(this::countInactiveSuppliers);

            }

        }


    }

    @FXML
    private void addNewCatalogueItem() {

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) supTableView.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage

        try {
            //Load the supplier menu
            //modal pop-up dialogue box
            FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_ADD_CATALOGUE_ITEM_POP_UP));
            Parent root = modalViewLoader.load();



            // New window setup as modal
            Stage catalogueModalWindow = new Stage();
            catalogueModalWindow.initOwner(currentDashboardStage);
            catalogueModalWindow.initModality(Modality.WINDOW_MODAL);
            catalogueModalWindow.initStyle(StageStyle.TRANSPARENT);


            Scene scene = new Scene(root);
            catalogueModalWindow.setScene(scene);

            catalogueModalWindow.showAndWait(); // Blocks interaction with the main stage

        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close


        }
    }


    @FXML
    private void deleteSupplier(){

        SupplierModel selectedSupplier = supTableView.getSelectionModel().getSelectedItem();

        if(selectedSupplier == null){
            AlertNotificationUtils.showErrorMessageAlert("Error Deleting Supplier", "Please select a supplier to delete");
            return;
        }
        if(AlertNotificationUtils.showConfirmationAlert("Delete Supplier", "Are you sure you want to delete this supplier?")){
            int supplierID = selectedSupplier.getSupplierID();
            supplierDAO.deleteSupplier(supplierID);
            loadSupplierTableData();
        } else{
            AlertNotificationUtils.showInformationMessageAlert("Action Aborted", "Supplier Deletion Cancelled");
        }
    }


    @FXML
    private void editCatalogueInformation(){
        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) supTableView.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage


        SupplierModel selectedSupplier = supTableView.getSelectionModel().getSelectedItem();

        if (selectedSupplier == null) {
            AlertNotificationUtils.showErrorMessageAlert("Error Loading Supplier Editor", "Please select a supplier to edit");
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect
        } else {
            try {
                //Load the supplier menu
                //modal pop-up dialogue box
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_EDIT_SUPPLIER_POP_UP));
                Parent root = modalViewLoader.load();

                ModifiedEditSupplierController editSupplierController = modalViewLoader.getController();
                editSupplierController.loadSelectedSupplierDetails(selectedSupplier);


                // New window setup as modal
                Stage supplierPopUpStage = new Stage();
                supplierPopUpStage.initOwner(currentDashboardStage);
                supplierPopUpStage.initModality(Modality.WINDOW_MODAL);
                supplierPopUpStage.initStyle(StageStyle.TRANSPARENT);


                Scene scene = new Scene(root);
                supplierPopUpStage.setScene(scene);

                supplierPopUpStage.showAndWait(); // Blocks interaction with the main stage

            } catch (IOException e) {
                e.printStackTrace();
            }  finally {
                currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close



                Platform.runLater(this::loadSupplierTableData);


                Platform.runLater(this::countActiveSuppliers);
                Platform.runLater(this::countInactiveSuppliers);

            }

        }
    }

    @FXML
    private void loadFilteredCatalogueTable(String filter){

        procurementList = ProcurementCatalogueDAO.getFilteredActiveProcurementCatalogue(filter);

        photo_col.setCellValueFactory(cellData -> {
            ProcurementCatalogueModel asset = cellData.getValue();
            String photoPath = asset.getAssetPicture();
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
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ConfigPropertiesUtils.getPropertyValue("DEFAULT_ASSET_PHOTO"))));
                    imageView.setImage(image);
                }
            } else {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ConfigPropertiesUtils.getPropertyValue("DEFAULT_ASSET_PHOTO"))));
                imageView.setImage(image);
            }
            return new SimpleObjectProperty<>(imageView);
        });

        assetName_col.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        manufacturer_col.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));
        category_col.setCellValueFactory(new PropertyValueFactory<>("assetCategory"));
        storageSpec_col.setCellValueFactory(new PropertyValueFactory<>("storageSpecs"));
        ramSpec_col.setCellValueFactory(new PropertyValueFactory<>("ramSpecs"));
        unitPrice_col.setCellValueFactory(new PropertyValueFactory<>("assetPrice"));

        catalogueTable.setItems(procurementList);
    }


    @FXML
    private void setupManufacturerFilter(){
        // Set the items of the catalogueTable
        List<AssetManufacturerModel> assetManufacturerList = AssetManufacturerDAO.getAllAssetManufacturers();
        assetManufacturerList.add(0, new AssetManufacturerModel(0, "All"));
        manufacturer_CB.getItems().addAll(assetManufacturerList);

        manufacturer_CB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getManufacturerName().contains("All")) {
                loadCatalogueTable("All");
            } else {
                loadFilteredCatalogueTable(newValue.getManufacturerName());

            }
        });
    }
    
    
    @FXML
    private void setupContractStatusFilter(){
        List<String> contractStatusList = List.of("All","Active", "Expired");
        contractFilter_CB.getItems().addAll(contractStatusList);

        contractFilter_CB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains("All")) {
                loadCatalogueTable("All");
            } else {
                loadCatalogueTable(newValue);

            }
        });

    }

    @FXML
    private void countActiveSuppliers(){

        System.out.println("Running Active Supplier Count");
        int activeSuppliers = supplierDAO.countActiveSupplierContracts();
        activeSuppliers_lbl.setText(String.valueOf(activeSuppliers));
    }

    @FXML
    private void countInactiveSuppliers(){

        System.out.println("Running Inactive Supplier Count");
        int inactiveSuppliers = supplierDAO.countInactiveSupplierContracts();
        InactiveSuppliers_lbl.setText(String.valueOf(inactiveSuppliers));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        setupManufacturerFilter();
        setupContractStatusFilter();

        loadCatalogueTable("All");

        Platform.runLater(this::countActiveSuppliers);
        Platform.runLater(this::countInactiveSuppliers);

        DateTimeUtils.dateTimeUpdates(dateTimeHolder);

        loadSupplierTableData();
        refreshTimer();
        searchSupplierDetails();
        tableListener();
    }
}
