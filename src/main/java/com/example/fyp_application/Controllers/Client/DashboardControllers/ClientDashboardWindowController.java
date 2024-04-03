package com.example.fyp_application.Controllers.Client.DashboardControllers;

import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.AlertNotificationUtils;
import com.example.fyp_application.Utils.DateTimeUtils;
import com.example.fyp_application.Views.ViewConstants;
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

public class ClientDashboardWindowController implements Initializable {

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



    public static AnchorPane swappableContentPane;



    private static Integer userID;

    @FXML
    private void initializeSideMenu(){
        try {

            VBox sideMenu = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(ViewConstants.CLIENT_SIDEBAR_MENU)));
            drawerContainer.setSidePane(sideMenu);
            openMenu_btn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> openMenu() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openMenu() {
        openMenu_btn.setVisible(false); // Hide the open menu button
        closeMenu_btn.setVisible(true); // Show the close menu button
        drawerContainer.setVisible(true); // Make the drawer visible
        drawerContainer.open(); // Open the drawer
        drawerContainer.setOverLayVisible(false); // This prevents clicking outside the drawer to close it
        drawerContainer.setResizableOnDrag(false); // This prevents resizing the drawer

        GaussianBlur blur = new GaussianBlur(5); // Set the level of the blur effect
        mainContentAnchorPane.setEffect(blur); // Set the effect on the app

        // Disable interaction with content screen
        mainContentAnchorPane.setDisable(true);

    }

    @FXML
    private void closeMenu() {
   // Make the drawer visible
        closeMenu_btn.setVisible(false); // Hide the close menu button
        openMenu_btn.setVisible(true);
        drawerContainer.setDefaultDrawerSize(230); // Example width in pixels
        drawerContainer.close(); // Close the drawer
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5)); // Adjust duration to match your close animation
        pause.setOnFinished(event -> drawerContainer.setVisible(false));
        pause.play();
        mainContentAnchorPane.setEffect(null); // App
        mainContentAnchorPane.setDisable(false); // Enable interaction with contentAP

    }


    public void loadCurrentUser() {
        userID = CurrentLoggedUserHandler.getCurrentLoggedUserID();
        String name = CurrentLoggedUserHandler.getCurrentLoggedUserFullName();
        String photoPath = CurrentLoggedUserHandler.getCurrentLoggedUserImagePath();


        username_lbl.setText(name);
        Image curPhoto = new Image(Objects.requireNonNull(getClass().getResourceAsStream(photoPath)));
        loggedUserImage.setFill(new ImagePattern(curPhoto));
        lastUpdateTime_lbl.setText("Last refreshed: " + DateTimeUtils.getCurrentTimeFormat());
    }


    @FXML
    private void closeApplication(){
        Stage stage = (Stage) exitApp_btn.getScene().getWindow();

        if (AlertNotificationUtils.showConfirmationAlert("Exit Application", "Are you sure you want to exit?")) {
            stage.close();
        }
    }

    @FXML
    private void minimizeApplication(){
        Stage stage = (Stage) minimizeApp_btn.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void refreshInformationHeader(){
        System.out.println("Refreshing Information");
        loadCurrentUser();

    }

    public void setLastLoginTime(){
        String lastLoginTime = DateTimeUtils.getYearMonthDayFormat();

        UserDAO.updateUserAccountStatusAndLastLoginTime(userID, lastLoginTime);

    }

    @FXML
    private void loadHomeScreen(){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ViewConstants.CLIENT_HOME_PAGE_VIEW));
            StackPane stackPane = fxmlLoader.load();

            swappableContentPane.getChildren().setAll(stackPane);/*
            swappableContentPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(ViewConstants.CLIENT_HOME_PAGE_VIEW)));
            mainContentAnchorPane.getChildren().setAll(swappableContentPane);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        swappableContentPane = mainContentAnchorPane;

        loadHomeScreen();
        loadCurrentUser();

        Platform.runLater(this::setLastLoginTime);
    }

}
