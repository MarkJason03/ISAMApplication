package com.example.fyp_application.Model;

import com.example.fyp_application.Utils.DatabaseConnectionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class AssetAllocationDAO {


    public static int insertAssetAllocation(int assetID,
                                            int userID,
                                            int agentID,
                                            int officeID,
                                            String loanType,
                                            String startDate,
                                            String dueDate,
                                            String allocationStatus,
                                            String assetConditionBefore,
                                            String additionalComments) {
        //sql query to insert data into the table
        String sql = """
                INSERT INTO tbl_AllocationHistory(
                		AssetID,
                		UserID,
                		AgentID,
                		OfficeID,
                		LoanType, --Loan / Staff Issue
                		StartDate, -- Start Date of Loan
                		DueDate, --Due date of loan
                		AllocationStatus, -- In use
                		AssetConditionBefore, -- Asset Condition before loan
                		AdditionalComments)
                VALUES (?,?,?,?,?,?,?,?,?,?)
                """;

        int allocationID = -1;
        //Try with resources to close the connection after the operation is done
        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            //prepared statement to execute the query
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                //set the values to the query
                preparedStatement.setInt(1, assetID);
                preparedStatement.setInt(2, userID);
                preparedStatement.setInt(3, agentID);
                preparedStatement.setInt(4, officeID);
                preparedStatement.setString(5, loanType);
                preparedStatement.setString(6, startDate);
                preparedStatement.setString(7, dueDate);
                preparedStatement.setString(8, allocationStatus);
                preparedStatement.setString(9, assetConditionBefore);
                preparedStatement.setString(10, additionalComments);

                //execute the query
                preparedStatement.executeUpdate();

                // Retrieve the generated allocation ID.
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        allocationID = generatedKeys.getInt(1);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    return allocationID;
    }


    public static void updateAllocation(int allocationID,
                                            String dueDate,
                                            String endDate,
                                            String allocationStatus,
                                            String assetConditionAfter,
                                            String additionalComments) {
        //sql query to insert data into the table
        String sql = """
                UPDATE tbl_AllocationHistory
                SET DueDate = ?,
                    EndDate = ?,
                    AllocationStatus = ?,
                    AssetConditionAfter = ?,
                    AdditionalComments = ?
                WHERE AllocationHistoryID = ?
                """;


        //Try with resources to close the connection after the operation is done
        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            //prepared statement to execute the query
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                //set the values to the query
                preparedStatement.setString(1, dueDate);
                preparedStatement.setString(2, endDate);
                preparedStatement.setString(3, allocationStatus);
                preparedStatement.setString(4, assetConditionAfter);
                preparedStatement.setString(5, additionalComments);
                preparedStatement.setInt(6, allocationID);
                //execute the query
                preparedStatement.executeUpdate();
          }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<AssetAllocationModel> getAssetAllocations() {
        ObservableList<AssetAllocationModel> assetAllocations = FXCollections.observableArrayList();
        String sql = """
                SELECT\s
                    -- Allocation Information
                    allocation.AllocationHistoryID, -- ID
                    allocation.AssetID,
                    allocation.AllocationStatus, -- Allocation Status
                    allocation.OverdueStatus,\s
                	allocation.LoanType,
                    allocation.StartDate, -- Allocation Start Date
                    allocation.DueDate, -- Allocation Due Date
                    allocation.EndDate,
                    allocation.AdditionalComments, -- Additional Comments

                	    -- Calculate Overdue Days
                    CASE
                        WHEN allocation.AllocationStatus != 'Return' AND date(allocation.DueDate) < date('now')
                             AND allocation.AllocationStatus = 'In Use'
                        THEN CAST(ROUND(julianday(date('now')) - julianday(date(allocation.DueDate))) AS INTEGER)
                        ELSE 0
                    END AS OverdueDays,
                    
                    -- Asset Information
                    asset.AssetName,
                    asset.SerialNo,
                    manu.ManufacturerName as Manufacturer,
                    cat.assetCategoryName as Category,
                    asset.StorageSpec,
                    asset.RamSpec,
                    asset.AssetCondition,
                    asset.AssetStatus,
                   \s
                    -- User Information
                    user.FirstName,
                    user.LastName,
                    dept.deptName as Department,
                    user.Username,
                    user.Email,
                    user.Phone,

                    -- Building Information
                    building.BuildingName,
                    office.OfficeName
                   \s

                   \s
                FROM\s
                    tbl_AllocationHistory as allocation
                    JOIN tbl_Assets as asset ON asset.AssetID = allocation.AssetID
                    JOIN tbl_assetCategory as cat ON cat.assetCategoryID = asset.assetCategoryID
                    JOIN tbl_assetManufacturer as manu ON manu.ManufacturerID = asset.ManufacturerID
                    JOIN tbl_BuildingOffices as office ON office.OfficeID = allocation.OfficeID
                    JOIN tbl_Buildings as building ON building.BuildingID = office.BuildingID
                    JOIN tbl_Users as user ON user.UserID = allocation.UserID
                    JOIN tbl_Departments as dept ON dept.deptID = user.deptID;

                                """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        AssetAllocationModel assetAllocation = new AssetAllocationModel(
                                resultSet.getInt("AllocationHistoryID"),
                                resultSet.getInt("AssetID"),
                                resultSet.getString("AllocationStatus"),
                                resultSet.getString("OverdueStatus"), // Overdue Status
                                resultSet.getString("LoanType"),
                                resultSet.getString("StartDate"),
                                resultSet.getString("DueDate"),
                                resultSet.getString("EndDate"),
                                resultSet.getString("AdditionalComments"),
                                resultSet.getString("OverdueDays"),
                                resultSet.getString("AssetName"),
                                resultSet.getString("SerialNo"),
                                resultSet.getString("Manufacturer"),
                                resultSet.getString("Category"),
                                resultSet.getString("StorageSpec"),
                                resultSet.getString("RamSpec"),
                                resultSet.getString("AssetCondition"),
                                resultSet.getString("AssetStatus"),
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getString("Department"),
                                resultSet.getString("Username"),
                                resultSet.getString("Email"),
                                resultSet.getString("Phone"),
                                resultSet.getString("BuildingName"),
                                resultSet.getString("OfficeName")
                        );
                        assetAllocations.add(assetAllocation);
                    }
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return assetAllocations;
    }

    public static ObservableList<AssetAllocationModel> getFilteredAllocations(String filter) {
        ObservableList<AssetAllocationModel> assetAllocations = FXCollections.observableArrayList();
        String sql = """
                SELECT\s
                    -- Allocation Information
                    allocation.AllocationHistoryID, -- ID
                    allocation.AssetID,
                    allocation.AllocationStatus, -- Allocation Status
                    allocation.OverdueStatus,\s
                	allocation.LoanType,
                    allocation.StartDate, -- Allocation Start Date
                    allocation.DueDate, -- Allocation Due Date
                    allocation.EndDate,
                    allocation.AdditionalComments, -- Additional Comments

                	    -- Calculate Overdue Days
                    CASE
                        WHEN allocation.AllocationStatus != 'Return' AND date(allocation.DueDate) < date('now')
                             AND allocation.AllocationStatus = 'In Use'
                        THEN CAST(ROUND(julianday(date('now')) - julianday(date(allocation.DueDate))) AS INTEGER)
                        ELSE 0
                    END AS OverdueDays,
                                        
                    -- Asset Information
                    asset.AssetName,
                    asset.SerialNo,
                    manu.ManufacturerName as Manufacturer,
                    cat.assetCategoryName as Category,
                    asset.StorageSpec,
                    asset.RamSpec,
                    asset.AssetCondition,
                    asset.AssetStatus,
                   \s
                    -- User Information
                    user.FirstName,
                    user.LastName,
                    dept.deptName as Department,
                    user.Username,
                    user.Email,
                    user.Phone,

                    -- Building Information
                    building.BuildingName,
                    office.OfficeName

                FROM\s
                    tbl_AllocationHistory as allocation
                    JOIN tbl_Assets as asset ON asset.AssetID = allocation.AssetID
                    JOIN tbl_assetCategory as cat ON cat.assetCategoryID = asset.assetCategoryID
                    JOIN tbl_assetManufacturer as manu ON manu.ManufacturerID = asset.ManufacturerID
                    JOIN tbl_BuildingOffices as office ON office.OfficeID = allocation.OfficeID
                    JOIN tbl_Buildings as building ON building.BuildingID = office.BuildingID
                    JOIN tbl_Users as user ON user.UserID = allocation.UserID
                    JOIN tbl_Departments as dept ON dept.deptID = user.deptID
                    WHERE allocation.AllocationStatus = ?;
                     """;

        try (Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, filter);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        AssetAllocationModel assetAllocation = new AssetAllocationModel(
                                resultSet.getInt("AllocationHistoryID"),
                                resultSet.getInt("AssetID"),
                                resultSet.getString("AllocationStatus"),
                                resultSet.getString("OverdueStatus"), // Overdue Status
                                resultSet.getString("LoanType"),
                                resultSet.getString("StartDate"),
                                resultSet.getString("DueDate"),
                                resultSet.getString("EndDate"),
                                resultSet.getString("AdditionalComments"),
                                resultSet.getString("OverdueDays"),
                                resultSet.getString("AssetName"),
                                resultSet.getString("SerialNo"),
                                resultSet.getString("Manufacturer"),
                                resultSet.getString("Category"),
                                resultSet.getString("StorageSpec"),
                                resultSet.getString("RamSpec"),
                                resultSet.getString("AssetCondition"),
                                resultSet.getString("AssetStatus"),
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getString("Department"),
                                resultSet.getString("Username"),
                                resultSet.getString("Email"),
                                resultSet.getString("Phone"),
                                resultSet.getString("BuildingName"),
                                resultSet.getString("OfficeName")
                        );
                        assetAllocations.add(assetAllocation);
                    }
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return assetAllocations;
    }


    public static ObservableList<AssetAllocationModel> getAllocationDetailsByUser(int userID){
        ObservableList<AssetAllocationModel> assetAllocationsByUser = FXCollections.observableArrayList();
        String sql = """
                SELECT
                	-- Allocation Information
                	allocation.AllocationHistoryID, -- ID
                	allocation.AssetID,
                	allocation.AllocationStatus, -- Allocation Status
                	allocation.OverdueStatus,
                	-- Calculate Overdue Days
                CASE
                    WHEN allocation.AllocationStatus != 'Return' AND date(allocation.DueDate) < date('now')
                         AND allocation.AllocationStatus = 'In Use'
                    THEN CAST(ROUND(julianday(date('now')) - julianday(date(allocation.DueDate))) AS INTEGER)
                    ELSE 0
                END AS OverdueDays,
                	allocation.LoanType,
                	allocation.StartDate, -- Allocation Start Date
                	allocation.DueDate, -- Allocation Due Date
                	allocation.EndDate, -- Allocation End Date
                	allocation.AdditionalComments, -- Additional Comments
                                
                                
                	-- Asset Information
                	asset.AssetName,
                	asset.SerialNo,
                	manu.ManufacturerName as Manufacturer,
                	cat.assetCategoryName as Category,
                	asset.StorageSpec,
                	asset.PhotoPath,
                	asset.RamSpec,
                	asset.AssetCondition,
                	asset.AssetStatus,
                  \s
                	-- User Information
                	user.FirstName,
                	user.LastName,
                	dept.deptName as Department,
                	user.Username,
                	user.Email,
                	user.Phone,
                                
                	-- Building Information
                	building.BuildingName,
                	office.OfficeName
                FROM
                	tbl_AllocationHistory as allocation
                	JOIN tbl_Assets as asset ON asset.AssetID = allocation.AssetID
                	JOIN tbl_assetCategory as cat ON cat.assetCategoryID = asset.assetCategoryID
                	JOIN tbl_assetManufacturer as manu ON manu.ManufacturerID = asset.ManufacturerID
                	JOIN tbl_BuildingOffices as office ON office.OfficeID = allocation.OfficeID
                	JOIN tbl_Buildings as building ON building.BuildingID = office.BuildingID
                	JOIN tbl_Users as user ON user.UserID = allocation.UserID
                	JOIN tbl_Departments as dept ON dept.deptID = user.deptID
                                
                WHERE allocation.AllocationStatus != 'Returned' AND allocation.UserID = ?;
                """;
        try(Connection connection = DatabaseConnectionUtils.getConnection()){
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, userID);
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while(resultSet.next()){
                        AssetAllocationModel assetAllocation = new AssetAllocationModel(
                                resultSet.getInt("AllocationHistoryID"),
                                resultSet.getInt("AssetID"),
                                resultSet.getString("AllocationStatus"),
                                resultSet.getString("OverdueStatus"), // Overdue Status
                                resultSet.getString("OverdueDays"),
                                resultSet.getString("LoanType"),
                                resultSet.getString("StartDate"),
                                resultSet.getString("DueDate"),
                                resultSet.getString("EndDate"),
                                resultSet.getString("AdditionalComments"),
                                resultSet.getString("AssetName"),
                                resultSet.getString("SerialNo"),
                                resultSet.getString("Manufacturer"),
                                resultSet.getString("Category"),
                                resultSet.getString("StorageSpec"),
                                resultSet.getString("PhotoPath"),
                                resultSet.getString("RamSpec"),
                                resultSet.getString("AssetCondition"),
                                resultSet.getString("AssetStatus"),
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getString("Department"),
                                resultSet.getString("Username"),
                                resultSet.getString("Email"),
                                resultSet.getString("Phone"),
                                resultSet.getString("BuildingName"),
                                resultSet.getString("OfficeName")
                        );
                        assetAllocationsByUser.add(assetAllocation);
                    }
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }

        return assetAllocationsByUser;
    }


    public static void checkAndUpdateOverdueAllocation(){

        String sql = """
                UPDATE tbl_AllocationHistory
                SET OverdueStatus = CASE
                                        WHEN AllocationStatus = 'In Use' AND DueDate < date('now') THEN 'Overdue'
                                        WHEN AllocationStatus = 'Return' THEN 'No'  --  "No" for "Return" asset status
                                        ELSE 'No'  -- Setting "No" as the default for all other cases
                                    END
                WHERE AllocationStatus IN ('In Use', 'Return') OR DueDate < date('now');

                                """;


        try( Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.executeUpdate();
                System.out.println("\nPrepared Statement Executed");
                DatabaseConnectionUtils.closeConnection(connection);
                System.out.println("Overdue Allocation Status has been Updated\n");
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }

    }

    public static int countAssetsByUserID(int currentLoggedUserID) {

        int assetCounter = 0;
        String sql = """
                SELECT COUNT(*) FROM tbl_AllocationHistory
                 WHERE UserID = ? AND AllocationStatus != 'Returned';
                """;

        try(Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, currentLoggedUserID);
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()) {
                        assetCounter = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return assetCounter;
    }

    public static double calculateTotalAssetCostByUserID(int currentLoggedUserID) {
        double totalAssetCost = 0;

        String sql = """
                SELECT
                    SUM(asset.AssetPrice)
                FROM
                    tbl_AllocationHistory as allocation
                JOIN
                    tbl_Assets as asset on asset.AssetID = allocation.AssetID
                WHERE
                    allocation.UserID = ? AND AllocationStatus != 'Returned';
                """;
        try(Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, currentLoggedUserID);
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()) {
                        totalAssetCost = resultSet.getDouble(1);
                    }
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return totalAssetCost;
    }


    public static int countTotalOverdueAssets(){
        int totalOverdueAssets = 0;

        String sql = """
                Select
                	count (*) from tbl_AllocationHistory
                WHERE OverdueStatus = 'Overdue';
                """;

        try(Connection connection = DatabaseConnectionUtils.getConnection()) {
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()) {
                        totalOverdueAssets = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return totalOverdueAssets;
    }
}
