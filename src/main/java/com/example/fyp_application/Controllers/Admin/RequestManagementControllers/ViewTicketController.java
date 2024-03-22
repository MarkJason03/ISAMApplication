package com.example.fyp_application.Controllers.Admin.RequestManagementControllers;

import com.example.fyp_application.Controllers.Shared.MessageBoxController;
import com.example.fyp_application.Model.*;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.AttachmentUtils;
import com.example.fyp_application.Utils.DateTimeUtils;
import com.example.fyp_application.Utils.GMailUtils;
import com.example.fyp_application.Views.ViewConstants;
import com.google.api.client.util.Strings;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewTicketController implements Initializable {


    @FXML
    private TextField ticketStatus_TF;
    @FXML
    private TextField ticketCategory_TF;
    @FXML
    private TextField ticketPriority_TF;
    @FXML
    private TextField agentName_TF;
    @FXML
    private TextField createdDate_TF;
    @FXML
    private TextField targetResolution_TF;
    @FXML
    private TextField lastName_TF;
    @FXML
    private TextField firstName_TF;
    @FXML
    private TextField username_TF;
    @FXML
    private TextField email_TF;
    @FXML
    private TextField phone_TF;
    @FXML
    private TextField department_TF;
    @FXML
    private Button assignToMyself_btn;
    @FXML
    private Button closeMenu_btn;

    @FXML
    private Button editProfile_btn11;


    @FXML
    private Button exitApp_btn;


    @FXML
    private Label fullNameHolder_lbl1;

    @FXML
    private Label fullNameHolder_lbl11;

    @FXML
    private AnchorPane headerAP;

    @FXML
    private TextArea knowledgeBaseDescriptionHolder_TA;

    @FXML
    private Label knowledgeBaseTitleHolder_lbl;


    @FXML
    private Label lastUpdateTime_lbl;

    @FXML
    private Circle loggedUserImage;

    @FXML
    private AnchorPane mainContentAnchorPane;

    @FXML
    private TableColumn<?, ?> messageBody_col;

    @FXML
    private TableColumn<?, ?> messageDate_col;

    @FXML
    private TableColumn<?, ?> messageID_col;
    @FXML
    private TableView<MessageHistoryModel> messageHistoryTable;

    @FXML
    private Button minimizeApp_btn;

    @FXML
    private Button quickClose_btn;

    @FXML
    private Button refreshHeader_btn;

    @FXML
    private Button respondButton;

    @FXML
    private TableColumn<?, ?> senderName_col;

    @FXML
    private TextArea ticketDescriptionHolder_TA;

    @FXML
    private Label ticketTitleHolder_lbl;


    @FXML
    private Label username_lbl;


    @FXML
    private TableView<TicketAttachmentModel> attachmentsTable;

    @FXML
    private TableColumn<?, ?> dateAdded_col;

    @FXML
    private TableColumn<?, ?> filePath_col;

    @FXML
    private AnchorPane mainAP;

    //to store the ticket ID to perform sql operations


    private int ticketID;
    private int agentID;

    private int requesterID;

    private static final MessageHistoryDAO MESSAGE_HISTORY_DAO = new MessageHistoryDAO();
    private static final TicketAttachmentDAO TICKET_ATTACHMENT_DAO = new TicketAttachmentDAO();

    private static final TicketDAO TICKET_DAO = new TicketDAO();
/*
    @FXML
    public void loadTicketDetails(TicketModel ticketModel) {

        ticketTitleHolder_lbl.setText(ticketModel.getTicketTitle());
        ticketDescriptionHolder_TA.setText(ticketModel.getTicketDescription());


        knowledgeBaseTitleHolder_lbl.setText(ticketModel.getCategoryName());
        knowledgeBaseDescriptionHolder_TA.setText(ticketModel.getKnowledgeBaseInfo());
    }
*/


    private ObservableList<TicketModel> ticketInformationArray;

    @FXML
    public void loadTicketInfo(int ticketID) throws SQLException {
        TicketDAO ticketDAO = new TicketDAO();
        ticketInformationArray = ticketDAO.getFullTicketDetails(ticketID);

        this.ticketID = ticketInformationArray.get(0).getTicketID();
        this.agentID = ticketInformationArray.get(0).getAgentID();
        System.out.println("Current Agent ID: "+ agentID);
        this.requesterID = ticketInformationArray.get(0).getUserID();

        UserModel userInformationArray = UserDAO.loadCurrentLoggedUser(requesterID);
        ticketTitleHolder_lbl.setText("Subject:" + ticketInformationArray.get(0).getTicketTitle());
        ticketDescriptionHolder_TA.setText(ticketInformationArray.get(0).getTicketDescription());
        knowledgeBaseTitleHolder_lbl.setText("Category: " + ticketInformationArray.get(0).getCategoryName());
        knowledgeBaseDescriptionHolder_TA.setText(ticketInformationArray.get(0).getKnowledgeBaseInfo());

        System.out.println("Current Ticket ID: " + ticketID);

        ticketStatus_TF.setText(ticketInformationArray.get(0).getTicketStatus());
        ticketCategory_TF.setText(ticketInformationArray.get(0).getCategoryName());
        ticketPriority_TF.setText(ticketInformationArray.get(0).getTicketPriority());
        agentName_TF.setText(ticketInformationArray.get(0).getAgentFullName());
        createdDate_TF.setText(ticketInformationArray.get(0).getDateCreated());
        targetResolution_TF.setText(ticketInformationArray.get(0).getTargetResolutionDate());


        firstName_TF.setText(userInformationArray.getFirstName());
        lastName_TF.setText(userInformationArray.getLastName());
        email_TF.setText(userInformationArray.getEmail());
        phone_TF.setText(userInformationArray.getPhone());
        username_TF.setText(userInformationArray.getUsername());
        department_TF.setText(userInformationArray.getDeptName());



    }


    @FXML
    private void quickClose() throws Exception {
        //Todo - experimental to try and close the call and
        TICKET_DAO.quickCloseTicket(ticketID,
                ticketInformationArray.get(0).getCategoryID(),
                "Closed",
                "Low",
                DateTimeUtils.setTargetResolutionDate(ticketInformationArray.get(0).getDateCreated()),
                DateTimeUtils.getYearMonthDayFormat()
        );

        GMailUtils.sendEmailTo(ticketInformationArray.get(0).getUserEmail(),
                "Call Closed: SD" +  ticketID,
                GMailUtils.generateAutoCloseEmailBody(ticketID,
                        ticketInformationArray.get(0).getUserFullName(),
                        ticketInformationArray.get(0).getTicketTitle(),
                        "Auto Resolution"
                )
        );


        quickClose_btn.getScene().getWindow().hide();

    }




    private ObservableList<TicketAttachmentModel> attachmentList;
    @FXML
    private void loadAttachmentTable(){
        try {
            attachmentList = TICKET_ATTACHMENT_DAO.loadTicketAttachments(ticketID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        dateAdded_col.setCellValueFactory(new PropertyValueFactory<>("dateUploaded"));
        filePath_col.setCellValueFactory(new PropertyValueFactory<>("filePath"));


        attachmentsTable.setItems(attachmentList);
    }


    private  ObservableList<MessageHistoryModel> messageHistoryList;
    @FXML
    private void loadMessageHistory(){
        messageHistoryList = MESSAGE_HISTORY_DAO.getMessageHistory(ticketID);


        messageID_col.setCellValueFactory(new PropertyValueFactory<>("messageID"));
        messageBody_col.setCellValueFactory(new PropertyValueFactory<>("messageBody"));
        messageDate_col.setCellValueFactory(new PropertyValueFactory<>("dateSent"));

        messageHistoryTable.setItems(messageHistoryList);

    }
    @FXML
    private void actionTicket() throws SQLException {
        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) mainAP.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage



        if(agentID == 0){
            System.out.println("Assigning Ticket to Current Logged Agent");
            assignTicketToCurrentAgent();
        }


        try {
            //modal pop-up dialogue box
            FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_ACTION_TICKET_POP_UP));
            Parent root = modalViewLoader.load();


            ActionTicketController actionTicketController = modalViewLoader.getController();
            actionTicketController.loadTicketInfo(ticketID);


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
            loadTicketInfo(ticketID);

            Platform.runLater(this::loadAttachmentTable);
            Platform.runLater(this::loadMessageHistory);

        }



    };


    @FXML
    private void editTicketDetails() throws SQLException {
        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) mainAP.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage



        if(agentID == 0){
            System.out.println("Assigning Ticket to Current Logged Agent");
            assignTicketToCurrentAgent();
        }


        try {
            //modal pop-up dialogue box
            FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.MODIFY_TICKET_DETAILS_POP_UP));
            Parent root = modalViewLoader.load();


            ModifyCallDetailsController modifyCallDetailsController = modalViewLoader.getController();
            modifyCallDetailsController.loadTicketDetails(ticketID);


            // New window setup as modal
            Stage editTicketDetailsModalWindow = new Stage();
            editTicketDetailsModalWindow.initOwner(currentDashboardStage);
            editTicketDetailsModalWindow.initModality(Modality.WINDOW_MODAL);
            editTicketDetailsModalWindow.initStyle(StageStyle.TRANSPARENT);


            Scene scene = new Scene(root);
            editTicketDetailsModalWindow.setScene(scene);

            editTicketDetailsModalWindow.showAndWait(); // Blocks interaction with the main stage

        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close
            loadTicketInfo(ticketID);

            Platform.runLater(this::loadAttachmentTable);
            Platform.runLater(this::loadMessageHistory);

        }



    };

    @FXML
    private void openFilePath(String path) throws IOException {
        AttachmentUtils.openAttachment(path);
    }



    @FXML
    private void assignTicketToCurrentAgent(){

        TicketDAO.assignTicketToAgent(ticketID, CurrentLoggedUserHandler.getCurrentLoggedAdminID());


        Platform.runLater(this::loadAttachmentTable);
        Platform.runLater(this::loadMessageHistory);

    }

    @FXML
    private void openMessageBox(int messageID) throws SQLException {
        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) mainAP.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage

        try {
            //modal pop-up dialogue box
            FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.SHARED_MESSAGE_HISTORY_BOX_POP_UP));
            Parent root = modalViewLoader.load();


            MessageBoxController messageBoxController = modalViewLoader.getController();
            messageBoxController.loadMessage(messageID);


            // New window setup as modal
            Stage messageBoxPopUp = new Stage();
            messageBoxPopUp.initOwner(currentDashboardStage);
            messageBoxPopUp.initModality(Modality.WINDOW_MODAL);
            messageBoxPopUp.initStyle(StageStyle.TRANSPARENT);


            Scene scene = new Scene(root);
            messageBoxPopUp.setScene(scene);

            messageBoxPopUp.showAndWait(); // Blocks interaction with the main stage

        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close
            loadTicketInfo(ticketID);

            Platform.runLater(this::loadAttachmentTable);
            Platform.runLater(this::loadMessageHistory);
        }
    }
    @FXML
    private void closeMenu() {
        closeMenu_btn.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(this::loadAttachmentTable);

        Platform.runLater(this::loadMessageHistory);

        attachmentsTable.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                String selectedItem = attachmentsTable.getSelectionModel().getSelectedItem().getFilePath();
                try {
                    openFilePath(selectedItem);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (!Strings.isNullOrEmpty(selectedItem)) {
                    System.out.println("Selected Item: " + selectedItem);
                }
            }
        });

        messageHistoryTable.setOnMouseClicked(mouseEvent -> {


            if (mouseEvent.getClickCount() == 2) {
                int selectedItem = messageHistoryTable.getSelectionModel().getSelectedItem().getMessageID();
                System.out.println(selectedItem);
                try {
                    openMessageBox(selectedItem);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
