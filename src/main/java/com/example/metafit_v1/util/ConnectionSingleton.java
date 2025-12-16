package com.example.metafit_v1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
    private static String DB_HOST = "localhost";
    private static String DB_PORT = "3306";
    private static String DB_NAME = "metafit";
    private static String DB_USER = "root";
    private static String DB_PASS = "armabir1";
    private static String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    private static ConnectionSingleton ins = new ConnectionSingleton();

    private ConnectionSingleton() {
        // Empty constructor - no persistent connection
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ConnectionSingleton getInstance() {
        return ins;
    }
}