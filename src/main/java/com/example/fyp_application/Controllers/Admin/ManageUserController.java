package com.example.fyp_application.Controllers.Admin;

import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Model.UserModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ManageUserController {

    @FXML
    private Button addUser_btn;

    @FXML
    private Button deleteUser_btn;

    @FXML
    private Button editUser_btn;

    @FXML
    private Label lastUpdate_lbl;

    @FXML
    private Button reload_btn;

    @FXML
    private Button search_btn;

    @FXML
    private TableView<?> userTableView;

    @FXML
    private TableColumn<?, ?> userTable_col_AccStatus;

    @FXML
    private TableColumn<?, ?> userTable_col_Dept;

    @FXML
    private TableColumn<?, ?> userTable_col_Email;

    @FXML
    private TableColumn<?, ?> userTable_col_ExpiresOn;

    @FXML
    private TableColumn<?, ?> userTable_col_Fname;

    @FXML
    private TableColumn<?, ?> userTable_col_Gender;

    @FXML
    private TableColumn<?, ?> userTable_col_LName;

    @FXML
    private TableColumn<?, ?> userTable_col_Phone;

    @FXML
    private TableColumn<?, ?> userTable_col_Role;

    @FXML
    private TableColumn<?, ?> userTable_col_Username;

    @FXML
    private TableColumn<?, ?> userTable_col_createdAt;

    @FXML
    private TableColumn<?, ?> userTable_col_userID;

    private UserDAO userDAO = new UserDAO(); //instance of the Data Access Object

    public void loadTableData(){
        ObservableList<UserModel> userList = userDAO.getAllUsers();

    }

    public void addUser() {

    }


    public void deleteUser() {
    }


    public void editUser() {
    }

    public void test(){
        System.out.println("Test");
     }


    public  void refreshTable(){

    }


    public void searchUser(){
        System.out.println("Search");
        System.out.println("Last Updated");
    }


    public void refreshTimer(){

        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter currentTimeFormat = DateTimeFormatter.ofPattern("HH:mm a");
        String formattedTime = currentTime.format(currentTimeFormat);

        lastUpdate_lbl.setText("Last Updated" + formattedTime);
    }



    public void initialize(URL location, ResourceBundle resources) {
        refreshTimer();
    }
}
