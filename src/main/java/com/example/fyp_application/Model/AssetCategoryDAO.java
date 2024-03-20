package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AssetCategoryDAO {


    public static List<AssetCategoryModel> getAllAssetCategories() {
        //List to store category data
        List<AssetCategoryModel> categoriesList = new ArrayList<>();
        String sql = "select * from tbl_assetCategory";

        //Try with resources to close the connection after the operation is done
        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            //prepared statement to execute the query
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                //loop through the result set and add the data to the list
                while (resultSet.next()) {
                    AssetCategoryModel categoryModel = new AssetCategoryModel(
                            resultSet.getInt("assetCategoryID"),
                            resultSet.getString("assetCategoryName"));
                    categoriesList.add(categoryModel);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //return the list of categories
        return categoriesList;
    }
}
