package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class TicketDAO {


    public void insertTicket(int userID, int categoryID, String ticketTitle, String ticketDescription, String dateCreated) {
        String sql = "INSERT INTO ticket (userID, categoryID, ticketTitle, ticketDescription, dateCreated) VALUES (?, ?, ?, ?, ?)";


        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userID);
                preparedStatement.setInt(2, categoryID);
                preparedStatement.setString(3, ticketTitle);
                preparedStatement.setString(4, ticketDescription);
                preparedStatement.setString(5, dateCreated);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


}
