package com.example.fyp_application.Controllers.Admin.AssetManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.SharedButtonUtils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AllocateAssetController implements Initializable {

    @FXML
    private TextField assetCondition_TF;

    @FXML
    private TextField assetName_TF;

    @FXML
    private TextField assetStatus_TF;

    @FXML
    private TextField buildingName_TF;

    @FXML
    private ComboBox<BuildingModel> building_CB;

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
    private TextField phone_TF;

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
    private ChoiceBox<String> loanStatus_CB;

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
    private ComboBox<BuildingOfficesModel> office_CB;

    @FXML
    private TextField ramSpec_TF;

    @FXML
    private Button refreshHeader_btn;

    @FXML
    private ChoiceBox<String> returnAssetCondition_CB;

    @FXML
    private ChoiceBox<String> returnAssetStatus_CB;

    @FXML
    private TextField accountStatus_TF;

    @FXML
    private TextField searchBar_TF;

    @FXML
    private TextField serialNo_TF;

    @FXML
    private TextField storageSpec_TF;

    @FXML
    private ComboBox<UserModel> userComboBox;

    @FXML
    private Label username_lbl;


    @FXML
    private Button submitForm_btn;

    @FXML
    private ProgressBar progressIndicator_PB;
    @FXML
    private int assetID;



    private ObservableList<UserModel> allUsers = FXCollections.observableArrayList();

    @FXML
    private void closeMenu() {

        SharedButtonUtils.closeMenu(exitApp_btn);
        SharedButtonUtils.closeMenu(closeMenu_btn);
    }

    private void setUserComboBox(){

        allUsers = UserDAO.getAllUsers();
        userComboBox.getItems().addAll(allUsers);
        // Define how to display the user information in the ComboBox
        userComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(UserModel user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty ? "" : user.getFirstName() + " " + user.getLastName());
            }
        });

        // Define how the selected name should be displayed in the input field
        userComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(UserModel user) {
                return user == null ? "" : user.getFullName();
            }

            @Override
            public UserModel fromString(String string) {
                return userComboBox.getItems().stream()
                        .filter(item -> item.getFullName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }


    @FXML
    private void userInformationListener(){
        userComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                UserModel selectedUser = userComboBox.getSelectionModel().getSelectedItem();
                firstName_TF.setText(selectedUser.getUsername());
                email_TF.setText(selectedUser.getEmail());
                phone_TF.setText(selectedUser.getPhone());
                accountStatus_TF.setText(selectedUser.getAccountStatus());
                lastName_TF.setText(selectedUser.getLastName());
                department_TF.setText(selectedUser.getDeptName());
            }
        });
    }



    @FXML
    public void loadSelectedAssetDetails(AssetModel selectedItem) {

        this.assetID = selectedItem.getAssetID();

        //Asset information
        assetName_TF.setText(selectedItem.getAssetName());
        serialNo_TF.setText(selectedItem.getSerialNumber());
        ramSpec_TF.setText(selectedItem.getRamSpec());
        storageSpec_TF.setText(selectedItem.getStorageSpec());
        manufacturer_TF.setText(selectedItem.getManufacturerName());
        category_TF.setText(selectedItem.getAssetCategoryName());


        //Asset status information
        assetCondition_TF.setText(selectedItem.getAssetCondition());
        assetStatus_TF.setText(selectedItem.getAssetStatus());



    }


    @FXML
    private void setupLocationComboBox() {
        List<BuildingModel> allBuildings = BuildingDAO.getAllBuildings();
        building_CB.getItems().addAll(allBuildings);

        building_CB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                BuildingModel selectedBuilding = building_CB.getSelectionModel().getSelectedItem();
                List<BuildingOfficesModel> allOffices = BuildingOfficesDAO.getSelectedBuildingOffices(selectedBuilding.getBuildingID());
                office_CB.getItems().clear();
                buildingName_TF.setText(selectedBuilding.getBuildingName());
                office_CB.getItems().addAll(allOffices);
            }
        });

/*        returnAssetStatus_CB.getItems().addAll("N/A","Available","In Use", "In Repair", "Retired", "Disposed");

        loanStatus_CB.getItems().addAll("In Use", "Extended");*/


        returnAssetStatus_CB.setValue("In Use");
        loanStatus_CB.setValue("In Use");
    }


    @FXML
    private void setupLocationListener(){
        office_CB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                BuildingOfficesModel selectedOffice = office_CB.getSelectionModel().getSelectedItem();

                officeName_TF.setText(selectedOffice.getOfficeName());
            }


        });
    }

    @FXML
    private void setupProgressBarListener(){

        ChangeListener<Object> progressBarListener = (observable, oldValue, newValue) -> {
            int filledCount = 0;
            if (loanStart_DP.getValue() != null) filledCount++;
            if (loanDue_DP.getValue() != null) filledCount++;
            if (userComboBox.getValue() != null) filledCount++;
            if (office_CB.getValue() != null) filledCount++;
            if (building_CB.getValue() != null) filledCount++;
            double progress = filledCount / 5.0; // Assuming 2 combo boxes
            progressIndicator_PB.setProgress(progress);
            System.out.println(progress);
        };

        loanStart_DP.valueProperty().addListener(progressBarListener);
        loanDue_DP.valueProperty().addListener(progressBarListener);

        userComboBox.valueProperty().addListener(progressBarListener);
        office_CB.valueProperty().addListener(progressBarListener);
        building_CB.valueProperty().addListener(progressBarListener);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setupLocationComboBox();
        setUserComboBox();
        userInformationListener();

        Platform.runLater(this::setupLocationListener);
        Platform.runLater(this::setupProgressBarListener);

        double progress = progressIndicator_PB.getProgress();
        submitForm_btn.setDisable(!(progress > 0.99) && progress <= 1.0);
    }
}
