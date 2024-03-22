package com.example.fyp_application.Utils;

import com.example.fyp_application.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.List;

public class TicketDetailsUtils {


    private static final TicketDAO TICKET_DAO = new TicketDAO();

    private static final TicketCategoryDAO TICKET_CATEGORY_DAO = new TicketCategoryDAO();

    private TicketDetailsUtils() {
        // Private constructor to hide the implicit public one
    }

    public static void displayTicketInformation(int ticketID, Label ticketTitleHolder_lbl, TextArea ticketDescriptionHolder_TA){
        ObservableList<TicketModel> ticketInformationArray = TICKET_DAO.getFullTicketDetails(ticketID);

        if (!ticketInformationArray.isEmpty()) {
            ticketTitleHolder_lbl.setText("Subject: " + ticketInformationArray.get(0).getTicketTitle());
            ticketDescriptionHolder_TA.setText(ticketInformationArray.get(0).getTicketDescription());
        } else {
            // Handle the case where the list is empty
            // For example, you might want to show an error message to the user
            System.out.println("Ticket not found?");
        }

        System.out.println(ticketID);
    }


    public static ObservableList<TicketModel> loadShortTicketDetails(int ticketID){
        ObservableList<TicketModel> ticketInformationArray = TICKET_DAO.getFullTicketDetails(ticketID);

        if (!ticketInformationArray.isEmpty()) {
            return ticketInformationArray;
        } else {
            System.out.println("Ticket not found");
            return FXCollections.observableArrayList();
        }
    }


    public static ObservableList<TicketModel>loadFullTicketDetails(int ticketID) {
        ObservableList<TicketModel> ticketInformationArray = TICKET_DAO.getFullTicketDetails(ticketID);

        if (!ticketInformationArray.isEmpty()) {
            return ticketInformationArray;
        } else {
            System.out.println("Ticket not found");
            return FXCollections.observableArrayList();
        }
    }

    public static void setTicketDetails(ObservableList<TicketModel> ticketInfo, ComboBox<String> ticketStatusCb, ComboBox<String> ticketPriorityCb, ComboBox<TicketCategoryModel> categoryCb, ComboBox<UserModel> agentListCb) {
        ticketStatusCb.setValue(ticketInfo.get(0).getTicketStatus());
        ticketPriorityCb.setValue(ticketInfo.get(0).getTicketPriority());

        for (TicketCategoryModel category : categoryCb.getItems()) {
            if (category.getCategoryID() == ticketInfo.get(0).getCategoryID()) {
                categoryCb.setValue(category);
                break;
            }
        }

        for (UserModel agent : agentListCb.getItems()) {
            if (agent.getUserID() == ticketInfo.get(0).getAgentID()) {
                agentListCb.setValue(agent);
                break;
            }
        }
    }


    public static List<TicketCategoryModel> setTicketCategoryComboBox(ComboBox<TicketCategoryModel> ticketCategory_CB){
        List<TicketCategoryModel> categoryModelList = TICKET_CATEGORY_DAO.getAllCategories();
        ticketCategory_CB.getItems().addAll(categoryModelList);

        return categoryModelList;
    }


    public static  void setTicketStatusComboBox(ComboBox<String> ticketStatus_CB){
        ticketStatus_CB.setItems(FXCollections.observableArrayList("In Progress", "Awaiting Response", "Closed"));
    }

    public static void setTicketPriorityComboBox(ComboBox<String> ticketPriority_CB){
        ticketPriority_CB.setItems(FXCollections.observableArrayList("Low", "Medium", "High"));
    }

}
