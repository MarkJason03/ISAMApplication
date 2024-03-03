package com.example.fyp_application.Controllers.Admin.UserManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.AlertNotificationHandler;
import com.example.fyp_application.Utils.InformationGeneratorHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;

import java.time.LocalDate;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifiedEditUserController implements Initializable{

    @FXML
    private ChoiceBox<String> accStatus_CB;

    @FXML
    private ChoiceBox<UserRoleModel> accountRole_CB;

    @FXML
    private ChoiceBox<DepartmentModel> dept_CB;

    @FXML
    private Button cancel_btn;

    @FXML
    private PasswordField confirmationPassword_TF1;

    @FXML
    private DatePicker createdDate_DP;

    @FXML
    private Label dateTimeHolder;


    @FXML
    private DatePicker dob_DP;

    @FXML
    private DatePicker expiryDate_DP;

    @FXML
    private PasswordField newPassword_TF1;

    @FXML
    private Label passwordChecker_lbl1;

    @FXML
    private Button randomPassword_btn;

    @FXML
    private Button saveProfileChanges_btn;

    @FXML
    private Button updatePassword_btn1;

    @FXML
    private TextField userEmail_TF;

    @FXML
    private TextField userFirstName_TF;

    @FXML
    private ChoiceBox<String> userGender_CB;

    @FXML
    private TextField userLastName_TF;

    @FXML
    private TextField userPhone_TF;

    @FXML
    private Circle userProfileHolder1;

    @FXML
    private Circle userProfilePhoto1;

    @FXML
    private TextField username_TF;

    UserRoleDAO USER_ROLE_DAO = new UserRoleDAO();

    DepartmentDAO DEPARTMENT_DAO = new DepartmentDAO();


    private static final AlertNotificationHandler ALERT_HANDLER = new AlertNotificationHandler();


    private int userID;




    @FXML
    private void saveProfileChanges() {
        //TODO: Implement this method

        String username = InformationGeneratorHandler.generateUsername(userFirstName_TF.getText(), userLastName_TF.getText());

        System.out.println(username);
    }

    private void updatePassword() {
        //TODO: Implement this method
    }


    @FXML
    private void cancelUpdate() {
        cancel_btn.getScene().getWindow().hide();
    }


    @FXML
    private void generateRandomPassword() {

        String randomPassword = InformationGeneratorHandler.generatePassword(12);

        System.out.println(randomPassword);
    }


    public void loadSelectedUserDetails(UserModel user) {

        this.userID = user.getUserID();
        username_TF.setText(user.getUsername());
        userFirstName_TF.setText(user.getFirstName());
        userLastName_TF.setText(user.getLastName());
        userEmail_TF.setText(user.getEmail());
        userPhone_TF.setText(user.getPhone());

        createdDate_DP.setValue(LocalDate.parse(user.getCreatedAt()));
        expiryDate_DP.setValue(LocalDate.parse(user.getExpiresAt()));
        dob_DP.setValue(LocalDate.parse(user.getDOB()));

        Image currentImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(user.getPhoto())));
        userProfileHolder1.setFill(new ImagePattern(currentImage));

        userGender_CB.setValue(user.getGender());
        accStatus_CB.setValue(user.getAccountStatus());

        for (UserRoleModel role : accountRole_CB.getItems()) {
            if (role.getUserRoleID() == user.getUserRoleID()) {
                accountRole_CB.setValue(role);
                break;
            }}

        for (DepartmentModel dept : dept_CB.getItems()) {
            if (dept.getDeptID() == user.getDeptID()) {
                dept_CB.setValue(dept);
                break;
            }
        }



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<DepartmentModel> departments = DEPARTMENT_DAO.getAllDepartments();
        dept_CB.getItems().addAll(departments);

        List<UserRoleModel> roles = USER_ROLE_DAO.getAllRoles();
        accountRole_CB.getItems().addAll(roles);

        userGender_CB.setItems(FXCollections.observableArrayList("Male","Female"));
        accStatus_CB.setItems(FXCollections.observableArrayList("Active","Inactive"));








    }
}
