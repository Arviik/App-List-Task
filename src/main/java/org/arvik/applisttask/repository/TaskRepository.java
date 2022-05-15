package org.arvik.applisttask.repository;

import org.arvik.applisttask.modele.List;
import org.arvik.applisttask.modele.Task;
import org.arvik.applisttask.modele.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TaskRepository extends Repository {
    private final UserRepository userRepo = new UserRepository();
    private final ListRepository listRepo = new ListRepository();
    private final StateRepository stateRepo = new StateRepository();
    private final TypeRepository typeRepo = new TypeRepository();

    public Task save(Task task) throws SQLException {
        PreparedStatement req;
        if (task.getId_tache() == 0) {
            req = getDatabase().getCnx().prepareStatement("INSERT INTO tache(libelle, description, difficulte, date_debut, date_fin, date_butoir, ref_compte, ref_liste, ref_type) VALUES (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            fillSaveRequest(task, req);
            ResultSet res = req.getGeneratedKeys();
            if (res.next()) {
                task.setId_tache(res.getInt(1));
            }
        } else {
            req = getDatabase().getCnx().prepareStatement("UPDATE tache SET libelle = ?, description = ?, difficulte = ?, date_debut = ?, date_fin = ?, date_butoir = ?, ref_compte = ?, ref_liste = ?, ref_type = ?, ref_etat = ? WHERE id_tache = ?");
            req.setInt(10, task.getRef_etat().getId_etat());
            req.setInt(11, task.getId_tache());
            fillSaveRequest(task, req);
        } try {
            getDatabase().getCnx().close();
        } catch (SQLException e) {
            e.printStackTrace();
        } return task;
    }

    private void fillSaveRequest(Task task, PreparedStatement req) throws SQLException {
        req.setString(1, task.getLibelle());
        req.setString(2, task.getDescription());
        req.setString(3, task.getDifficulte());
        req.setString(4, task.getDate_debut());
        req.setString(5, task.getDate_fin());
        req.setString(6, task.getDate_butoir());
        req.setInt(7, task.getRef_compte().getId_compte());
        req.setInt(8, task.getRef_liste().getId_liste());
        req.setInt(9, task.getRef_type().getId_type());
        req.executeUpdate();
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("Select * FROM tache;");
            fillArrayListofTask(tasks, req, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                getDatabase().getCnx().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } return tasks;
    }

    public ArrayList<Task> getTasksByList(List list) {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("Select * FROM tache WHERE ref_liste = ?;");
            req.setInt(1, list.getId_liste());
            fillArrayListofTask(tasks, req, list);
        } catch (SQLException e) {
            e.printStackTrace();
        } return tasks;
    }

    public ArrayList<Task> getTasksByLists(ArrayList<List> lists) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (List list : lists){
            tasks.addAll(getTasksByList(list));
        } return tasks;
    }

    private void fillArrayListofTask(ArrayList<Task> tasks, PreparedStatement req, List list) {
        try {
            ResultSet res = req.executeQuery();
            while (res.next()) {
                tasks.add(new Task(
                        res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6),
                        res.getString(7),
                        userRepo.getUser(res.getInt(8)),
                        list,//listRepo.getList(res.getInt(9), new TaskRepository()),
                        stateRepo.getState(res.getInt(10)),
                        typeRepo.getType(res.getInt(11))
                ));
            }
            req.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Task getTask(int id_tache){
        Task task = null;
        try {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("Select * FROM tache WHERE id_tache = ?;");
            req.setInt(1, id_tache);
            ResultSet res = req.executeQuery();
            if (res.next()) {
                task = new Task(
                        res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6),
                        res.getString(7),
                        userRepo.getUser(res.getInt(8)),
                        listRepo.getList(res.getInt(9),new TaskRepository()),
                        stateRepo.getState(res.getInt(10)),
                        typeRepo.getType(res.getInt(11))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                getDatabase().getCnx().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } return task;
    }

    public void deleteTask(Task task) throws SQLException {
        if (task.getId_tache()>0) {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("DELETE FROM tache WHERE id_tache = ?;");
            req.setInt(1, task.getId_tache());
            req.executeUpdate();
        }
    }
}