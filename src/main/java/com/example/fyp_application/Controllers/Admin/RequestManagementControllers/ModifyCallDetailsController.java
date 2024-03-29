package com.example.fyp_application.Controllers.Admin.RequestManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ModifyCallDetailsController implements Initializable  {

    @FXML
    private ComboBox<UserModel> agentList_CB;

    @FXML
    private ComboBox<TicketCategoryModel> category_CB;

    @FXML
    private Button closeMenu_btn;

    @FXML
    private TextField department_TF;

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
    private AnchorPane mainContentAnchorPane;


    @FXML
    private CheckBox ticketTitle_checkbox;

    @FXML
    private ComboBox<TicketCategoryModel>presetTitle_CB;

    @FXML
    private TextField phone_TF;

    @FXML
    private Button refreshHeader_btn;

    @FXML
    private ComboBox<String> ticketPriority_CB;

    @FXML
    private ComboBox<String> ticketStatus_CB;

    @FXML
    private ComboBox<UserModel> userList_CB;

    @FXML
    private TextField userSearchBar_TF;

    @FXML
    private TextField username_TF;

    @FXML
    private TextField ticketTitle_TF;

    private static final TicketCategoryDAO TICKET_CATEGORY_DAO = new TicketCategoryDAO();

    @FXML
    private void closeWindow() {
        // Close the menu
        SharedButtonUtils.closeMenu(closeMenu_btn);
        SharedButtonUtils.closeMenu(exitApp_btn);
    }

    @FXML
    private ObservableList<TicketModel> ticketInfo = FXCollections.observableArrayList();

    public void loadTicketDetails(int ticketID) {
        ticketInfo = TicketDetailsUtils.loadFullTicketDetails(ticketID);
    }

/*
    private void searchUser() {
        // Search for the user in the database
        UserDetailsUtils.userSearchBarListener(userSearchBar_TF, userList_CB, UserDetailsUtils.getAllUsers());
    }
    */

    @FXML
    private void setTicketDetails(){
        // Set the ticket details

        ticketTitle_TF.setText(ticketInfo.get(0).getTicketTitle());
        ticketStatus_CB.setValue(ticketInfo.get(0).getTicketStatus());
        ticketPriority_CB.setValue(ticketInfo.get(0).getTicketPriority());

        for(TicketCategoryModel category : category_CB.getItems()){
            if(category.getCategoryID() == ticketInfo.get(0).getCategoryID()){
                category_CB.setValue(category);
                break;
            }
        }

        for(UserModel agent : agentList_CB.getItems()){
            if(agent.getUserID() == ticketInfo.get(0).getAgentID()){
                agentList_CB.setValue(agent);
                break;
            }
        }

        for (UserModel user : userList_CB.getItems()) {
            if (user.getUserID() == ticketInfo.get(0).getUserID()) {
                userList_CB.setValue(user);
                break;
            }
        }


    }

    private void setUserDetails(){
        // Set the user details
        UserDetailsUtils.setUserDetails(userList_CB, firstName_TF, lastName_TF, email_TF, phone_TF, username_TF, department_TF);
    }


    public List<TitleCategoryModel> getSelectedCategoryTitles(int categoryID) {
        String sql = "select * from tbl_categoryTitlePresets where ticketCategoryID = ?";
        return DatabaseConnectionUtils.executeQuery(sql, resultSet -> {
            try {
                return new TitleCategoryModel(
                        resultSet.getInt("titleID"),
                        resultSet.getInt("ticketCategoryID"),
                        resultSet.getString("TitleName")
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, categoryID);
    }

    @FXML
    private void saveChanges(){

        if (ticketTitle_TF.getText().isEmpty()){
            AlertNotificationUtils.showInformationMessageAlert("Error", "Ticket title cannot be empty");
            return;
        }

        if(category_CB.getSelectionModel().getSelectedItem().getCategoryName().equals("Others")
                && !ticketTitle_TF.getText().isEmpty()
        ){
            //save the changes to the database
            TicketDAO.recategorizeTicketDetails(
                    userList_CB.getSelectionModel().getSelectedItem().getUserID(),
                    agentList_CB.getSelectionModel().getSelectedItem().getUserID(),
                    category_CB.getSelectionModel().getSelectedItem().getCategoryID(),
                    ticketTitle_TF.getText(),
                    ticketStatus_CB.getSelectionModel().getSelectedItem(),
                    ticketPriority_CB.getSelectionModel().getSelectedItem(),
                    ticketInfo.get(0).getTicketID()

            );
            AlertNotificationUtils.showInformationMessageAlert("Success", "Ticket details updated successfully");
            closeWindow();
        } else {
            //save the changes to the database
            TicketDAO.recategorizeTicketDetails(
                    userList_CB.getSelectionModel().getSelectedItem().getUserID(),
                    agentList_CB.getSelectionModel().getSelectedItem().getUserID(),
                    category_CB.getSelectionModel().getSelectedItem().getCategoryID(),
                    presetTitle_CB.getSelectionModel().getSelectedItem().getTitleName(),
                    ticketStatus_CB.getSelectionModel().getSelectedItem(),
                    ticketPriority_CB.getSelectionModel().getSelectedItem(),
                    ticketInfo.get(0).getTicketID()

            );
            AlertNotificationUtils.showInformationMessageAlert("Success", "Ticket details updated successfully");
            closeWindow();
        }

    }

    @FXML

    private void setupComboBoxes(){
        UserDetailsUtils.setAgentComboBox(agentList_CB);
        UserDetailsUtils.setupUserComboBox(userList_CB);

        TicketDetailsUtils.setTicketPriorityComboBox(ticketPriority_CB);
        TicketDetailsUtils.setTicketStatusComboBox(ticketStatus_CB);
    }

    @FXML
    private void setupCategoryTitleListener(){
        category_CB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.getCategoryName().equals("Others")){
                presetTitle_CB.getItems().clear();
                List<TitleCategoryModel> categoryTitles = getSelectedCategoryTitles(newValue.getCategoryID());
                presetTitle_CB.getItems().addAll(categoryTitles);
                presetTitle_CB.setValue(categoryTitles.get(0));
                presetTitle_CB.setVisible(true);

            } else {
                presetTitle_CB.getItems().clear();
                presetTitle_CB.setVisible(false);
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<TicketCategoryModel> categoryModelList = TICKET_CATEGORY_DAO.getAllCategories();
        category_CB.getItems().addAll(categoryModelList);



        //setup combo boxes
        setupComboBoxes();

        //set the title presets listener combo box;
        setUserDetails();
        setupCategoryTitleListener();



        //setTicketDetails();
        setUserDetails();
        Platform.runLater(this::setTicketDetails);
    }
}
