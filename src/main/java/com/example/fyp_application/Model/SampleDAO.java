package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class SampleDAO {


    public static void insertValues(int id, String photoPath) {
        try (Connection con = DatabaseConnectionHandler.getConnection()) {
            PreparedStatement ps = con.prepareStatement("insert into tbl_Sample values(?, ?)");
            ps.setInt(1, id);
            ps.setString(2, photoPath);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static ObservableList<SampleModel> getAllSamples() {
        ObservableList<SampleModel> sampleModelObservableList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM tbl_Sample";

        try (Connection connection = DatabaseConnectionHandler.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                SampleModel sampleModel = new SampleModel(
                        resultSet.getInt("sampleID"),
                        resultSet.getString("PhotoPath")
                );
                sampleModelObservableList.add(sampleModel);
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return sampleModelObservableList;
    }




    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //SampleDAO.insertValues(1, "C:\\Users\\user\\Desktop\\image.jpg", null);
    }
}
