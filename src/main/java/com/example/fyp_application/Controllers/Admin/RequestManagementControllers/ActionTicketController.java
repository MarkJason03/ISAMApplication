package com.example.fyp_application.Controllers.Admin.RequestManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.AttachmentHandler;
import com.example.fyp_application.Utils.DateTimeHandler;
import com.example.fyp_application.Utils.GMailHandler;
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
    private Label targetResolution_lbl;

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

    private static final TicketDAO TICKET_DAO = new TicketDAO();

    private static  final TicketCategoryDAO TICKET_CATEGORY_DAO = new TicketCategoryDAO();


    @FXML
    private  int ticketID;
    private int agentID;
    private int userID;


    @FXML
    private Button b1;



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


    @FXML
    private void cancelAction(){
        backBtn.getScene().getWindow().hide();
    }

    private final ObservableList<String> filePaths = FXCollections.observableArrayList();
    @FXML
    private void addAttachment(){
        // Instantiate the attachment handler
        AttachmentHandler.addAttachments(filePaths,attachmentListView);
    }


    @FXML
    private void deleteAttachment(){
        // Instantiate the attachment handler
        AttachmentHandler.deleteAttachments(attachmentListView);
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
            System.out.println(ticketID);

            ticketStatus_CB.setValue(ticketInfolist.get(0).getTicketStatus());
            targetResolution_lbl.setText(DateTimeHandler.dateParser(ticketInfolist.get(0).getDateCreated()));
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
        AttachmentHandler.openAttachment(path);
    }



    @FXML

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<TicketCategoryModel> categoryModelList = TICKET_CATEGORY_DAO.getAllCategories();
        ticketCategory_CB.getItems().addAll(categoryModelList);


        ticketStatus_CB.setItems(FXCollections.observableArrayList("Created", "In Progress", "Awaiting Response", "Closed"));
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

        attachmentListView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                String selectedItem = attachmentListView.getSelectionModel().getSelectedItem();
                openFile(selectedItem);

            }
        });




    }
}
