package com.example.fyp_application.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Helper class to handle database connections
public class DatabaseHandler {

    //src/main/resources/db/ISAMDB.db
    //  jdbc:sqlite:/D:\FYP_Application\src\main\resources\db\ISAMDB.db
    private static final String dbUrl = "jdbc:sqlite:/D:\\FYP_Application\\src\\main\\resources\\db\\ISAMDB.db";

    public static Connection getConnection() {
        try{
            return DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            return null;
        }
    }


    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
