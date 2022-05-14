package org.arvik.applisttask.modele;

public class State {
    private int id_etat;
    private String etat;

    public State(int id_etat, String etat) {
        this.id_etat = id_etat;
        this.etat = etat;
    }

    public State(String etat) {
        this.etat = etat;
    }

    public int getId_etat() {
        return id_etat;
    }

    public void setId_etat(int id_etat) {
        this.id_etat = id_etat;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
