package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TicketCategoryDAO {


    public List<TicketCategoryModel> getAllCategories() {
        List<TicketCategoryModel> categories = new ArrayList<>();
        String sql = "select ticketCategoryID, categoryName from tbl_ticketCategory";

        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
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






}
