package com.example.fyp_application.Controllers.Client.ClientRequestControllers;

import com.example.fyp_application.Model.TicketDAO;
import com.example.fyp_application.Model.TicketModel;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.AlertNotificationUtils;
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

public class ClientRequestDashboardController implements Initializable {

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
    private TableColumn<?, ?> ticketDateClosed_col;

    @FXML
    private TableColumn<?, ?> ticketDateCreated_col;

    @FXML
    private TableColumn<?, ?> ticketDescription_col;

    @FXML
    private TableColumn<?, ?> ticketID_col;

    @FXML
    private TableColumn<?, ?> ticketStatus_col;

    @FXML
    private TableColumn<?, ?> ticketTitle_col;

    @FXML
    private Label userCounter_lbl;

    @FXML
    private Label userCounter_lbl1;

    @FXML
    private Label userInactiveCounter_lbl;

    @FXML
    private TextField username_TF;

    @FXML
    private Button viewRequest;

    public static final int CURRENT_LOGGED_USER_ID = CurrentLoggedUserHandler.getCurrentLoggedUserID();

    private final TicketDAO ticketDAO = new TicketDAO();//instance of the Ticket Data Access Object

    @FXML
    private ObservableList<TicketModel>requestsData;
    @FXML
    private void loadRequestsData(){

        requestsData = ticketDAO.getTicketsByUserID(CURRENT_LOGGED_USER_ID);

        ticketID_col.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        ticketTitle_col.setCellValueFactory(new PropertyValueFactory<>("ticketTitle"));
        ticketDescription_col.setCellValueFactory(new PropertyValueFactory<>("ticketDescription"));
        ticketDateCreated_col.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        ticketDateClosed_col.setCellValueFactory(new PropertyValueFactory<>("dateClosed"));
        ticketStatus_col.setCellValueFactory(new PropertyValueFactory<>("ticketStatus"));
        agentName_col.setCellValueFactory(new PropertyValueFactory<>("agentFullName"));

        requestTableView.setItems(requestsData);
    }


    @FXML
    private void openNewRequestForm(){

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) requestTableView.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage

        try {
            //Load the new request form
            //modal pop-up dialogue box
            FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.CLIENT_RAISE_TICKET_POP_UP));
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

            loadRequestsData();

        }
    }


    @FXML
    private void viewRequestDetails(){
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
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.CLIENT_VIEW_TICKET_POP_UP));
                Parent root = modalViewLoader.load();


                ClientViewRequestController viewTicketController = modalViewLoader.getController();
                viewTicketController.loadTicketInformation(requestTableView.getSelectionModel().getSelectedItem().getTicketID());


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
                Platform.runLater(this::loadRequestsData);

            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRequestsData();
    }
}
