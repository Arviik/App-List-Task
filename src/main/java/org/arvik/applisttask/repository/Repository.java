package org.arvik.applisttask.repository;

import org.arvik.applisttask.database.Database;

public abstract class Repository {
    private final Database database;

    public Repository() {
        database = new Database();
    }

    public Database getDatabase() {
        return database;
    }
}