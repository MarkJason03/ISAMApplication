package com.example.fyp_application.Controllers.Admin.DashboardControllers;

import com.example.fyp_application.Controllers.Admin.SupplierManagementControllers.ModifiedEditSupplierController;
import com.example.fyp_application.Model.SupplierDAO;
import com.example.fyp_application.Model.SupplierModel;
import com.example.fyp_application.Views.ViewHandler;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifiedHomePageController implements Initializable {

    @FXML
    private AnchorPane contentAP;

    @FXML
    private AnchorPane dashboardStatAP;

    @FXML
    private Button hello_btn;

    @FXML
    private FlowPane sampleFlowPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //loadFlowPaneData();

    }
}
