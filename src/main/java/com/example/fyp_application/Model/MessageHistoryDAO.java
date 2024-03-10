package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageHistoryDAO {



    public static void recordMessage(int ticketID, String messageBody, String timestamp) throws SQLException {


        String sql = """
                Insert into tbl_messageHistory (TicketID, MessageBody,Timestamp) values (?,?,?);
                """;

        try(Connection connection = DatabaseConnectionHandler.getConnection()){
            assert  connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,ticketID);
                preparedStatement.setString(2,messageBody);
                preparedStatement.setString(3,timestamp);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public ObservableList<MessageHistoryModel>getMessageHistory (int ticketID) {
        //List to store supplier data
        ObservableList<MessageHistoryModel> messageHistory = FXCollections.observableArrayList();

        String sql = """
                SELECT
                    history.MessageID,
                    history.TicketID,
                    user.FirstName || ' ' || user.LastName AS Sender,
                    agent.FirstName || ' ' || agent.LastName As Agent,
                    history.MessageBody,
                    history.Timestamp
                 FROM
                    tbl_messageHistory AS history
                 JOIN
                    tbl_tickets AS tickets ON history.TicketID = tickets.TicketID
                 JOIN
                    tbl_Users AS user ON tickets.UserID = user.UserID
                 JOIN
                    tbl_Users AS agent ON tickets.AgentID = agent.UserID
                 WHERE
                    history.TicketID =?;
                """;


        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set the ticketID parameter in the prepared statement
                preparedStatement.setInt(1, ticketID);

                // Execute the query after setting the parameter
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int MessageID = resultSet.getInt("MessageID");
                        int TicketID = resultSet.getInt("TicketID");
                        String userSenderName = resultSet.getString("Sender");
                        String agentSenderName = resultSet.getString("Agent");
                        String Body = resultSet.getString("MessageBody");
                        String time = resultSet.getString("Timestamp");
                        MessageHistoryModel message = new MessageHistoryModel(
                                MessageID, TicketID, userSenderName, agentSenderName, Body, time);
                        messageHistory.add(message);
                    }
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return messageHistory;
    }

    public ObservableList<MessageHistoryModel>getSpecificMessage(int messageID){
        ObservableList<MessageHistoryModel> individualMessage = FXCollections.observableArrayList();

        String sql = """
                SELECT
                	history.MessageID,
                	tickets.Title,
                	history.MessageBody,
                	history.Timestamp
                FROM
                	tbl_messageHistory AS history
                JOIN
                	tbl_tickets AS tickets ON history.TicketID = tickets.TicketID
                JOIN
                	tbl_Users AS user ON tickets.UserID = user.UserID
                JOIN
                	tbl_Users AS agent ON tickets.AgentID = agent.UserID
                WHERE
                	history.MessageID =?;
                """;


        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set the ticketID parameter in the prepared statement
                preparedStatement.setInt(1, messageID);

                // Execute the query after setting the parameter
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int MessageID = resultSet.getInt("MessageID");
                        String title = resultSet.getString("Title");
                        String body= resultSet.getString("MessageBody");
                        String time = resultSet.getString("Timestamp");
                        MessageHistoryModel message = new MessageHistoryModel(
                                MessageID,title,body,time);
                        individualMessage.add(message);
                    }
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return individualMessage;
    }

}
