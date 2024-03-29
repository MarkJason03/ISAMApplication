package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TicketCategoryDAO {


    public List<TicketCategoryModel> getAllCategories() {
        List<TicketCategoryModel> categories = new ArrayList<>();
        String sql = "select ticketCategoryID, categoryName from tbl_ticketCategory";

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    TicketCategoryModel category = new TicketCategoryModel(
                            resultSet.getInt("ticketCategoryID"),
                            resultSet.getString("categoryName"));
                    categories.add(category);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }



        return categories;
    }


    public List<TicketCategoryModel>getSelectedCategoryTitles(int categoryID){
        List<TicketCategoryModel> categoryTitles = new ArrayList<>();
        String sql = "select ticketCategoryID, TitleName from tbl_categoryTitlePresets where ticketCategoryID = ?";

        //Try with resources to close the connection after the operation is done
            try (Connection connection = DatabaseConnectionUtils.getConnection()) {
                assert connection != null;
                //prepared statement to execute the query
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, categoryID);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        //loop through the result set and add the data to the list
                        while (resultSet.next()) {
                            TicketCategoryModel buildingOfficesModel = new TicketCategoryModel(
                                    resultSet.getInt("ticketCategoryID"),
                                    resultSet.getString("TitleName")
                            );
                        categoryTitles.add(buildingOfficesModel);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return categoryTitles;
    }


}
