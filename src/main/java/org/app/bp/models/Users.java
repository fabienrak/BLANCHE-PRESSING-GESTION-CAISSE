package org.app.bp.models;

public class Users {

    private int id_user;
    private String username;
    private String password;
    private String matricule;
    private String nom;
    private String prenom;
    private String contact;
    private String poste;
    private String sites;

    public Users(int id_user, String username, String password, String matricule, String nom, String prenom, String contact, String poste, String sites) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.contact = contact;
        this.poste = poste;
        this.sites = sites;
    }

    public Users(String username, String matricule, String nom, String prenom, String contact, String poste, String sites) {
        this.id_user = -1;
        this.username = username;
        //this.password = password;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.contact = contact;
        this.poste = poste;
        this.sites = sites;
    }

    public Users(int id_user, String username, String matricule, String nom, String prenom, String contact, String poste, String sites) {
        this.id_user = -1;
        this.username = username;
        //this.password = password;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.contact = contact;
        this.poste = poste;
        this.sites = sites;
    }


    /*public Users(String username, String password, String matricule, String nom, String prenom, String contact, String poste, String sites) {
        this.id_user = -1;
        this.username = username;
        this.password = password;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.contact = contact;
        this.poste = poste;
        this.sites = sites;
    }*/


    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getSites() {
        return sites;
    }

    public void setSites(String sites) {
        this.sites = sites;
    }
}
