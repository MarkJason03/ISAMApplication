package com.example.fyp_application.Model;

public class TitleCategoryModel  {

    private int titleID;


    private int categoryID;
    private String titleName;

    public TitleCategoryModel(int titleID, int categoryID, String titleName) {
        this.titleID = titleID;
        this.categoryID = categoryID;
        this.titleName = titleName;
    }




    public int getTitleID() {
        return titleID;
    }


    public void setTitleID(int titleID) {
        this.titleID = titleID;
    }


    public int getCategoryID() {
        return categoryID;
    }


    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    @Override
    public String toString() {
        return titleName;
    }
}