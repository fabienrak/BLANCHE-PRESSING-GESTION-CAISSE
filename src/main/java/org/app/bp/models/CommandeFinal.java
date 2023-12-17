package org.app.bp.models;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.ObservableList;

public class CommandeFinal {
    private int idCommande = 0;
    private LocalDate dateCommande = null;
    private LocalDate dateLivraison = null;
    private String code = null;
    private Clients client = null;
    private ObservableList<CommandeClient> listeCommandeClient = null;
    private List<FactureAvance> listeFactureAvance = null;
    private double prixTotal = 0.0;

    public CommandeFinal(){
        this.code = "BP_000001";
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
     */
    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
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
     */
    public void setListeCommandeClient(ObservableList<CommandeClient> listeCommandeClient) {
        this.listeCommandeClient = listeCommandeClient;
        int i = 0;
        prixTotal = 0;
        for(i = 0 ; i < listeCommandeClient.size() ; i++){
            prixTotal = prixTotal + listeCommandeClient.get(i).getPrixTotal();
        }
    }
    /**
     * @return the listeFactureAvance
     */
    public List<FactureAvance> getListeFactureAvance() {
        return listeFactureAvance;
    }
    /**
     * @param listeFactureAvance the listeFactureAvance to set
     */
    public void setListeFactureAvance(List<FactureAvance> listeFactureAvance) {
        this.listeFactureAvance = listeFactureAvance;
    }        
    
}
