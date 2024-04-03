package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TitleCategoryDAO {

    public static List<TitleCategoryModel> getSelectedCategoryTitles(int categoryID) {
        List<TitleCategoryModel> categoriesList = new ArrayList<>();
        String sql = "SELECT * FROM tbl_categoryTitlePresets WHERE ticketCategoryID = ?;";

        try (Connection connection = DatabaseConnectionUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Setting the categoryID in the PreparedStatement
            preparedStatement.setInt(1, categoryID);

            // Execute the query after setting the parameter
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TitleCategoryModel titleCategoryModel = new TitleCategoryModel(
                            resultSet.getInt("titleID"),
                            resultSet.getInt("ticketCategoryID"),
                            resultSet.getString("TitleName"));
                    categoriesList.add(titleCategoryModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoriesList;
    }
}
