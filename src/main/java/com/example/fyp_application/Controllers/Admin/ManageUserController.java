package com.example.fyp_application.Controllers.Admin;

import com.example.fyp_application.Utils.AlertHandler;
import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Model.UserModel;
import com.example.fyp_application.Utils.TimeHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;


public class ManageUserController  implements Initializable {

    @FXML
    public Label userInactiveCounter_lbl;

    @FXML
    public TextField searchBar_TF;

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
    private Label userCounter_lbl;

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
    private final AlertHandler alertHandler = new AlertHandler();//instance of the Alert Handler Controller

    private final String dbUrl = "jdbc:sqlite:/D:\\FYP_Application\\src\\main\\resources\\db\\ISAMDB.db";


/*
    public ObservableList<UserModel> getUsers(){
        ObservableList<UserModel> arrayList = FXCollections.observableArrayList();

        String sql = "SELECT tbl_Users.UserID, FirstName,LastName,Gender,Email,Username,Phone,AccountStatus,CreatedAt,ExpiresOn, \n" +
                "       tbl_userRoles.userRoleName As Role,\n" +
                "       tbl_Departments.deptName AS departmentName\n" +
                "FROM tbl_Users\n" +
                "JOIN tbl_userRoles ON tbl_Users.userRoleID = tbl_userRoles.userRoleID\n" +
                "JOIN tbl_Departments ON tbl_Users.deptID = tbl_Departments.deptID;";


        UserModel userModel;

        try{
            Connection connect = DriverManager.getConnection(dbUrl);
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                userModel = new UserModel(
                        resultSet.getInt("UserID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Gender"),
                        resultSet.getString("Email"),
                        resultSet.getString("Username"),
                        resultSet.getString("Phone"),
                        resultSet.getString("AccountStatus"),
                        resultSet.getString("CreatedAt"),
                        resultSet.getString("ExpiresOn"),
                        resultSet.getString("Role"),
                        resultSet.getString("departmentName"));
                arrayList.add(userModel);
            };

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }*/

    private ObservableList<UserModel> userListData;

    public void loadTableData(){
        userListData = userDAO.getAllUsers();


        userListData.forEach(System.out::println);


        userTable_col_userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        userTable_col_FName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        userTable_col_LName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userTable_col_Gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        userTable_col_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        userTable_col_Username.setCellValueFactory(new PropertyValueFactory<>("username"));
        userTable_col_Phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        userTable_col_AccStatus.setCellValueFactory(new PropertyValueFactory<>("accountStatus"));
        userTable_col_Role.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        userTable_col_Dept.setCellValueFactory(new PropertyValueFactory<>("deptName"));
        userTable_col_createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        userTable_col_ExpiresOn.setCellValueFactory(new PropertyValueFactory<>("expiresAt"));

        userTableView.setItems(userListData);


    }



    @FXML
    private void addUser() {

    }


    @FXML
    private void deleteUser() {

        if (alertHandler.showConfirmationAlert("Delete User Confirmation", "Are you sure you want to delete this user?")) {
            UserModel selectedUser = userTableView.getSelectionModel().getSelectedItem();
            int userID = selectedUser.getUserID();
            userDAO.deleteUser(userID);
            loadTableData();
        }
        else{
            alertHandler.showInformationMessageAlert("Delete User", "User Deletion Cancelled");
        }
    }


    @FXML
    private void editUser() {
    }

    @FXML
    private void test(){
        System.out.println("Test");
        /*System.out.println(userListData);*/
        loadTableData();
     }


    @FXML
    private  void refreshTable(){

    }


    @FXML
    private void searchUser(){
        System.out.println("Search");
        System.out.println("Last Updated");
    }


    @FXML
    private void displayTableRefreshTime(){
        String currentTime = TimeHandler.getCurrentTime();
        lastUpdate_lbl.setText("Last Updated : " + currentTime);
    }


    @FXML
    private void displayActiveUsers() {
        int userCount = userDAO.countActiveUsers();
        userCounter_lbl.setText("Total Users : " + userCount);

    }

    @FXML
    private void displayInactiveUsers() {
        int userCount = userDAO.countInactiveUsers();
        userInactiveCounter_lbl.setText("Total Users : " + userCount);
    }


    @FXML
    private void searchBarListener(){
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
    }



    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(this::loadTableData);
        displayTableRefreshTime();
        displayActiveUsers();
        displayInactiveUsers();
        searchBarListener();

    }
}
