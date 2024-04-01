package com.example.fyp_application.Controllers.Admin.RequestManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.AlertNotificationUtils;
import com.example.fyp_application.Utils.AttachmentUtils;
import com.example.fyp_application.Utils.DateTimeUtils;
import com.example.fyp_application.Utils.GMailUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.fyp_application.Controllers.Admin.DashboardControllers.AdminDashboardWindowController.photoPath;
import static com.example.fyp_application.Controllers.Admin.DashboardControllers.AdminDashboardWindowController.userID;
import static com.example.fyp_application.Controllers.Admin.DashboardControllers.AdminDashboardWindowController.name;

public class RaiseTicketController implements Initializable {

    @FXML
    private Button addAttachment_btn;

    @FXML
    private Button backBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField dept_TF;

    @FXML
    private TextField email_TF;

    @FXML
    private Button exitApp_btn;

    @FXML
    private TextField firstName_TF;

    @FXML
    private AnchorPane headerAP;

    @FXML
    private TextField lastName_TF;

    @FXML
    private Label lastUpdateTime_lbl;

    @FXML
    private Circle loggedUserImage;

    @FXML
    private AnchorPane mainContentAnchorPane;

    @FXML
    private Button minimizeApp_btn;

    @FXML
    private Button openProfileBtn;

    @FXML
    private TextField phone_TF;

    @FXML
    private Button refreshHeader_btn;

    @FXML
    private Button removeAttachment_btn;

    @FXML
    private TextField searchBar_TF;

    @FXML
    private Button sendBtn;

    @FXML
    private TextArea ticketDetails;

    @FXML
    private TextField ticketTitle;

    @FXML
    private ComboBox<UserModel> userComboBox;

    @FXML
    private TextField username_TF;

    @FXML
    private Label username_lbl;

    @FXML
    private TitledPane attachmentTitlePane;

    @FXML
    private CheckBox attachmentCheckbox;

    @FXML
    private ListView<String> attachmentListView;


    //Default Ticket Category - Uncategorized
    private static final int ticketCategory = 4;

    private static final TicketDAO TICKET_DAO = new TicketDAO();

    @FXML
    private void raiseNewTicket() throws Exception {
        if (isEmptyFields()) {
            AlertNotificationUtils.showErrorMessageAlert("Empty Fields", "Please fill in all the fields");
            return;
        }

        UserModel selectedUser = userComboBox.getSelectionModel().getSelectedItem();
        int userID = selectedUser.getUserID();
        String title = ticketTitle.getText();
        String details = ticketDetails.getText();

        // Open a new ticket request
        int ticketID = TICKET_DAO.openUserTicketRequest(

                // Inserting the ticket details into the database - ticketCategory is assumed to be uncategorized/others as default option
                userID,
                ticketCategory,
                title,
                details,
                "Created",
                "Low",
                DateTimeUtils.getYearMonthDayFormat()
        );

        // Check if the attachments ListView is not null and has items
        if (attachmentListView != null && !attachmentListView.getItems().isEmpty()) {
            // Iterate over the attachments and queue them for insertion
            for (String filePath : attachmentListView.getItems()) {
                // Assuming you have a method to insert just the file path and ticket ID into the database
                TicketAttachmentDAO.insertAttachment(ticketID, filePath, DateTimeUtils.getYearMonthDayFormat());
            }

        }

        // Send an email notification
        sendEmailNotification(ticketID, firstName_TF.getText(), title, details);

        // Show a confirmation alert
        AlertNotificationUtils.showInformationMessageAlert("Ticket Raised", "Your ticket has been raised successfully");

        // Close the window
        closeWindow();
    }

    // Dummy method to represent sending an email notification
    private void sendEmailNotification(int ticketID,String firstname, String title, String details) throws Exception {
        // Implement the email sending logic here

       GMailUtils.sendEmailTo(email_TF.getText(), "Call Logged SD " + ticketID , GMailUtils.generateTicketRequestEmailBody(ticketID, firstname, title, details));

    }

