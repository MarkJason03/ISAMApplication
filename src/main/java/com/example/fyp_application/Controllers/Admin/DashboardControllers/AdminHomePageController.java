package com.example.fyp_application.Controllers.Admin.DashboardControllers;

import com.example.fyp_application.Controllers.Admin.ProcurementManagementControllers.EditProcurementRequestController;
import com.example.fyp_application.Controllers.Admin.RequestManagementControllers.ManageRequestController;
import com.example.fyp_application.Controllers.Admin.RequestManagementControllers.ViewTicketController;
import com.example.fyp_application.Controllers.Admin.UserManagementControllers.ModifiedEditUserController;
import com.example.fyp_application.Controllers.Client.ClientRequestControllers.ClientRequestDashboardController;
import com.example.fyp_application.Model.*;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.AlertNotificationUtils;
import com.example.fyp_application.Utils.UserDetailsUtils;
import com.example.fyp_application.Views.ViewConstants;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;

public class AdminHomePageController implements Initializable {


    @FXML
    private AnchorPane contentAP;

    @FXML
    private AnchorPane dashboardStatAP;

    @FXML
    private Label deparment_lbl;

    @FXML
    private Label email_lbl;

    @FXML
    private Label expiresDate_lbl;

    @FXML
    private Label fullName_lbl;

    @FXML
    private Circle imageHolder_shape;

    @FXML
    private Circle imageHolder_shape1;

    @FXML
    private Label phone_lbl;

    @FXML
    private TableColumn<?, ?> procurementComment_col;

    @FXML
    private TableColumn<?, ?> procurementID_col;

    @FXML
    private TableView<ProcurementRequestModel> procurementRequestTable;

    @FXML
    private TableColumn<?, ?> procurementStatus_col;

    @FXML
    private TableColumn<ProcurementRequestModel, FontIcon> sample;

    @FXML
    private Button test2btn;

    @FXML
    private Button testbtn;

    @FXML
    private TableColumn<?, ?> ticketCategory_col;

    @FXML
    private TableColumn<?, ?> ticketCreated_col;

    @FXML
    private TableColumn<?, ?> ticketEscalation_col;

    @FXML
    private TableColumn<?, ?> ticketID_col;

    @FXML
    private TableColumn<TicketModel,FontIcon> ticketIcon_col;

    @FXML
    private TableColumn<?, ?> ticketResolution_col;

    @FXML
    private TableColumn<?, ?> ticketStatus_col;

    @FXML
    private TableView<TicketModel> ticketTable;

    @FXML
    private TableColumn<?, ?> ticketTitle_col;

    @FXML
    private Label totalCreatedCallHolder_lbl;

    @FXML
    private Label totalInProgressTicket_lbl;

    @FXML
    private Label totalOverdueHolder_lbl;

