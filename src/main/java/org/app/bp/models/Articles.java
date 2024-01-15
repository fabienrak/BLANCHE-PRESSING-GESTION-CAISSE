package org.app.bp.models;

public class Articles {

    private int id_article;
    private String nom_article;
    private int prix;
    private String prefix_code;

    public Articles(int id_article, String nom_article, int prix, String prefix_code) {
        this.id_article = id_article;
        this.nom_article = nom_article;
        this.prix = prix;
        this.prefix_code = prefix_code;
    }

    public Articles(String nom_article, int prix, String prefix_code) {
        this.id_article = -1;
        this.nom_article = nom_article;
        this.prix = prix;
        this.prefix_code = prefix_code;
    }

    public int getId_article() {
        return id_article;
    }

    public void setId_article(int id_article) {
        this.id_article = id_article;
    }

    public String getNom_article() {
        return nom_article;
    }

    public void setNom_article(String nom_article) {
        this.nom_article = nom_article;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getPrefix_code() {
        return prefix_code;
    }

    public void setPrefix_code(String prefix_code) {
        this.prefix_code = prefix_code;
    }
}
