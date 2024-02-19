package com.example.fyp_application.Controllers.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class AdminDashboardController {

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

    public void openUserMenu() throws IOException {
   /*     AnchorPane userMenu = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/AdminSide/ManageUserView.fxml")));
        adminDashboardPane.setCenter(userMenu);*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AdminSide/ManageUserView.fxml"));
        AnchorPane userMenu = loader.load(); // Load the FXML
        ManageUserController controller = loader.getController(); // Get the associated controller
        controller.refreshTimer(); // Explicitly call methods if needed
        adminDashboardPane.setCenter(userMenu);

    }

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

/*
        AtomicReference<Double> x = new AtomicReference<>((double) 0);
        AtomicReference<Double> y = new AtomicReference<>((double) 0);

        parent.setOnMousePressed((MouseEvent event) ->{
            x.set(event.getSceneX());
            y.set(event.getSceneY());
        });

        parent.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x.get());
            stage.setY(event.getScreenY() - y.get());
        });
*/

        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Login Screen");
        stage.setScene(scene);
        stage.show();

    }
}
