package com.example.fyp_application.Model;

public class TicketModel {


    private int ticketID;

    private int userID;

    private int agentID;


    private int categoryID;

    private String ticketTitle;

    private String ticketDescription;

    private String ticketStatus;


    private String ticketPriority;

    private String dateCreated;

    private String dateClosed;

    private String targetResolutionDate;



    // Constructor for creating ticket - user side
    public TicketModel(
            int userID,
            int categoryID,
            String title,
            String description,
            String dateCreated){

        this.userID = userID;
        this.categoryID = categoryID;
        this.ticketTitle = title;
        this.ticketDescription = description;
        this.dateCreated = dateCreated;
    }

    // Constructor for creating ticket - agent side




    // getter methods


    public int getTicketID() {
        return ticketID;
    }

    public int getUserID() {
        return userID;
    }

    public int getAgentID() {
        return agentID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public String getTicketPriority() {
        return ticketPriority;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateClosed() {
        return dateClosed;
    }

    public String getTargetResolutionDate() {
        return targetResolutionDate;
    }


    // setter methods

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setAgentID(int agentID) {
        this.agentID = agentID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public void setTicketPriority(String ticketPriority) {
        this.ticketPriority = ticketPriority;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateClosed(String dateClosed) {
        this.dateClosed = dateClosed;
    }

    public void setTargetResolutionDate(String targetResolutionDate) {
        this.targetResolutionDate = targetResolutionDate;
    }


}
