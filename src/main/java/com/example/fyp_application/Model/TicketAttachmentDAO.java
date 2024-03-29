package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketAttachmentDAO {




    public static void insertAttachment(int ticketID, String filePath, String dateUploaded) {
        String sql = "INSERT INTO tbl_ticketAttachments (TicketID, FilePath, DateAdded) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, ticketID);
                preparedStatement.setString(2, filePath);
                preparedStatement.setString(3, dateUploaded);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public ObservableList<TicketAttachmentModel> loadTicketAttachments(int ticketID) throws SQLException {
        ObservableList<TicketAttachmentModel> attachmentList = FXCollections.observableArrayList();

        String sql = """
                SELECT FilePath, DateAdded FROM tbl_ticketAttachments WHERE TicketID = ?;
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, ticketID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String filePath = resultSet.getString("FilePath");
                    String dateAdded = resultSet.getString("DateAdded");

                    TicketAttachmentModel attachment = new TicketAttachmentModel(filePath, dateAdded);
                    attachmentList.add(attachment);
                }
            }
        } catch (SQLException error) {

            throw new SQLException("Error loading ticket attachments: " + error.getMessage(), error);
        }

        return attachmentList;
    }



}
