package com.example.fyp_application.Controllers.Admin.RequestManagementControllers;

import com.example.fyp_application.Model.TicketDAO;
import com.example.fyp_application.Model.TicketModel;
import com.example.fyp_application.Utils.AlertNotificationHandler;
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
import java.util.ResourceBundle;

public class ManageRequestController implements Initializable {


    private static final AlertNotificationHandler ALERT_HANDLER = new AlertNotificationHandler() ;
    @FXML
    private TableColumn<?, ?> agentName_col;

    @FXML
    private AnchorPane contentAP;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private Button deleteUser_btn;

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
            Stage supplierPopUpStage = new Stage();
            supplierPopUpStage.initOwner(currentDashboardStage);
            supplierPopUpStage.initModality(Modality.WINDOW_MODAL);
            supplierPopUpStage.initStyle(StageStyle.TRANSPARENT);


            Scene scene = new Scene(root);
            supplierPopUpStage.setScene(scene);

            supplierPopUpStage.showAndWait(); // Blocks interaction with the main stage

        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close



            Platform.runLater(this::countCreatedRequests);
            Platform.runLater(this::countOnProgressRequests);
            Platform.runLater(this::countClosedCalls);

        }





    }

    @FXML
    private void reloadTable() {

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
            ALERT_HANDLER.showErrorMessageAlert("Error Loading Supplier Editor", "Please select a supplier to edit");
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect
        }
        else {
            try {
                //Load the supplier menu
                //modal pop-up dialogue box
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_VIEW_TICKET_POP_UP));
                Parent root = modalViewLoader.load();


                ViewTicketController viewTicketController = modalViewLoader.getController();
                viewTicketController.sample(requestTableView.getSelectionModel().getSelectedItem().getTicketID());


                // New window setup as modal
                Stage supplierPopUpStage = new Stage();
                supplierPopUpStage.initOwner(currentDashboardStage);
                supplierPopUpStage.initModality(Modality.WINDOW_MODAL);
                supplierPopUpStage.initStyle(StageStyle.TRANSPARENT);


                Scene scene = new Scene(root);
                supplierPopUpStage.setScene(scene);

                supplierPopUpStage.showAndWait(); // Blocks interaction with the main stage

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close


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
        ticketTitle_col.setCellValueFactory(new PropertyValueFactory<>("ticketTitle"));
        ticketStatus_col.setCellValueFactory(new PropertyValueFactory<>("ticketStatus"));
        ticketPriority_col.setCellValueFactory(new PropertyValueFactory<>("ticketPriority"));
        ticketCategory_col.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        agentName_col.setCellValueFactory(new PropertyValueFactory<>("agentFullName"));
        ticketDateCreated_col.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        ticketDateClosed_col.setCellValueFactory(new PropertyValueFactory<>("dateClosed"));


        requestTableView.setItems(ticketList);


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTicketsTable();

    }
}
