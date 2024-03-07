package com.example.fyp_application.Controllers.Admin.RequestManagementControllers;

import com.example.fyp_application.Model.*;
import com.google.api.client.util.Strings;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewTicketController implements Initializable {

    @FXML
    private Button closeMenu_btn;

    @FXML
    private Button editProfile_btn11;

    @FXML
    private TextField email_TF1;

    @FXML
    private TextField email_TF11;

    @FXML
    private Button exitApp_btn;

    @FXML
    private TextField firstName_TF1;

    @FXML
    private TextField firstName_TF11;

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
    private TextField lastName_TF1;

    @FXML
    private TextField lastName_TF11;

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
    private TableView<?> messageHistoryTable;

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
    private TextField username_TF1;

    @FXML
    private TextField username_TF11;

    @FXML
    private Label username_lbl;


    @FXML
    private TableView<TicketAttachmentModel> attachmentsTable;

    @FXML
    private TableColumn<?, ?> dateAdded_col;

    @FXML
    private TableColumn<?, ?> filePath_col;

    //to store the ticket ID to perform sql operations


    private  int ticketID;

    private static final TicketAttachmentDAO TICKET_ATTACHMENT_DAO = new TicketAttachmentDAO();

/*
    @FXML
    public void loadTicketDetails(TicketModel ticketModel) {

        ticketTitleHolder_lbl.setText(ticketModel.getTicketTitle());
        ticketDescriptionHolder_TA.setText(ticketModel.getTicketDescription());


        knowledgeBaseTitleHolder_lbl.setText(ticketModel.getCategoryName());
        knowledgeBaseDescriptionHolder_TA.setText(ticketModel.getKnowledgeBaseInfo());
    }
*/


    private ObservableList<TicketModel> ticketModelObservableList;

    @FXML
    public void sample(int ticketID){
        TicketDAO ticketDAO = new TicketDAO();
        ticketModelObservableList  = ticketDAO.getTicketDetails(ticketID);

        this.ticketID = ticketModelObservableList.get(0).getTicketID();

        ticketTitleHolder_lbl.setText("Subject:" + ticketModelObservableList.get(0).getTicketTitle());
        ticketDescriptionHolder_TA.setText(ticketModelObservableList.get(0).getTicketDescription());
        knowledgeBaseTitleHolder_lbl.setText("Category: " + ticketModelObservableList.get(0).getCategoryName());
        knowledgeBaseDescriptionHolder_TA.setText(ticketModelObservableList.get(0).getKnowledgeBaseInfo());

        System.out.println(ticketID);

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



    @FXML
    private void openFilePath(String path) throws IOException {
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
    private void closeMenu() {
        closeMenu_btn.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(this::loadAttachmentTable);

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
    }
}
