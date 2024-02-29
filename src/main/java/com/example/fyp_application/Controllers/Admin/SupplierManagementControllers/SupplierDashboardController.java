package com.example.fyp_application.Controllers.Admin.SupplierManagementControllers;

import com.example.fyp_application.Utils.AlertNotificationHandler;
import com.example.fyp_application.Model.SupplierDAO;
import com.example.fyp_application.Model.SupplierModel;
import com.example.fyp_application.Utils.DateTimeHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SupplierDashboardController implements Initializable {

    @FXML
    private Button addUser_btn;

    @FXML
    private Button addUser_btn1;

    @FXML
    private Button deleteUser_btn;

    @FXML
    private Button deleteUser_btn1;

    @FXML
    private Button editUser_btn;

    @FXML
    private Button editUser_btn1;

    @FXML
    private Label lastUpdate_lbl;

    @FXML
    private Label lastUpdate_lbl1;

    @FXML
    private Button reload_btn;

    @FXML
    private Button reload_btn1;

    @FXML
    private TextField searchBar_TF;

    @FXML
    private TextField searchBar_TF1;

    @FXML
    private Button search_btn;

    @FXML
    private Button search_btn1;

    @FXML
    private TextArea supAddress_TF;

    @FXML
    private TextField supName_TF;

    @FXML
    private TextField supAddress_TF1;

    @FXML
    private TextField supContact_TF;

    @FXML
    private TextField supContact_TF1;

    @FXML
    private TextField supEmail_TF;

    @FXML
    private TextField supEmail_TF1;

    @FXML
    private TextField supID_TF;

    @FXML
    private TextField supID_TF1;


    @FXML
    private TextField supName_TF1;

    @FXML
    private TableView<SupplierModel> supTableView;

    @FXML
    private TableView<?> procurementTableView;

    @FXML
    private TableColumn<?, ?> supTable_col_ID;

    @FXML
    private TableColumn<?, ?> supTable_col_ID1;

    @FXML
    private TableColumn<?, ?> supTable_col_supAddress;

    @FXML
    private TableColumn<?, ?> supTable_col_supAddress1;

    @FXML
    private TableColumn<?, ?> supTable_col_supContact;

    @FXML
    private TableColumn<?, ?> supTable_col_supContact1;

    @FXML
    private TableColumn<?, ?> supTable_col_supEmail;

    @FXML
    private TableColumn<?, ?> supTable_col_supEmail1;

    @FXML
    private TableColumn<?, ?> supTable_col_supName;

    @FXML
    private TableColumn<?, ?> supTable_col_supName1;

    @FXML
    private Label userCounter_lbl;

    @FXML
    private Label userInactiveCounter_lbl;

    @FXML
    private TableView<?> userTableView1;

    private static final AlertNotificationHandler ALERT_HANDLER = new AlertNotificationHandler();//instance of the Alert Handler Controller
    private final SupplierDAO supplierDAO = new SupplierDAO();//instance of the Supplier Data Access Object


    @FXML
    private void refreshTimer(){
        String currentTime = DateTimeHandler.getCurrentTime();
        lastUpdate_lbl.setText("Last Updated : " + currentTime);
    }


    @FXML
    private void deleteSupplier(){


        // TODO

        if(ALERT_HANDLER.showConfirmationAlert("Delete Supplier", "Are you sure you want to delete this supplier?")){
            SupplierModel selectedSupplier = supTableView.getSelectionModel().getSelectedItem();
            int supplierID = selectedSupplier.getSupplierID();
            supplierDAO.deleteSupplier(supplierID);
            loadSupplierTableData();
        } else{
            ALERT_HANDLER.showInformationMessageAlert("Delete Supplier", "Supplier Deletion Cancelled");
        }
    }

    @FXML
    private ObservableList<SupplierModel> supplierListData;

    @FXML
    private void loadSupplierTableData(){
        supplierListData = supplierDAO.getAllSuppliers();

        supTable_col_ID.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        supTable_col_supName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supTable_col_supAddress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        supTable_col_supEmail.setCellValueFactory(new PropertyValueFactory<>("supplierEmail"));
        supTable_col_supContact.setCellValueFactory(new PropertyValueFactory<>("supplierContact"));

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

    @FXML
    private void tableListener(){
        supTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                SupplierModel selectedSupplier = supTableView.getSelectionModel().getSelectedItem();
                supID_TF.setText(String.valueOf(selectedSupplier.getSupplierID()));
                supName_TF.setText(selectedSupplier.getSupplierName());
                supAddress_TF.setText(selectedSupplier.getSupplierAddress());
                supEmail_TF.setText(selectedSupplier.getSupplierEmail());
                supContact_TF.setText(selectedSupplier.getSupplierContact());
            }
        });
    }


/*    @FXML
    private void displayUserDetails(SupplierModel supplierModel){
        searchBar_TF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                userTableView.setItems(userListData); // Reset to show all data
                return;
            }
            ObservableList<UserModel> filteredList = FXCollections.observableArrayList();
            for (UserModel userModel : userListData) {
                if (userModel.getFirstName().toLowerCase().contains(newValue.toLowerCase()) ||
                        userModel.getLastName().toLowerCase().contains(newValue.toLowerCase())) {
                    filteredList.add(userModel);
                }
            }
            userTableView.setItems(filteredList);
        });
    }*/

    //opens the supplier modal menu
    @FXML
    private void openAddSupplierWindow() {
        //blur effect
        GaussianBlur blur = new GaussianBlur(10);

        Stage currentDashboardStage = (Stage) supTableView.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage

        try {
            //Load the supplier menu
            //modal pop-up dialogue box
            FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource("/FXML/AdminView/SupplierView/AddSupplierView.fxml"));
            Parent root = modalViewLoader.load();

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
            loadSupplierTableData();
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
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource("/FXML/AdminView/SupplierView/EditSupplierView.fxml"));
                Parent root = modalViewLoader.load();

                EditSupplierController editSupplierController = modalViewLoader.getController();
                editSupplierController.loadSelectedSupplierInfo(selectedSupplier);


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
                loadSupplierTableData();
            }

        }


    }




    @FXML
    public void initialize(URL url, ResourceBundle rb){
        // TODO
        Platform.runLater(this::loadSupplierTableData);
        refreshTimer();
        searchSupplierDetails();
        tableListener();
        searchSupplierDetails();

    }

}
