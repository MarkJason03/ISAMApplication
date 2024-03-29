package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AssetManufacturerDAO {

    public static List<AssetManufacturerModel> getAllAssetManufacturers() {
        //List to store department data
        List<AssetManufacturerModel> manufacturerList = new ArrayList<>();
        String sql = "select * from tbl_assetManufacturer";

        //Try with resources to close the connection after the operation is done
        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            //prepared statement to execute the query
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                //loop through the result set and add the data to the list
                while (resultSet.next()) {
                    AssetManufacturerModel manufacturerModel= new AssetManufacturerModel(
                            resultSet.getInt("ManufacturerID"),
                            resultSet.getString("ManufacturerName"));
                    manufacturerList.add(manufacturerModel);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //return the list of manufacturers
        return manufacturerList;
    }
}
