package com.example.fyp_application.Controllers.Admin;

import com.example.fyp_application.Model.UserModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class AdminDashboardController implements Initializable {

    public Label loggedUserHolder_lbl;
    public AnchorPane homePageAP;
    @FXML
    private Button dashboard_btn;

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


    public void openDashboardMenu(){
        // Open the dashboard menu
        //To be implemented

        adminDashboardPane.setCenter(homePageAP);
    }

    public void openRequestMenu(){
        //To be implemented

    }
    public void openSupplierMenu() throws IOException {
        //To be implemented
        //Opens the supplier menu FXML File
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AdminSide/ManageSupplierView.fxml"));
        AnchorPane supplierMenu = loader.load(); // Load the FXML
        ManageSupplierController controller = loader.getController(); // Get the associated controller
        adminDashboardPane.setCenter(supplierMenu);
    }

    public void openAssetMenu(){
        //To be implemented

    }

    public void openUserMenu() throws IOException {

        //Opens the user menu FXML File
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AdminSide/ManageUserView.fxml"));
        AnchorPane userMenu = loader.load(); // Load the FXML
        ManageUserController controller = loader.getController(); // Get the associated controller
        adminDashboardPane.setCenter(userMenu);

    }
    /*     AnchorPane userMenu = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/AdminSide/ManageUserView.fxml")));
         adminDashboardPane.setCenter(userMenu);*/
    public void logOutApp() throws IOException {

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

    public void initialize(URL location, ResourceBundle resources) {


    }
}
