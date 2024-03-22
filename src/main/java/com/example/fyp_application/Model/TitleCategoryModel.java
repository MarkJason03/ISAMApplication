package com.example.fyp_application.Model;

public class TitleCategoryModel extends TicketCategoryModel {

    public TitleCategoryModel(int titleID, int categoryID, String titleName) {
        super(titleID, categoryID, titleName);
    }

    @Override
    public String toString() {
        return getTitleName();
    }
}