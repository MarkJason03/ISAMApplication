package com.example.fyp_application.Controllers.Admin.RequestManagementControllers;

import com.example.fyp_application.Model.SupplierModel;
import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Model.UserModel;
import com.example.fyp_application.Utils.AlertNotificationHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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


    private static final AlertNotificationHandler ALERT_HANDLER = new AlertNotificationHandler();

    private static final UserDAO USER_DAO = new UserDAO();

    @FXML
    private void raiseNewTicket(){
        //TODO: Implement this method
        // This method should raise a new ticket and send it to the database

        if (isEmptyFields()) {
            ALERT_HANDLER.showErrorMessageAlert("Empty Fields", "Please fill in all the fields");
            return;
        } else {
            String title = ticketTitle.getText();
            String details = ticketDetails.getText();
            UserModel selectedUser = userComboBox.getSelectionModel().getSelectedItem();
            int userID = selectedUser.getUserID();
            String username = selectedUser.getUsername();
            String email = selectedUser.getEmail();
            String phone = selectedUser.getPhone();
            String firstName = selectedUser.getFirstName();
            String lastName = selectedUser.getLastName();
            String dept = selectedUser.getDeptName();

            System.out.println("Title: " + title);
            System.out.println("Details: " + details);
            System.out.println("User ID: " + userID);
            System.out.println("Username: " + username);
            System.out.println("Email: " + email);
            System.out.println("Phone: " + phone);
            System.out.println("First Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("Department: " + dept);



        }






    }



    @FXML
    private boolean isEmptyFields() {

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



    private  ObservableList<UserModel> allUsers = FXCollections.observableArrayList();
    private void setUserComboBox(){

        allUsers = UserDAO.getAllUsers();
        userComboBox.getItems().addAll(allUsers);
        // Define how to display the user information in the ComboBox
        userComboBox.setCellFactory(lv -> new ListCell<UserModel>() {
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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserComboBox();
        userInformationListener();
        searchUserInformation();

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
