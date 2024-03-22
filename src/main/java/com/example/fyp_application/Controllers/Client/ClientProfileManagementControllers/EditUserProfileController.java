package com.example.fyp_application.Controllers.Client.ClientProfileManagementControllers;

import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Model.UserModel;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.AlertNotificationUtils;
import com.example.fyp_application.Utils.DateTimeUtils;
import com.example.fyp_application.Views.ViewConstants;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditUserProfileController implements Initializable {

    @FXML
    private TextField accountStatus_TF;

    @FXML
    private TextField createdAt_TF;

    @FXML
    private TextField dept_TF;

    @FXML
    private Button editProfile_btn;

    @FXML
    private Button editProfile_btn1;

    @FXML
    private TextField email_TF;

    @FXML
    private TextField firstName_TF;

    @FXML
    private TextField gender_TF;

    @FXML
    private TextField lastName_TF;

    @FXML
    private PasswordField password_TF;

    @FXML
    private TextField userName_TF;

    @FXML
    private Circle userProfileHolder;

    @FXML
    private Circle userProfilePhoto;

    @FXML
    private TextField phone_TF;

    @FXML
    private TextField dob_TF;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private AnchorPane accountSettingsAP;


    @FXML
    private Label usernameMainHolder_lbl;

    private final int userID = CurrentLoggedUserHandler.getCurrentLoggedUserID();

    private final UserDAO USER_DAO = new UserDAO(); // This is a class that handles database operations for user model





    private void loadUserData(Integer userID) throws SQLException {
        UserModel userModel = USER_DAO.loadCurrentLoggedUser(userID);

        if (userModel != null) {
            firstName_TF.setText(userModel.getFirstName());
            lastName_TF.setText(userModel.getLastName());
            email_TF.setText(userModel.getEmail());
            gender_TF.setText(userModel.getGender());
            userName_TF.setText(userModel.getUsername());
            //password_TF.setText(userModel.getPassword());
            createdAt_TF.setText(userModel.getCreatedAt());
            accountStatus_TF.setText(userModel.getAccountStatus());
            dept_TF.setText(userModel.getDeptName());
            phone_TF.setText(userModel.getPhone());
            dob_TF.setText(userModel.getDOB());
            usernameMainHolder_lbl.setText(userModel.getFirstName());
            Image curPhoto = new Image(Objects.requireNonNull(getClass().getResourceAsStream(userModel.getPhoto())));
            userProfileHolder.setFill(new ImagePattern(curPhoto));
        } else {
            System.out.println("User not found with ID: " + userID);
        }
    }

    @FXML
    private void editAccountSettings() throws SQLException {

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) accountSettingsAP.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur);

        UserModel userModel = USER_DAO.loadCurrentLoggedUser(userID);
        try {
                //Load the supplier menu
                //modal pop-up dialogue box
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.CLIENT_EDIT_PROFILE_POP_UP));
                Parent root = modalViewLoader.load();

                EditProfilePopUpController accountSettingsController = modalViewLoader.getController();
                accountSettingsController.loadUserDetails(userModel);



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
                currentDashboardStage.getScene().getRoot().setEffect(null);// Remove blur effect and reload data on close
                loadUserData(userID);
                CurrentLoggedUserHandler.setNewName(userModel.getFirstName());

        }

    }

    @FXML
    private void changeProfilePhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {

            String filePath = selectedFile.getAbsolutePath();
            String keyPath = "\\Assets"; // Use File.separator + "Assets" for more portability
            int index = filePath.indexOf(keyPath);
            String relativePath = index >= 0 ? filePath.substring(index) : filePath; // Keeps original path if keyPath not found
            String newPath = relativePath.replaceAll("\\\\", "/");// Replace backslashes with forward slashes

            runProfileChanges(newPath);

        } else {
            AlertNotificationUtils.showInformationMessageAlert("Cancelled Upload", "No file was selected");
        }

    }

    private void runProfileChanges(String newPath){
        new Thread(() -> {
            String  timeStamp = DateTimeUtils.getCurrentDateFormat() + " " + DateTimeUtils.getCurrentTimeFormat();
            UserDAO userDAO = new UserDAO();
            userDAO.updateProfilePhoto(CurrentLoggedUserHandler.getCurrentLoggedUserID(), newPath, timeStamp);
            Platform.runLater(() -> {
                try {
                    loadUserData(CurrentLoggedUserHandler.getCurrentLoggedUserID());
                    UserModel userModel = userDAO.loadCurrentLoggedUser(userID);
                    CurrentLoggedUserHandler.setNewPhoto(userModel.getPhoto());


                } catch (SQLException error) {
                    error.printStackTrace();
                }
            });

        }).start();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateTimeUtils.dateTimeUpdates(dateTimeHolder);
        try {
            loadUserData(userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
