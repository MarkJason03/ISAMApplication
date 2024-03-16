package com.example.fyp_application.Model;

public class TicketCategoryModel {

    private int categoryID; // Assuming categoryID is an integer
    private int knowledgeID; // Assuming knowledgeID is an integer that links to the knowledgeBase
    private String categoryName;

    // Constructor
    public TicketCategoryModel(int categoryID, int knowledgeID, String categoryName) {
        this.categoryID = categoryID;
        this.knowledgeID = knowledgeID;
        this.categoryName = categoryName;
    }


    public TicketCategoryModel(int categoryID, String categoryName){

        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getKnowledgeID() {
        return knowledgeID;
    }

    public void setKnowledgeID(int knowledgeID) {
        this.knowledgeID = knowledgeID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    @Override
    public String toString() {
        return categoryName;
    }
}
