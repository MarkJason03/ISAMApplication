package com.example.fyp_application.Controllers.Admin.AssetManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.DateTimeHandler;
import com.example.fyp_application.Utils.GMailHandler;
import com.example.fyp_application.Utils.SharedButtonUtils;
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
import java.sql.SQLException;
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
    private ChoiceBox<String> AssetStatus_CB;

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

/*
    @FXML
    private void insertAssetAllocation() {



        UserModel selectedUser = userComboBox.getSelectionModel().getSelectedItem();
        BuildingOfficesModel selectedOffice = office_CB.getSelectionModel().getSelectedItem();
        BuildingModel selectedBuilding = building_CB.getSelectionModel().getSelectedItem();

        AssetAllocationModel newAllocation = new AssetAllocationModel();
        newAllocation.setAssetID(assetID);
        newAllocation.setUserID(selectedUser.getUserID());
        newAllocation.setOfficeID(selectedOffice.getOfficeID());
        newAllocation.setBuildingID(selectedBuilding.getBuildingID());
        newAllocation.setLoanType(loanStatus_CB.getValue());
        newAllocation.setStartDate(loanStart_DP.getValue().toString());
        newAllocation.setDueDate(loanDue_DP.getValue().toString());
        newAllocation.setEndDate(loanReturn_DP.getValue().toString());
        newAllocation.setAllocationStatus("Active");
        newAllocation.setAssetConditionBefore(assetCondition_TF.getText());
        newAllocation.setAssetConditionAfter(returnAssetCondition_CB.getValue());
        newAllocation.setAdditionalComments(comment_TA.getText());

        AssetAllocationDAO.insertAssetAllocation(newAllocation);

    }*/

/*

    @FXML
    private void handleTicketUpdate() {

        Task<Void> updateTask = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                submitTicketDetailChanges();
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                submitResponseAsync(); // Call the next step when succeeded
            }

            @Override
            protected void failed() {
                super.failed();
            }
        };

        new Thread(updateTask).start();
    }

    private void submitResponseAsync() {
        Task<Void> responseTask = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                submitResponse(); // This method now just contains the DB operation, no FX handling
                sendUpdateEmail();
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                submitAttachmentsAsync(); // Proceed to attachments after response
            }

            @Override
            protected void failed() {
                super.failed();
            }
        };

        new Thread(responseTask).start();
    }

    private void submitAttachmentsAsync() {
        Task<Void> attachmentTask = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                submitAttachment();
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                // Close the window or do other UI stuff as needed
            }

            @Override
            protected void failed() {
                super.failed();
                // Handle failure
            }
        };

        new Thread(attachmentTask).start();
    }

    @FXML
    private void submitTicketDetailChanges(){
        TicketDAO.updateTicketDetails(ticketID,
                ticketCategory_CB.getValue().getCategoryID(),
                ticketStatus_CB.getValue(),
                ticketPriority_CB.getValue(),
                targetResolution_lbl.getText()
        );
    }

    @FXML
    private void submitResponse() throws SQLException {
        MessageHistoryDAO.recordMessage(ticketID,
                responseDetails.getText().concat( "\n\n" + ticketInfolist.get(0).getAgentFullName()),
                DateTimeHandler.getCurrentDateTime());
    }



    @FXML
    private void submitAttachment(){

        if (attachmentListView != null){
            for (String filePath : filePaths) {
                TicketAttachmentDAO.insertAttachment(ticketID,filePath,DateTimeHandler.getSQLiteDate());
            }
        }
    }


    @FXML
    private void sendUpdateEmail() {
        GMailHandler.sendEmailTo(ticketInfolist.get(0).getUserEmail(),
                "Call In Progress: SD" +  ticketID,
                GMailHandler.generateResponseEmailBody(ticketID,
                        ticketInfolist.get(0).getUserFullName(),
                        ticketInfolist.get(0).getTicketTitle(),
                        responseDetails.getText()
                )
        );
    }


    */



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


        AssetStatus_CB.setValue("In Use");
        loanStatus_CB.setItems(FXCollections.observableArrayList("Loan", "Staff Issue"));
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

/*
            //ensuring dynamic lock if the form is not complete
            submitForm_btn.setDisable(progress == 1.0);*/
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


        DateTimeHandler.dateValidator(loanStart_DP);
        DateTimeHandler.dateValidator(loanDue_DP);

        Platform.runLater(this::setupLocationListener);
        Platform.runLater(this::setupProgressBarListener);

    }
}
