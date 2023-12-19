package org.app.bp.models;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.app.bp.utils.Erreur;

import javafx.collections.ObservableList;

public class CommandeFinal {
    private int idCommande = 0;
    private LocalDate dateCommande = null;
    private LocalDate dateLivraison = null;
    private String code = null;
    private Clients client = null;
    private ObservableList<CommandeClient> listeCommandeClient = null;
    private ObservableList<FactureAvance> listeFactureAvance = null;
    private double prixTotal = 0.0;
    private double avance = 0.0;
    private double reste = 0.0;
    /**
     * @return the reste
     */
    public double getReste() {
        return (prixTotal - avance);
    }

    /**
     * @param reste the reste to set
     */
    public void setReste(double reste) {
        this.reste = reste;
    }

    /**
     * @return the avance
     */
    public double getAvance() {
        return avance;
    }

    /**
     * @param avance the avance to set
     */
    public void setAvance(double avance) {
        this.avance = avance;
    }
    private String prixTotalAffiche = null;


    /**
     * @return the prixTotalAffiche
     */
    public String getPrixTotalAffiche() {
        return NumberFormat.getInstance(java.util.Locale.FRENCH).format(prixTotal) +" Ar ";
    }

    /**
     * @param prixTotalAffiche the prixTotalAffiche to set
     */
    public void setPrixTotalAffiche(String prixTotalAffiche) {
        this.prixTotalAffiche = prixTotalAffiche;
    }

    public double getTotalAvance(){
        
        return avance;
    }

    public double getResteApayer(){
        return getPrixTotal() - getTotalAvance();
    }

    public CommandeFinal(){
        LocalDateTime now = LocalDateTime.now();
        this.code = "CBP_"+now.format(DateTimeFormatter.ofPattern("YYYYMMDD-hhmmssSSS"));
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
    /**
     * @return the idCommande
     */
    public int getIdCommande() {
        return idCommande;
    }
    /**
     * @param idCommande the idCommande to set
     */
    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }
    /**
     * @return the dateCommande
     */
    public LocalDate getDateCommande() {
        return dateCommande;
    }
    /**
     * @param dateCommande the dateCommande to set
     */
    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }
    /**
     * @return the dateLivraison
     */
    public LocalDate getDateLivraison() {
        return dateLivraison;
    }
    /**
     * @param dateLivraison the dateLivraison to set
     * @throws Erreur
     */
    public void setDateLivraison(LocalDate dateLivraison) throws Erreur {
        this.dateLivraison = dateLivraison;
        if(dateLivraison.isBefore(dateCommande) == true){
            throw new Erreur("Date de livraison invalide");
        }
    }
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * @return the client
     */
    public Clients getClient() {
        return client;
    }
    /**
     * @param client the client to set
     */
    public void setClient(Clients client) {
        this.client = client;
    }
    /**
     * @return the listeCommandeClient
     */
    public ObservableList<CommandeClient> getListeCommandeClient() {
        return listeCommandeClient;
    }
    /**
     * @param listeCommandeClient the listeCommandeClient to set
     * @throws Erreur
     */
    public void setListeCommandeClient(ObservableList<CommandeClient> listeCommandeClient) throws Erreur {
        this.listeCommandeClient = listeCommandeClient;
        if(listeCommandeClient.size() <= 0){
            throw new Erreur("Vous n'avez aucun commande");
        }
        int i = 0;
        prixTotal = 0;
        for(i = 0 ; i < listeCommandeClient.size() ; i++){
            prixTotal = prixTotal + listeCommandeClient.get(i).getPrixTotal();
        }
    }
    /**
     * @return the listeFactureAvance
     */
    public ObservableList<FactureAvance> getListeFactureAvance() {
        return listeFactureAvance;
    }
    /**
     * @param listeFactureAvance the listeFactureAvance to set
     */
    public void setListeFactureAvance(ObservableList<FactureAvance> listeFactureAvance) {
        this.listeFactureAvance = listeFactureAvance;
        avance = 0.0;
        if(listeFactureAvance != null){
            for(FactureAvance fac : listeFactureAvance){
                if(fac.getEtat() == 1){
                    avance = avance + fac.getPrixAvance();
                }
            }
        }

    }        
    
}
