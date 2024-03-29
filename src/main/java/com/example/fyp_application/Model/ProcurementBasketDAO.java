package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProcurementBasketDAO {



    public static void insertBasketDetails(int procurementID, int catalogueID, int quantity, double totalPrice){
        String sql  = "INSERT INTO tbl_Basket (ProcurementID, CatalogueID, Quantity, TotalPrice) VALUES (?,?,?,?)";

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, procurementID);
                preparedStatement.setInt(2, catalogueID);
                preparedStatement.setInt(3, quantity);
                preparedStatement.setDouble(4, totalPrice);
                preparedStatement.executeUpdate();
                DatabaseConnectionUtils.closeConnection(connection);
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }


    public static ObservableList<ProcurementBasketModel>getBasketDetails(int procurementID){

        ObservableList<ProcurementBasketModel> basketDetails = FXCollections.observableArrayList();

        ProcurementBasketModel procurementBasketModel;

        String sql = """
                SELECT
                    catalogue.CatalogueID,
                    catalogue.AssetName,
                    catalogue.AssetPrice,
                    basket.Quantity,
                    basket.TotalPrice
                                
                FROM tbl_Basket AS basket
                JOIN tbl_Catalogue AS catalogue ON catalogue.CatalogueID = basket.CatalogueID
                JOIN tbl_Procurement AS procurement ON procurement.ProcurementID = basket.ProcurementID
                WHERE basket.ProcurementID = ?;
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, procurementID);
                    var resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        procurementBasketModel = new ProcurementBasketModel(
                                resultSet.getInt("CatalogueID"),
                                resultSet.getString("AssetName"),
                                (int) resultSet.getDouble("AssetPrice"),
                                resultSet.getInt("Quantity"),
                                (int) resultSet.getDouble("TotalPrice")
                        );
                        basketDetails.add(procurementBasketModel);
                    }
                    DatabaseConnectionUtils.closeConnection(connection);
                }
        } catch (SQLException error) {
                error.printStackTrace();
            }
        return basketDetails;
    }

}
