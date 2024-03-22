package com.example.fyp_application.Controllers.Admin.RequestManagementControllers;

import com.example.fyp_application.Model.TicketDAO;
import com.example.fyp_application.Model.TicketModel;
import com.example.fyp_application.Utils.AlertNotificationUtils;
import com.example.fyp_application.Utils.DateTimeUtils;
import com.example.fyp_application.Views.ViewConstants;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageRequestController implements Initializable {


    
    @FXML
    private TableColumn<?, ?> agentName_col;

    @FXML
    private AnchorPane contentAP;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private Button assignTicket_btn;

    @FXML
    private Label deptHolder_lbl;

    @FXML
    private Button editProfile_btn1;

    @FXML
    private TextField email_TF;

    @FXML
    private TextField firstName_TF;

    @FXML
    private Label fullNameHolder_lbl;

    @FXML
    private TextField lastName_TF;

    @FXML
    private Label lastUpdate_lbl;

    @FXML
    private Button newRequest;

    @FXML
    private Button reload_btn;

    @FXML
    private TableView<TicketModel> requestTableView;

    @FXML
    private TextField searchBar_TF;

    @FXML
    private TableColumn<?, ?> ticketCategory_col;

    @FXML
    private TableColumn<?, ?> ticketDateClosed_col;

    @FXML
    private TableColumn<?, ?> ticketDateCreated_col;

    @FXML
    private TableColumn<?, ?> ticketID_col;

    @FXML
    private TableColumn<?, ?> ticketPriority_col;

    @FXML
    private TableColumn<?, ?> ticketStatus_col;

    @FXML
    private TableColumn<?, ?> ticketTitle_col;

    @FXML
    private Label userCounter_lbl;

    @FXML
    private Label userCounter_lbl1;

    @FXML
    private TableColumn<?, ?> userFullname_col;

    @FXML
    private Label userInactiveCounter_lbl;

    @FXML
    private TextField username_TF;

    @FXML
    private Button viewRequest;

    private static final TicketDAO TICKET_DAO = new TicketDAO();




    @FXML
    private void raiseNewTicket() {

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) requestTableView.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage


        //SupplierModel selectedSupplier = requestTableView.getSelectionModel().getSelectedItem();

        try {
            //Load the supplier menu
            //modal pop-up dialogue box
            FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_RAISE_TICKET_POP_UP));
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

            loadTicketsTable();


            Platform.runLater(this::countCreatedRequests);
            Platform.runLater(this::countOnProgressRequests);
            Platform.runLater(this::countClosedCalls);

        }





    }

    @FXML
    private void reloadTable() {

        Platform.runLater(this::loadTicketsTable);
        dateTimeHolder.setText("Last Updated: " + DateTimeUtils.getCurrentTimeFormat());
    }


    @FXML
    private void countCreatedRequests() {
        // TODO counts the number of created requests
    }


    @FXML
    private void countOnProgressRequests() {
        // TODO counts the number of in progress requests
    }

    @FXML
    private void countClosedCalls() {
        // TODO counts the number of closed requests
    }


    @FXML
    private void actionTicket(){

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) requestTableView.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage


        TicketModel selectedTicket = requestTableView.getSelectionModel().getSelectedItem();

        if (selectedTicket == null) {
            AlertNotificationUtils.showErrorMessageAlert("Unable to load ticket details", "Please select a ticket to view details.");
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect
        }
        else {
            try {
                //Load the supplier menu
                //modal pop-up dialogue box
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_VIEW_TICKET_POP_UP));
                Parent root = modalViewLoader.load();


                ViewTicketController viewTicketController = modalViewLoader.getController();
                viewTicketController.loadTicketInfo(requestTableView.getSelectionModel().getSelectedItem().getTicketID());


                // New window setup as modal
                Stage supplierPopUpStage = new Stage();
                supplierPopUpStage.initOwner(currentDashboardStage);
                supplierPopUpStage.initModality(Modality.WINDOW_MODAL);
                supplierPopUpStage.initStyle(StageStyle.TRANSPARENT);


                Scene scene = new Scene(root);
                supplierPopUpStage.setScene(scene);

                supplierPopUpStage.showAndWait(); // Blocks interaction with the main stage

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            } finally {
                currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close
                //Platform.runLater(this::loadTicketsTable);

                loadTicketsTable();
                Platform.runLater(this::countCreatedRequests);
                Platform.runLater(this::countOnProgressRequests);
                Platform.runLater(this::countClosedCalls);

            }
        }

    }

    @FXML
    private void assignTicket(){

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) requestTableView.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage


        TicketModel selectedTicket = requestTableView.getSelectionModel().getSelectedItem();

        if (selectedTicket == null) {
            AlertNotificationUtils.showErrorMessageAlert("Unable to load ticket details", "Please select a ticket to view details.");
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect
        }
        else {
            try {
                //Load the supplier menu
                //modal pop-up dialogue box
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ASSIGN_TICKET_POP_UP));
                Parent root = modalViewLoader.load();


                AssignCallController assignCallController = modalViewLoader.getController();
                assignCallController.loadTicketDetails(requestTableView.getSelectionModel().getSelectedItem().getTicketID());

                // New window setup as modal
                Stage assignTicketModalWidow = new Stage();
                assignTicketModalWidow.initOwner(currentDashboardStage);
                assignTicketModalWidow.initModality(Modality.WINDOW_MODAL);
                assignTicketModalWidow.initStyle(StageStyle.TRANSPARENT);


                Scene scene = new Scene(root);
                assignTicketModalWidow.setScene(scene);

                assignTicketModalWidow.showAndWait(); // Blocks interaction with the main stage

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close
                //Platform.runLater(this::loadTicketsTable);

                loadTicketsTable();
                Platform.runLater(this::countCreatedRequests);
                Platform.runLater(this::countOnProgressRequests);
                Platform.runLater(this::countClosedCalls);

            }
        }

    }

    @FXML
    private ObservableList<TicketModel> ticketList;

    @FXML
    private void loadTicketsTable(){
        ticketList = TICKET_DAO.getAllTickets();

        ticketID_col.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        userFullname_col.setCellValueFactory(new PropertyValueFactory<>("userFullName"));
        agentName_col.setCellValueFactory(new PropertyValueFactory<>("agentFullName"));
        ticketTitle_col.setCellValueFactory(new PropertyValueFactory<>("ticketTitle"));
        ticketStatus_col.setCellValueFactory(new PropertyValueFactory<>("ticketStatus"));
        ticketPriority_col.setCellValueFactory(new PropertyValueFactory<>("ticketPriority"));
        ticketCategory_col.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        ticketDateCreated_col.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        ticketDateClosed_col.setCellValueFactory(new PropertyValueFactory<>("dateClosed"));


        requestTableView.setItems(ticketList);


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTicketsTable();

    }
}
