package org.arvik.applisttask.repository;

import org.arvik.applisttask.modele.State;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StateRepository extends Repository {

    public State save(State state) throws SQLException {
        PreparedStatement req;
        if (state.getId_etat() == 0) {
            req = getDatabase().getCnx().prepareStatement("INSERT INTO etat(etat) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            req.setString(1, state.getEtat());
            req.executeUpdate();
            ResultSet res = req.getGeneratedKeys();
            if (res.next()) {
                state.setId_etat(res.getInt(1));
            }
        } else {
            req = getDatabase().getCnx().prepareStatement("UPDATE etat SET etat = ? WHERE id_etat = ?");
            req.setString(1, state.getEtat());
            req.setInt(2, state.getId_etat());
            req.executeUpdate();
        } return state;
    }

    public ArrayList<State> getStates() {
        ArrayList<State> states = new ArrayList<>();
        try {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("Select * FROM etat;");
            ResultSet res = req.executeQuery();
            while (res.next()) {
                states.add(new State(
                        res.getInt(1),
                        res.getString(2)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return states;
    }

    public State getState(int id_state) {
        State state = null;
        try {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("Select * FROM etat WHERE id_etat = ?");
            req.setInt(1, id_state);
            ResultSet res = req.executeQuery();
            if (res.next()) {
                state = new State(
                        res.getInt(1),
                        res.getString(2)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return state;
    }

    public void deleteState(State state) throws SQLException {
        if (state.getId_etat() > 0) {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("DELETE FROM etat WHERE id_etat = ?");
            req.setInt(1, state.getId_etat());
            req.executeUpdate();
        }
    }
}
