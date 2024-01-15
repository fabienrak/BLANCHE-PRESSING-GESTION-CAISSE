package org.app.bp.models;

public class Sites {

    private int id_site;
    private String nom_site;
    private String lieu;
    private String contact;
    private String code_site;

    public Sites(int id_site, String nom_site, String lieu, String contact, String code_site) {
        this.id_site = id_site;
        this.nom_site = nom_site;
        this.lieu = lieu;
        this.contact = contact;
        this.code_site = code_site;
    }

    public Sites(String nom_site, String lieu, String contact, String code_site) {
        this.id_site = -1;
        this.nom_site = nom_site;
        this.lieu = lieu;
        this.contact = contact;
        this.code_site = code_site;
    }

    public int getId_site() {
        return id_site;
    }

    public void setId_site(int id_site) {
        this.id_site = id_site;
    }

    public String getNom_site() {
        return nom_site;
    }

    public void setNom_site(String nom_site) {
        this.nom_site = nom_site;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCode_site() {
        return code_site;
    }

    public void setCode_site(String code_site) {
        this.code_site = code_site;
    }
}
