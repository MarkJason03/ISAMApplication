package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssetDAO {


    public static ObservableList<AssetModel> getAllAssets() {
        //List to store supplier data
        ObservableList<AssetModel> assetList = FXCollections.observableArrayList();
        //Instance of the class
        AssetModel assetModel;

        String sql = """
                SELECT asset.*,
                	   assetCategory.assetCategoryName as Category,\s
                	   manufacturer.ManufacturerName as Manufacturer
                FROM tbl_Assets AS asset
                JOIN tbl_assetCategory AS assetCategory ON asset.assetCategoryID = assetCategory.assetCategoryID
                JOIN tbl_assetManufacturer AS manufacturer ON asset.ManufacturerID = manufacturer.ManufacturerID;
                """;


        try (Connection connection = DatabaseConnectionHandler.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                assetModel = new AssetModel(
                        resultSet.getInt("AssetID"),
                        resultSet.getInt("assetCategoryID"),
                        resultSet.getInt("ManufacturerID"),
                        resultSet.getString("AssetName"),
                        resultSet.getString("SerialNo"),
                        resultSet.getString("StorageSpec"),
                        resultSet.getString("RamSpec"),
                        resultSet.getString("OperatingSystem"),
                        resultSet.getString("PurchaseDate"),
                        resultSet.getString("EoLDate"),
                        resultSet.getString("WarrantyStartDate"),
                        resultSet.getString("WarrantyEndDate"),
                        resultSet.getString("AssetCondition"),
                        resultSet.getString("AssetStatus"),
                        resultSet.getString("PhotoPath"),
                        resultSet.getString("Category"),
                        resultSet.getString("Manufacturer")
                );
                assetList.add(assetModel);
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return assetList;
    }



}
