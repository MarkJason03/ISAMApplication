package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class TicketDAO {


    //
    public void openAdminTicketRequest(int userID, int categoryID, String ticketTitle, String ticketDescription, String dateCreated) {
        String sql = "INSERT INTO ticket (userID, categoryID, ticketTitle, ticketDescription, dateCreated) VALUES (?, ?, ?, ?, ?)";


        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
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


    public int openUserTicketRequest(int userID, int categoryID, String ticketTitle, String ticketDescription, String ticketStatus, String ticketPriority, String dateCreated) {
        String sql = "INSERT INTO tbl_tickets (UserID, Title, Description, Status, Priority, categoryID , DateCreated) VALUES (?,?, ?, ?, ?, ?, ?)";

        // Initialize ticketID with a value indicating that no ID has been created.
        int ticketID = -1;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            // Update the statement to return the generated keys.
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, userID);
                preparedStatement.setString(2, ticketTitle);
                preparedStatement.setString(3, ticketDescription);
                preparedStatement.setString(4, ticketStatus);
                preparedStatement.setString(5,ticketPriority);
                preparedStatement.setInt(6,categoryID);
                preparedStatement.setString(7, dateCreated);

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
        //List to store ticket information
        ObservableList<TicketModel> ticketList = FXCollections.observableArrayList();
        //Instance of the class
        TicketModel ticketModel;

        String sql = """
                    SELECT
                        request.TicketID,
                        user.FirstName || ' ' || user.LastName AS User,
                        COALESCE(agent.FirstName || ' ' || agent.LastName, 'No Agent Assigned') AS Agent,
                        request.Title,
                        request.Status,
                        request.Priority,
                        reqcat.categoryName as Category,
                        request.DateCreated,
                        request.DateClosed,
                        CASE
                            WHEN request.Status != 'Closed' AND date(request.TargetResolution) < date('now')
                                 AND (request.Status = 'In Progress' OR request.Status = 'Awaiting Response') THEN 'Breached'
                            WHEN request.Status != 'Closed' THEN 'Normal'
                            ELSE 'Ignored'
                        END AS EscalationStatus
                    FROM
                        tbl_tickets AS request
                    JOIN
                        tbl_ticketCategory as reqcat ON reqcat.ticketCategoryID = request.categoryID
                    LEFT JOIN
                        tbl_Users as agent ON agent.UserID = request.AgentID -- Listing all tickets with or without an agent
                    JOIN
                        tbl_Users AS user ON user.UserID = request.UserID;
                """;


        TicketModel ticket;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ticket = new TicketModel(
                            resultSet.getInt("TicketID"),
                            resultSet.getString("User"),
                            resultSet.getString("Agent"),
                            resultSet.getString("Title"),
                            resultSet.getString("Status"),
                            resultSet.getString("Priority"),
                            resultSet.getString("Category"),
                            resultSet.getString("DateCreated"),
                            resultSet.getString("DateClosed"),
                            resultSet.getString("EscalationStatus")
                    );
                    ticketList.add(ticket);
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return ticketList;
    }



    public ObservableList<TicketModel> getTicketsByUserID(int userID) {
        ObservableList<TicketModel> ticketDetailsPerUserID = FXCollections.observableArrayList();
        String sql = """
                SELECT
                    requests.TicketID,
                    requests.Title,
                    requests.Description,
                    requests.Status,
                    requests.DateCreated,
                    requests.DateClosed,
                    agent.FirstName || ' ' || agent.LastName as Agent
                FROM\s
                    tbl_tickets as requests
                JOIN
                    tbl_Users as user ON user.UserID = requests.UserID
                LEFT JOIN
                    tbl_Users as agent ON agent.UserID = requests.AgentID  
                WHERE
                    requests.UserID = ?;
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        TicketModel ticket = new TicketModel(
                                resultSet.getInt("TicketID"),
                                resultSet.getString("Title"),
                                resultSet.getString("Description"),
                                resultSet.getString("Status"),
                                resultSet.getString("DateCreated"),
                                resultSet.getString("DateClosed"),
                                resultSet.getString("Agent")
                        );
                        ticketDetailsPerUserID.add(ticket);
                    }
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return ticketDetailsPerUserID;
    }

/*
    public void displayTicketInformation(int ticketID) {
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

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
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


    public ObservableList<TicketModel> getFullTicketDetails(int ticketID){
        ObservableList<TicketModel> ticketDetails = FXCollections.observableArrayList();
        String sql = """
                    SELECT
                        request.TicketID,
                        request.UserID,
                        request.AgentID,
                        request.categoryID,
                        user.FirstName || ' ' || user.LastName AS User,
                        user.Email,
                        request.Title,
                        request.Description,
                        request.Status,
                        request.Priority,
                        reqcat.categoryName as Category,
                        info.knowledgeInformation,
                        agent.FirstName || ' ' || agent.LastName AS Agent,
                        request.DateCreated,
                        request.TargetResolution,
                        request.DateClosed,
                        CASE
                            WHEN request.Status != 'Closed' AND date(request.TargetResolution) < date('now')
                                 AND (request.Status = 'In Progress' OR request.Status = 'Awaiting Response') THEN 'Breached'
                            WHEN request.Status != 'Closed' THEN 'Normal'
                            ELSE 'Ignored'
                        END AS EscalationStatus
                    FROM
                        tbl_tickets AS request
                    JOIN
                        tbl_ticketCategory as reqcat on reqcat.ticketCategoryID = request.categoryID
                    JOIN
                        tbl_knowledgeBase AS info on reqcat.knowledgeID = info.knowledgeID
                    JOIN
                        tbl_Users AS user ON user.UserID = request.UserID
                    LEFT JOIN
                        tbl_Users AS agent ON agent.UserID = request.AgentID
                    WHERE request.TicketID = ?;
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
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
                                resultSet.getString("Email"),
                                resultSet.getString("Title"),
                                resultSet.getString("Description"),
                                resultSet.getString("Status"),
                                resultSet.getString("Priority"),
                                resultSet.getString("Category"),
                                resultSet.getString("knowledgeInformation"),
                                resultSet.getString("Agent"),
                                resultSet.getString("DateCreated"),
                                resultSet.getString("TargetResolution"),
                                resultSet.getString("DateClosed"),
                                resultSet.getString("EscalationStatus")
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
                select\s
                	TicketID,
                	categoryID,
                	Title,
                	Status,
                	Priority,
                	DateCreated,
                	user.FirstName || ' ' || user.LastName as Fullname,
                	agent.FirstName || ' ' || agent.LastName as Agentname,
                	user.Email\s
                From tbl_tickets\s
                Join tbl_Users as user on user.UserID = tbl_tickets.UserID
                Join tbl_Users as agent on agent.UserID = tbl_tickets.AgentID
                Where TicketID = ?;
                 """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
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
                                resultSet.getString("DateCreated"),
                                resultSet.getString("Fullname"),
                                resultSet.getString("Agentname"),
                                resultSet.getString("Email")
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




    public void quickCloseTicket(int ticketID, int categoryID,String status, String priority, String targetResDate , String DateClosed ) throws SQLException {

        // todo update these to quick close a ticket then update put message history too

        String sql = "UPDATE tbl_tickets set  categoryID=?, Status=?, Priority = ? , TargetResolution = ? , DateClosed = ? where TicketID = ?";
        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1,categoryID);
                preparedStatement.setString(2,status);
                preparedStatement.setString(3, priority);
                preparedStatement.setString(4,targetResDate);
                preparedStatement.setString(5,DateClosed);
                preparedStatement.setInt(6,ticketID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void updateTicketDetailsByAdmin(int ticketID, int categoryID, String status, String priority, String targetResDate ){
        // todo update these to quick close a ticket then update put message history too

        String sql = "UPDATE tbl_tickets set  categoryID=?, Status=?, Priority = ? , TargetResolution = ? where TicketID = ?";
        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1,categoryID);
                preparedStatement.setString(2,status);
                preparedStatement.setString(3, priority);
                preparedStatement.setString(4,targetResDate);
                preparedStatement.setInt(5,ticketID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateTicketClosingDetailsByAdmin(
            int ticketID,
            int categoryID,
            String status,
            String priority,
            String dateClosed
    ){
        String sql = "UPDATE tbl_tickets set categoryID = ?, Status = ?, Priority = ?, DateClosed =? where TicketID = ?";
        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, categoryID);
                preparedStatement.setString(2, status);
                preparedStatement.setString(3, priority);
                preparedStatement.setString(4, dateClosed);
                preparedStatement.setInt(5, ticketID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public static void updateTicketStatusByUser(int ticketID, String status){
        String sql = "UPDATE tbl_tickets SET Status = ?, DateClosed = NULL WHERE TicketID = ?";

        try (Connection connection = DatabaseConnectionUtils.getConnection()){
            assert connection != null;
            try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1,status);
                preparedStatement.setInt(2,ticketID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void assignTicketToAgent(int ticketID, int agentID){
        String sql = "UPDATE tbl_tickets set AgentID = ? where TicketID = ?";

        try (Connection connection = DatabaseConnectionUtils.getConnection()){
            assert connection != null;
            try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,agentID);
                preparedStatement.setInt(2,ticketID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void  recategorizeTicketDetails(
            int userID,
            int agentID,
            int categoryID,
            String title,
            String status,
            String priority,
            int ticketID
    ){
        // todo update these to quick close a ticket then update put message history too

        String sql = "UPDATE tbl_tickets set UserID=?, AgentID=?, CategoryID=?, Title=?, Status=?, Priority=? Where TicketID = ?";
        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1,userID);
                preparedStatement.setInt(2,agentID);
                preparedStatement.setInt(3,categoryID);
                preparedStatement.setString(4,title);
                preparedStatement.setString(5,status);
                preparedStatement.setString(6, priority);
                preparedStatement.setInt(7,ticketID);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
