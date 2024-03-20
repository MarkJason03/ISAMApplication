package com.example.fyp_application.Controllers.Admin.AssetManagementControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class ReturnAllocatedAssetController {

    @FXML
    private TextField accountStatus_TF;

    @FXML
    private TextField assetCondition_TF;

    @FXML
    private TextField assetName_TF;

    @FXML
    private TextField assetStatus_TF;

    @FXML
    private TextField buildingName_TF;

    @FXML
    private ComboBox<?> building_CB;

    @FXML
    private TextField category_TF;

    @FXML
    private Button closeMenu_btn;

    @FXML
    private TextArea comment_TA;

    @FXML
    private TextField department_TF;

    @FXML
    private TextField email_TF;

    @FXML
    private Button exitApp_btn;

    @FXML
    private TextField firstName_TF;

    @FXML
    private AnchorPane headerAP;

    @FXML
    private TextField lastName_TF;

    @FXML
    private Label lastUpdateTime_lbl;

    @FXML
    private DatePicker loanDue_DP;

    @FXML
    private DatePicker loanReturn_DP;

    @FXML
    private DatePicker loanStart_DP;

    @FXML
    private ChoiceBox<?> loanStatus_CB;

    @FXML
    private Circle loggedUserImage;

    @FXML
    private AnchorPane mainAP;

    @FXML
    private AnchorPane mainContentAnchorPane;

    @FXML
    private TextField manufacturer_TF;

    @FXML
    private Button minimizeApp_btn;

    @FXML
    private TextField officeName_TF;

    @FXML
    private ComboBox<?> office_CB;

    @FXML
    private TextField phone_TF;

    @FXML
    private ProgressBar progressIndicator_PB;

    @FXML
    private TextField ramSpec_TF;

    @FXML
    private Button refreshHeader_btn;

    @FXML
    private ChoiceBox<?> returnAssetCondition_CB;

    @FXML
    private ChoiceBox<?> returnAssetStatus_CB;

    @FXML
    private TextField searchBar_TF;

    @FXML
    private TextField serialNo_TF;

    @FXML
    private TextField storageSpec_TF;

    @FXML
    private Button submitForm_btn;

    @FXML
    private ComboBox<?> userComboBox;

    @FXML
    private Label username_lbl;

    @FXML
    void closeMenu(ActionEvent event) {

    }

}
