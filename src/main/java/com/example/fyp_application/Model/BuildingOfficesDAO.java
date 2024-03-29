package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BuildingOfficesDAO {



/*
    public static List<BuildingOfficesModel> getAllOffices() {
        //List to store category data
        List<BuildingOfficesModel> officeLists = new ArrayList<>();
        String sql = "select * from tbl_BuildingOffices";

        //Try with resources to close the connection after the operation is done
        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            //prepared statement to execute the query
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                //loop through the result set and add the data to the list
                while (resultSet.next()) {
                     BuildingOfficesModel buildingOfficesModel = new BuildingOfficesModel (
                            resultSet.getInt("OfficeID"),
                            resultSet.getInt("BuildingID"),
                            resultSet.getString("assetCategoryName"));
                    officeLists.add(buildingOfficesModel);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //return the list of offices
        return officeLists;
    }*/

    public static List<BuildingOfficesModel>getSelectedBuildingOffices(int buildingID) {
        //List to store category data
        List<BuildingOfficesModel> officeLists = new ArrayList<>();
        String sql = "select * from tbl_BuildingOffices where BuildingID = ?";

        //Try with resources to close the connection after the operation is done
        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            //prepared statement to execute the query
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, buildingID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    //loop through the result set and add the data to the list
                    while (resultSet.next()) {
                        BuildingOfficesModel buildingOfficesModel = new BuildingOfficesModel(
                                resultSet.getInt("OfficeID"),
                                resultSet.getInt("BuildingID"),
                                resultSet.getString("OfficeName"));
                        officeLists.add(buildingOfficesModel);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return officeLists;
    }
}
