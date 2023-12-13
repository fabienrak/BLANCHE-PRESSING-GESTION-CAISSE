package org.app.bp.models;

public class Marchandises {

    private int id_marchandises;
    private int article_id;
    private int nombre_article;
    private String code;
    private int client_id;

    public Marchandises(int id_marchandises, int article_id, int nombre_article, String code, int client_id) {
        this.id_marchandises = id_marchandises;
        this.article_id = article_id;
        this.nombre_article = nombre_article;
        this.code = code;
        this.client_id = client_id;
    }

    public Marchandises(int article_id, int nombre_article, String code, int client_id) {
        this.id_marchandises = -1;
        this.article_id = article_id;
        this.nombre_article = nombre_article;
        this.code = code;
        this.client_id = client_id;
    }

    public int getId_marchandises() {
        return id_marchandises;
    }

    public void setId_marchandises(int id_marchandises) {
        this.id_marchandises = id_marchandises;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getNombre_article() {
        return nombre_article;
    }

    public void setNombre_article(int nombre_article) {
        this.nombre_article = nombre_article;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }
}
