package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierDAO {


    public ObservableList<SupplierModel>getAllSuppliers (){
        //List to store supplier data
        ObservableList<SupplierModel> supplierList = FXCollections.observableArrayList();
        //Instance of the class
        SupplierModel supplierModel;

        String sql = "SELECT * FROM tbl_Suppliers";


        try{
            Connection connection = DatabaseConnectionHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                supplierModel = new SupplierModel(
                        resultSet.getInt("supplierID"),
                        resultSet.getString("supplierName"),
                        resultSet.getString("supplierAddress"),
                        resultSet.getString("supplierEmail"),
                        resultSet.getString("supplierPhone")
                );
            supplierList.add(supplierModel);
            }
            DatabaseConnectionHandler.closeConnection(connection);

        } catch (SQLException error) {
            error.printStackTrace();
        }

        return supplierList;
    }



    public void addSupplier(String supplierName, String supplierAddress, String supplierEmail, String supplierPhone){
        String sql  = "INSERT INTO tbl_Suppliers (supplierName, supplierAddress, supplierEmail, supplierPhone) VALUES (?,?,?,?)";

        try {
            Connection connection = DatabaseConnectionHandler.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, supplierName);
            preparedStatement.setString(2, supplierAddress);
            preparedStatement.setString(3, supplierEmail);
            preparedStatement.setString(4, supplierPhone);
            preparedStatement.executeUpdate();
            DatabaseConnectionHandler.closeConnection(connection);


        } catch (SQLException error) {
            error.printStackTrace();
        }
    }


    public void updateSupplier(Integer supplierID, String supplierName,  String supplierAddress, String supplierEmail, String supplierPhone){
        String sql = "UPDATE tbl_Suppliers SET supplierName = ? , supplierAddress = ?, supplierEmail = ?, supplierPhone = ? WHERE supplierID = ?";

        try {
            Connection connection = DatabaseConnectionHandler.getConnection();
            assert connection != null;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, supplierName);
            preparedStatement.setString(2, supplierAddress);
            preparedStatement.setString(3, supplierEmail);
            preparedStatement.setString(4, supplierPhone);
            preparedStatement.setInt(5, supplierID);
            preparedStatement.executeUpdate();

            DatabaseConnectionHandler.closeConnection(connection);

        } catch (SQLException error) {
            error.printStackTrace();
        }

    }

    public void deleteSupplier(int supplierID){
        String sql = "delete from tbl_Suppliers where supplierID = ?";
        try{

            Connection connection = DatabaseConnectionHandler.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, supplierID );
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException error){
            error.printStackTrace();
        }
    }
}
