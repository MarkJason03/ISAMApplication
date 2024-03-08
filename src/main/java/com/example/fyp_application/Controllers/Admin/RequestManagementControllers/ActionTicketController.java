package com.example.fyp_application.Controllers.Admin.RequestManagementControllers;

import com.example.fyp_application.Model.TicketModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    private Button cancelBtn;

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
    private ComboBox<?> ticketCategory_CB;

    @FXML
    private TextArea ticketDetails;

    @FXML
    private ComboBox<String> ticketPriority_CB;

    @FXML
    private ComboBox<String> ticketStatus_CB;

    @FXML
    private TextField ticketTitle;

    @FXML
    private void submitResponse(){

    }



    @FXML
    private void submitAttachment(){



    }

    @FXML
    private void quickClose(){
        //Todo - experimental to try and close the call and
    }




    @FXML
    private void addAttachment(){
    }


    @FXML
    private void deleteAttachment(){

    }


    @FXML
    private void loadTicketInfo(){

        ObservableList<TicketModel>ticketInfolist;


    }

    @FXML
    private void openFile(String path) {
        if (Desktop.isDesktopSupported()) {
            try {
                File file = new File(path);
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exceptions (file not found, no application associated with the file type, etc.)
            }
        }
    }



    @FXML

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ticketStatus_CB.setItems(FXCollections.observableArrayList("Created", "In Progress", "Closed"));
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
