package com.example.fyp_application.Controllers.Admin.SupplierManagementControllers;

import com.example.fyp_application.Model.SupplierDAO;
import com.example.fyp_application.Model.SupplierModel;
import com.example.fyp_application.Utils.AlertNotificationHandler;
import com.example.fyp_application.Utils.DateTimeHandler;
import com.example.fyp_application.Views.ViewHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifiedManageSupplierController implements Initializable {

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


    private static final AlertNotificationHandler ALERT_HANDLER = new AlertNotificationHandler();//instance of the Alert Handler Controller
    private final SupplierDAO supplierDAO = new SupplierDAO();//instance of the Supplier Data Access Object


    private void refreshTimer(){
        String currentTime = DateTimeHandler.getCurrentTime();
        lastUpdate_lbl.setText("Last Updated : " + currentTime);
    }

    @FXML
    private ObservableList<SupplierModel> supplierListData;

    @FXML
    private void loadSupplierTableData(){
        supplierListData = supplierDAO.getAllSuppliers();

        supTable_col_ID.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        supTable_col_supName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supTable_col_contractEmail.setCellValueFactory(new PropertyValueFactory<>("supplierEmail"));
        supTable_col_contractStatus.setCellValueFactory(new PropertyValueFactory<>("supplierContractStatus"));
        supTable_col_contractStart.setCellValueFactory(new PropertyValueFactory<>("contractStartDate"));
        supTable_col_contractEnds.setCellValueFactory(new PropertyValueFactory<>("contractEndDate"));


        supTableView.setItems(supplierListData);


    }

    @FXML
    private void searchSupplierDetails(){
        searchBar_TF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                supTableView.setItems(supplierListData); // Reset to show all data
                return;
            }
            ObservableList<SupplierModel> filteredList = FXCollections.observableArrayList();
            for (SupplierModel supplierModel : supplierListData) {
                if (supplierModel.getSupplierName().toLowerCase().contains(newValue.toLowerCase())) {
                    filteredList.add(supplierModel);
                }
            }
            supTableView.setItems(filteredList);
        });
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
            FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewHandler.ADMIN_ADD_SUPPLIER_POP_UP));
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


            Platform.runLater(this::runContractStatusUpdate);
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
            ALERT_HANDLER.showErrorMessageAlert("Error Loading Supplier Editor", "Please select a supplier to edit");
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect
        } else {
            try {
                //Load the supplier menu
                //modal pop-up dialogue box
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewHandler.ADMIN_EDIT_SUPPLIER_POP_UP));
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


                Platform.runLater(this::runContractStatusUpdate);
                Platform.runLater(this::loadSupplierTableData);


                Platform.runLater(this::countActiveSuppliers);
                Platform.runLater(this::countInactiveSuppliers);

            }

        }


    }


    @FXML
    private void deleteSupplier(){

        SupplierModel selectedSupplier = supTableView.getSelectionModel().getSelectedItem();

        if(selectedSupplier == null){
            ALERT_HANDLER.showErrorMessageAlert("Error Deleting Supplier", "Please select a supplier to delete");
            return;
        }
        if(ALERT_HANDLER.showConfirmationAlert("Delete Supplier", "Are you sure you want to delete this supplier?")){
            int supplierID = selectedSupplier.getSupplierID();
            supplierDAO.deleteSupplier(supplierID);
            loadSupplierTableData();
        } else{
            ALERT_HANDLER.showInformationMessageAlert("Action Aborted", "Supplier Deletion Cancelled");
        }
    }



    private void runContractStatusUpdate() {

        try {
            System.out.println("Running Contract Status Update");
            supplierDAO.checkAndUpdateContractStatus();
            System.out.println("Contract Status Updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

        Platform.runLater(this::runContractStatusUpdate);
        Platform.runLater(this::loadSupplierTableData);


        Platform.runLater(this::countActiveSuppliers);
        Platform.runLater(this::countInactiveSuppliers);

        DateTimeHandler.dateTimeUpdates(dateTimeHolder);

        refreshTimer();
        searchSupplierDetails();
        tableListener();
    }
}
