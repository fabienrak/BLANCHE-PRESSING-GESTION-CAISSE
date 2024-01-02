package org.app.bp.models;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.app.bp.controller.ListeCommande;
import org.app.bp.services.CommandeService;
import org.app.bp.services.FacturationService;
import org.app.bp.services.PdfService;
import org.app.bp.utils.Erreur;

import javafx.scene.control.Button;
import javafx.scene.paint.Paint;

public class FactureAvance {
    private int idFacture = 0;
    private String numeroFacture = "";
    private CommandeFinal commande;
    private double prixAvance = 0.0;
    private LocalDate dateFacturation = null;
    private int type = 0;

    private String prixAvanceAffiche = null;
    private Button buttonFacturer = null;
    private int etat = 0;
    private Button button_supprimer = null;
    private Button valider = null;
    private String afficheType = null;
    

        /**
     * @return the afficheType
     */
    public String getAfficheType() {
        if(type == 0){
            afficheType = "avance";
        }else{
            afficheType = "final";
        }
        return afficheType.toUpperCase();
    }

    /**
     * @param afficheType the afficheType to set
     */
    public void setAfficheType(String afficheType) {
        this.afficheType = afficheType;
    }

        /**
     * @return the valider
     */
    public Button getValider() {
        return valider;
    }

    /**
     * @param valider the valider to set
     */
    public void setValider(Button valider) {
        this.valider = valider;
    }

        /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the button_supprimer
     */
    public Button getButton_supprimer() {
        return button_supprimer;
    }

    /**
     * @param button_supprimer the button_supprimer to set
     */
    public void setButton_supprimer(Button button_supprimer) {
        this.button_supprimer = button_supprimer;
    }

    /**
     * @return the etat
     */
    public int getEtat() {
        return etat;
    }

    /**
     * @param etat the etat to set
     */
    public void setEtat(int etat) {
        this.etat = etat;
    }

    /**
     * @return the buttonFacturer
     */
    public Button getButtonFacturer() {
        return buttonFacturer;
    }

    /**
     * @param buttonFacturer the buttonFacturer to set
     */
    public void setButtonFacturer(Button buttonFacturer) {
        this.buttonFacturer = buttonFacturer;
    }

    private ListeCommande listeCommandeControlleur;

    public void generationButtonFacturer(CommandeFinal commandeFinal,FacturationService factureServ){
        ///// 
        this.buttonFacturer = new Button("Facturer");
        this.buttonFacturer.setStyle("-fx-background-color: green;");
        this.buttonFacturer.setTextFill(Paint.valueOf("black"));
        buttonFacturer.setVisible(false);
        CommandeService comServ = new CommandeService();
        this.buttonFacturer.setOnAction(event->{
    //        listeCommandeControlleur.afficheListeAvance();
            if(type == 0){
                    PdfService.generationFactureAccompte(this, commandeFinal);
            }else{
                try {
                    commandeFinal.setListeCommandeClient(comServ.getListCommandeClient(commandeFinal));
                } catch (Erreur e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                commandeFinal.setListeFactureAvance(factureServ.getListeFactureAvance(commandeFinal));
                PdfService.generationDeFactureFinal(commandeFinal, this);
            }
        });
    }

    public void ImprimerFacture(CommandeFinal commandeFinal,CommandeService comServ,FacturationService factureServ){
                    try {
                    commandeFinal.setListeCommandeClient(comServ.getListCommandeClient(commandeFinal));
                } catch (Erreur e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //commandeFinal.setListeFactureAvance(factureServ.getListeFactureAvance(commandeFinal));
                PdfService.generationDeFactureFinal(commandeFinal, this);
    }


    public double getDejaPayerFinal(){
        double  t = 0;
        if(etat == 1){
            t = prixAvance;
        }
        return t;
    }
    
    public void generationButtonValider(CommandeFinal commandeFinal,FacturationService factureServ){
        /////
        this.valider = new Button("Payer");
        this.valider.setStyle("-fx-background-color: yellow;");
        this.valider.setTextFill(Paint.valueOf("black"));
        this.valider.setVisible(false);
        this.valider.setOnAction(event->{
            factureServ.validerEtatFacture(this);
            listeCommandeControlleur.afficheListeAvance();
        });
    }
    public void generationButtonSupprimer(CommandeFinal commandeFinal,FacturationService factureServ){
        /////
        this.button_supprimer = new Button("annuler");
        this.button_supprimer.setStyle("-fx-background-color: red;");
        this.button_supprimer.setTextFill(Paint.valueOf("black"));
        this.button_supprimer.setVisible(false);
        
        this.button_supprimer.setOnAction(event->{
            //PdfService.generationFactureAccompte(this, commandeFinal);
            factureServ.deleteFactureAvance(this);    
            listeCommandeControlleur.afficheListeAvance();
        });
    }

    public void modifierFactureAvance(CommandeFinal com,ListeCommande lc,CommandeService comServ){
        this.listeCommandeControlleur = lc;
        this.buttonFacturer.setVisible(true);
        if(etat == 0){
            if(type == 1){
                    this.button_supprimer.setVisible(true);
            
            }
            if(comServ.presenceFactureFinale(com) == false && type == 0){
                this.button_supprimer.setVisible(true);
            }
        }
    }
    public void nonAfficheModification(){
        this.buttonFacturer.setVisible(false);
            this.button_supprimer.setVisible(false);
        this.valider.setVisible(false);        
    }
    public String getPrixAvanceAffiche() {
        return NumberFormat.getInstance(java.util.Locale.FRENCH).format(prixAvance) + " Ar ";
    }

    public void setPrixAvanceAffiche(String prixAvanceAffiche) {
        this.prixAvanceAffiche = prixAvanceAffiche;
    }
    
    ///// facture avance
    public void ajout(FacturationService factureServ,CommandeFinal commande)throws Erreur{
        if(prixAvance > commande.getResteApayer()){
            throw new Erreur("Avance très élevée ");
        }
        if(prixAvance == commande.getResteApayer()){
                throw new Erreur("Votre avance est egale au reste à payer");
        }
        if(prixAvance == 0){
            throw new Erreur("Votre avance est insuffisant");    
        }
        this.type = 0;
        generateNumero(commande, "A");
        factureServ.nouveauFactureAvance(this, commande);
    }
    public void verificationAvance(CommandeFinal commande)throws Erreur{
        if(prixAvance > commande.getResteApayer()){
            throw new Erreur("Avance très élevée ");
        }
        if(prixAvance == commande.getResteApayer()){
                throw new Erreur("Votre avance est egale au reste à payer");
        }
        if(prixAvance == 0){
            throw new Erreur("Votre avance est insuffisant");    
        }
    }

    private void generateNumero(CommandeFinal commande,String a){
        this.numeroFacture = "F"+commande.getCode()+a;
    }
    /// facturation final
    public void ajoutFactureFinal(FacturationService factureServ,CommandeFinal commande)throws Erreur{
        this.dateFacturation = LocalDate.now();
        this.type = 1;
        generateNumero(commande, "F");
        //factureServ.nouveauFactureAvance(this, commande);
    }

    public FactureAvance(){
        LocalDateTime now = LocalDateTime.now();
        this.numeroFacture = "FBP_"+now.format(DateTimeFormatter.ofPattern("YYYYMMDD-hhmmssSSS")); 
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
