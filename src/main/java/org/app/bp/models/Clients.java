package org.app.bp.models;

public class Clients {

    private int id_client;
    private String nom_client;
    private String prenom_client;
    private String contact_client_1;
    private String contact_client_2;
    private String adresse_client_1;
    private String adresse_client_2;

    public Clients(int id_client, String nom_client, String prenom_client, String contact_client_1, String contact_client_2, String adresse_client_1, String adresse_client_2) {
        this.id_client = id_client;
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.contact_client_1 = contact_client_1;
        this.contact_client_2 = contact_client_2;
        this.adresse_client_1 = adresse_client_1;
        this.adresse_client_2 = adresse_client_2;
    }

    public Clients(String nom_client, String prenom_client, String contact_client_1, String contact_client_2, String adresse_client_1, String adresse_client_2) {
        this.id_client = -1;
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.contact_client_1 = contact_client_1;
        this.contact_client_2 = contact_client_2;
        this.adresse_client_1 = adresse_client_1;
        this.adresse_client_2 = adresse_client_2;
    }



    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getPrenom_client() {
        return prenom_client;
    }

    public void setPrenom_client(String prenom_client) {
        this.prenom_client = prenom_client;
    }

    public String getContact_client_1() {
        return contact_client_1;
    }

    public void setContact_client_1(String contact_client_1) {
        this.contact_client_1 = contact_client_1;
    }

    public String getContact_client_2() {
        return contact_client_2;
    }

    public void setContact_client_2(String contact_client_2) {
        this.contact_client_2 = contact_client_2;
    }

    public String getAdresse_client_1() {
        return adresse_client_1;
    }

    public void setAdresse_client_1(String adresse_client_1) {
        this.adresse_client_1 = adresse_client_1;
    }

    public String getAdresse_client_2() {
        return adresse_client_2;
    }

    public void setAdresse_client_2(String adresse_client_2) {
        this.adresse_client_2 = adresse_client_2;
    }
}
