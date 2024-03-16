package com.example.fyp_application.Controllers.Client.ClientRequestControllers;

import com.example.fyp_application.Model.TicketAttachmentDAO;
import com.example.fyp_application.Model.TicketDAO;
import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Model.UserModel;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientRaiseRequestController implements Initializable {

    @FXML
    private Button addAttachment_btn;

    @FXML
    private CheckBox attachmentCheckbox;

    @FXML
    private ListView<String> attachmentListView;

    @FXML
    private TitledPane attachmentTitlePane;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button closeMenu_btn;

    @FXML
    private Button exitApp_btn;

    @FXML
    private AnchorPane headerAP;

    @FXML
    private Label lastUpdateTime_lbl;

    @FXML
    private Circle loggedUserImage;

    @FXML
    private AnchorPane mainContentAnchorPane;

    @FXML
    private Button minimizeApp_btn;

    @FXML
    private Button refreshHeader_btn;

    @FXML
    private Button removeAttachment_btn;

    @FXML
    private Button sendBtn;

    @FXML
    private TextArea ticketDetails;

    @FXML
    private TextField ticketTitle;

    @FXML
    private Label username_lbl;


    private final TicketDAO TICKET_DAO = new TicketDAO();

    //Default Ticket Category - Uncategorized
    private static final int ticketCategory = 4;

    private static String userEmail = "";
 /*   @FXML
    private void raiseNewTicket() {
        if (validateFields()) {
            AlertNotificationHandler.showErrorMessageAlert("Empty Fields", "Please fill in all the fields");
            return;
        }
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                UserModel user = UserDAO.loadCurrentLoggedUser(CurrentLoggedUserHandler.getCurrentLoggedUserID());
                String title = ticketTitle.getText();
                String details = ticketDetails.getText();

                int ticketID = TICKET_DAO.openUserTicketRequest(
                        user.getUserID(), ticketCategory, title, details, "Created", "Low", DateTimeHandler.getCurrentDateTime()
                );

                if (attachmentListView != null && !attachmentListView.getItems().isEmpty()) {
                    for (String filePath : attachmentListView.getItems()) {
                        TicketAttachmentDAO.insertAttachment(ticketID, filePath, DateTimeHandler.getSQLiteDate());
                    }
                }

                sendEmailNotificationAsync(ticketID, user.getFirstName(), title, details);
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            AlertNotificationHandler.showInformationMessageAlert("Ticket Raised", "Your ticket has been raised successfully");
            closeWindow();
        });

        task.setOnFailed(e -> {
            // Handle any exceptions, possibly show an error alert
            Throwable problem = task.getException();
            AlertNotificationHandler.showErrorMessageAlert("Error", "An error occurred: " + problem.getMessage());
        });

        new Thread(task).start();
    }


    @FXML
    private void sendEmailNotificationAsync(int ticketID,String firstname, String title, String details) {

        GMailHandler.sendEmailTo(userEmail, "Call Logged SD " + ticketID ,
                GMailHandler.generateTicketRequestEmailBody(ticketID, firstname, title, details));


    }*/


    @FXML
    private void raiseNewTicket(){
        //validate the fields
        if(validateFields()){
            AlertNotificationHandler.showErrorMessageAlert("Empty Fields", "Please fill in all the fields");
            return;
        }
        //create a new task to handle the ticket creation
        Task<Void> task = createTicketAsync();
        handleTaskActions(task);

        new Thread(task).start();
    }


    //create a new task to handle the ticket creation
    private Task<Void> createTicketAsync(){
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                //get the current logged user
                UserModel user = UserDAO.loadCurrentLoggedUser(CurrentLoggedUserHandler.getCurrentLoggedUserID());
                String title = ticketTitle.getText();
                String details = ticketDetails.getText();
                userEmail = user.getEmail();
                //open a new ticket request which returns the created ticket ID after database insertion
                int ticketID = TICKET_DAO.openUserTicketRequest(
                        user.getUserID(), ticketCategory, title, details, "Created", "Low", DateTimeHandler.getCurrentDateTime()
                );
                //upload the attachment and send the email notification
                uploadAttachmentAsync(ticketID);
                sendEmailNotificationAsync(ticketID, user.getFirstName(), title, details);
                return null;
            }
        };
    }

    @FXML
    private void sendEmailNotificationAsync(int ticketID, String firstname, String title, String details) {
        //send the email notification to the user
        GMailHandler.sendEmailTo(userEmail, "Call Logged SD " + ticketID,
                GMailHandler.generateTicketRequestEmailBody(ticketID, firstname, title, details));
    }

    @FXML
    private void uploadAttachmentAsync(int ticketID){
        //upload the attachment to the database if the attachment list is not empty
        if (attachmentListView != null && !attachmentListView.getItems().isEmpty()) {
            for (String filePath : attachmentListView.getItems()) {
                TicketAttachmentDAO.insertAttachment(ticketID, filePath, DateTimeHandler.getSQLiteDate());
            }
        }
    }

    private void handleTaskActions(Task<Void> task){
        task.setOnSucceeded(e -> {
            AlertNotificationHandler.showInformationMessageAlert("Ticket Raised", "Your ticket has been raised successfully");
            closeWindow();
        });

        task.setOnFailed(e -> {
            // Handle any exceptions, possibly show an error alert
            Throwable problem = task.getException();
            AlertNotificationHandler.showErrorMessageAlert("Error", "An error occurred: ");
            System.out.println(problem.getMessage());
        });
    }

    @FXML
    private boolean validateFields() {
        return
                ticketTitle.getText().isEmpty() ||
                ticketDetails.getText().isEmpty();
    }

    @FXML
    private ObservableList<String> attachments = FXCollections.observableArrayList();

    @FXML
    private void addAttachment(){
        AttachmentHandler.addAttachments(attachments,attachmentListView);
    }

    @FXML
    private void deleteAttachment(){
        AttachmentHandler.deleteAttachments(attachmentListView);
    }


    public void closeWindow() {
        SharedButtonUtils.closeMenu(closeMenu_btn);

    }


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
/*
        attachmentCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                attachmentTitlePane.setDisable(false);
            } else {
                attachmentTitlePane.setExpanded(false);
                attachmentTitlePane.setDisable(true);

                attachmentListView.getItems().clear();


            }
        });


        attachmentListView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                String selectedItem = attachmentListView.getSelectionModel().getSelectedItem();
                openAttachment(selectedItem);

            }
        });
    }*/

        AttachmentHandler.setAttachmentListState(attachmentCheckbox, attachmentTitlePane);
        AttachmentHandler.setAttachmentListListener(attachmentListView);

    }

}
