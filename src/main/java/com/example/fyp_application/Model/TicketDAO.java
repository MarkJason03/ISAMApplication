package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


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

    public static int countOngoingTicketByUserID(int userID){
        int ticketCount = 0;
        String sql = """
                SELECT
                	COUNT(requests.Status)
                FROM
                	tbl_tickets as requests
                JOIN
                	tbl_Users as user ON user.UserID = requests.UserID
                LEFT JOIN
                	tbl_Users as agent ON agent.UserID = requests.AgentID
                WHERE
                	requests.UserID = ? AND requests.Status != 'Closed';
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()){
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,userID);
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()){
                        ticketCount = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketCount;
    }


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


    public static ObservableList<TicketModel>getTicketInformationByAgentID(int agentID){
        ObservableList<TicketModel> ticketDetailsPerAgentID = FXCollections.observableArrayList();
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
                WHERE request.AgentID = ? and request.Status != 'Closed';
                """;

        try(Connection connection = DatabaseConnectionUtils.getConnection()){
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,agentID);
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()){
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
                        ticketDetailsPerAgentID.add(ticket);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketDetailsPerAgentID;
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


    public static int  countCreatedTicketsByAgent(){
        int counter = 0;

        String sql = """
                SELECT
                	Count (request.Status)
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
                WHERE request.Status = 'Created';
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()){
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()){
                        counter = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    public static int countInProgressTicketByAgent(int agentID){
        int counter = 0;

        String sql = """
            SELECT
                Count (request.Status)
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
            WHERE request.AgentID = ? AND request.Status != 'Created';
            """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()){
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, agentID); // Set the agentID parameter
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()){
                        counter = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    public static List<XYChart.Data<String, Number>> getTicketVolumeForMonth(LocalDate startDate) {
        List<XYChart.Data<String, Number>> dataPoints = new ArrayList<>();

        String sql = """
                SELECT
                    strftime('%Y-%m-%d', DateCreated) AS CreationDate,
                    COUNT(*) AS TicketCount
                FROM
                    tbl_Tickets
                WHERE
                    DateCreated BETWEEN ? AND ?
                GROUP BY
                    strftime('%Y-%m-%d', DateCreated)
                ORDER BY
                    CreationDate;
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, startDate.toString());
                preparedStatement.setString(2, startDate.plusMonths(1).toString());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String date = resultSet.getString("CreationDate");
                        LocalDate localDate = LocalDate.parse(date);
                        String formattedDate = localDate.format(DateTimeFormatter.ofPattern("MM-dd"));
                        int count = resultSet.getInt("TicketCount");
                        dataPoints.add(new XYChart.Data<>(formattedDate, count));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataPoints;
    }

    public static int countTotalBreachedTicketsForMonth(LocalDate startDate) {
        int count = 0;
        // Format the start date to "yyyy-MM" for the SQL query
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String formattedStartDate = startDate.format(formatter);

        String sql = """
            SELECT
                SUM(CASE WHEN julianday(TargetResolution) < julianday('now') THEN 1 ELSE 0 END) AS BreachedTicketCount
            FROM
                tbl_Tickets
            WHERE
                strftime('%Y-%m', DateCreated) = ?
                AND Status != 'Closed';
            """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, formattedStartDate);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        count = resultSet.getInt("BreachedTicketCount");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }


    public static int countTotalCreatedCalls() {
        int count = 0;
        String sql = """
                SELECT
                    COUNT(*)
                FROM
                    tbl_tickets
                WHERE
                    Status = 'Created';
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        count = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;

    }

    public static int countTotalOngoingCalls() {
        int count = 0;
        String sql = """
                SELECT
                    COUNT(*)
                FROM
                    tbl_tickets
                WHERE
                    Status = 'In Progress' OR Status = 'Awaiting Response' AND Status != 'Closed';
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        count = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int countTotalBreachedCalls(){
        int counter = 0;

        String sql = """
                SELECT
                    SUM(CASE
                            WHEN request.Status != 'Closed' AND date(request.TargetResolution) < date('now')
                                 AND (request.Status = 'In Progress' OR request.Status = 'Awaiting Response') THEN 1
                            ELSE 0
                        END) AS Breached
                FROM
                    tbl_tickets AS request
                WHERE request.Status != 'Closed';
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()){
                        counter = resultSet.getInt("Breached");
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    public static int countCreatedCallsFromClient(int userID){
        int counter = 0;

        String sql = """
                SELECT
                    COUNT(*)
                FROM
                    tbl_tickets
                WHERE
                    UserID = ? AND Status = 'Created';
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,userID);
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()){
                        counter = resultSet.getInt(1);
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    public static int countOngoingCallsFromClient(int userID){
        int counter = 0;

        String sql = """
                SELECT
                    COUNT(*)
                FROM
                    tbl_tickets
                WHERE
                    UserID = ? AND (Status = 'In Progress' OR Status = 'Awaiting Response') AND Status != 'Closed';
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,userID);
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()){
                        counter = resultSet.getInt(1);
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    public static int countResolvedPreviousCallsFromClient(int userID){
        int counter = 0;

        String sql = """
                SELECT
                    COUNT(*)
                FROM
                    tbl_tickets
                WHERE
                    UserID = ? AND Status = 'Closed';
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,userID);
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()){
                        counter = resultSet.getInt(1);
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }
}