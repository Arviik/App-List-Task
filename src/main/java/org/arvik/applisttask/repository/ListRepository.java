package org.arvik.applisttask.repository;

import org.arvik.applisttask.modele.List;
import org.arvik.applisttask.modele.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ListRepository extends Repository {
    private final UserRepository userRepo = new UserRepository();

    public List save(User user, List list) throws SQLException {
        PreparedStatement req;
        if (list.getId_liste() == 0) {
            req = getDatabase().getCnx().prepareStatement("INSERT INTO liste(libelle, description) VALUES (?,?);", Statement.RETURN_GENERATED_KEYS);
            req.setString(1, list.getLibelle());
            req.setString(2, list.getDescription());
            req.executeUpdate();
            ResultSet res = req.getGeneratedKeys();
            if (res.next()) {
                list.setId_liste(res.getInt(1));
            }
            req = getDatabase().getCnx().prepareStatement("INSERT INTO gere(ref_compte, ref_liste) VALUES (?,?);");
            req.setInt(1, user.getId_compte());
            req.setInt(2, list.getId_liste());
            req.executeUpdate();
        } else {
            req = getDatabase().getCnx().prepareStatement("UPDATE liste SET libelle = ?, description = ?  WHERE id_liste = ?;");
            req.setString(1, list.getLibelle());
            req.setString(2, list.getDescription());
            req.setInt(3, list.getId_liste());
            req.executeUpdate();
        } try {
            getDatabase().getCnx().close();
        } catch (SQLException e) {
            e.printStackTrace();
        } return list;
    }

    public void addUserToList(User user, List list) throws SQLException {
        PreparedStatement req = getDatabase().getCnx().prepareStatement("INSERT INTO gere(ref_compte, ref_liste) VALUES (?,?);");
        req.setInt(1, user.getId_compte());
        req.setInt(2, list.getId_liste());
        req.executeUpdate();
        try {
            getDatabase().getCnx().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<List> getLists(TaskRepository taskRepo) {
        ArrayList<List> lists = new ArrayList<>();
        try {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("Select * FROM liste;");
            ResultSet res = req.executeQuery();
            while (res.next()) {
                lists.add(new List(
                        res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        userRepo.getUserByList(res.getInt(1)),
                        taskRepo.getTasksByList(res.getInt(1))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                getDatabase().getCnx().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } return lists;
    }

    public List getList(int id_liste, TaskRepository taskRepo) {
        List list = null;
        try {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("Select * FROM liste WHERE id_liste = ?");
            req.setInt(1, id_liste);
            ResultSet res = req.executeQuery();
            if (res.next()) {
                list = new List(
                        res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        userRepo.getUserByList(res.getInt(1)),
                        taskRepo.getTasksByList(res.getInt(1))
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
        } return list;
    }

    public ArrayList<List> getListsByUser(User user, TaskRepository taskRepo) {
        ArrayList<List> lists = new ArrayList<>();
        try {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("Select liste.* FROM liste LEFT JOIN gere ON liste.id_liste = gere.ref_liste WHERE ref_compte = ?");
            req.setInt(1, user.getId_compte());
            ResultSet res = req.executeQuery();
            UserRepository userRepo = new UserRepository();
            while (res.next()) {
                lists.add(new List(
                        res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        userRepo.getUserByList(res.getInt(1)),
                        taskRepo.getTasksByList(res.getInt(1))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                getDatabase().getCnx().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } return lists;
    }

    public void deleteTask(List list) throws SQLException {
        if (list.getId_liste() > 0) {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("DELETE FROM liste WHERE id_liste = ?");
            req.setInt(1, list.getId_liste());
            req.executeUpdate();
            req = getDatabase().getCnx().prepareStatement("DELETE FROM gere WHERE ref_liste = ?");
            req.setInt(1, list.getId_liste());
            req.executeUpdate();
        }
    }
}
