package org.arvik.applisttask.modele;

public class Task {
    private int id_tache;
    private String libelle;
    private String description;
    private String difficulte;
    private String date_debut;
    private String date_fin;
    private String date_butoir;
    private User ref_compte;
    private List ref_liste;
    private State ref_etat;
    private Type ref_type;

    public Task(int id_tache, String libelle, String description, String difficulte, String date_debut, String date_fin, String date_butoir, User ref_compte, List ref_liste, State ref_etat, Type ref_type) {
        this.id_tache = id_tache;
        this.libelle = libelle;
        this.description = description;
        this.difficulte = difficulte;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.date_butoir = date_butoir;
        this.ref_compte = ref_compte;
        this.ref_liste = ref_liste;
        this.ref_etat = ref_etat;
        this.ref_type = ref_type;
    }

    public Task(String libelle, String description, String difficulte, String date_debut, String date_fin, String date_butoir, User ref_compte, List ref_liste, Type ref_type) {
        this.libelle = libelle;
        this.description = description;
        this.difficulte = difficulte;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.date_butoir = date_butoir;
        this.ref_compte = ref_compte;
        this.ref_liste = ref_liste;
        this.ref_type = ref_type;
    }

    public int getId_tache() {
        return id_tache;
    }

    public void setId_tache(int id_tache) {
        this.id_tache = id_tache;
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

    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getDate_butoir() {
        return date_butoir;
    }

    public void setDate_butoir(String date_butoir) {
        this.date_butoir = date_butoir;
    }

    public User getRef_compte() {
        return ref_compte;
    }

    public String getRef_compteToString() {
        return this.ref_compte.toString();
    }

    public void setRef_compte(User ref_compte) {
        this.ref_compte = ref_compte;
    }

    public List getRef_liste() {
        return ref_liste;
    }

    public String getRef_listeLibelle() {
        return this.ref_liste.getLibelle();
    }

    public void setRef_liste(List ref_liste) {
        this.ref_liste = ref_liste;
    }

    public State getRef_etat() {
        return ref_etat;
    }

    public String getRef_etatEtat() {
        return this.ref_etat.getEtat();
    }

    public void setRef_etat(State ref_etat) {
        this.ref_etat = ref_etat;
    }

    public Type getRef_type() {
        return ref_type;
    }

    public String getRef_typeLibelle() {
        if (this.ref_type == null) {
            return "";
        } else {
            return this.ref_type.getLibelle();
        }
    }

    public void setRef_type(Type ref_type) {
        this.ref_type = ref_type;
    }
}