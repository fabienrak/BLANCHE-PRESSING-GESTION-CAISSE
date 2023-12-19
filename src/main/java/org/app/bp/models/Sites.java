package org.app.bp.models;

public class Sites {
    private int id_site;
    private String nom_site;
    private String lieu;
    private String contact;
    private String email;


    /**
     * @return the id_site
     */
    public int getId_site() {
        return id_site;
    }
    /**
     * @param id_site the id_site to set
     */
    public void setId_site(int id_site) {
        this.id_site = id_site;
    }
    /**
     * @return the nom_site
     */
    public String getNom_site() {
        return nom_site;
    }
    /**
     * @param nom_site the nom_site to set
     */
    public void setNom_site(String nom_site) {
        this.nom_site = nom_site;
    }
    /**
     * @return the lieu
     */
    public String getLieu() {
        return lieu;
    }
    /**
     * @param lieu the lieu to set
     */
    public void setLieu(String lieu) {
        this.lieu = lieu;
    }
    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }
    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
