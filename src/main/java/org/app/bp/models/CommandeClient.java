package org.app.bp.models;

import java.text.NumberFormat;

import org.app.bp.controller.Commande2Controlleur;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;

public class CommandeClient {
    private Articles article = null;
    private String nomArticle;
    private int nombre;
    private Service service;
    private double prixUnitaire;
    private double prixTotal;
    private Button bouttonSupprimer;
    private String prixUnitaireAffiche;
    private String prixTotalAffiche;

    /**
     * @return the prixUnitaireAffiche
     */
    public String getPrixUnitaireAffiche() {
        return NumberFormat.getInstance(java.util.Locale.FRENCH).format(prixUnitaire)+" Ar ";
    }
    /**
     * @param prixUnitaireAffiche the prixUnitaireAffiche to set
     */
    public void setPrixUnitaireAffiche(String prixUnitaireAffiche) {
        this.prixUnitaireAffiche = prixUnitaireAffiche;
    }
    /**
     * @return the prixTotalAffiche
     */
    public String getPrixTotalAffiche() {
        return NumberFormat.getInstance(java.util.Locale.FRENCH).format(prixTotal) + " Ar ";
    }
    /**
     * @param prixTotalAffiche the prixTotalAffiche to set
     */
    public void setPrixTotalAffiche(String prixTotalAffiche) {
        this.prixTotalAffiche = prixTotalAffiche;
    }
    public void generateButtonSupprimer(Commande2Controlleur c,ObservableList<CommandeClient> listeCommande){
        this.bouttonSupprimer = new Button("X");
        this.bouttonSupprimer.setStyle("-fx-background-color: red;");
        this.bouttonSupprimer.setTextFill(Paint.valueOf("black"));
        this.bouttonSupprimer.setOnAction(event->{
            System.out.println("effacer");
            listeCommande.remove(this);
            c.miseAJourTableViewCommande();
        });
    
    }
    /**
     * @return the bouttonSupprimer
     */
    public Button getBouttonSupprimer() {
        return bouttonSupprimer;
    }
    /**
     * @param bouttonSupprimer the bouttonSupprimer to set
     */
    public void setBouttonSupprimer(Button bouttonSupprimer) {
        this.bouttonSupprimer = bouttonSupprimer;
    }
    /**
     * 
     */
    public CommandeClient() {
    }
    /**
     * @param article
     * @param nomArticle
     * @param nombre
     * @param service
     * @param prixUnitaire
     * @param prixTotal
     */
    public CommandeClient(Articles article, String nomArticle, int nombre, Service service, double prixUnitaire,
            double prixTotal) {
        this.article = article;
        this.nomArticle = nomArticle;
        this.nombre = nombre;
        this.service = service;
        this.prixUnitaire = prixUnitaire;
        this.prixTotal = prixTotal;
    }
    /**
     * @return the article
     */
    public Articles getArticle() {
        return article;
    }
    /**
     * @param article the article to set
     */
    public void setArticle(Articles article) {
        this.article = article;
        this.nomArticle = article.getNom_article();
        this.prixUnitaire = article.getPrix();
        this.prixTotal = nombre * prixUnitaire;
    }
    /**
     * @return the service
     */
    public Service getService() {
        return service;
    }
    /**
     * @param service the service to set
     */
    public void setService(Service service) {
        this.service = service;
    }
    /**
     * @return the nomArticle
     */
    public String getNomArticle() {
        return this.nomArticle;
    }
    /**
     * @param nomArticle the nomArticle to set
     */
    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    /**
     * @return the nombre
     */
    public int getNombre() {
        return nombre;
    }
    /**
     * @param nombre the nombre to set
     */
    public void setNombre(int nombre) {
        this.nombre = nombre;
        this.prixTotal = nombre * prixUnitaire;
    }
   
    /**
     * @return the prixUnitaire
     */
    public double getPrixUnitaire() {
        return prixUnitaire;
    }
    /**
     * @param prixUnitaire the prixUnitaire to set
     */
    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
        this.prixTotal = nombre * prixUnitaire;
    }
    /**
     * @return the prixTotal
     */
    public double getPrixTotal() {
        return prixTotal;
    }
    /**
     * @param prixTotal the prixTotal to set
     */
    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }
}
