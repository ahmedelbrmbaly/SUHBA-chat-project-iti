package com.suhba.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";


    private static final String URL = "jdbc:mysql://localhost:3306/iti";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Connection connection;

    private DatabaseConnection() {

    }

    public static Connection getInstance() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            synchronized (DatabaseConnection.class) {
                if (connection == null || connection.isClosed()) {
                    Class.forName(DRIVER);
                    connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    System.out.println("Database connected!");
                }
            }
        }
        return connection;
    }
}
