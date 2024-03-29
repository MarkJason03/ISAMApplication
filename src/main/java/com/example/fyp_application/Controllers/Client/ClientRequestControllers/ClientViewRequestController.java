package com.example.fyp_application.Controllers.Client.ClientRequestControllers;

import com.example.fyp_application.Controllers.Shared.MessageBoxController;
import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.AttachmentUtils;
import com.example.fyp_application.Utils.SharedButtonUtils;
import com.example.fyp_application.Utils.TableListenerUtils;
import com.example.fyp_application.Views.ViewConstants;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class ClientViewRequestController implements Initializable {

    @FXML
    private AnchorPane mainAP;
    @FXML
    private TableView<TicketAttachmentModel> attachmentTable;

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<?, ?> dateAdded_col;

    @FXML
    private Button exitApp_btn;

    @FXML
    private TableColumn<?, ?> fileName_col;

    @FXML
    private Label fullNameHolder_lbl11;

    @FXML
    private AnchorPane headerAP;

    @FXML
    private AnchorPane mainContentAnchorPane;

    @FXML
    private TableColumn<?, ?> messageBody_col;

    @FXML
    private TableColumn<?, ?> messageDate_col;

    @FXML
    private TableView<MessageHistoryModel> messageHistoryTable;

    @FXML
    private TableColumn<?, ?> messageID_col;

    @FXML
    private Button minimizeApp_btn;

    @FXML
    private Button refreshHeader_btn;

    @FXML
    private Button respondButton;

    @FXML
    private TextArea ticketDescriptionHolder_TA;

    @FXML
    private Label ticketTitleHolder_lbl;

    private int ticketID;
    private static final MessageHistoryDAO MESSAGE_HISTORY_DAO = new MessageHistoryDAO();
    private static final TicketAttachmentDAO TICKET_ATTACHMENT_DAO = new TicketAttachmentDAO();

    private static final TicketDAO TICKET_DAO = new TicketDAO();

    @FXML
    private void cancelAction() {
        SharedButtonUtils.closeMenu(backBtn);
    }

    @FXML
    public void loadTicketInformation(int ticketID){
        ObservableList<TicketModel> ticketInformationArray = TICKET_DAO.getFullTicketDetails(ticketID);

        if (!ticketInformationArray.isEmpty()) {
            this.ticketID = ticketInformationArray.get(0).getTicketID();
            ticketTitleHolder_lbl.setText("Subject: " + ticketInformationArray.get(0).getTicketTitle());
            ticketDescriptionHolder_TA.setText(ticketInformationArray.get(0).getTicketDescription());
        } else {
            // Handle the case where the list is empty
            // For example, you might want to show an error message to the user
            System.out.println("Ticket not found?");

        }

        System.out.println(ticketID);
    }

    @FXML
    private void loadAttachments(){

        ObservableList<TicketAttachmentModel> attachmentList;
        try {
            attachmentList = TICKET_ATTACHMENT_DAO.loadTicketAttachments(ticketID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        dateAdded_col.setCellValueFactory(new PropertyValueFactory<>("dateUploaded"));
        fileName_col.setCellValueFactory(new PropertyValueFactory<>("filePath"));
        attachmentTable.setItems(attachmentList);
    }


    @FXML
    private void loadTicketMessages(){
        ObservableList<MessageHistoryModel> messageHistoryList = MESSAGE_HISTORY_DAO.getMessageHistory(ticketID);

        messageID_col.setCellValueFactory(new PropertyValueFactory<>("messageID"));
        messageBody_col.setCellValueFactory(new PropertyValueFactory<>("messageBody"));
        messageDate_col.setCellValueFactory(new PropertyValueFactory<>("dateSent"));

        messageHistoryTable.setItems(messageHistoryList);

    }

    @FXML
    private void openMessageBox(int messageID){
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
            loadTicketInformation(ticketID);

            Platform.runLater(this::loadAttachments);
            Platform.runLater(this::loadTicketMessages);
        }
    }

    @FXML
    private void openAttachments(String path){
        AttachmentUtils.openAttachment(path);
    }

    @FXML
    private void respondToTicket(){
        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) mainAP.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage

        try {
            //modal pop-up dialogue box
            FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.CLIENT_RESPONSE_TICKET_POP_UP));
            Parent root = modalViewLoader.load();

            System.out.println(ticketID);

            ClientResponseActionController responseActionController = modalViewLoader.getController();
            responseActionController.loadTicketInfo(ticketID);


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
            loadTicketInformation(ticketID);

            Platform.runLater(this::loadAttachments);
            Platform.runLater(this::loadTicketMessages);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            loadTicketMessages();
            loadAttachments();
        });

        TableListenerUtils.addDoubleClickHandlerToAttachmentTable(attachmentTable, this::openAttachments);
        TableListenerUtils.addDoubleClickHandlerToMessageHistoryTable(messageHistoryTable, this::openMessageBox);
    }

}
