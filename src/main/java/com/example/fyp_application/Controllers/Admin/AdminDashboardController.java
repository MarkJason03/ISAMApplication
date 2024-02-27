package com.example.fyp_application.Controllers.Admin;

import com.example.fyp_application.Controllers.Admin.SupplierControllers.SupplierDashboardController;
import com.example.fyp_application.Utils.AlertHandler;
import com.example.fyp_application.Utils.TimeHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML
    private Label loggedUserHolder_lbl;

    @FXML
    private AnchorPane homePageAP;

    @FXML
    private Button minimize_btn;

    @FXML
    private Button exit_btn;

    @FXML
    private Button maximize_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Label dateTime_lbl;

    @FXML
    private Button editCurUserProfile_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button manageAssets_btn;

    @FXML
    private Button manageRequests_btn;

    @FXML
    private Button manageSuppliers_btn;

    @FXML
    private Button manageUsers_btn;

    @FXML
    private Button reports_btn;

    @FXML
    private BorderPane adminDashboardPane;


    private static final AlertHandler ALERT_HANDLER = new AlertHandler();


    @FXML
    private void openDashboardMenu(){
        // Open the dashboard menu
        //To be implemented

        adminDashboardPane.setCenter(homePageAP);
    }

    public void openRequestMenu(){
        //To be implemented

    }
    @FXML
    private void openSupplierMenu() throws IOException {
        //To be implemented
        //Opens the supplier menu FXML File
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AdminView/SupplierView/ManageSupplierView.fxml"));
        AnchorPane supplierMenu = loader.load(); // Load the FXML
        SupplierDashboardController controller = loader.getController(); // Get the associated controller
        adminDashboardPane.setCenter(supplierMenu);
    }

    @FXML
    private void openAssetMenu(){
        //To be implemented

    }

    @FXML
    private void openUserMenu() throws IOException {

        //Opens the user menu FXML File
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AdminView/ManageUserView.fxml"));
        AnchorPane userMenu = loader.load(); // Load the FXML
        ManageUserController controller = loader.getController(); // Get the associated controller
        adminDashboardPane.setCenter(userMenu);

    }
    /*     AnchorPane userMenu = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/AdminSide/ManageUserView.fxml")));
         adminDashboardPane.setCenter(userMenu);*/
    @FXML
    private void logOutApp() throws IOException {

/*
        Stage dashboardMenuStage = (Stage) logout_btn.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/LoginPage.fxml")));
        Scene scene = new Scene(newRoot);
        dashboardMenuStage.initStyle(StageStyle.DECORATED);
        dashboardMenuStage.setScene(scene);
        dashboardMenuStage.show();
*/

        logout_btn.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/LoginPage.fxml")));
        Stage stage  = new Stage();
        Scene scene = new Scene(parent);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Login Screen");
        stage.setScene(scene);
        stage.show();

    }

//
//    private UserModel userModel;
//
//    public void setUserModel(UserModel userModel) {
//        this.userModel = userModel;
//        loggedUserHolder_lbl.setText(userModel.getFirstName());
//    }

    @FXML
    private void getCurrentTime(){
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTime()));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    private void updateTime (){

        dateTime_lbl.setText(TimeHandler.getCurrentTime() + " " + TimeHandler.getCurrentDate());

/*        LocalTime currentTime = LocalTime.now();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm a");
        String formattedDate = currentDate.format(dateFormatter);
        String formattedTime = currentTime.format(formatter);

        dateTime_lbl.setText(formattedDate+ " " + formattedTime);*/
    }


    @FXML
    private void minimizeWindow(){
        Stage stage = (Stage) minimize_btn.getScene().getWindow();
        stage.setIconified(true);
    }


    @FXML
    private void closeWindow(){
        Stage stage = (Stage) exit_btn.getScene().getWindow();

        if(ALERT_HANDLER.showConfirmationAlert("Exit Application", "Are you sure you want to exit the application?")){
            stage.close();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {

        getCurrentTime();

    }
}
