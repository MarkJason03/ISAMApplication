package com.example.fyp_application.Controllers.Admin.RequestManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.AlertNotificationUtils;
import com.example.fyp_application.Utils.AttachmentUtils;
import com.example.fyp_application.Utils.DateTimeUtils;
import com.example.fyp_application.Utils.GMailUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ActionTicketController implements Initializable {


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
    private Button quickClose_btn;

    @FXML
    private Button exitApp_btn;

    @FXML
    private AnchorPane headerAP;

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
    private Label ticketDateHolder_lbl;

    @FXML
    private Label ticketDateTitle_lbl;

    @FXML
    private ComboBox<TicketCategoryModel> ticketCategory_CB;

    @FXML
    private TextArea responseDetails;

    @FXML
    private ComboBox<String> ticketPriority_CB;

    @FXML
    private ComboBox<String> ticketStatus_CB;

    @FXML
    private TextField ticketTitle;

    @FXML
    private Button closeResponse_btn;

    private static final TicketDAO TICKET_DAO = new TicketDAO();

    private static  final TicketCategoryDAO TICKET_CATEGORY_DAO = new TicketCategoryDAO();


    @FXML
    private  int ticketID;
    private int agentID;
    private int userID;


    @FXML
    private Button b1;





    @FXML

    private void handleTicketChanges(){


        if (!responseDetails.getText().isEmpty()){

            if (ticketStatus_CB.getValue().equals("Closed")){
                startClosingTicketThread();
            } else {
                startOngoingTicketThread();
            }
        } else {
            AlertNotificationUtils.showErrorMessageAlert("Empty Response", "Please enter a response");
        }
    }

    @FXML
    private void startOngoingTicketThread() {


        Task<Void> updateTask = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                // Insert the ticket details changes to the database
                System.out.println("Running the update ticket thread");
                insertOngoingTicketDetailChanges();
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.println("Ticket updated successfully");
                System.out.println("Inserting response\n");
                insertingResponseTask(); // proceed to insert the message response to the message history table
            }

            @Override
            protected void failed() {
                super.failed();
            }
        };

        new Thread(updateTask).start();
    }

    //thread for closing the ticket
    @FXML
    private void startClosingTicketThread(){
        Task<Void> closeTask = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                // Insert the ticket details changes to the database

                System.out.println("Running the close ticket thread");
                insertClosedTicketDetailChanges();
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.println("Ticket closed successfully");
                System.out.println("Inserting response\n");
                insertingResponseTask(); // proceed to insert the last message response to the message history table
            }

            @Override
            protected void failed() {
                super.failed();
            }
        };

        new Thread(closeTask).start();
    }

    //thread for inserting the response to the message history table
    private void insertingResponseTask() {

        // Check if the ticket cb value is not closed
        if (!ticketStatus_CB.getValue().equals("Closed")) {
            Task<Void> ongoingTicketTask = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    System.out.println("Inserting response\n");
                    insertMessageToHistory(); // Store the message response to the message history table
                    System.out.println("Sending email\n");
                    sendTicketUpdateEmail(); // send the email with the response recorded
                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    System.out.println("Response recorded\n");
                    insertingAttachmentTask(); //Once the response is saved, proceed to insert attachments
                }

                @Override
                protected void failed() {
                    super.failed();
                }
            };

            new Thread(ongoingTicketTask).start();

        } else {
            Task<Void> closeTicketTask = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    System.out.println("Inserting response\n");
                    insertMessageToHistory(); // Store the message response to the message history table
                    System.out.println("Sending email\n");
                    sendCloseTicketEmail(); // send the close email with the response recorded
                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();

                    System.out.println("Inserting attachment\n");
                    insertingAttachmentTask(); //Once the response is saved, proceed to insert attachments after the response if there is any
                }

                @Override
                protected void failed() {
                    super.failed();
                }
            };

            new Thread(closeTicketTask).start();
        }

    }

    private void insertingAttachmentTask() {
        Task<Void> attachmentInsertionTask = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                System.out.println("Inserting attachment\n");
                insertAttachmentToHistory(); // Insert the attachments to the attachment table
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.println("Closing window\n");
                // Close the window or do other UI stuff as needed
                closeWindow();
            }

            @Override
            protected void failed() {
                super.failed();
            }
        };

        new Thread(attachmentInsertionTask).start();
    }

    @FXML
    private void insertOngoingTicketDetailChanges(){
        // Update the ticket details to ongoing or awaiting response
        TicketDAO.updateTicketDetailsByAdmin(ticketID,
                ticketCategory_CB.getValue().getCategoryID(),
                ticketStatus_CB.getValue(),
                ticketPriority_CB.getValue(),
                ticketDateHolder_lbl.getText()
                );
    }

    @FXML
    private void insertClosedTicketDetailChanges(){
        // Update the ticket details to closed with the current day as closing date stamp
        TicketDAO.updateTicketClosingDetailsByAdmin(
                ticketID,
                ticketCategory_CB.getValue().getCategoryID(),
                ticketStatus_CB.getValue(),
                ticketPriority_CB.getValue(),
                DateTimeUtils.getYearMonthDayFormat()
        );

    }

    @FXML
    private void insertMessageToHistory() throws SQLException {
        //record the message response to the message history table and append the agent name who responded to it

        //Incase an agent forgets to assign the to themselves it will automatically assume the the name of the agent who is logged in
        //To help the user know who responded to the ticket and help responding agent to know who responded to the ticket
        if (ticketInfolist.get(0).getAgentFullName() != null){
            MessageHistoryDAO.recordMessage(ticketID,
                    responseDetails.getText().concat( "\n\n" + ticketInfolist.get(0).getAgentFullName()),
                    DateTimeUtils.getCurrentDateTime());
        } else{
            MessageHistoryDAO.recordMessage(ticketID,
                    responseDetails.getText().concat( "\n\n" + CurrentLoggedUserHandler.getCurrentLoggedAdminName()),
                    DateTimeUtils.getCurrentDateTime());
        }

    }



    @FXML
    private void insertAttachmentToHistory(){

        if (attachmentListView != null){
            for (String filePath : filePaths) {
                TicketAttachmentDAO.insertAttachment(ticketID,filePath, DateTimeUtils.getYearMonthDayFormat());
            }
        }
    }


    @FXML
    private void sendTicketUpdateEmail() {
        GMailUtils.sendEmailTo(ticketInfolist.get(0).getUserEmail(),
                "Call In Progress: SD" +  ticketID,
                GMailUtils.generateResponseEmailBody(ticketID,
                        ticketInfolist.get(0).getUserFullName(),
                        ticketInfolist.get(0).getTicketTitle(),
                        responseDetails.getText()
                )
        );
    }





    @FXML
    private void sendCloseTicketEmail(){
        GMailUtils.sendEmailTo(ticketInfolist.get(0).getUserEmail(),
                "Ticket Closed: SD" +  ticketID,
                GMailUtils.generateCloseTicketEmailBody(ticketID,
                        ticketInfolist.get(0).getUserFullName(),
                        ticketInfolist.get(0).getTicketTitle(),
                        responseDetails.getText()
                ));
    }

    @FXML
    private void closeWindow(){
        backBtn.getScene().getWindow().hide();
    }

    private final ObservableList<String> filePaths = FXCollections.observableArrayList();
    @FXML
    private void addAttachment(){
        // Instantiate the attachment handler
        AttachmentUtils.addAttachments(filePaths,attachmentListView);
    }


    @FXML
    private void deleteAttachment(){
        // Instantiate the attachment handler
        AttachmentUtils.deleteAttachments(attachmentListView);
    }

    @FXML
    private void test(){
        System.out.println(ticketInfolist.get(0).getAgentFullName());
        System.out.println(ticketInfolist.get(0).getUserFullName());
    }

    private ObservableList<TicketModel>ticketInfolist;
    @FXML
    public void loadTicketInfo(int ticketID){


        ticketInfolist = TICKET_DAO.getShortenedTicketInformation(ticketID);

        if(!ticketInfolist.isEmpty()){
            this.ticketID = ticketInfolist.get(0).getTicketID();
            System.out.println("This is the ticket ID " + ticketID);

            if (ticketInfolist.get(0).getTicketStatus().equals("Created")){
                ticketStatus_CB.setValue("In Progress");
            } else {
                ticketStatus_CB.setValue(ticketInfolist.get(0).getTicketStatus());
            }
            ticketDateHolder_lbl.setText(DateTimeUtils.setTargetResolutionDate(ticketInfolist.get(0).getDateCreated()));
            ticketPriority_CB.setValue(ticketInfolist.get(0).getTicketPriority());
            ticketTitle.setText(ticketInfolist.get(0).getTicketTitle());




            for (TicketCategoryModel category : ticketCategory_CB.getItems()) {
                if (category.getCategoryID() == ticketInfolist.get(0).getCategoryID()) {
                    ticketCategory_CB.setValue(category);
                    break;
                }
            }
        } else {
            System.out.println("Ticket not found");
        }
    }

    @FXML
    private void openFile(String path) {
        AttachmentUtils.openAttachment(path);
    }



    @FXML

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<TicketCategoryModel> categoryModelList = TICKET_CATEGORY_DAO.getAllCategories();
        ticketCategory_CB.getItems().addAll(categoryModelList);


        ticketStatus_CB.setItems(FXCollections.observableArrayList("In Progress", "Awaiting Response", "Closed"));
        ticketPriority_CB.setItems(FXCollections.observableArrayList("Low", "Medium", "High"));




        attachmentCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                attachmentTitlePane.setDisable(false);
            } else {
                attachmentTitlePane.setExpanded(false);
                attachmentTitlePane.setDisable(true);

                attachmentListView.getItems().clear();


            };
        });

        ticketStatus_CB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Closed".equals(newValue)) {
                closeResponse_btn.setVisible(true);
                ticketDateTitle_lbl.setText("Closing Date");
                ticketDateHolder_lbl.setText(DateTimeUtils.getYearMonthDayFormat());
                sendBtn.setVisible(false);
            } else {
                sendBtn.setVisible(true);
                ticketDateTitle_lbl.setText("Target Resolution");
                ticketDateHolder_lbl.setText(DateTimeUtils.setTargetResolutionDate(ticketInfolist.get(0).getDateCreated()));
                closeResponse_btn.setVisible(false);
            }
        });

        attachmentListView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                String selectedItem = attachmentListView.getSelectionModel().getSelectedItem();
                openFile(selectedItem);

            }
        });




    }
}
