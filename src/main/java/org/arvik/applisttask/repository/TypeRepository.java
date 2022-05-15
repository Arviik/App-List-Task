package org.arvik.applisttask.repository;

import org.arvik.applisttask.modele.Type;
import java.sql.*;
import java.util.ArrayList;

public class TypeRepository extends Repository {

    public Type save(Type type) throws SQLException {
        PreparedStatement req;
        if (type.getId_type() == 0) {
            req = getDatabase().getCnx().prepareStatement("INSERT INTO type(libelle, ref_type) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            fillSaveRequest(type, req);
            ResultSet res = req.getGeneratedKeys();
            if (res.next()) {
                type.setId_type(res.getInt(1));
            }
        } else {
            req = getDatabase().getCnx().prepareStatement("UPDATE type SET libelle = ?, ref_type = ? WHERE id_type = ?");
            req.setInt(3, type.getId_type());
            fillSaveRequest(type, req);
        } return type;
    }

    private void fillSaveRequest(Type type, PreparedStatement req) throws SQLException {
        req.setString(1, type.getLibelle());
        if (type.getRef_type().getId_type() == 0) {
            req.setNull(2, Types.INTEGER);
        } else {
            req.setInt(2, type.getRef_type().getId_type());
        }
        req.executeUpdate();
    }

    public ArrayList<Type> getTypes() {
        ArrayList<Type> types = new ArrayList<>();
        try {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("Select * FROM type;");
            ResultSet res = req.executeQuery();
            while (res.next()) {
                types.add(new Type(
                        res.getInt(1),
                        res.getString(2),
                        res.getInt(3)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return types;
    }

    public Type getType(int id_type){
        Type type = null;
        try {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("Select * FROM type WHERE id_type = ?;");
            req.setInt(1, id_type);
            ResultSet res = req.executeQuery();
            if (res.next()) {
                type = new Type(
                        res.getInt(1),
                        res.getString(2),
                        res.getInt(3)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return type;
    }

    public void deleteType(Type type) throws SQLException {
        if (type.getId_type()>0) {
            PreparedStatement req = getDatabase().getCnx().prepareStatement("DELETE FROM type WHERE id_type = ?;");
            req.setInt(1, type.getId_type());
            req.executeUpdate();
        }
    }
}