package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcurementCatalogueDAO {

    public static ObservableList<ProcurementCatalogueModel> getActiveProcurementCatalogue(){
        ObservableList<ProcurementCatalogueModel> activeCatalogue = FXCollections.observableArrayList();

        String sql = """
                Select
                	catalogue.CatalogueID,
                	catalogue.AssetName,
                	catalogue.StorageSpec,
                	catalogue.RamSpec,
                	manu.ManufacturerName as Manufacturer,
                	cat.assetCategoryName as Category,
                	catalogue.AssetPrice as "Unit Price",
                	catalogue.AssetPicture
                From tbl_Catalogue as catalogue
                JOIN tbl_assetManufacturer as manu on manu.ManufacturerID = catalogue.ManufacturerID
                JOIN tbl_assetCategory as cat on cat.assetCategoryID = catalogue.assetCategoryID
                JOIN tbl_Suppliers as sup on sup.supplierID = catalogue.supplierID
                WHERE sup.supplierContractStatus != 'Expired';
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
                        resultSet.getString("Manufacturer"),
                        resultSet.getString("Category"),
                        resultSet.getInt("Unit Price"),
                        resultSet.getString("AssetPicture")
                );
                activeCatalogue.add(catalogueModel);
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }

        return activeCatalogue;
    }


    public static ObservableList<ProcurementCatalogueModel> getFilteredActiveProcurementCatalogue(String filter) {
        ObservableList<ProcurementCatalogueModel> activeCatalogue = FXCollections.observableArrayList();

        String sql = """
                Select
                	catalogue.CatalogueID,
                	catalogue.AssetName,
                	catalogue.StorageSpec,
                	catalogue.RamSpec,
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
                        resultSet.getString("Manufacturer"),
                        resultSet.getString("Category"),
                        resultSet.getInt("Unit Price"),
                        resultSet.getString("AssetPicture")
                );
                activeCatalogue.add(catalogueModel);
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return activeCatalogue;
    }
}