    @FXML
    private BarChart<String,Number> sampleBarChart;


//    @FXML
//    private void setUserPieChart() {
//        int activeUser = UserDAO.countActiveUsers();
//        int inactiveUser = UserDAO.countInactiveUsers();
//        int expiredUser = UserDAO.countExpiredUsers();
//
//        // Creating the pie chart data
//        PieChart.Data activeData = new PieChart.Data("Active", activeUser);
//        PieChart.Data inactiveData = new PieChart.Data("Inactive", inactiveUser);
//        PieChart.Data expiredData = new PieChart.Data("Expired", expiredUser);
//
//        userPieChart.setTitle("User Status");
//        userPieChart.getData().addAll(activeData, inactiveData, expiredData);
//
//    }
//
//    @FXML
//    private void setUserLineChart() {
//        // Assuming we're setting this up for three time points: T1, T2, and T3
//        // In your real application, these would be actual data points like months or years
//        String[] timePoints = {"T1", "T2", "T3"};
//
//        // Example user counts at three different time points
//        // For demonstration, using static data; replace these with your actual data retrieval
//        int[][] userCounts = {
//                {UserDAO.countActiveUsers(), 120, 130}, // Simulated counts for active users over time
//                {UserDAO.countInactiveUsers(), 80, 90}, // Simulated counts for inactive users
//                {UserDAO.countExpiredUsers(), 50, 60}   // And for expired users
//        };
//
//        XYChart.Series<String, Number> seriesActive = new XYChart.Series<>();
//        seriesActive.setName("Active Users");
//
//        XYChart.Series<String, Number> seriesInactive = new XYChart.Series<>();
//        seriesInactive.setName("Inactive Users");
//
//        XYChart.Series<String, Number> seriesExpired = new XYChart.Series<>();
//        seriesExpired.setName("Expired Users");
//
//        for (int i = 0; i < timePoints.length; i++) {
//            seriesActive.getData().add(new XYChart.Data<>(timePoints[i], userCounts[0][i]));
//            seriesInactive.getData().add(new XYChart.Data<>(timePoints[i], userCounts[1][i]));
//            seriesExpired.getData().add(new XYChart.Data<>(timePoints[i], userCounts[2][i]));
//        }
//
//        sampleLineChart.getData().addAll(seriesActive, seriesInactive, seriesExpired);
//    }
//
//
//    @FXML
//    private void setUserBarChart() {
//        int activeUser = UserDAO.countActiveUsers();
//        int inactiveUser = UserDAO.countInactiveUsers();
//        int expiredUser = UserDAO.countExpiredUsers();
//
//        System.out.println("Active users: " + activeUser);
//        System.out.println("Inactive users: " + inactiveUser);
//        System.out.println("Expired users: " + expiredUser);
//
//        CategoryAxis xAxis = (CategoryAxis) sampleBarChart.getXAxis();
//        xAxis.setLabel("User Status");
//
//        NumberAxis yAxis = (NumberAxis) sampleBarChart.getYAxis();
//        yAxis.setLabel("User Count");
//
//        XYChart.Series<String, Number> series = new XYChart.Series<>();
//        series.setName("Account Status");
//
//        series.getData().add(new XYChart.Data<>("Active", activeUser));
//        series.getData().add(new XYChart.Data<>("Inactive", inactiveUser));
//        series.getData().add(new XYChart.Data<>("Expired", expiredUser));
//        xAxis.getCategories().addAll("Active", "Inactive", "Expired");
//
//        sampleBarChart.getData().clear();
//        sampleBarChart.layout();
//        sampleBarChart.getData().add(series);
//    }


    @FXML
    private void openProcurementRequest() {
        // Open the procurement request page
        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) contentAP.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage


        //SupplierModel selectedSupplier = requestTableView.getSelectionModel().getSelectedItem();

