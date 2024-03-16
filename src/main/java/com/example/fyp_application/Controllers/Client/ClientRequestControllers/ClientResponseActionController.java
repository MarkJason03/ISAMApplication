package com.example.fyp_application.Controllers.Client.ClientRequestControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.AlertNotificationHandler;
import com.example.fyp_application.Utils.AttachmentHandler;
import com.example.fyp_application.Utils.DateTimeHandler;
import com.example.fyp_application.Utils.SharedButtonUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientResponseActionController implements Initializable {

    @FXML
    private Button addAttachment_btn;

    @FXML
    private CheckBox attachmentCheckbox;

    @FXML
    private ListView<String> attachmentListView;

    @FXML
    private TitledPane attachmentTitlePane;

    @FXML
    private Button backBtn;

    @FXML
    private Button exitApp_btn;

    @FXML
    private AnchorPane headerAP;

    @FXML
    private AnchorPane mainContentAnchorPane;

    @FXML
    private Button minimizeApp_btn;

    @FXML
    private Button quickClose_btn;


    @FXML
    private Button removeAttachment_btn;

    @FXML
    private TextArea responseDetails;

    @FXML
    private Button sendBtn;

    @FXML
    private TextField ticketTitle;


    private  int ticketID;

    private static final String TICKET_STATUS = "Awaiting Response";

    private static final TicketDAO TICKET_DAO = new TicketDAO();
    @FXML
    private void handleTicketUpdate() {


        if (responseDetails.getText().isEmpty()) {
            // Show error message
            AlertNotificationHandler.showErrorMessageAlert("Response cannot be empty", "Please fill in the email body");
            return;
        }

        Task<Void> updateTask = new Task<>() {
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
                submitResponse(); // Submit the response
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
        if (filePaths.isEmpty()) {
            // If there are no attachments, close the window directly
            closeWindow();
        } else {
            // If there are attachments, upload them first
            Task<Void> attachmentTask = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    submitAttachment();
                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    // After uploading the attachments, close the window
                    closeWindow();
                }

                @Override
                protected void failed() {
                    super.failed();
                    // Handle failure
                    throw new UnsupportedOperationException("Attachment submission failed");
                }
            };

            new Thread(attachmentTask).start();
        }
    }

    @FXML
    private void submitTicketDetailChanges(){
        TicketDAO.updateTicketStatus(ticketID,
                TICKET_STATUS);
    }

    @FXML
    private void submitResponse() throws SQLException {
        MessageHistoryDAO.recordMessage(ticketID,
                responseDetails.getText().concat( "\n\n" + ticketInfoList.get(0).getUserFullName()),
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

    private ObservableList<TicketModel> ticketInfoList;

    public void loadTicketInfo(int ticketID){
        ticketInfoList = TICKET_DAO.getTicketDetails(ticketID);

        if (!ticketInfoList.isEmpty()) {
            this.ticketID = ticketInfoList.get(0).getTicketID();
            ticketTitle.setText(ticketInfoList.get(0).getTicketTitle());
        } else {
            System.out.println("Ticket not found");
        }
        System.out.println(ticketID);
    }

    @FXML
    private void addAttachment() {
        // Add the attachment
        AttachmentHandler.addAttachments(filePaths, attachmentListView);

    }
    private final ObservableList<String> filePaths = FXCollections.observableArrayList();
    @FXML
    private void deleteAttachment() {
        // Delete the attachment
        AttachmentHandler.addAttachments(filePaths, attachmentListView);
    }


    @FXML
    private void sendResponse() {


    }


    @FXML
    private void closeWindow() {
        // Close the menu
        SharedButtonUtils.closeMenu(backBtn);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set the attachment list listener
        AttachmentHandler.setAttachmentListListener(attachmentListView);
        AttachmentHandler.setAttachmentListState(attachmentCheckbox, attachmentTitlePane);

    }
}
