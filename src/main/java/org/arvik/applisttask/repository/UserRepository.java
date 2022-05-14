package org.arvik.applisttask.repository;

import org.arvik.applisttask.modele.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserRepository extends Repository {

    public User save(User user) throws SQLException {
        PreparedStatement req;
        if (user.getId_compte() == 0) {
            req = getDatabase().getCnx().prepareStatement("INSERT INTO compte(nom, prenom, email, mdp) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            fillSaveRequest(user, req);
            req.executeUpdate();
            ResultSet res = req.getGeneratedKeys();
            if (res.next()) {
                user.setId_compte(res.getInt(1));
            }
        } else {
            req = getDatabase().getCnx().prepareStatement("UPDATE compte SET nom = ?, prenom = ?, email = ?, mdp = ?, estAdmin = ? WHERE id_compte = ?");
            fillSaveRequest(user, req);
            req.setBoolean(5, user.isEstAdmin());
            req.setInt(6, user.getId_compte());
            req.executeUpdate();
        } try {
            getDatabase().getCnx().close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                getDatabase().getCnx().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } return user;
    }

    private void fillSaveRequest(User user, PreparedStatement req) throws SQLException {
        req.setString(1, user.getNom());
        req.setString(2, user.getPrenom());
        req.setString(3, user.getEmail());
        req.setString(4, user.getMdp());
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("Select * FROM compte;");
            ResultSet res = req.executeQuery();
            while (res.next()) {
                users.add(new User(
                        res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getBoolean(6)
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
        } return users;
    }

    public User getUser(int id_user) {
        User user = null;
        try {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("Select * FROM compte WHERE id_compte = ?");
            req.setInt(1, id_user);
            ResultSet res = req.executeQuery();
            if (res.next()) {
                user = new User(
                        res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getBoolean(6)
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
        } return user;
    }

    public User getUserByMail(String email) {
        User user = null;
        try {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("Select * FROM compte WHERE email = ?");
            req.setString(1, email);
            ResultSet res = req.executeQuery();
            if (res.next()) {
                user = new User(
                        res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getBoolean(6)
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
        } return user;
    }

    public ArrayList<User> getUserByList(int id_List) {
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("Select compte.* FROM compte LEFT JOIN gere ON compte.id_compte = gere.ref_compte WHERE ref_liste = ?");
            req.setInt(1, id_List);
            ResultSet res = req.executeQuery();
            while (res.next()) {
                assert false;
                users.add(new User(
                        res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getBoolean(6)
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
        } return users;
    }

    public User connexion(String email, String mdp) {
        User user = null;
        try {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("Select * FROM compte WHERE email = ? AND mdp = ?");
            req.setString(1, email);
            req.setString(2, mdp);
            ResultSet res = req.executeQuery();
            if (res.next()) {
                user = new User(
                        res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getBoolean(6)
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
        } return user;
    }

    public void deleteUser(User user) throws SQLException {
        if (user.getId_compte() > 0) {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("DELETE FROM compte WHERE id_compte = ?");
            req.setInt(1, user.getId_compte());
            req.executeUpdate();
            req = getDatabase().getCnx().prepareStatement("DELETE FROM gere WHERE ref_compte = ?");
            req.setInt(1, user.getId_compte());
            req.executeUpdate();
        } try {
            getDatabase().getCnx().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changePassword(User user) throws SQLException {
        PreparedStatement req = getDatabase().getCnx().prepareStatement("UPDATE compte SET mdp = ? WHERE id_compte = ?");
        req.setString(1, user.getMdp());
        req.setInt(2, user.getId_compte());
        req.executeUpdate();
    }
}