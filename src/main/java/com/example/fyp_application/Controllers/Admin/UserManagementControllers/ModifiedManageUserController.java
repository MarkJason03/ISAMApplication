package com.example.fyp_application.Controllers.Admin.UserManagementControllers;

import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Model.UserModel;
import com.example.fyp_application.Utils.AlertNotificationUtils;
import com.example.fyp_application.Utils.DateTimeUtils;
import com.example.fyp_application.Utils.TableListenerUtils;
import com.example.fyp_application.Views.ViewConstants;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Label deptHolder_lbl;



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
    private Label activeUsers_lbl;

    @FXML
    private Label inactiveUsers_lbl;

    @FXML
    private Label expiredUsers_lbl;

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

    private final UserDAO USER_DAO = new UserDAO(); //instance of the Data Access Object

    private ObservableList<UserModel> userListData;

    @FXML
    private void loadTableData() {

            userListData = UserDAO.getAllUsers();

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
            AlertNotificationUtils.showErrorMessageAlert("Error Loading Account Editor", "Please select a user to edit");
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
                Platform.runLater(this::setupMiniDashboard);


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
            Platform.runLater(this::setupMiniDashboard);
        }
    }


    @FXML
    private void createAccountReport(){
        try{
            TableListenerUtils.exportTableViewToExcel(userTableView);
            AlertNotificationUtils.showInformationMessageAlert("Export Successful", "User Account Report has been exported successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void searchUserDetails(){

        TableListenerUtils.searchTableDetails(searchBar_TF, userTableView, userListData, (userDetails, searchDetail) ->
                userDetails.getFirstName().toLowerCase().contains(searchDetail.toLowerCase()) ||
                        userDetails.getLastName().toLowerCase().contains(searchDetail.toLowerCase()) ||
                        userDetails.getUsername().toLowerCase().contains(searchDetail.toLowerCase()));


    }
    private void tableListener(){
        userTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                UserModel selectedUser = userTableView.getSelectionModel().getSelectedItem();


                //personal info
                fullNameHolder_lbl.setText(selectedUser.getFirstName() + " " + selectedUser.getLastName());
                deptHolder_lbl.setText("Department: " + selectedUser.getDeptName());
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


    @FXML
    private void setupMiniDashboard(){
        activeUsers_lbl.setText("Total: " + UserDAO.countActiveUsers());
        inactiveUsers_lbl.setText("Total: " + UserDAO.countInactiveUsers());
        expiredUsers_lbl.setText("Total: " + UserDAO.countExpiredUsers());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupMiniDashboard();
        loadTableData();

        searchUserDetails();
        tableListener();


        DateTimeUtils.dateTimeUpdates(dateTimeHolder);

    }
}

