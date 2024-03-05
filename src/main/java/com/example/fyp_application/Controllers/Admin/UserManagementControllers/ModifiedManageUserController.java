package com.example.fyp_application.Controllers.Admin.UserManagementControllers;

import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Model.UserModel;
import com.example.fyp_application.Utils.AlertNotificationHandler;
import com.example.fyp_application.Utils.DateTimeHandler;
import com.example.fyp_application.Views.ViewConstants;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifiedManageUserController implements Initializable {
    @FXML
    private TextField accRole_TF;

    @FXML
    private TextField accStatus_TF;

    @FXML
    private Button addUser_btn;

    @FXML
    private AnchorPane contentAP;

    @FXML
    private TextField createdOn_TF;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private Button deleteUser_btn;

    @FXML
    private Label deptHolder_lbl;

    @FXML
    private Button editProfile_btn1;

    @FXML
    private Button editUser_btn;

    @FXML
    private TextField email_TF;

    @FXML
    private TextField expiresOn_TF;

    @FXML
    private TextField firstName_TF;

    @FXML
    private Label fullNameHolder_lbl;

    @FXML
    private TextField lastLogin_TF;

    @FXML
    private TextField lastName_TF;

    @FXML
    private Label lastUpdate_lbl;

    @FXML
    private Button reload_btn;

    @FXML
    private TextField searchBar_TF;

    @FXML
    private Label userCounter_lbl;

    @FXML
    private Label userInactiveCounter_lbl;

    @FXML
    private TableView<UserModel> userTableView;


    @FXML
    private TableColumn<?, ?> userTable_col_AccStatus;

    @FXML
    private TableColumn<?, ?> userTable_col_Dept;

    @FXML
    private TableColumn<?, ?> userTable_col_Email;

    @FXML
    private TableColumn<?, ?> userTable_col_ExpiresOn;

    @FXML
    private TableColumn<?, ?> userTable_col_FName;

    @FXML
    private TableColumn<?, ?> userTable_col_LName;

    @FXML
    private TableColumn<?, ?> userTable_col_Role;

    @FXML
    private TableColumn<?, ?> userTable_col_Username;

    @FXML
    private TableColumn<?, ?> userTable_col_createdAt;

    @FXML
    private TableColumn<?, ?> userTable_col_userID;

    @FXML
    private TextField username_TF;
    private static final AlertNotificationHandler ALERT_HANDLER = new AlertNotificationHandler();//instance of the Alert Handler Controller

    private final UserDAO USER_DAO = new UserDAO(); //instance of the Data Access Object

    private ObservableList<UserModel> userListData;

    @FXML
    private void loadTableData() {

            userListData = USER_DAO.getAllUsers();

            userTable_col_userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            userTable_col_FName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            userTable_col_LName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            userTable_col_Username.setCellValueFactory(new PropertyValueFactory<>("username"));
            userTable_col_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
            userTable_col_AccStatus.setCellValueFactory(new PropertyValueFactory<>("accountStatus"));
            userTable_col_Role.setCellValueFactory(new PropertyValueFactory<>("roleName"));
            userTable_col_Dept.setCellValueFactory(new PropertyValueFactory<>("deptName"));
            userTable_col_createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
            userTable_col_ExpiresOn.setCellValueFactory(new PropertyValueFactory<>("expiresAt"));

            userTableView.setItems(userListData);

        }



    @FXML
    private void editUserDetails(){


        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) userTableView.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage


        UserModel selectedUser = userTableView.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            ALERT_HANDLER.showErrorMessageAlert("Error Loading Account Editor", "Please select a user to edit");
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect
        } else {
            try {
                //Load the supplier menu
                //modal pop-up dialogue box
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_EDIT_USER_POP_UP));
                Parent root = modalViewLoader.load();

                ModifiedEditUserController editUserController = modalViewLoader.getController();
                editUserController.loadSelectedUserDetails(selectedUser);


    /*            ModifiedEditSupplierController editSupplierController = modalViewLoader.getController();
                editSupplierController.loadSelectedSupplierDetails(selectedUser);*/


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
                Platform.runLater(this::loadTableData);


    /*            Platform.runLater(this::runContractStatusUpdate);
                Platform.runLater(this::loadSupplierTableData);


                Platform.runLater(this::countActiveSuppliers);
                Platform.runLater(this::countInactiveSuppliers);*/

            }

        }


    }

    @FXML
    public void addUser() {

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) userTableView.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage

        try {
            //Load the add user menu
            //modal pop-up dialogue box
            FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_ADD_USER_POP_UP));
            Parent root = modalViewLoader.load();

            ModifiedAddUserController addUserController = modalViewLoader.getController();

            // New window setup as modal
            Stage addUserPopUpStage = new Stage();
            addUserPopUpStage.initOwner(currentDashboardStage);
            addUserPopUpStage.initModality(Modality.WINDOW_MODAL);
            addUserPopUpStage.initStyle(StageStyle.TRANSPARENT);

            Scene scene = new Scene(root);
            addUserPopUpStage.setScene(scene);
            addUserPopUpStage.showAndWait(); // Blocks interaction with the main stage

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close
            Platform.runLater(this::loadTableData);

        }
    }

    @FXML
    private void deleteUser(){
        UserModel selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            ALERT_HANDLER.showErrorMessageAlert("Error Deleting User", "Please select a user to delete");
            return;
        }

        if(ALERT_HANDLER.showConfirmationAlert("Delete User", "Are you sure you want to delete this user?")){
            int userID = selectedUser.getUserID();
            USER_DAO.deleteUser(userID);
            loadTableData();

        } else {

             ALERT_HANDLER.showInformationMessageAlert("Action Aborted", "User has not been deleted");

            }
    }

    @FXML
    private void reloadTable(){
        Platform.runLater(this::loadTableData);
        lastUpdate_lbl.setText("Last Updated: " + DateTimeHandler.getCurrentTime());
    }



    @FXML
    private void searchUserDetails(){
        searchBar_TF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                userTableView.setItems(userListData); // Reset to show all data
                return;
            }
            ObservableList<UserModel> filteredList = FXCollections.observableArrayList();
            for (UserModel userModel : userListData) {
                if(userModel.getFirstName().toLowerCase().contains(newValue.toLowerCase())) {
                    filteredList.add(userModel);
                }

            }
            userTableView.setItems(filteredList);
        });
    }
    private void tableListener(){
        userTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                UserModel selectedUser = userTableView.getSelectionModel().getSelectedItem();


                //personal info
                fullNameHolder_lbl.setText(selectedUser.getFirstName() + " " + selectedUser.getLastName());
                deptHolder_lbl.setText(selectedUser.getDeptName());
                email_TF.setText(selectedUser.getEmail());
                username_TF.setText(selectedUser.getUsername());
                firstName_TF.setText(selectedUser.getFirstName());
                lastName_TF.setText(selectedUser.getLastName());

                //account info
                accRole_TF.setText(selectedUser.getRoleName());
                accStatus_TF.setText(selectedUser.getAccountStatus());
                createdOn_TF.setText(selectedUser.getCreatedAt());
                expiresOn_TF.setText(selectedUser.getExpiresAt());
                lastLogin_TF.setText(selectedUser.getLastLogin());
            }
        });
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::loadTableData);

        searchUserDetails();
        tableListener();

    /*            Platform.runLater(this::runContractStatusUpdate);
                Platform.runLater(this::loadSupplierTableData);


                Platform.runLater(this::countActiveSuppliers);
                Platform.runLater(this::countInactiveSuppliers);*/

        DateTimeHandler.dateTimeUpdates(dateTimeHolder);
    }



}

