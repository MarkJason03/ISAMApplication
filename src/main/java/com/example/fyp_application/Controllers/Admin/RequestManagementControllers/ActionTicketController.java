package com.example.fyp_application.Controllers.Admin.RequestManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.AlertNotificationHandler;
import com.example.fyp_application.Utils.DateTimeHandler;
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
import javafx.stage.FileChooser;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    private ComboBox<TicketCategoryModel> ticketCategory_CB;

    @FXML
    private TextArea ticketDetails;

    @FXML
    private ComboBox<String> ticketPriority_CB;

    @FXML
    private ComboBox<String> ticketStatus_CB;

    @FXML
    private TextField ticketTitle;

    private static final TicketDAO TICKET_DAO = new TicketDAO();

    private static final AlertNotificationHandler ALERT_HANDLER = new AlertNotificationHandler();
    private static  final TicketCategoryDAO TICKET_CATEGORY_DAO = new TicketCategoryDAO();
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
    private void cancelAction(){
        backBtn.getScene().getWindow().hide();
    }

    private final ObservableList<String> filePaths = FXCollections.observableArrayList();
    @FXML
    private void addAttachment(){
        // Create a FileChooser object
        FileChooser fileChooser = new FileChooser();

        // Set the title of the FileChooser and extension filters
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Select Files", "*.*"));

        // Show the FileChooser and get the selected files
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null); // Let users select multiple files


        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            for (File file : selectedFiles) {
                filePaths.add(file.getAbsolutePath());
            }
        }

        else {
            // Show an error message if no file was selected
            ALERT_HANDLER.showInformationMessageAlert("Action Aborted", "No file selected");
        }

        // This will reset your ListView's items every time. If you want to accumulate files, you should update the list, not reset it.
        attachmentListView.setItems(filePaths);
    }


    @FXML
    private void deleteAttachment(){
        attachmentListView.getItems().remove(attachmentListView.getSelectionModel().getSelectedItem());

    }



    @FXML
    public void loadTicketInfo(int ticketID){

        ObservableList<TicketModel>ticketInfolist;
        ticketInfolist = TICKET_DAO.getShortenedTicketInformation(ticketID);



        for (TicketModel items : ticketInfolist){
            System.out.println(items);
        }
        System.out.println(ticketInfolist.get(0).getTicketStatus());
        ticketStatus_CB.setValue(ticketInfolist.get(0).getTicketStatus());
        targetResolution_lbl.setText(DateTimeHandler.dateParser(ticketInfolist.get(0).getDateCreated()));

        ticketTitle.setText(ticketInfolist.get(0).getTicketTitle());




        for (TicketCategoryModel category : ticketCategory_CB.getItems()) {
            if (category.getCategoryID() == ticketInfolist.get(0).getCategoryID()) {
                ticketCategory_CB.setValue(category);
                break;
            }}

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
        List<TicketCategoryModel> categoryModelList = TICKET_CATEGORY_DAO.getAllCategories();
        ticketCategory_CB.getItems().addAll(categoryModelList);


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
