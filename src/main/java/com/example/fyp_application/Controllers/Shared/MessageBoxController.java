package com.example.fyp_application.Controllers.Shared;

import com.example.fyp_application.Model.MessageHistoryDAO;
import com.example.fyp_application.Model.MessageHistoryModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageBoxController {


    @FXML
    private Button close_Btn;

    @FXML
    private Label messageTimestamp_lbl;

    @FXML
    private TextArea responseDetails;

    @FXML
    private TextField ticketTitle;


    //Shared controller for loading individual message container for user and admin.
    private static final MessageHistoryDAO MESSAGE_HISTORY_DAO = new MessageHistoryDAO();


    public void loadMessage(int messageID){
        ObservableList<MessageHistoryModel> messageHistoryList = MESSAGE_HISTORY_DAO.getSpecificMessage(messageID);

        System.out.println(messageHistoryList.get(0).getMessageBody());

        ticketTitle.setText(messageHistoryList.get(0).getTicketTitle());
        messageTimestamp_lbl.setText(messageHistoryList.get(0).getDateSent());
        responseDetails.setText(messageHistoryList.get(0).getMessageBody());

    }

    @FXML
    private void closeWindow() {
        close_Btn.getScene().getWindow().hide();

    }

}
