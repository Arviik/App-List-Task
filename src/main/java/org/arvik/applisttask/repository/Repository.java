package org.arvik.applisttask.repository;

import org.arvik.applisttask.database.Database;

public abstract class Repository {
    private static final Database database = new Database();

    public Repository() {

    }

    public static Database getDatabase() {
        return database;
    }
}