package com.example.fyp_application.Controllers.Admin.SupplierManagementControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCatalogueItemController implements Initializable {

    @FXML
    private Button addPhoto_btn;

    @FXML
    private ChoiceBox<?> assetCategory_CB;

    @FXML
    private TextField assetName_TF;

    @FXML
    private TextField assetPrice_TF;

    @FXML
    private Button cancel_btn;

    @FXML
    private Label dateChecker_lbl;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private ChoiceBox<?> manufacturer_CB;

    @FXML
    private ChoiceBox<?> ramSpec_CB;

    @FXML
    private Button saveProfileChanges_btn;

    @FXML
    private ChoiceBox<?> storageSpec_CB;

    @FXML
    private TextField supEmail_TF;

    @FXML
    private ChoiceBox<?> supplier_CB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