    // Dummy method to close the window
    private void closeWindow() {
        // Implement the window closing logic here, e.g., get a handle to the stage and call close()
        cancelBtn.getScene().getWindow().hide();
    }


    @FXML
    private boolean isEmptyFields() {
            // Check if the ticket title, details, and user combo box are empty
        return ticketTitle.getText().isEmpty() || ticketDetails.getText().isEmpty() || userComboBox.getSelectionModel().isEmpty();
    }


    @FXML
    private void closeMenu(){

        backBtn.getScene().getWindow().hide();
        cancelBtn.getScene().getWindow().hide();
    }


    private final ObservableList<String> filePaths = FXCollections.observableArrayList();
    @FXML
    private void addAttachment() {
/*        // Create a FileChooser object
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
            AlertNotificationUtils.showInformationMessageAlert("Action Aborted", "No file selected");
        }

        // This will reset your ListView's items every time. If you want to accumulate files, you should update the list, not reset it.
        attachmentListView.setItems(filePaths);*/

        AttachmentUtils.addAttachments(filePaths,attachmentListView);
    }



    @FXML
    private void deleteAttachment(){

        //attachmentListView.getItems().remove(attachmentListView.getSelectionModel().getSelectedItem());
        AttachmentUtils.deleteAttachments(attachmentListView);
    }


    @FXML
    private void openFile(String path) {
        AttachmentUtils.openAttachment(path);
    }


    private  ObservableList<UserModel> allUsers = FXCollections.observableArrayList();
    private void setUserComboBox(){

        allUsers = UserDAO.getAllUsers();
        userComboBox.getItems().addAll(allUsers);
        // Define how to display the user information in the ComboBox
        userComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(UserModel user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty ? "" : user.getFirstName() + " " + user.getLastName());
            }
        });

        // Define how the selected name should be displayed in the input field
        userComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(UserModel user) {
                return user == null ? "" : user.getFullName();
            }

            @Override
            public UserModel fromString(String string) {
                return userComboBox.getItems().stream()
                        .filter(item -> item.getFullName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }


    @FXML
    private void userInformationListener(){
        userComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                UserModel selectedUser = userComboBox.getSelectionModel().getSelectedItem();
                username_TF.setText(selectedUser.getUsername());
                email_TF.setText(selectedUser.getEmail());
                phone_TF.setText(selectedUser.getPhone());
                firstName_TF.setText(selectedUser.getFirstName());
                lastName_TF.setText(selectedUser.getLastName());
                dept_TF.setText(selectedUser.getDeptName());
            }
        });
    }

    @FXML
    private void loadCurrentUser() {
        userID = CurrentLoggedUserHandler.getCurrentLoggedAdminID();
        name = CurrentLoggedUserHandler.getCurrentLoggedAdminFullName();
        photoPath = CurrentLoggedUserHandler.getCurrentLoggedAdminImagePath();


        username_lbl.setText(name);
        javafx.scene.image.Image curPhoto = new Image(Objects.requireNonNull(getClass().getResourceAsStream(photoPath)));
        loggedUserImage.setFill(new ImagePattern(curPhoto));
        lastUpdateTime_lbl.setText("Last refreshed: " + DateTimeUtils.getCurrentTimeFormat());
    }

    @FXML
    private void searchUserInformation(){
        searchBar_TF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                userComboBox.setItems(allUsers); // Reset to show all data
                return;
            }
            ObservableList<UserModel> filteredList = FXCollections.observableArrayList();
            for (UserModel userModel : allUsers) {
                if (userModel.getFullName().toLowerCase().contains(newValue.toLowerCase())) {
                    filteredList.add(userModel);

                }
            }
            userComboBox.setItems(filteredList);
        });
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserComboBox();
        userInformationListener();
        searchUserInformation();
        Platform.runLater(this::loadCurrentUser);

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
                openFile(selectedItem);

                }
            });

    }
}
