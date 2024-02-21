package com.example.fyp_application.Model;

import com.example.fyp_application.Controllers.AlertHandlerController;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

//Helper class to handle database connections
public class DatabaseHandler {
    //src/main/resources/db/ISAMDB.db
    //  jdbc:sqlite:/D:\FYP_Application\src\main\resources\db\ISAMDB.db
    //
    private static final String dbUrl = "jdbc:sqlite:/D:\\FYP_Application\\src\\main\\resources\\db\\ISAMDB.db";
    public static Connection getConnection() {
        try{
            Connection connection = DriverManager.getConnection(dbUrl);
            return connection;
        } catch (SQLException e) {
            return null;
        }
    }
}
