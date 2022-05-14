package org.arvik.applisttask.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;

public class Database {
    private Connection cnx = null;

    public Connection getCnx() throws SQLException {
        try {
            cnx = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/app-list-task?serverTimezone=UTC",
                    "app-list-task",
                    "app-list-task"
            );
        } catch (SQLNonTransientConnectionException e){
            e.printStackTrace();
            cnx.close();
            cnx = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/app-list-task?serverTimezone=UTC",
                    "app-list-task",
                    "app-list-task"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cnx;
    }
}