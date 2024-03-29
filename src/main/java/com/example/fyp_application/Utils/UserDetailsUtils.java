package com.example.fyp_application.Utils;

import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Model.UserModel;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.StringConverter;

import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;
public class UserDetailsUtils {

    private static final UserDAO USER_DAO = new UserDAO();


    public static void setupUserComboBox(ComboBox<UserModel> userComboBox){
        ObservableList<UserModel> allUsers;
        allUsers = UserDAO.getAllUsers();
        userComboBox.getItems().addAll(allUsers);
        // Define how to display the user information in the ComboBox
        userComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(UserModel user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty ? "" : user.getFirstName() + " " + user.getLastName());
            }
        });

        // Define how the selected name should be displayed in the input field
        userComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(UserModel user) {
                return user == null ? "" : user.getFullName();
            }

            @Override
            public UserModel fromString(String string) {
                return userComboBox.getItems().stream()
                        .filter(item -> item.getFullName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    public static void setAgentComboBox(ComboBox<UserModel>agentComboBox){
        ObservableList<UserModel> allAgents;
        allAgents = UserDAO.getAllAgents();
        agentComboBox.getItems().addAll(allAgents);
        // Define how to display the user information in the ComboBox
        agentComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(UserModel user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty ? "" : user.getFirstName() + " " + user.getLastName());
            }
        });

        // Define how the selected name should be displayed in the input field
        agentComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(UserModel user) {
                return user == null ? "" : user.getFullName();
            }

            @Override
            public UserModel fromString(String string) {
                return agentComboBox.getItems().stream()
                        .filter(item -> item.getFullName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }
    public static void setUserDetails(ComboBox<UserModel> userComboBox, TextField firstName_TF, TextField lastName_TF, TextField email_TF, TextField phone_TF, TextField username_TF, TextField dept_TF) {
        userComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                UserModel selectedUser = userComboBox.getSelectionModel().getSelectedItem();
                username_TF.setText(selectedUser.getUsername());
                email_TF.setText(selectedUser.getEmail());
                phone_TF.setText(selectedUser.getPhone());
                firstName_TF.setText(selectedUser.getFirstName());
                lastName_TF.setText(selectedUser.getLastName());
                dept_TF.setText(selectedUser.getDeptName());
            }
        });
    }
    public static void setupIDCard(UserModel userModel, Label fullName_lbl, Label deparment_lbl, Label expiresDate_lbl, Label email_lbl, Label phone_lbl, Circle imageHolder_shape) throws SQLException {
        // If the user is not an admin
        if( CurrentLoggedUserHandler.getCurrentLoggedUserID() != null){
            fullName_lbl.setText(CurrentLoggedUserHandler.getCurrentLoggedUserFullName());
            deparment_lbl.setText("Department: " + userModel.getDeptName());
            expiresDate_lbl.setText("Expires: " + userModel.getExpiresAt());
            email_lbl.setText(userModel.getEmail());
            phone_lbl.setText(userModel.getPhone());
            Image curPhoto = new Image(Objects.requireNonNull(UserDetailsUtils.class.getResourceAsStream(CurrentLoggedUserHandler.getCurrentLoggedUserImagePath())));
            imageHolder_shape.setFill(new ImagePattern(curPhoto));
        } else {
            // If the user is an admin
            fullName_lbl.setText(CurrentLoggedUserHandler.getCurrentLoggedAdminFullName());
            deparment_lbl.setText("Department: " + userModel.getDeptName());
            expiresDate_lbl.setText("Expires: " + userModel.getExpiresAt());
            email_lbl.setText(userModel.getEmail());
            phone_lbl.setText(userModel.getPhone());
            Image curPhoto = new Image(Objects.requireNonNull(UserDetailsUtils.class.getResourceAsStream(CurrentLoggedUserHandler.getCurrentLoggedAdminImagePath())));
            imageHolder_shape.setFill(new ImagePattern(curPhoto));
        }
    }
}
