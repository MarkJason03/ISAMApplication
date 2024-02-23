package com.example.fyp_application.Controllers.Client;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class ClientDashboardController {

    public Button logout_btn;
    @FXML
    private Button menu_btn;


    @FXML
    private AnchorPane menuPanel;


    @FXML
    private VBox sideBar;


    private boolean isMenuClosed;


    public void logout() throws IOException {
        logout_btn.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/LoginPage.fxml")));
        Stage stage  = new Stage();
        Scene scene = new Scene(parent);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Login Screen");
        stage.setScene(scene);
        stage.show();

    }
}
