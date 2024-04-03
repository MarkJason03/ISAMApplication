package com.example.fyp_application.Controllers.Admin.ProcurementManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditProcurementRequestController implements Initializable {

    @FXML
    private HBox actionButton_Hbox;

    @FXML
    private TextArea approverComment_TA;

    @FXML
    private TableColumn<?, ?> assetName_col;

    @FXML
    private TableView<ProcurementBasketModel> catalogueTable;

    @FXML
    private Button closeMenu_btn;

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
    private Circle loggedUserImage;

    @FXML
    private AnchorPane mainContentAnchorPane;

    @FXML
    private TableColumn<?, ?> qty_col;

    @FXML
    private TableColumn<?, ?> totalPrice_col;

    @FXML
    private TextField phone_TF;

    @FXML
    private TableColumn<?, ?> catalogueID_col;

    @FXML
    private HBox procurementManager_Hbox;


    @FXML
    private Button refreshHeader_btn;

    @FXML
    private TextArea requestorCommment_TA;

    @FXML
    private Button saveProcurementRequest_btn;

    @FXML
    private Button saveProcurementRequest_btn1;

    @FXML
    private TextField totalCalculatedCost_TF;

    @FXML
    private TableColumn<?, ?> unitPrice_col;

    @FXML
    private TextField username_TF;

    @FXML
    private Label username_lbl;

    @FXML
    private TextField subtotal_TF;


    @FXML
    private Label currentStatus_lbl;

    @FXML
    private Label approvedBy_lbl;

    @FXML
    private Label dateHolder_lbl;

    @FXML
    private final double  VAT = 0.2;

    private int PROCUREMENT_ID; // This is the procurement ID that will be passed to this controller

    private String PROCUREMENT_STATUS; // This is the procurement status that will be passed to this controller
    @FXML
    private void closeWindow() {
        SharedButtonUtils.closeMenu(closeMenu_btn);
    }


    @FXML
    public void loadProcurementBasketInfo(int procurementID) {
        ObservableList<ProcurementBasketModel>basketDetails = ProcurementBasketDAO.getBasketDetails(procurementID);

        this.PROCUREMENT_ID = procurementID;
        catalogueID_col.setCellValueFactory(new PropertyValueFactory<>("catalogID"));
        assetName_col.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        unitPrice_col.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        qty_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalPrice_col.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        double subtotal = 0;

        for (ProcurementBasketModel basket : basketDetails) {
            subtotal += basket.getTotalCost();
        }
        String subTotalFormatted = String.format("%.2f", subtotal);
        String TotalPriceWithVAT = String.format("%.2f", subtotal*VAT + subtotal);
        subtotal_TF.setText("£ " + subTotalFormatted);
        totalCalculatedCost_TF.setText("£ " + TotalPriceWithVAT);
        catalogueTable.setItems(basketDetails);
    }



    @FXML
    private void loadProcurementRequestInfo() throws SQLException {

        ObservableList<ProcurementRequestModel> requesterDetails = ProcurementRequestDAO.getProcurementRequestDetails(PROCUREMENT_ID);

        loadRequesterDetails(requesterDetails.get(0).getUserID());
        requestorCommment_TA.setText(requesterDetails.get(0).getRequesterComment());

        System.out.println(requesterDetails.get(0).getProcurementRequestStatus());
        if (requesterDetails.get(0).getProcurementManagerComment() != null) {
            approverComment_TA.setText(requesterDetails.get(0).getProcurementManagerComment());
        }

        this.PROCUREMENT_STATUS = requesterDetails.get(0).getProcurementRequestStatus();
        currentStatus_lbl.setText("Status: " + requesterDetails.get(0).getProcurementRequestStatus());

        System.out.println(requesterDetails.get(0).getProcurementApproverName());

        if (requesterDetails.get(0).getProcurementApproverName() != null) {
            approvedBy_lbl.setText("Officer: " + requesterDetails.get(0).getProcurementApproverName());
        } else {
            approvedBy_lbl.setText("Officer: Not assigned");
        }

        if ( requesterDetails.get(0).getProcurementCompletionDate() != null) {
            dateHolder_lbl.setText("Date Closed: " + requesterDetails.get(0).getProcurementCompletionDate());
        } else {
            dateHolder_lbl.setText("Date Raised: " + requesterDetails.get(0).getProcurementRequestDate());
        }
    }

    @FXML
    private void loadRequesterDetails(int userID) throws SQLException {

        UserModel userModel = UserDAO.loadCurrentLoggedUser(userID);
        if (userModel != null) {
            firstName_TF.setText(userModel.getFirstName());
            lastName_TF.setText(userModel.getLastName());
            email_TF.setText(userModel.getEmail());
            department_TF.setText(userModel.getDeptName());
            username_TF.setText(userModel.getUsername());
            email_TF.setText(userModel.getEmail());
            phone_TF.setText(userModel.getPhone());
        } else {
            System.out.println("User not found with ID: " + userID);
        }
    }


    @FXML
    private void rejectProcurementRequest() {
        String status = "Rejected";
        ProcurementRequestDAO.updateProcurementRequest(PROCUREMENT_ID,
                CurrentLoggedUserHandler.getCurrentLoggedAdminID(),
                status,
                DateTimeUtils.getYearMonthDayFormat(),
                approverComment_TA.getText());
    }


    @FXML
    private void approveProcurementRequest() {

        //Current arbitrary budget for department is 250_000 - can be edited on config.properties
        String departmentBudgetString = ConfigPropertiesUtils.getPropertyValue("DEPARTMENT_BUDGET");
        double currentDepartmentBudget = Double.parseDouble(Objects.requireNonNull(departmentBudgetString));

        //Get the total cost of the procurement request
        double remainingBudget = currentDepartmentBudget - Double.parseDouble(totalCalculatedCost_TF.getText().replace("£", "").trim());


        if (remainingBudget < 0) {
            AlertNotificationUtils.showInformationMessageAlert("Budget exceeded",
                    "Remaining department budget is " +  currentDepartmentBudget + "\n " +
                            "and the Total cost of the procurement request is " + totalCalculatedCost_TF.getText() + ". " +
                            "\nAutomatically rejecting the request.");

            System.out.println("Remaining department budget:" + remainingBudget);
            approverComment_TA.setText("The current financial budget exceeded, cannot accept the request.");
            rejectProcurementRequest();
            closeWindow();
        } else {
            //If the remaining balance is not negative Update the department budget
            String formattedRemainingBudget = String.format("%.2f", remainingBudget);
            ConfigPropertiesUtils.setPropertyValue("DEPARTMENT_BUDGET", formattedRemainingBudget);

            //Update the procurement request

            if (approverComment_TA.getText().isEmpty()) {
                approverComment_TA.setText("Approved");
            }

            String status = "Approved";
            ProcurementRequestDAO.updateProcurementRequest(PROCUREMENT_ID,
                    CurrentLoggedUserHandler.getCurrentLoggedAdminID(),
                    status,
                    DateTimeUtils.getYearMonthDayFormat(),
                    approverComment_TA.getText());

            //
            AlertNotificationUtils.showInformationMessageAlert("Procurement Request Approved",
                    "Procurement Request has been approved successfully");
            closeWindow();
        }
    }


    @FXML
    private void setViewOnly() {

        // Due to lack of development time, this could be exploited
        // Thus a permission table would be suitable to handle this in future development

        if (CurrentLoggedUserHandler.getCurrentLoggedAdminID() != 1){
            actionButton_Hbox.setVisible(false);
            saveProcurementRequest_btn.setDisable(true);
            saveProcurementRequest_btn1.setDisable(true);
            approverComment_TA.setEditable(false);
            requestorCommment_TA.setEditable(false);
        } else if (PROCUREMENT_STATUS.equals("Approved") || PROCUREMENT_STATUS.equals("Rejected")) {
            actionButton_Hbox.setVisible(false);
            saveProcurementRequest_btn.setDisable(true);
            saveProcurementRequest_btn1.setDisable(true);
            approverComment_TA.setEditable(false);
            requestorCommment_TA.setEditable(false);
        } else {
            actionButton_Hbox.setVisible(true);
            saveProcurementRequest_btn.setDisable(false);
            saveProcurementRequest_btn1.setDisable(false);
            approverComment_TA.setEditable(true);
            requestorCommment_TA.setEditable(true);
        }


    }







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Platform.runLater(() -> {
            try {
                loadProcurementRequestInfo();
                setViewOnly();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
