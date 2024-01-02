package org.app.bp.models;

import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.app.bp.controller.ListeCommande;
import org.app.bp.services.CommandeService;
import org.app.bp.services.FacturationService;
import org.app.bp.services.SiteServices;
import org.app.bp.utils.Erreur;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;

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
    private int livre = 0;
    private Button buttonLivre = null;
    private String afficheLivre = null;
    
    /**
     * @return the afficheLivre
     */
    public String getAfficheLivre() {
        if (livre == 0) {
            afficheLivre = "NON";
        }else{
            afficheLivre = "OUI";
        }
        return afficheLivre;
    }

    /**
     * @param afficheLivre the afficheLivre to set
     */
    public void setAfficheLivre(String afficheLivre) {
        this.afficheLivre = afficheLivre;
    }

    /**
     * @return the buttonLivre
     */
    
     public Button getButtonLivre() {
        return buttonLivre;
    }

    public double getAvanceFinal(){
        return avanceFinal;
    }
    /**
     * @param buttonLivre the buttonLivre to set
     */
    public void setButtonLivre(Button buttonLivre) {
        this.buttonLivre = buttonLivre;
    }
    private ListeCommande lComControlleur = null;
    
    public void generationButtonLivrer(CommandeService comServ){
        this.buttonLivre = new Button("Livrer");
        this.buttonLivre.setStyle("-fx-background-color: yellow;");
        this.buttonLivre.setTextFill(Paint.valueOf("black"));
        this.buttonLivre.setVisible(false);
        this.buttonLivre.setOnAction(event->{
           // factureServ.validerEtatFacture(this);
           
            validationLivraison(comServ, new FacturationService());
            livre = 1;
            lComControlleur.afficheListeAvance();
            verificationLivraison(lComControlleur);
        });
    }

    public void nonModifiable(){
        if(this.buttonLivre == null){
            generationButtonLivrer(new CommandeService());
        }
        this.buttonLivre.setVisible(false);
    }

    public void verificationLivraison(ListeCommande com){
        if(this.buttonLivre == null){
            generationButtonLivrer(new CommandeService());
        }
        this.lComControlleur = com;
        buttonLivre.setVisible(true);
        if(livre == 1){
            buttonLivre.setText("Déjà livrer");
            buttonLivre.setDisable(true);
            afficheLivre = "OUI";
        }else{
            buttonLivre.setText("Livrer");
            afficheLivre = "NON";
        }
    }
    
    public void validationLivraison(CommandeService comServ,FacturationService factServ){
        int i = 0;
      //  if(listeFactureAvance == null){
            listeFactureAvance = factServ.getListeFactureAvance(this);    
            setListeFactureAvance(listeFactureAvance);
        //}
        if(comServ.presenceFactureFinale(this) == false){
            FactureAvance factureAvance = new FactureAvance();
            factureAvance.setPrixAvance(prixTotal - avanceFinal);
            factureAvance.setDateFacturation(LocalDate.now());
            factureAvance.setType(1);
            try {
                factureAvance.ajoutFactureFinal(factServ, this);
                factureAvance.setType(1);
                factServ.nouveauFactureAvance(factureAvance, this);
            } catch (Erreur e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            listeFactureAvance = factServ.getListeFactureAvance(this);                
        }
        for (int j = 0; j < listeFactureAvance.size(); j++) {
            factServ.validerEtatFacture(listeFactureAvance.get(j));
        }
        comServ.validerLivraison(this);
    }
    public static void main (String[] args){
        String[] list = DateFormatSymbols.getInstance(Locale.FRENCH).getMonths();
        int i = 0 , j = 0;
        for(i = 0 ; i < list.length ; i++){
            j = i + 1;
            System.out.println("("+j+" , "+list[i]+")");
        }
    }
    
    /**
     * @return the livre
     */
    public int getLivre() {
        return livre;
    }

    /**
     * @param livre the livre to set
     */
    public void setLivre(int livre) {
        this.livre = livre;
    }

    /**
     * @return the reste
     */
    public double getReste() {
        return prixTotal - avance;
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
        return getReste();
    }

    public CommandeFinal(){

    }

    public void genererCode(){
        LocalDate date = LocalDate.now();
        CommandeService commandeService = new CommandeService();
        Sites sites = new SiteServices().getSites();
        int t = commandeService.getNombreCommandeDATE(date);
        this.code = date.format(DateTimeFormatter.ofPattern("yyMM"))+sites.getCode()+genererNumero(t+1);
    }


    private String genererNumero(int t){
        String value = String.valueOf(t);
        int taille = 3, i = value.length();
        while(i < 3){
            value = "0"+value;
            i++;
        }
        return value;
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
    private double avanceFinal = 0;
    public void setListeFactureAvance(ObservableList<FactureAvance> listeFactureAvance) {
        this.listeFactureAvance = listeFactureAvance;
        avance = 0.0;
        avanceFinal = 0;
        if(listeFactureAvance != null){
            for(FactureAvance fac : listeFactureAvance){
                if(fac.getEtat() == 1){
                    avance = avance + fac.getPrixAvance();
                }
                if(fac.getType() == 0){
                    avanceFinal = avanceFinal + fac.getPrixAvance();
                }
            }
        }

    }        
    
}
