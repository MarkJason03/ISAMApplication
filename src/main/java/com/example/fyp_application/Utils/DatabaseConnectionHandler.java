package com.example.fyp_application.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Helper class to handle database connections
public class DatabaseConnectionHandler {


    /*private static final String dbUrl = "jdbc:sqlite:/D:\\FYP_Application\\src\\main\\resources\\db\\ISAMDB.db";*/
    //private static final String dbUrl = "jdbc:sqlite::resource:db/ISAMDB.db";
    private static final String dbUrl = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/main/resources/db/ISAMDB.db";

    public static Connection getConnection() {
        try{
            return DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            return null;
        }
    }


    public static boolean isDbConnected(Connection connection) {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
