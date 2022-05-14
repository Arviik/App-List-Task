package org.arvik.applisttask.modele;

public class Type {
    private int id_type;
    private String libelle;
    private Type ref_type;

    public Type(int id_type, String libelle, Type ref_type) {
        this.id_type = id_type;
        this.libelle = libelle;
        this.ref_type = ref_type;
    }

    public Type(String libelle, Type ref_type) {
        this.libelle = libelle;
        this.ref_type = ref_type;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Type getRef_type() {
        return ref_type;
    }

    public void setRef_type(Type ref_type) {
        this.ref_type = ref_type;
    }
}
