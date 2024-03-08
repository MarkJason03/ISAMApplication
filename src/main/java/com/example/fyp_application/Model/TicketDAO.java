package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class TicketDAO {


    //
    public void openAdminTicketRequest(int userID, int categoryID, String ticketTitle, String ticketDescription, String dateCreated) {
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


    public int openUserTicketRequest(int userID, String ticketTitle, String ticketDescription, String ticketStatus, String dateCreated) {
        String sql = "INSERT INTO tbl_tickets (UserID, Title, Description, Status, DateCreated) VALUES (?, ?, ?, ?, ?)";

        // Initialize ticketID with a value indicating that no ID has been created.
        int ticketID = -1;

        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            // Update the statement to return the generated keys.
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, userID);
                preparedStatement.setString(2, ticketTitle);
                preparedStatement.setString(3, ticketDescription);
                preparedStatement.setString(4, ticketStatus);
                preparedStatement.setString(5, dateCreated);
                preparedStatement.executeUpdate();

                // Retrieve the generated ticket ID.
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ticketID = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ticketID;
    }






    public ObservableList<TicketModel> getAllTickets () {
        //List to store supplier data
        ObservableList<TicketModel> ticketList = FXCollections.observableArrayList();
        //Instance of the class
        TicketModel ticketModel;

        String sql = """
                SELECT\s
                    request.TicketID,
                    user.FirstName || ' ' || user.LastName AS User,
                    request.Title,
                    request.Status,
                    request.Priority,
                	reqcat.categoryName as Category,
                	agent.FirstName || ' ' || agent.LastName AS Agent,
                    request.DateCreated,
                	request.DateClosed
                FROM\s
                    tbl_tickets AS request
                JOIN
                	tbl_ticketCategory as reqcat on reqcat.ticketCategoryID = request.categoryID
                JOIN\s
                    tbl_Users AS user ON user.UserID = request.UserID
                JOIN\s
                    tbl_Users AS agent ON agent.UserID = request.AgentID
                """;


        TicketModel ticket;

        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ticket = new TicketModel(
                            resultSet.getInt("TicketID"),
                            resultSet.getString("User"),
                            resultSet.getString("Title"),
                            resultSet.getString("Status"),
                            resultSet.getString("Priority"),
                            resultSet.getString("Category"),
                            resultSet.getString("Agent"),
                            resultSet.getString("DateCreated"),
                            resultSet.getString("DateClosed")
                    );
                    ticketList.add(ticket);
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return ticketList;
    }

/*
    public void loadTicketInformation(int ticketID) {
        String sql = """
                SELECT\s
                    request.TicketID,
                    request.UserID,
                    request.AgentID,
                    request.categoryID,
                    user.FirstName || ' ' || user.LastName AS User,
                    request.Title,
                    request.Description,
                    request.Status,
                    request.Priority,
                    reqcat.categoryName as Category,
                    info.knowledgeInformation,
                    agent.FirstName || ' ' || agent.LastName AS Agent,
                    request.DateCreated,
                    request.DateClosed
                FROM\s
                    tbl_tickets AS request
                JOIN
                	tbl_ticketCategory as reqcat on reqcat.ticketCategoryID = request.categoryID
                JOIN
                	tbl_knowledgeBase AS info on reqcat.knowledgeID = info.knowledgeID
                JOIN\s
                    tbl_Users AS user ON user.UserID = request.UserID
                	
                LEFT JOIN\s
                    tbl_Users AS agent ON agent.UserID = request.AgentID
                Where request.TicketID = ?;
                """;

        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, ticketID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Set the ticket information
                        ticketID = resultSet.getInt("TicketID");
                        int userID = resultSet.getInt("UserID");
                        int agentID = resultSet.getInt("AgentID");
                        int categoryID = resultSet.getInt("categoryID");
                        String user = resultSet.getString("User");
                        String title = resultSet.getString("Title");
                        String description = resultSet.getString("Description");
                        String status = resultSet.getString("Status");
                        String priority = resultSet.getString("Priority");
                        String category = resultSet.getString("Category");
                        String agent = resultSet.getString("Agent");
                        String dateCreated = resultSet.getString("DateCreated");
                        String dateClosed = resultSet.getString("DateClosed");
                    }
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }*/


    public ObservableList<TicketModel>getTicketDetails(int ticketID){
        ObservableList<TicketModel> ticketDetails = FXCollections.observableArrayList();
        String sql = """
                SELECT\s
                    request.TicketID,
                    request.UserID,
                    request.AgentID,
                    request.categoryID,
                    user.FirstName || ' ' || user.LastName AS User,
                    request.Title,
                    request.Description,
                    request.Status,
                    request.Priority,
                    reqcat.categoryName as Category,
                    info.knowledgeInformation,
                    agent.FirstName || ' ' || agent.LastName AS Agent,
                    request.DateCreated,
                    request.DateClosed
                FROM\s
                    tbl_tickets AS request
                JOIN
                	tbl_ticketCategory as reqcat on reqcat.ticketCategoryID = request.categoryID
                JOIN
                	tbl_knowledgeBase AS info on reqcat.knowledgeID = info.knowledgeID
                JOIN\s
                    tbl_Users AS user ON user.UserID = request.UserID
                	
                LEFT JOIN\s
                    tbl_Users AS agent ON agent.UserID = request.AgentID
                Where request.TicketID = ?;
                """;

        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, ticketID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        TicketModel ticket = new TicketModel(
                                resultSet.getInt("TicketID"),
                                resultSet.getInt("UserID"),
                                resultSet.getInt("AgentID"),
                                resultSet.getInt("categoryID"),
                                resultSet.getString("User"),
                                resultSet.getString("Title"),
                                resultSet.getString("Description"),
                                resultSet.getString("Status"),
                                resultSet.getString("Priority"),
                                resultSet.getString("Category"),
                                resultSet.getString("knowledgeInformation"),
                                resultSet.getString("Agent"),
                                resultSet.getString("DateCreated"),
                                resultSet.getString("DateClosed")
                        );
                        ticketDetails.add(ticket);
                    }
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return ticketDetails;
    }


    public ObservableList<TicketModel> getShortenedTicketInformation(int ticketID){
        ObservableList<TicketModel>shortenedTicketDetails = FXCollections.observableArrayList();

        String sql = """
                select TicketID,categoryID,Title,Status,Priority,DateCreated from tbl_tickets where TicketID = ?;
                """;

        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, ticketID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        TicketModel ticket = new TicketModel(
                                resultSet.getInt("TicketID"),
                                resultSet.getInt("categoryID"),
                                resultSet.getString("Title"),
                                resultSet.getString("Status"),
                                resultSet.getString("Priority"),
                                resultSet.getString("DateCreated")
                        );
                        shortenedTicketDetails.add(ticket);
                    }
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }

        return shortenedTicketDetails;
    }


    public void quickCloseTicket(){

        // todo update these to quick close a ticket then update put message history too

        String sql = "UPDATE tbl_ticket set Priority = ? , TargetResolutionDate = ? , DateClosed = ? where TicketID = ?";
    }
}
