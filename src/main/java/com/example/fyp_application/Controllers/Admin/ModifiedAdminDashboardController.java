package com.example.fyp_application.Controllers.Admin;

import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.AlertNotificationHandler;
import com.example.fyp_application.Utils.DateTimeHandler;
import com.example.fyp_application.Views.ViewHandler;
import com.jfoenix.controls.JFXDrawer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
public class ModifiedAdminDashboardController implements Initializable {

    @FXML
    private Button closeMenu_btn;

    @FXML
    private JFXDrawer drawerContainer;

    @FXML
    private Button exitApp_btn;

    @FXML
    private AnchorPane headerAP;

    @FXML
    private Label lastUpdateTime_lbl;

    @FXML
    private Circle loggedUserImage;

    @FXML
    private AnchorPane mainContentAnchorPane;

    @FXML
    private Button minimizeApp_btn;

    @FXML
    private Button openMenu_btn;

    @FXML
    private Button refreshHeader_btn;

    @FXML
    private Label username_lbl;

    public void refreshInformationHeader() {
        //TODO
    }

    public void closeApplication()
    {//TODO
    }

    public void closeMenuCopy() {
        //TODO
    }

    public void initializeSideMenu() {
        //TODO
    }

    public void minimizeApplication() {
        //Minimalize the application
        Stage stage = (Stage) minimizeApp_btn.getScene().getWindow();
        stage.setIconified(true);
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO
    }
}
