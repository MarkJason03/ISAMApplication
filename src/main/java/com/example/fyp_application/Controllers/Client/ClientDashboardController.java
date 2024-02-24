package com.example.fyp_application.Controllers.Client;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class ClientDashboardController implements Initializable {

    @FXML
    private JFXDrawer drawerContainer;

    @FXML
    private Button menu_btn;

    @FXML
    private Button menu_btn1;

/*    @FXML
    private void logout() throws IOException {
        logout_btn.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/LoginPage.fxml")));
        Stage stage  = new Stage();
        Scene scene = new Scene(parent);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Login Screen");
        stage.setScene(scene);
        stage.show();

    }*/

    @FXML
    private void initializeSideMenu(){
        try {

            VBox sideMenu = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/ClientView/ClientSideBar.fxml")));
            drawerContainer.setSidePane(sideMenu);
            menu_btn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> toggleMenu() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toggleMenu(){
        if (drawerContainer.isOpened()) {
            drawerContainer.close();
        } else {
            drawerContainer.open();
        }
    }

    @FXML
    private void closeMenu(){
        if (drawerContainer.isOpened()) {
            drawerContainer.close();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
/*
        initializeSideMenu();
*/
    }
}
