package com.example.fyp_application.Controllers.Admin.NavigationController;

import com.example.fyp_application.Controllers.Admin.AssetManagementControllers.ManageAssetController;
import com.example.fyp_application.Controllers.Admin.DashboardControllers.OverviewStatisticsController;
import com.example.fyp_application.Controllers.Admin.ProfileManagementController.EditAdminProfileController;
import com.example.fyp_application.Controllers.Admin.RequestManagementControllers.ManageRequestController;
import com.example.fyp_application.Controllers.Admin.SupplierManagementControllers.ModifiedManageSupplierController;
import com.example.fyp_application.Controllers.Admin.DashboardControllers.AdminDashboardWindowController;
import com.example.fyp_application.Controllers.Admin.DashboardControllers.AdminHomePageController;
import com.example.fyp_application.Controllers.Admin.UserManagementControllers.ModifiedManageUserController;
import com.example.fyp_application.Controllers.Shared.SharedProfileController;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Views.ViewConstants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminSidebarController implements Initializable {

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
        //Opens the dashboard page / home page


        swapScene(ViewConstants.ADMIN_HOME_PAGE_VIEW, AdminHomePageController.class);
    }


    @FXML
    private void openStatistics() throws IOException {
        // Opens the statistics page

        swapScene(ViewConstants.ADMIN_VIEW_STATISTICS, OverviewStatisticsController.class);


    }


    @FXML
    private void openManageUsers() throws  IOException{

        //Opens the manage users page
        swapScene(ViewConstants.ADMIN_MANAGE_USER_VIEW, ModifiedManageUserController.class);

    }


    @FXML
    private void openManageAssets() throws IOException {
        //TODO - write the manage assets page - perhaps a list of assets and their details
        swapScene(ViewConstants.ADMIN_MANAGE_ASSET_VIEW, ManageAssetController.class);
    }

    @FXML
    private void openManageSuppliers() throws IOException {
        //Opens the manage suppliers page

        swapScene(ViewConstants.ADMIN_MANAGE_SUPPLIER_VIEW, ModifiedManageSupplierController.class);
    }


    @FXML
    private void openManageRequests() throws IOException {
        //TODO - write the manage requests page - perhaps a list of requests and their details

        swapScene(ViewConstants.ADMIN_MANAGE_REQUEST_VIEW, ManageRequestController.class);
    }




    @FXML
    private void openEditProfile() throws IOException {
        swapScene(ViewConstants.SHARED_EDIT_PROFILE_VIEW, SharedProfileController.class);
        //swapScene(ViewConstants.ADMIN_EDIT_MY_PROFILE_VIEW, EditAdminProfileController.class);
    }


    @FXML
    private void logoutUser() throws IOException {

        //Swap stage to log in screen
        System.out.println("Wiping admin id");
        System.out.println("Current admin id: " + CurrentLoggedUserHandler.getCurrentLoggedAdminID());
        CurrentLoggedUserHandler.setCurrentAdmin(null, null, null, null);
        System.out.println("wiped admin id" + CurrentLoggedUserHandler.getCurrentLoggedAdminID());
        logout_btn.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(ViewConstants.APP_LOGIN)));
        Stage stage  = new Stage();
        Scene scene = new Scene(parent);
        stage.initStyle(StageStyle.UNDECORATED);
        /*stage.setTitle("Login Screen");*/
        stage.setScene(scene);
        stage.show();

    }

    private <T> void swapScene(String newContent, Class<T> controllerClass) throws IOException {

        // swap the scene to the new content
        try {

            // Load the new content
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(newContent)));
            StackPane newLoadedPane = loader.load();
            // Get the controller for the new content
            T controller = loader.getController();
            // Check if the controller is the type you want
            if (controllerClass.isInstance(controller)) {
                // Replace the current content with the new lo
                AdminDashboardWindowController.swappableContentPane.getChildren().setAll(newLoadedPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        reports_btn.setVisible(CurrentLoggedUserHandler.getCurrentLoggedAdminID() == 1);
    }
}
