package com.example.fyp_application.Controllers.Shared;

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

public class EditProfileController implements Initializable {

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

    private final int adminID = CurrentLoggedUserHandler.getCurrentLoggedAdminID();

    private UserModel userModel = null;
    private void checkUserType(){

        // Check if the user is an admin or a client
        if ( userID == 0){
            try {
                loadUserData(adminID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                loadUserData(userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    private void loadUserData(Integer loggedID) throws SQLException {
        userModel = UserDAO.loadCurrentLoggedUser(loggedID);

        if (userModel != null) {
            firstName_TF.setText(userModel.getFirstName());
            lastName_TF.setText(userModel.getLastName());
            email_TF.setText(userModel.getEmail());
            gender_TF.setText(userModel.getGender());
            userName_TF.setText(userModel.getUsername());

            createdAt_TF.setText(userModel.getCreatedAt());
            accountStatus_TF.setText(userModel.getAccountStatus());
            dept_TF.setText(userModel.getDeptName());
            phone_TF.setText(userModel.getPhone());
            dob_TF.setText(userModel.getDOB());
            usernameMainHolder_lbl.setText(userModel.getFirstName() + " " + userModel.getLastName());
            Image curPhoto = new Image(Objects.requireNonNull(getClass().getResourceAsStream(userModel.getPhoto())));
            userProfileHolder.setFill(new ImagePattern(curPhoto));
            CurrentLoggedUserHandler.setUserFullName(userModel.getFullName());
            CurrentLoggedUserHandler.setNewPhoto(userModel.getPhoto());
        } else {
            System.out.println("User not found with ID: " + loggedID);
        }
    }

    @FXML
    private void editAccountSettings() throws SQLException {

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) accountSettingsAP.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur);


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
            checkUserType();


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
            String  timeStamp = DateTimeUtils.getUKDateFormat() + " " + DateTimeUtils.getCurrentTimeFormat();
            UserDAO.updateProfilePhoto(CurrentLoggedUserHandler.getCurrentLoggedUserID(), newPath, timeStamp);
            Platform.runLater(this::checkUserType);

        }).start();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateTimeUtils.dateTimeUpdates(dateTimeHolder);
        checkUserType();
    }


}
