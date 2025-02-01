package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connectivity {
    public Connection connection;
    public Connection getConnection(){
        String dbName="iti";
        String username="root";
        String password="root";
        try {
            //  Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName,username,password);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }
}
