package com.example.fyp_application.Controllers.Client;

import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Model.UserModel;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.AlertHandler;
import com.example.fyp_application.Utils.TimeHandler;
import com.example.fyp_application.Views.ViewHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

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

    private int userID = CurrentLoggedUserHandler.getUserID();

    private final AlertHandler ALERT_HANDLER = new AlertHandler();

    @FXML
    private void setTimeUpdater(){
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> loadCurrentDateTime()));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


    @FXML
    private void loadCurrentDateTime() {
        dateTimeHolder.setText("Today " + TimeHandler.getMonthDayYearFormat() + " | " + TimeHandler.getCurrentTime());
    }



    private void loadUserData(Integer userID) throws SQLException {
/*        String sql = """
            SELECT UserID, FirstName, LastName, Email, Gender, Photo, Phone, DOB, Password, Username, CreatedAt, AccountStatus, tbl_Departments.deptName as Department
            FROM tbl_Users
            JOIN tbl_Departments ON tbl_Users.deptID = tbl_Departments.deptID
            WHERE UserID = ?;
            """;

        try (Connection connection = DatabaseConnectionHandler.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the userID in the query
            preparedStatement.setInt(1, userID);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a result is returned
            if (resultSet.next()) {
                // Assuming UserModel has a constructor that matches these fields
                UserModel userModel = new UserModel( // You need to fetch this as well if your constructor expects it
                        resultSet.getInt("UserID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Email"),
                        resultSet.getString("Gender"),
                        resultSet.getString("Photo"),
                        resultSet.getString("Phone"),
                        resultSet.getString("DOB"),
                        resultSet.getString("Password"),
                        resultSet.getString("Username"),
                        resultSet.getString("CreatedAt"),
                        resultSet.getString("AccountStatus"),
                        resultSet.getString("Department")
                );

                // Now you can use the userModel to set UI components, e.g.,
                firstName_TF.setText(userModel.getFirstName());
                // ...set other fields as needed
            } else {
                // Handle case where user is not found
                System.out.println("User not found with ID: " + userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions
        }*/

        UserDAO userDAO = new UserDAO();
        UserModel userModel = userDAO.loadLoggedInUser(userID);

        if (userModel != null) {
            firstName_TF.setText(userModel.getFirstName());
            lastName_TF.setText(userModel.getLastName());
            email_TF.setText(userModel.getEmail());
            gender_TF.setText(userModel.getGender());
            userName_TF.setText(userModel.getUsername());
            password_TF.setText(userModel.getPassword());
            createdAt_TF.setText(userModel.getCreatedAt());
            accountStatus_TF.setText(userModel.getAccountStatus());
            dept_TF.setText(userModel.getDeptName());
            phone_TF.setText(userModel.getPhone());
            dob_TF.setText(userModel.getDOB());
            Image curPhoto = new Image(Objects.requireNonNull(getClass().getResourceAsStream(userModel.getPhoto())));
            userProfileHolder.setFill(new ImagePattern(curPhoto));
        } else {
            System.out.println("User not found with ID: " + userID);
        }
    }

    @FXML
    private void editAccountSettings(){

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) accountSettingsAP.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur);

        try {
                //Load the supplier menu
                //modal pop-up dialogue box
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewHandler.CLIENT_EDIT_PROFILE_POPUP));
                Parent root = modalViewLoader.load();

                ProfilePopUpController accountSettingsController = modalViewLoader.getController();



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
            ALERT_HANDLER.showInformationMessageAlert("Cancelled Upload", "No file was selected");
        }

    }

    private void runProfileChanges(String newPath){
        new Thread(() -> {
            UserDAO userDAO = new UserDAO();
            userDAO.updateProfilePhoto(CurrentLoggedUserHandler.getUserID(), newPath);
            Platform.runLater(() -> {
                try {
                    loadUserData(CurrentLoggedUserHandler.getUserID());
                    UserModel userModel = userDAO.loadLoggedInUser(userID);
                    CurrentLoggedUserHandler.setNewPhoto(userModel.getPhoto());


                } catch (SQLException error) {
                    error.printStackTrace();
                }
            });

        }).start();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTimeUpdater();
        try {
            loadUserData(userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
