package com.example.fyp_application.Controllers.Admin.RequestManagementControllers;

import com.example.fyp_application.Model.TicketDAO;
import com.example.fyp_application.Model.TicketModel;
import com.example.fyp_application.Model.UserDAO;
import com.example.fyp_application.Model.UserModel;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class AssignCallController implements Initializable {

    @FXML
    private Button close_Btn;

    @FXML
    private TextArea messageDetails;

    @FXML
    private Button send_btn;

    @FXML
    private ComboBox<UserModel> userComboBox;

    @FXML
    private TextField userSearchBar_TF;


    @FXML
    private void closeWindow() {
        SharedButtonUtils.closeMenu(close_Btn);
    }


    @FXML
    private void setupUserComboBox() {
        // Set the user combo box with the users in the database
        ObservableList<UserModel> allAdmins;
        allAdmins = UserDAO.getAllAgents();
        userComboBox.getItems().addAll(allAdmins);
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
    private void searchUser() {
        // Search for the user in the database

        SearchBarListenerUtils.userSearchBarListener(userSearchBar_TF, userComboBox, UserDAO.getAllAgents());
    }


    @FXML
    private ObservableList<TicketModel> ticketInfo = FXCollections.observableArrayList();

    public void loadTicketDetails(int ticketID) {
        ticketInfo = TicketDetailsUtils.loadShortTicketDetails(ticketID);
    }




    @FXML
    private void startAgentAssignmentThread() {

        if (!userComboBox.getSelectionModel().isEmpty() && !messageDetails.getText().isEmpty()) {
            // Start the thread to assign the ticket to the selected agent

            Task<Void> assignAgent = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    // Insert the ticket details changes to the database
                    System.out.println("Updating ticket details");
                    saveAgentAssignmentChanges();
                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    sendEmail(); // proceed to insert the message response to the message history table
                    AlertNotificationUtils.showInformationMessageAlert("Ticket Assigned", "The ticket has been assigned to the selected agent.");
                    closeWindow();
                }

                @Override
                protected void failed() {
                    super.failed();
                }
            };

            new Thread(assignAgent).start();
        } else{

            AlertNotificationUtils.showErrorMessageAlert("Error Assigning Agent", "Please select an agent and provide a message");
        }
    }
    @FXML
    private void saveAgentAssignmentChanges() {
        // Save the changes to the database
        TicketDAO.assignTicketToAgent(ticketInfo.get(0).getTicketID(), userComboBox.getSelectionModel().getSelectedItem().getUserID());

    }

    @FXML
    private void sendEmail() {
        // Send the message to the user
        GMailUtils.sendEmailTo(userComboBox.getSelectionModel().getSelectedItem().getEmail(),
                "Ticket Assignment", GMailUtils.generateAssignTicketEmailBody(
                        ticketInfo.get(0).getTicketID(),
                        ticketInfo.get(0).getAgentFullName(),
                        ticketInfo.get(0).getTicketTitle(),
                        messageDetails.getText(),
                        CurrentLoggedUserHandler.getCurrentLoggedAdminFullName())
                );
    }


/*
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
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUserComboBox();
        searchUser();

    }
}
