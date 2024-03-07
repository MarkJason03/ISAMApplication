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



    // Foreign Keys

    private String knowledgeBaseInfo;
    private String agentFullName;
    private String categoryName;
    private String userFullName;






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

    // Constructor for viewing ticket - user side


    // Constructor for creating ticket - agent side








    // Constructor for viewing ticket - agent side // table view
    public TicketModel(int ticketID,
                       String userFullName,
                       String ticketTitle,
                       String status,
                       String priority,
                       String category,
                       String agentFullName,
                       String dateCreated,
                       String dateClosed) {
        this.ticketID = ticketID;
        this.userFullName = userFullName;
        this.ticketTitle = ticketTitle;
        this.ticketStatus = status;
        this.ticketPriority = priority;
        this.categoryName = category;
        this.agentFullName = agentFullName;
        this.dateCreated = dateCreated;
        this.dateClosed = dateClosed;
    }


    //Constructor for viewing ticket page view
    public TicketModel(int ticketID,
                       int userID,
                       int agentID,
                       int categoryID,
                       String userFullName,
                       String ticketTitle,
                       String description,
                       String status,
                       String priority,
                       String categoryName,
                       String knowledgeInfo,
                       String agentFullName,
                       String dateCreated,
                       String dateClosed) {

        this.ticketID = ticketID;
        this.userID = userID;
        this.agentID = agentID;
        this.categoryID = categoryID;
        this.userFullName = userFullName;
        this.ticketTitle = ticketTitle;
        this.ticketDescription = description;
        this.ticketStatus = status;
        this.ticketPriority = priority;
        this.categoryName = categoryName;
        this.knowledgeBaseInfo = knowledgeInfo;
        this.agentFullName = agentFullName;
        this.dateCreated = dateCreated;
        this.dateClosed = dateClosed;
    }

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

    public String getAgentFullName() {
        return agentFullName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getKnowledgeBaseInfo() {
        return knowledgeBaseInfo;
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

    public void setAgentFullName(String agentFullName) {
        this.agentFullName = agentFullName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public void setKnowledgeBaseInfo(String knowledgeBaseInfo) {
        this.knowledgeBaseInfo = knowledgeBaseInfo;
    }

}
