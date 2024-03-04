package com.example.fyp_application.Controllers.Client.NavigationController;

import com.example.fyp_application.Controllers.Client.DashboardControllers.ClientHomePageController;
import com.example.fyp_application.Controllers.Client.ProfileManagement.EditUserProfileController;
import com.example.fyp_application.Controllers.Client.DashboardControllers.ClientDashboardController;
import com.example.fyp_application.Views.ViewHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class ClientSideBarController {

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button manageAssets_btn;

    @FXML
    private Button manageUsers_btn;

    @FXML
    private Button reports_btn;

    @FXML
    private Button editProfile_btn;


    @FXML
    private void openEditProfile () throws IOException {
        swapScene(ViewHandler.CLIENT_EDIT_PROFILE_VIEW, EditUserProfileController.class);
    }

    @FXML
    private void openDashboard() throws IOException {
        swapScene(ViewHandler.CLIENT_HOME_PAGE_VIEW, ClientHomePageController.class);
    }


    @FXML
    private void logoutUser() throws IOException {

        logout_btn.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(ViewHandler.APP_LOGIN)));
        Stage stage  = new Stage();
        Scene scene = new Scene(parent);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

    }


    private <T> void swapScene(String newContent, Class<T> controllerClass) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(newContent)));
            StackPane newLoadedPane = loader.load();
            T controller = loader.getController();
            if (controllerClass.isInstance(controller)) {
                // Assuming you want to replace all children of temporaryAP with those of newLoadedPane
                ClientDashboardController.swappableContentPane.getChildren().setAll(newLoadedPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
