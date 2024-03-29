package com.example.fyp_application.Model;

public class TicketCategoryModel {

    private int categoryID; // Assuming categoryID is an integer
    private int knowledgeID; // Assuming knowledgeID is an integer that links to the knowledgeBase

    private int titleID; // Assuming titleID is an integer that links to the title presets table
    private String categoryName;

    private String TitleName;
    // Constructor
    public TicketCategoryModel(int titleID, int categoryID, String titleName) {
        this.titleID = titleID;
        this.categoryID = categoryID;
        this.TitleName = titleName;
    }


    public TicketCategoryModel(int categoryID, String categoryName){

        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public int getCategoryID() {
        return categoryID;
    }

    public int getTitleID() {
        return titleID;
    }

    public void setTitleID(int titleID) {
        this.titleID = titleID;
    }
    public String getTitleName() {
        return TitleName;
    }

    public void setTitleName(String TitleName) {
        this.TitleName = TitleName;
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
