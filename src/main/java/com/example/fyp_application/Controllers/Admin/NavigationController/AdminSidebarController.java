package com.example.fyp_application.Controllers.Admin.NavigationController;

import com.example.fyp_application.Controllers.Admin.ProfileManagementController.EditAdminProfileController;
import com.example.fyp_application.Controllers.Admin.SupplierManagementControllers.ModifiedManageSupplierController;
import com.example.fyp_application.Controllers.Admin.DashboardControllers.ModifiedAdminDashboardController;
import com.example.fyp_application.Controllers.Admin.DashboardControllers.ModifiedHomePageController;
import com.example.fyp_application.Controllers.Admin.UserManagementControllers.ModifiedManageUserController;
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

public class AdminSidebarController {

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
    private void openDashboard() throws IOException {
        //TODO


        swapScene(ViewHandler.ADMIN_HOME_PAGE_VIEW, ModifiedHomePageController.class);
    }


    @FXML
    private void openReports() throws IOException {
        //TODO


    }


    @FXML
    private void openManageUsers() throws  IOException{

        //TODO
        swapScene(ViewHandler.ADMIN_MANAGE_USER_VIEW, ModifiedManageUserController.class);

    }


    @FXML
    private void openManageAssets() {
        //TODO
    }

    @FXML
    private void openManageSuppliers() throws IOException {
        //TODO

        swapScene(ViewHandler.ADMIN_MANAGE_SUPPLIER_VIEW, ModifiedManageSupplierController.class);
    }


    @FXML
    private void openManageRequests() {
        //TODO
    }




    @FXML
    private void openEditProfile() throws IOException {
        swapScene(ViewHandler.ADMIN_EDIT_MY_PROFILE_VIEW, EditAdminProfileController.class);
    }


    @FXML
    private void logoutUser() throws IOException {

        //TODO

        //Swap stage to log in screen
        logout_btn.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(ViewHandler.APP_LOGIN)));
        Stage stage  = new Stage();
        Scene scene = new Scene(parent);
        stage.initStyle(StageStyle.UNDECORATED);
        /*stage.setTitle("Login Screen");*/
        stage.setScene(scene);
        stage.show();

    }

    private <T> void swapScene(String newContent, Class<T> controllerClass) throws IOException {

        try {

            // Load the new content
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(newContent)));
            StackPane newLoadedPane = loader.load();
            // Get the controller for the new content
            T controller = loader.getController();
            // Check if the controller is the type you want
            if (controllerClass.isInstance(controller)) {
                // Replace the current content with the new lo
                ModifiedAdminDashboardController.swappableContentPane.getChildren().setAll(newLoadedPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
