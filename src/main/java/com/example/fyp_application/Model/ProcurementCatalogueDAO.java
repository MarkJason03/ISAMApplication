package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcurementCatalogueDAO {

    public static ObservableList<ProcurementCatalogueModel> getFilteredSupplierContractCatalogue(String filter){
        ObservableList<ProcurementCatalogueModel> activeCatalogue = FXCollections.observableArrayList();

        String sql = """
            SELECT
                catalogue.CatalogueID,
                catalogue.AssetName,
                catalogue.StorageSpec,
                catalogue.RamSpec,
                sup.supplierName as Supplier,
                manu.ManufacturerName as Manufacturer,
                cat.assetCategoryName as Category,
                catalogue.AssetPrice as "Unit Price",
                catalogue.AssetPicture
            FROM tbl_Catalogue as catalogue
            JOIN tbl_assetManufacturer as manu on manu.ManufacturerID = catalogue.ManufacturerID
            JOIN tbl_assetCategory as cat on cat.assetCategoryID = catalogue.assetCategoryID
            JOIN tbl_Suppliers as sup on sup.supplierID = catalogue.SupplierID
            WHERE sup.supplierContractStatus != ?;
            """;

        try (Connection connection = DatabaseConnectionUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, filter); // Use the filter parameter correctly

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ProcurementCatalogueModel catalogueModel = new ProcurementCatalogueModel(
                            resultSet.getInt("CatalogueID"),
                            resultSet.getString("AssetName"),
                            resultSet.getString("StorageSpec"),
                            resultSet.getString("RamSpec"),
                            resultSet .getString("Supplier"),
                            resultSet.getString("Manufacturer"),
                            resultSet.getString("Category"),
                            resultSet.getDouble("Unit Price"),
                            resultSet.getString("AssetPicture")
                    );
                    activeCatalogue.add(catalogueModel);
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }

        return activeCatalogue;
    }


    public static ObservableList<ProcurementCatalogueModel> getAllCatalogueList(){
        ObservableList<ProcurementCatalogueModel> inactiveSupplierCatalogue = FXCollections.observableArrayList();

        String sql = """
            Select
                catalogue.CatalogueID,
                catalogue.AssetName,
                catalogue.StorageSpec,
                catalogue.RamSpec,
                sup.supplierName as Supplier,
                manu.ManufacturerName as Manufacturer,
                cat.assetCategoryName as Category,
                catalogue.AssetPrice as "Unit Price",
                catalogue.AssetPicture
            From tbl_Catalogue as catalogue
            JOIN tbl_assetManufacturer as manu on manu.ManufacturerID = catalogue.ManufacturerID
            JOIN tbl_assetCategory as cat on cat.assetCategoryID = catalogue.assetCategoryID
            JOIN tbl_Suppliers as sup on sup.supplierID = catalogue.supplierID;

                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                ProcurementCatalogueModel catalogueModel = new ProcurementCatalogueModel(
                        resultSet.getInt("CatalogueID"),
                        resultSet.getString("AssetName"),
                        resultSet.getString("StorageSpec"),
                        resultSet.getString("RamSpec"),
                        resultSet.getString("Supplier"),
                        resultSet.getString("Manufacturer"),
                        resultSet.getString("Category"),
                        resultSet.getDouble("Unit Price"),
                        resultSet.getString("AssetPicture")
                );
                inactiveSupplierCatalogue.add(catalogueModel);
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }

        return inactiveSupplierCatalogue;
    }


    public static ObservableList<ProcurementCatalogueModel> getFilteredActiveProcurementCatalogue(String filter) {
        ObservableList<ProcurementCatalogueModel> activeCatalogue = FXCollections.observableArrayList();

        String sql = """
                Select
                	catalogue.CatalogueID,
                	catalogue.AssetName,
                	catalogue.StorageSpec,
                	catalogue.RamSpec,
                	sup.supplierName as Supplier,
                	manu.ManufacturerName as Manufacturer,
                	cat.assetCategoryName as Category,
                	catalogue.AssetPrice as "Unit Price",
                	catalogue.AssetPicture
                From tbl_Catalogue as catalogue
                JOIN tbl_assetManufacturer as manu on manu.ManufacturerID = catalogue.ManufacturerID
                JOIN tbl_assetCategory as cat on cat.assetCategoryID = catalogue.assetCategoryID
                JOIN tbl_Suppliers as sup on sup.supplierID = catalogue.supplierID
                WHERE manu.ManufacturerName = ? AND sup.supplierContractStatus != 'Expired';
                """;

        try(Connection connection = DatabaseConnectionUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, filter);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProcurementCatalogueModel catalogueModel = new ProcurementCatalogueModel(
                        resultSet.getInt("CatalogueID"),
                        resultSet.getString("AssetName"),
                        resultSet.getString("StorageSpec"),
                        resultSet.getString("RamSpec"),
                        resultSet.getString("Supplier"),
                        resultSet.getString("Manufacturer"),
                        resultSet.getString("Category"),
                        resultSet.getDouble("Unit Price"),
                        resultSet.getString("AssetPicture")
                );
                activeCatalogue.add(catalogueModel);
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return activeCatalogue;
    }

    public static void addCatalogueItem(
            int supplierID,
            int manufacturerID,
            int categoryID,
            String assetName,
            String storageSpec,
            String ramSpec,
            int assetPrice,
            String assetPicture
    ){
        String sql = """
                INSERT INTO tbl_Catalogue (SupplierID, ManufacturerID, assetCategoryID, AssetName, StorageSpec, RamSpec, AssetPrice, AssetPicture)
                VALUES (?,?,?,?,?,?,?,?)
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, supplierID);
                preparedStatement.setInt(2, manufacturerID);
                preparedStatement.setInt(3, categoryID);
                preparedStatement.setString(4, assetName);
                preparedStatement.setString(5, storageSpec);
                preparedStatement.setString(6, ramSpec);
                preparedStatement.setDouble(7, assetPrice);
                preparedStatement.setString(8, assetPicture);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }
}
