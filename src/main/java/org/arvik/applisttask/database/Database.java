package org.arvik.applisttask.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection cnx;

    public Connection getCnx() {
        try {
            cnx = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/app-list-task?serverTimezone=UTC",
                    "app-list-task",
                    "app-list-task"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        } return cnx;
    }
}