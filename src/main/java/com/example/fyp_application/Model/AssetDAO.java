package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

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
                        resultSet.getString("AssetStatus"),
                        resultSet.getString("AssetCondition"),
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


    public static ObservableList<AssetModel>getSelectedAsset(int assetID) throws SQLException {
        ObservableList<AssetModel> assetSet = FXCollections.observableArrayList();
        AssetModel assetModel;

        String sql = """
                SELECT asset.*,
                	   assetCategory.assetCategoryName as Category,
                	   manufacturer.ManufacturerName as Manufacturer
                FROM tbl_Assets AS asset
                JOIN tbl_assetCategory AS assetCategory ON asset.assetCategoryID = assetCategory.assetCategoryID
                JOIN tbl_assetManufacturer AS manufacturer ON asset.ManufacturerID = manufacturer.ManufacturerID
                where asset.AssetID = ?;
                """;

        try (Connection connection = DatabaseConnectionHandler.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, assetID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
                    assetSet.add(assetModel);
                }
            } catch (SQLException error) {
                error.printStackTrace();
            }
        }
        return assetSet;
    }


    public static void updateAssetDetails(int assetID,
                                          int assetCategoryID,
                                          int manufacturerID,
                                          String assetName,
                                          String serialNo,
                                          String storageSpec,
                                          String ramSpec,
                                          String operatingSystem,
                                          String purchaseDate,
                                          String eolDate,
                                          String warrantyStartDate,
                                          String warrantyEndDate,
                                          String assetCondition,
                                          String assetStatus,
                                          String photoPath) {
        //Update the asset details

        String sql = """
                UPDATE tbl_Assets
                SET assetCategoryID = ?,
                    ManufacturerID = ?,
                    AssetName = ?,
                    SerialNo = ?,
                    StorageSpec = ?,
                    RamSpec = ?,
                    OperatingSystem = ?,
                    PurchaseDate = ?,
                    EoLDate = ?,
                    WarrantyStartDate = ?,
                    WarrantyEndDate = ?,
                    AssetCondition = ?,
                    AssetStatus = ?,
                    PhotoPath = ?
                WHERE AssetID = ?;
                """;

        try(Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, assetCategoryID);
                preparedStatement.setInt(2, manufacturerID);
                preparedStatement.setString(3, assetName);
                preparedStatement.setString(4, serialNo);
                preparedStatement.setString(5, storageSpec);
                preparedStatement.setString(6, ramSpec);
                preparedStatement.setString(7, operatingSystem);
                preparedStatement.setString(8, purchaseDate);
                preparedStatement.setString(9, eolDate);
                preparedStatement.setString(10, warrantyStartDate);
                preparedStatement.setString(11, warrantyEndDate);
                preparedStatement.setString(12, assetCondition);
                preparedStatement.setString(13, assetStatus);
                preparedStatement.setString(14, photoPath);
                preparedStatement.setInt(15, assetID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }


    public static void addAsset(
            int assetCategoryID,
            int manufacturerID,
            String assetName,
            String serialNo,
            String storageSpec,
            String ramSpec,
            String operatingSystem,
            String purchaseDate,
            String eolDate,
            String warrantyStartDate,
            String warrantyEndDate,
            String assetCondition,
            String assetStatus,
            String photoPath
    ) {
        //Add Asset
        String sql = """
                INSERT INTO tbl_Assets (
                assetCategoryID,
                ManufacturerID,
                AssetName,
                SerialNo,
                StorageSpec,
                RamSpec,
                OperatingSystem,
                PurchaseDate,
                EoLDate,
                WarrantyStartDate,
                WarrantyEndDate,
                AssetCondition,
                AssetStatus,
                PhotoPath) Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?);
                """;

        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, assetCategoryID);
                preparedStatement.setInt(2, manufacturerID);
                preparedStatement.setString(3, assetName);
                preparedStatement.setString(4, serialNo);
                preparedStatement.setString(5, storageSpec);
                preparedStatement.setString(6, ramSpec);
                preparedStatement.setString(7, operatingSystem);
                preparedStatement.setString(8, purchaseDate);
                preparedStatement.setString(9, eolDate);
                preparedStatement.setString(10, warrantyStartDate);
                preparedStatement.setString(11, warrantyEndDate);
                preparedStatement.setString(12, assetCondition);
                preparedStatement.setString(13, assetStatus);
                preparedStatement.setString(14, photoPath);

                System.out.println("Executing update statement \n");
                preparedStatement.executeUpdate();
                System.out.println("Asset added \n");
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }
}