        try {
            //Load the supplier menu
            //modal pop-up dialogue box
            FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_RAISE_PROCUREMENT_TICKET_VIEW));
            Parent root = modalViewLoader.load();




            // New window setup as modal
            Stage requestPopUp = new Stage();
            requestPopUp.initOwner(currentDashboardStage);
            requestPopUp.initModality(Modality.WINDOW_MODAL);
            requestPopUp.initStyle(StageStyle.TRANSPARENT);


            Scene scene = new Scene(root);
            requestPopUp.setScene(scene);

            requestPopUp.showAndWait(); // Blocks interaction with the main stage

        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close

        }

    }

    @FXML
    private void openManageRequest() {
        // Open the manage request page
        openTicketDetails(procurementRequestTable, false);
    }

    @FXML
    public Stage  openTicketDetails(TableView<ProcurementRequestModel> tableView, boolean isFromDashboard){

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) tableView.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage


        ProcurementRequestModel selectedTicket = tableView.getSelectionModel().getSelectedItem();
        Stage ticketModalWindow = null;

        if (selectedTicket == null) {
            AlertNotificationUtils.showErrorMessageAlert("Unable to load ticket details", "Please select a ticket to view details.");
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect
        }
        else {
            try {
                //Load the supplier menu
                //modal pop-up dialogue box
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.PROCUREMENT_OFFICER_EDIT_TICKET_POP_UP));
                Parent root = modalViewLoader.load();


                EditProcurementRequestController editProcurementRequestController = modalViewLoader.getController();
                editProcurementRequestController.loadProcurementBasketInfo(tableView.getSelectionModel().getSelectedItem().getProcurementRequestID());


                // New window setup as modal
                ticketModalWindow = new Stage();
                ticketModalWindow.initOwner(currentDashboardStage);
                ticketModalWindow.initModality(Modality.WINDOW_MODAL);
                ticketModalWindow.initStyle(StageStyle.TRANSPARENT);


                Scene scene = new Scene(root);
                ticketModalWindow.setScene(scene);

                ticketModalWindow.showAndWait(); // Blocks interaction with the main stage

            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close

            }
        }
        return ticketModalWindow;
    }


    @FXML
    private void viewProcurementRequestByUser() {
        // View the procurement request
        ObservableList<ProcurementRequestModel> procurementTicketsByUser = ProcurementRequestDAO.getProcurementTicketsByUser(CurrentLoggedUserHandler.getCurrentLoggedAdminID());

        sample.setCellValueFactory(cellData -> {
            FontIcon icon;
            // if request is approved
            if (cellData.getValue().getProcurementRequestStatus().equals("Approved")) {
                icon = new FontIcon("mdi2b-book-check");

                // if request is awaiting approval
            } else if (cellData.getValue().getProcurementRequestStatus().equals("Awaiting Approval")) {
                icon = new FontIcon("mdi2b-book-clock");
            } else {
                // if request is rejected
                icon = new FontIcon("mdi2b-book-cancel");
            }
            icon.setIconSize(40);
            return new SimpleObjectProperty<>(icon);
        });
        procurementID_col.setCellValueFactory(new PropertyValueFactory<>("procurementRequestID"));
        procurementStatus_col.setCellValueFactory(new PropertyValueFactory<>("procurementRequestStatus"));
        procurementComment_col.setCellValueFactory(new PropertyValueFactory<>("procurementManagerComment"));

        procurementRequestTable.setItems(procurementTicketsByUser);

    }

    @FXML
    private void setupIDCard() {


        try {
            UserModel userModel = UserDAO.loadCurrentLoggedUser(CurrentLoggedUserHandler.getCurrentLoggedAdminID());

            UserDetailsUtils.setupIDCard(userModel, fullName_lbl, deparment_lbl, expiresDate_lbl, email_lbl, phone_lbl, imageHolder_shape);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadTicketTableByAgent() {
        // Set up the statistics for the home page

        ObservableList<TicketModel> requestList = TicketDAO.getTicketInformationByAgentID(CurrentLoggedUserHandler.getCurrentLoggedAdminID());

        ticketIcon_col.setCellValueFactory(cellData -> {
            FontIcon icon;
            if (cellData.getValue().getTicketStatus().equals("Created")) {
                icon = new FontIcon("mdi2b-book-alert");
            } else if (cellData.getValue().getTicketStatus().equals("Awaiting Response") || cellData.getValue().getTicketStatus().equals("In Progress")) {
                icon = new FontIcon("mdi2b-book-clock");
            } else {
                icon = new FontIcon("mdi2b-book-check");
            }
            icon.setIconSize(40);
            return new SimpleObjectProperty<>(icon);
        });

        ticketID_col.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        ticketTitle_col.setCellValueFactory(new PropertyValueFactory<>("ticketTitle"));
        ticketCategory_col.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        ticketEscalation_col.setCellValueFactory(new PropertyValueFactory<>("escalationStatus"));
        ticketStatus_col.setCellValueFactory(new PropertyValueFactory<>("ticketStatus"));
        ticketCreated_col.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        ticketResolution_col.setCellValueFactory(new PropertyValueFactory<>("targetResolutionDate"));

        ticketTable.setItems(requestList);
    }

    @FXML
    private void setupHomeStats() {
        // Set up the statistics for the home page
        totalCreatedCallHolder_lbl.setText("Created: " + TicketDAO.countCreatedTicketsByAgent());
        totalInProgressTicket_lbl.setText("To do: " + TicketDAO.countInProgressTicketByAgent(CurrentLoggedUserHandler.getCurrentLoggedAdminID()));
        totalOverdueHolder_lbl.setText("Total: " + AssetAllocationDAO.countTotalOverdueAssets());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
/*
        Platform.runLater(this::setUserPieChart);
        Platform.runLater(this::setUserLineChart);
        setUserBarChart();
*/
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> {
                    try {
                        setupIDCard();
                        viewProcurementRequestByUser();
                        loadTicketTableByAgent();
                        setupHomeStats();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();


        ticketTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ManageRequestController manageRequestController = new ManageRequestController();
                Stage modalStage = manageRequestController.openTicketDetails(ticketTable, true);
                if (modalStage != null) {
                    modalStage.setOnHidden(e -> loadTicketTableByAgent());
                }
            }
        });
    }
}
