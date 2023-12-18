package org.app.bp.models;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.app.bp.services.FacturationService;
import org.app.bp.utils.Erreur;

public class FactureAvance {
    private int idFacture = 0;
    private String numeroFacture = "";
    private CommandeFinal commande;
    private double prixAvance = 0.0;
    private LocalDate dateFacturation = null;
    private String prixAvanceAffiche = null;
    
    public String getPrixAvanceAffiche() {
        return NumberFormat.getInstance(java.util.Locale.FRENCH).format(prixAvance) + " Ar ";
    }

    public void setPrixAvanceAffiche(String prixAvanceAffiche) {
        this.prixAvanceAffiche = prixAvanceAffiche;
    }
    
    public void ajout(FacturationService factureServ,CommandeFinal commande)throws Erreur{
        if(prixAvance > commande.getResteApayer()){
            throw new Erreur("Avance très élevée ");
        }
        if(prixAvance == 0){
            throw new Erreur("Votre avance est insuffisant");    
        }
        factureServ.nouveauFactureAvance(this, commande);
    }

    public FactureAvance(){
   
        LocalDateTime now = LocalDateTime.now();
        this.numeroFacture = "FABP_"+now.format(DateTimeFormatter.ofPattern("YYYYMMDD-hhmm")); 
    }
    
    /**
     * @return the numeroFacture
     */
    public String getNumeroFacture() {
        return numeroFacture;
    }
    /**
     * @param numeroFacture the numeroFacture to set
     */
    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }
    /**
     * @return the idFacture
     */
    public int getIdFacture() {
        return idFacture;
    }
    /**
     * @param idFacture the idFacture to set
     */
    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }
    /**
     * @return the commande
     */
    public CommandeFinal getCommande() {
        return commande;
    }
    /**
     * @param commande the commande to set
     */
    public void setCommande(CommandeFinal commande) {
        this.commande = commande;
    }
    /**
     * @return the prixAvance
     */
    public double getPrixAvance() {
        return prixAvance;
    }
    /**
     * @param prixAvance the prixAvance to set
     */
    public void setPrixAvance(double prixAvance) {
        this.prixAvance = prixAvance;
    }
    /**
     * @return the dateFacturation
     */
    public LocalDate getDateFacturation() {
        return dateFacturation;
    }
    /**
     * @param dateFacturation the dateFacturation to set
     */
    public void setDateFacturation(LocalDate dateFacturation) {
        this.dateFacturation = dateFacturation;
    }

}
