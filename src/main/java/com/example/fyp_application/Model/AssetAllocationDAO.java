package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionHandler;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AssetAllocationDAO {


/*    public <ObservableList> ObservableList<AssetAllocationModel> getAssetAllocations() {
        return null;
    }
    */
/*

    public static ObservableList<AssetAllocationModel> getAssetAllocations() {
        String
    }
*/


    public static void insertAssetAllocation( int assetID,
                                              int userID,
                                              int agentID,
                                              int officeID,
                                              String loanType,
                                              String startDate,
                                              String dueDate,
                                              String endDate,
                                              String allocationStatus,
                                              String assetConditionBefore,
                                              String assetConditionAfter,
                                              String additionalComments) {
        //sql query to insert data into the table
        String sql = "insert into tbl_assetAllocation (AssetID, UserID, AgentID, OfficeID, LoanType, StartDate, DueDate, EndDate, AllocationStatus, AssetConditionBefore, AssetConditionAfter, AdditionalComments) values (?,?,?,?,?,?,?,?,?,?,?,?)";
        //Try with resources to close the connection after the operation is done
        try (Connection connection = DatabaseConnectionHandler.getConnection()) {
            assert connection != null;
            //prepared statement to execute the query
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                //set the values to the query
                preparedStatement.setInt(1, assetID);
                preparedStatement.setInt(2, userID);
                preparedStatement.setInt(3, agentID);
                preparedStatement.setInt(4, officeID);
                preparedStatement.setString(5, loanType);
                preparedStatement.setString(6, startDate);
                preparedStatement.setString(7, dueDate);
                preparedStatement.setString(8, endDate);
                preparedStatement.setString(9, allocationStatus);
                preparedStatement.setString(10, assetConditionBefore);
                preparedStatement.setString(11, assetConditionAfter);
                preparedStatement.setString(12, additionalComments);

                //execute the query
                preparedStatement.executeUpdate();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
