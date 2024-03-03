package com.example.fyp_application.Controllers.Client.DashboardControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ClientHomePageController {

    @FXML
    private AnchorPane contentAP;

    @FXML
    private AnchorPane dashboardStatAP;

    @FXML
    private Button hello_btn;



    @FXML
    private void test(){
        System.out.println("Hello World");
    }
}