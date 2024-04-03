package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {


    public static ObservableList<SupplierModel>getAllSuppliers() {
        //List to store supplier data
        ObservableList<SupplierModel> supplierList = FXCollections.observableArrayList();
        //Instance of the class
        SupplierModel supplierModel;

        String sql = "SELECT * FROM tbl_Suppliers";


        try (Connection connection = DatabaseConnectionUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                supplierModel = new SupplierModel(
                        resultSet.getInt("supplierID"),
                        resultSet.getString("supplierName"),
                        resultSet.getString("supplierAddress"),
                        resultSet.getString("supplierEmail"),
                        resultSet.getString("supplierPhone"),
                        resultSet.getString("supplierContractStatus"),
                        resultSet.getString("contractStartDate"),
                        resultSet.getString("contractEndDate")
                );
                supplierList.add(supplierModel);
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return supplierList;
    }


    public static ObservableList<SupplierModel> getSupplierDetails(int supplierID) {

        ObservableList<SupplierModel> supplierList = FXCollections.observableArrayList();

        String sql = """
                SELECT * FROM tbl_Suppliers WHERE supplierID = ?;
                """;

        try(Connection connection = DatabaseConnectionUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, supplierID);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    SupplierModel supplierModel = new SupplierModel(
                            resultSet.getInt("supplierID"),
                            resultSet.getString("supplierName"),
                            resultSet.getString("supplierAddress"),
                            resultSet.getString("supplierEmail"),
                            resultSet.getString("supplierPhone"),
                            resultSet.getString("supplierContractStatus"),
                            resultSet.getString("contractStartDate"),
                            resultSet.getString("contractEndDate")
                    );
                    supplierList.add(supplierModel);
                }
                return supplierList;
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return  supplierList;
    }

    public static ObservableList<SupplierModel> getFilteredSupplierList(String filter) {
        //List to store supplier data
        ObservableList<SupplierModel> supplierList = FXCollections.observableArrayList();
        //Instance of the class
        SupplierModel supplierModel;

        String sql = """
                SELECT *
                FROM tbl_Suppliers
                WHERE supplierContractStatus = ?;
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, filter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    supplierModel = new SupplierModel(
                            resultSet.getInt("supplierID"),
                            resultSet.getString("supplierName"),
                            resultSet.getString("supplierAddress"),
                            resultSet.getString("supplierEmail"),
                            resultSet.getString("supplierPhone"),
                            resultSet.getString("supplierContractStatus"),
                            resultSet.getString("contractStartDate"),
                            resultSet.getString("contractEndDate")
                    );
                    supplierList.add(supplierModel);
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return supplierList;
    }


    public void addSupplier(String supplierName, String supplierEmail, String supplierPhone, String contractStatus, String supplierAddress, String contractStartDate, String contractEndDate){
        String sql  = "INSERT INTO tbl_Suppliers (supplierName, supplierAddress, supplierEmail, supplierPhone, supplierContractStatus, contractStartDate, contractEndDate) VALUES (?,?,?,?,?,?,?)";

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, supplierName);
                preparedStatement.setString(2, supplierAddress);
                preparedStatement.setString(3, supplierEmail);
                preparedStatement.setString(4, supplierPhone);
                preparedStatement.setString(5, contractStatus);
                preparedStatement.setString(6, contractStartDate);
                preparedStatement.setString(7, contractEndDate);
                preparedStatement.executeUpdate();
                DatabaseConnectionUtils.closeConnection(connection);
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }


    public void updateSupplier(Integer supplierID, String supplierName,  String supplierAddress, String supplierEmail, String supplierPhone,
                               String contractStatus, String contractEndDate){
//        String sql = "UPDATE tbl_Suppliers SET supplierName = ? , supplierAddress = ?, supplierEmail = ?, supplierPhone = ? WHERE supplierID = ?";

        String sql = """
                Update tbl_Suppliers SET supplierName = ?, supplierAddress = ?, supplierEmail = ?, supplierPhone = ?,
                supplierContractStatus = ?, contractEndDate = ?
                WHERE supplierID = ?;
                """;


        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, supplierName);
                preparedStatement.setString(2, supplierAddress);
                preparedStatement.setString(3, supplierEmail);
                preparedStatement.setString(4, supplierPhone);
                preparedStatement.setString(5, contractStatus);
                preparedStatement.setString(6, contractEndDate);
                preparedStatement.setInt(7, supplierID);
                preparedStatement.executeUpdate();
                DatabaseConnectionUtils.closeConnection(connection);
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }


        try {
            Connection connection = DatabaseConnectionUtils.getConnection();
            assert connection != null;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, supplierName);
            preparedStatement.setString(2, supplierAddress);
            preparedStatement.setString(3, supplierEmail);
            preparedStatement.setString(4, supplierPhone);
            preparedStatement.setInt(5, supplierID);
            preparedStatement.executeUpdate();

            DatabaseConnectionUtils.closeConnection(connection);

        } catch (SQLException error) {
            error.printStackTrace();
        }

    }




    public void deleteSupplier(int supplierID){
        String sql = "delete from tbl_Suppliers where supplierID = ?";
        try{

            Connection connection = DatabaseConnectionUtils.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, supplierID );
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException error){
            error.printStackTrace();
        }
    }


    public static void checkAndUpdateContractStatus() {

        String sql = """
                UPDATE tbl_Suppliers
                SET supplierContractStatus = "Expired"
                WHERE contractEndDate < date("now") AND supplierContractStatus <> "Expired";
                """;


        try( Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                System.out.println("\nChecking and updating contract status");
                preparedStatement.executeUpdate();
                DatabaseConnectionUtils.closeConnection(connection);
                System.out.println("Contract status updated\n");
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }



    public static int countActiveSupplierContracts() {

        int counter = 0;

        String sql = "SELECT COUNT(supplierID) FROM tbl_Suppliers WHERE supplierContractStatus = 'Active'";


        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    counter = resultSet.getInt(1);
                }
                System.out.println("Active Suppliers: " + counter);
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return counter;

    }

    public static int  countInactiveSupplierContracts(){

        int counter = 0;
        String sql = "SELECT COUNT(supplierID) FROM tbl_Suppliers WHERE supplierContractStatus = 'Expired'";


        try(Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery())
            {
                if (resultSet.next()) {
                    counter = resultSet.getInt(1);
                }
                System.out.println("Inactive Suppliers: " + counter);

            }
        } catch (SQLException error) {
            error.printStackTrace();
        }

        return counter;
    }


    public static List<SupplierModel> getAllSupplierNameList() {
        //List to store category data
        List<SupplierModel> categoriesList = new ArrayList<>();
        String sql = """
                SELECT
                	supplierID,
                	supplierName
                FROM
                	tbl_Suppliers;
                """;

        //Try with resources to close the connection after the operation is done
        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            //prepared statement to execute the query
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                //loop through the result set and add the data to the list
                while (resultSet.next()) {
                    SupplierModel SupplierModel = new SupplierModel(
                            resultSet.getInt("supplierID"),
                            resultSet.getString("supplierName"));
                    categoriesList.add(SupplierModel);
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
