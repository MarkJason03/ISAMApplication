package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

/*    public static List<XYChart.Data<String, Number>> getMonthlyProcurementSpending(LocalDate startDate) {
        List<XYChart.Data<String, Number>> dataPoints = new ArrayList<>();

        String sql = """
                SELECT
                    strftime('%Y-%m', DateRaised) AS Month,
                    SUM(TotalPrice) AS MonthlySpending
                FROM
                    tbl_Basket AS bask
                JOIN
                    tbl_Procurement AS proc ON proc.ProcurementID = bask.ProcurementID
                WHERE
                    proc.ProcurementStatus = 'Approved'
                GROUP BY
                    strftime('%Y-%m', DateRaised)
                ORDER BY
                    Month;
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, startDate.toString());
                preparedStatement.setString(2, startDate.plusMonths(1).toString());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String month = resultSet.getString("Month");
                        double spending = resultSet.getDouble("MonthlySpending");
                        dataPoints.add(new XYChart.Data<>(month, spending));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataPoints;
    }*/

    public static List<XYChart.Data<String, Number>> getMonthlyProcurementSpending(LocalDate startDate, LocalDate endDate) {
        List<XYChart.Data<String, Number>> dataPoints = new ArrayList<>();

        // Adjust the SQL query to filter records between startDate and an endDate
        String sql = """
            SELECT
                strftime('%Y-%m', proc.DateRaised) AS Month,
                SUM(bask.TotalPrice) AS MonthlySpending
            FROM
                tbl_Basket AS bask
            JOIN
                tbl_Procurement AS proc ON proc.ProcurementID = bask.ProcurementID
            WHERE
                proc.ProcurementStatus = 'Approved' AND
                proc.DateRaised >= ? AND
                proc.DateRaised < ?
            GROUP BY
                Month
            ORDER BY
                Month;
            """;

        // Ensure endDate is correctly set to capture the full end month
        LocalDate adjustedEndDate = endDate.plusMonths(1).withDayOfMonth(1);

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, startDate.withDayOfMonth(1).toString());
                preparedStatement.setString(2, adjustedEndDate.toString());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String month = resultSet.getString("Month");
                        double spending = resultSet.getDouble("MonthlySpending");
                        dataPoints.add(new XYChart.Data<>(month, spending));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataPoints;
    }


}
