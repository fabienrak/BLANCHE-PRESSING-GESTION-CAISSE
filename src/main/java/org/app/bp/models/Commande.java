package org.app.bp.models;

public class Commande {

    //  Commande
    private int id_commande;
    private String date_commande;
    private String titre_commande;
    private int prix_total;
    private int avance;
    private int reste;
    private String date_livraison;
    private String etat_commande;

    //  Marchandises
    private int marchandise_id;
    private String nombre_article;
    private int article_id;
    private String code;
    private int client_id;
    private String etat_article;

}
