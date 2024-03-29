package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ProcurementRequestDAO {



    public static int insertProcurementRequest(int userID, String procurementStatus, String dateRaised, String requesterComment) {
        String sql = "INSERT INTO tbl_Procurement (UserID,  ProcurementStatus, DateRaised, RequesterComment) VALUES (?,?,?,?)";

        //
        int procurementID = -1;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            // Update the statement to return the generated keys.
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, userID);
                preparedStatement.setString(2, procurementStatus);
                preparedStatement.setString(3, dateRaised);
                preparedStatement.setString(4, requesterComment);


                preparedStatement.executeUpdate();

                // Retrieve the generated procurement ID which will be used to attach to the basket
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        procurementID = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return procurementID;
    }


    public static ObservableList<ProcurementRequestModel> getShortenedProcurementDetails(int procurementID) {
        ObservableList<ProcurementRequestModel> procurementDetails = FXCollections.observableArrayList();
        ProcurementRequestModel procurementRequestModel;

        String sql = """
                select
                   user.UserID,
                   officer.UserID AS ProcurementManagerID,
                   RequesterComment
                	
                from tbl_Procurement as procurement
                JOIN tbl_Users AS user ON user.UserID = procurement.UserID
                LEFT JOIN tbl_Users AS officer ON officer.UserID = procurement.ProcurementManagerID
                WHERE procurement.ProcurementID = ?;
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, procurementID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        procurementRequestModel = new ProcurementRequestModel(
                                resultSet.getInt("UserID"),
                                resultSet.getInt("ProcurementManagerID"),
                                resultSet.getString("RequesterComment")
                        );
                        procurementDetails.add(procurementRequestModel);
                    }
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return procurementDetails;
    }


    public static ObservableList<ProcurementRequestModel> getProcurementTicketsByUser(int userID) {
        ObservableList<ProcurementRequestModel> procurementRequestPerUser = FXCollections.observableArrayList();
        ProcurementRequestModel procurementRequestModel;

        String sql = """
                SELECT
                	procurement.ProcurementID,
                	procurement.ProcurementStatus,
                	procurement.ProcurementComment
                	
                FROM tbl_Procurement as procurement
                JOIN tbl_Users as user on user.UserID = procurement.UserID
                where procurement.UserID = ?;
                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        procurementRequestModel = new ProcurementRequestModel(
                                resultSet.getInt("ProcurementID"),
                                resultSet.getString("ProcurementStatus"),
                                resultSet.getString("ProcurementComment")
                        );
                        procurementRequestPerUser.add(procurementRequestModel);
                    }
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return procurementRequestPerUser;
    }

    public static void updateProcurementRequest(int procurementID, int procurementOfficerID, String procurementStatus, String dateClosed, String procurementManagerComment) {
        String sql = "UPDATE tbl_Procurement SET ProcurementManagerID = ?, ProcurementStatus = ?, DateClosed = ?, ProcurementComment = ? WHERE ProcurementID = ?";

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, procurementOfficerID);
                preparedStatement.setString(2, procurementStatus);
                preparedStatement.setString(3, dateClosed);
                preparedStatement.setString(4, procurementManagerComment);
                preparedStatement.setInt(5, procurementID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }

}
