package com.example.fyp_application.Controllers.Admin.AssetManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
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
    private DatePicker loanStart_DP;

    @FXML
    private ChoiceBox<String> loanIssueType_CB;

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
    private ChoiceBox<String> currentStatus_CB;

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
    private void startAllocationThread() {

        if (isFormValid()) {
            Task<Void> closeTask = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    // Insert the ticket details changes to the database

                    System.out.println("Running the insertion of allocation thread");
                    insertAssetAllocation();
                    System.out.println("Allocation inserted successfully");
                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    System.out.println("Updating asset status");
                    updateAssetStatus(); // proceed to insert the last message response to the message history table
                    System.out.println("Asset status updated successfully");
                    AlertNotificationUtils.showInformationMessageAlert("Success", "Asset Allocated Successfully");
                    closeMenu();
                }

                @Override
                protected void failed() {
                    super.failed();
                }
            };
        new Thread(closeTask).start();
        } else {
            AlertNotificationUtils.showErrorMessageAlert("Invalid Entry", "Please fill in all fields");
        }

    }


    private void insertAssetAllocation() {
        // Insert the asset allocation details into the database
        int allocationID = AssetAllocationDAO.insertAssetAllocation(
                assetID, // Asset ID
                userComboBox.getValue().getUserID(), // User ID
                CurrentLoggedUserHandler.getCurrentLoggedAdminID(), // Agent ID
                office_CB.getValue().getOfficeID(), // Office ID
                loanIssueType_CB.getValue(), // Loan Type
                DateTimeUtils.setYearMonthDayFormat(loanStart_DP.getValue()), // Start Date
                DateTimeUtils.setYearMonthDayFormat(loanDue_DP.getValue()), // Due Date
                currentStatus_CB.getValue(),// Allocation Status
                assetCondition_TF.getText(),
                comment_TA.getText());

        // Update the asset status to the selected status
        sendEmail(allocationID);
    }


    private void sendEmail(int allocationID){
        GMailUtils.sendEmailTo(userComboBox.getValue().getEmail(),
                "Allocation Form Receipt",
                GMailUtils.generateAssetAllocationBody(allocationID,
                        userComboBox.getValue().getFullName(),
                        assetName_TF.getText(),
                        serialNo_TF.getText(),
                        loanIssueType_CB.getValue(),
                        currentStatus_CB.getValue(),
                        comment_TA.getText(),
                        loanStart_DP.getValue().toString(),
                        loanDue_DP.getValue().toString()
                ));
    }

    private void updateAssetStatus(){
        // Update the asset status to the selected status
        AssetDAO.updateAssetStatus(assetID, currentStatus_CB.getValue());
    }



    @FXML
    private boolean isFormValid() {
        return loanIssueType_CB.getValue() != null
                && loanStart_DP.getValue() != null
                && loanDue_DP.getValue() != null
                && userComboBox.getValue() != null
                && office_CB.getValue() != null
                && building_CB.getValue() != null;
    }


    @FXML
    private void closeMenu() {
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
                firstName_TF.setText(selectedUser.getFirstName());
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
        currentStatus_CB.setValue("In Use");
        loanIssueType_CB.setItems(FXCollections.observableArrayList("Loan", "Staff Issue"));
        loanStart_DP.setValue(LocalDate.now());
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
            if (loanIssueType_CB.getValue() != null) filledCount++;
            if (loanDue_DP.getValue() != null) filledCount++;
            if (userComboBox.getValue() != null) filledCount++;
            if (office_CB.getValue() != null) filledCount++;
            if (building_CB.getValue() != null) filledCount++;
            double progress = filledCount / 5.0; // Assuming 2 combo boxes
            progressIndicator_PB.setProgress(progress);
            System.out.println(progress);

            //ensuring dynamic lock if the form is not complete

        };

        loanIssueType_CB.valueProperty().addListener(progressBarListener);
        loanDue_DP.valueProperty().addListener(progressBarListener);
        userComboBox.valueProperty().addListener(progressBarListener);
        office_CB.valueProperty().addListener(progressBarListener);
        building_CB.valueProperty().addListener(progressBarListener);

        //
        submitForm_btn.disableProperty().bind(progressIndicator_PB.progressProperty().isNotEqualTo((long) 1.0));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                setupLocationComboBox();
                setUserComboBox();
                userInformationListener();

                TextFieldListenerUtils.userSearchBarListener(searchBar_TF, userComboBox, allUsers);
                DateTimeUtils.dateValidator(loanStart_DP);
                DateTimeUtils.dateValidator(loanDue_DP);

                setupLocationListener();
                setupProgressBarListener();

                return null;
            }
        };

        new Thread(task).start();
    }
}
