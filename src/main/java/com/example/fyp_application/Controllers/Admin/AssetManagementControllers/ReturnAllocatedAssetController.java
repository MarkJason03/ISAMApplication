package com.example.fyp_application.Controllers.Admin.AssetManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.AlertNotificationUtils;
import com.example.fyp_application.Utils.DateTimeUtils;
import com.example.fyp_application.Utils.GMailUtils;
import com.example.fyp_application.Utils.SharedButtonUtils;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ReturnAllocatedAssetController implements Initializable {
    @FXML
    private ChoiceBox<String> allocationStatus_CB;

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
    private TextField phone_TF;

    @FXML
    private TextField ramSpec_TF;

    @FXML
    private Button refreshHeader_btn;

    @FXML
    private ChoiceBox<String> returnAssetCondition_CB;

    @FXML
    private ChoiceBox<String> returnAssetStatus_CB;

    @FXML
    private TextField serialNo_TF;

    @FXML
    private TextField storageSpec_TF;

    @FXML
    private Button submitForm_btn;

    @FXML
    private TextField username_TF;

    @FXML
    private Label username_lbl;

    @FXML
    private CheckBox email_checkBox;

    @FXML
    private Button sendReminder_btn;


    private boolean viewOnly ;

    @FXML
    private int assetID;

    @FXML
    private int allocationID;

    @FXML
    public void loadAllocationInformation(AssetAllocationModel assetAllocationModel, boolean viewOnly){

        this.viewOnly = viewOnly;
        allocationID = assetAllocationModel.getAllocationID();
        assetID = assetAllocationModel.getAssetID();
        System.out.println("Asset ID: "+assetID);
        System.out.println("Allocation ID: "+allocationID);
        assetName_TF.setText(assetAllocationModel.getAssetName());
        serialNo_TF.setText(assetAllocationModel.getSerialNumber());
        manufacturer_TF.setText(assetAllocationModel.getManufacturer());
        category_TF.setText(assetAllocationModel.getCategory());
        storageSpec_TF.setText(assetAllocationModel.getStorageSpec());
        ramSpec_TF.setText(assetAllocationModel.getRamSpec());
        assetCondition_TF.setText(assetAllocationModel.getAssetCondition());
        assetStatus_TF.setText(assetAllocationModel.getAssetStatus());
        firstName_TF.setText(assetAllocationModel.getFirstName());
        lastName_TF.setText(assetAllocationModel.getLastName());
        department_TF.setText(assetAllocationModel.getDepartment());
        username_TF.setText(assetAllocationModel.getUsername());
        email_TF.setText(assetAllocationModel.getEmail());
        phone_TF.setText(assetAllocationModel.getPhone());
        buildingName_TF.setText(assetAllocationModel.getBuildingName());
        officeName_TF.setText(assetAllocationModel.getOfficeName());
        loanStart_DP.setValue(LocalDate.parse(assetAllocationModel.getStartDate()));
        loanDue_DP.setValue(LocalDate.parse(assetAllocationModel.getDueDate()));
        comment_TA.setText(assetAllocationModel.getAdditionalComments());
        returnAssetCondition_CB.setValue(assetAllocationModel.getAssetCondition());
        returnAssetStatus_CB.setValue(assetAllocationModel.getAssetStatus());
        allocationStatus_CB.setValue(assetAllocationModel.getAllocationStatus());

        for (BuildingModel buildingModel : building_CB.getItems()) {
            if (buildingModel.getBuildingName().equals(assetAllocationModel.getBuildingName())) {
                building_CB.setValue(buildingModel);
            }
        }

        for (BuildingOfficesModel buildingOfficesModel : office_CB.getItems()) {
            if (buildingOfficesModel.getOfficeName().equals(assetAllocationModel.getOfficeName())) {
                office_CB.setValue(buildingOfficesModel);
            }
        }

        setViewOnly();
    }


    @FXML
    private void setViewOnly(){
        if (viewOnly || CurrentLoggedUserHandler.getCurrentLoggedAdminID() == null || allocationStatus_CB.getValue().equals("Returned")){
            loanStart_DP.setEditable(false);
            loanStart_DP.setDisable(true);
            loanDue_DP.setEditable(false);
            loanDue_DP.setDisable(true);
            loanReturn_DP.setEditable(false);
            loanReturn_DP.setDisable(true);
            comment_TA.setEditable(false);
            returnAssetCondition_CB.setDisable(true);
            returnAssetStatus_CB.setDisable(true);
            submitForm_btn.setDisable(true);
            submitForm_btn.setVisible(false);
            allocationStatus_CB.setDisable(true);
            office_CB.setDisable(true);
            building_CB.setDisable(true);
            sendReminder_btn.setDisable(true);
            sendReminder_btn.setVisible(false);
            email_checkBox.setDisable(true);
            email_checkBox.setVisible(false);


        } else {
            loanStart_DP.setEditable(true);
            loanStart_DP.setDisable(false);
            loanDue_DP.setEditable(true);
            loanDue_DP.setDisable(false);
            loanReturn_DP.setEditable(true);
            loanReturn_DP.setDisable(false);
            comment_TA.setEditable(true);
            returnAssetCondition_CB.setDisable(false);
            returnAssetStatus_CB.setDisable(false);
            submitForm_btn.setDisable(false);
            allocationStatus_CB.setDisable(false);
            office_CB.setDisable(false);
            building_CB.setDisable(false);

        }
    }

    @FXML
    private void setupComboBoxes() {
        List<BuildingModel> allBuildings = BuildingDAO.getAllBuildings();
        building_CB.getItems().addAll(allBuildings);

        building_CB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                BuildingModel selectedBuilding = building_CB.getSelectionModel().getSelectedItem();
                List<BuildingOfficesModel> allOffices = BuildingOfficesDAO.getSelectedBuildingOffices(selectedBuilding.getBuildingID());
                office_CB.getItems().clear();
                buildingName_TF.setText(selectedBuilding.getBuildingName());
                office_CB.getItems().addAll(allOffices);
                officeName_TF.setText(allOffices.get(0).getOfficeName());
            }
        });

        returnAssetCondition_CB.getItems().addAll("Excellent", "Good", "Fair", "Poor");
        returnAssetStatus_CB.getItems().addAll("Available", "In Repair", "Retired", "Disposed");
        allocationStatus_CB.getItems().addAll("In Use", "Returned");


    }



    @FXML
    private void sendEmailReminder() {
        GMailUtils.sendEmailTo(email_TF.getText(),
                "Return Asset Reminder",
                GMailUtils.generateOverdueReminderEmailBody(
                        allocationID,
                        firstName_TF.getText()+" "+lastName_TF.getText(),
                        assetName_TF.getText(),
                        serialNo_TF.getText()));

        AlertNotificationUtils.showInformationMessageAlert("Reminder Sent", "Reminder email has been sent successfully");
    }

    @FXML
    private void startReturnAllocationThread() {

        if (isFormValid()) {
            Task<Void> closeTask = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    // Insert the ticket details changes to the database

                    System.out.println("Running the insertion of allocation thread");
                    updateAllocationHistory();
                    System.out.println("Allocation inserted successfully");
                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    System.out.println("Updating asset status");
                    updateAssetStatus(); // proceed to insert the last message response to the message history table
                    System.out.println("Asset status updated successfully");
                    AlertNotificationUtils.showInformationMessageAlert("Success", "Return form has been updated successfully");
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


    private void updateAllocationHistory() {
        // Insert the asset allocation details into the database
        AssetAllocationDAO.updateAllocation(
                allocationID, // Asset ID
                DateTimeUtils.setYearMonthDayFormat(loanDue_DP.getValue()), // Due Date
                DateTimeUtils.setYearMonthDayFormat(loanReturn_DP.getValue()), // End Date
                allocationStatus_CB.getValue(), // Allocation Status
                returnAssetCondition_CB.getValue(),//Return Asset condition
                comment_TA.getText());

        // Update the asset status to the selected status

        System.out.println("Debugging email checkbox: "+email_checkBox.isSelected());
        if (!email_checkBox.isSelected()) {
            System.out.println("Sending email");
            sendEmail();
            System.out.println("Email sent successfully, closing menu");
            closeMenu();
        }
    }


    private void sendEmail(){
        GMailUtils.sendEmailTo(email_TF.getText(),
                "Return Form Receipt",
                GMailUtils.generateReturnAllocationBody(
                        firstName_TF.getText()+" "+lastName_TF.getText(),
                        assetName_TF.getText(),
                        serialNo_TF.getText(),
                        returnAssetCondition_CB.getValue(),
                        allocationStatus_CB.getValue(),
                        loanDue_DP.getValue().toString(),
                        comment_TA.getText()
                ));
    }

    private void updateAssetStatus(){
        // Update the asset status to the selected status
        AssetDAO.updateAssetStatus(assetID, returnAssetStatus_CB.getValue());
    }


    @FXML
    private boolean isFormValid() {
        return returnAssetCondition_CB.getValue() != null
                && returnAssetStatus_CB.getValue() != null
                && loanReturn_DP.getValue() != null
                && loanDue_DP.getValue() != null
                && office_CB.getValue() != null
                && building_CB.getValue() != null;
    }





    @FXML
    private void closeMenu() {
        viewOnly = false;
        SharedButtonUtils.closeMenu(closeMenu_btn);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setupComboBoxes();




    }
}
