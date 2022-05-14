package org.arvik.applisttask.modele;

import java.util.ArrayList;

public class List {
    private int id_liste;
    private String libelle;
    private String description;
    private ArrayList<User> users;
    private ArrayList<Task> tasks;

    public List(int id_liste, String libelle, String description, ArrayList<User> users, ArrayList<Task> tasks) {
        this.id_liste = id_liste;
        this.libelle = libelle;
        this.description = description;
        this.users = users;
        this.tasks = tasks;
    }

    public List(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
    }

    public int getId_liste() {
        return id_liste;
    }

    public void setId_liste(int id_liste) {
        this.id_liste = id_liste;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsers() {
        StringBuilder usersStb = new StringBuilder();
        for (User user : users) {
            usersStb.append(user.toString()).append("; ");
        }
        return usersStb.toString();
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getTasks() {
        StringBuilder tasksStb = new StringBuilder();
        for (Task task : tasks) {
            tasksStb.append(task.getLibelle()).append("; ");
        }
        return tasksStb.toString();
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}