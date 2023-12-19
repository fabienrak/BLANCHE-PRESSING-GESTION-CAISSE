package org.app.bp.controller;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.app.bp.models.Clients;
import org.app.bp.models.CommandeClient;
import org.app.bp.models.CommandeFinal;
import org.app.bp.models.FactureAvance;
import org.app.bp.services.CommandeService;
import org.app.bp.services.FacturationService;
import org.app.bp.utils.Erreur;
import org.app.bp.utils.Utils;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ListeCommande implements Initializable{

    @FXML
    private AnchorPane content_liste_commande;
    @FXML
    private Label lbl_client;
    @FXML
    private Pane paneD1;
    @FXML
    private Pane paneD2;
    @FXML
    private Pane paneD3;
    @FXML
    private Label val_prix_avance;
    
    @FXML
    private Label val_prix_total;
    
    @FXML
    private Label val_reste;
    
    @FXML
    private TextField txt_avance;
    @FXML
    private Label lbl_client_adresse;
    @FXML
    private Label lbl_client_contact;
    @FXML
    private TableView tableCommande;
    @FXML
    private DatePicker datePicker_commande;
    @FXML
    private DatePicker datePicker_livraison;
    @FXML
    private TextField txt_code;

    @FXML
    private Label lbl_payement_avance;
    @FXML
    private HBox hbox_payement_avance;

    @FXML
    private Button bt_facture_tout;
    @FXML
    private TableView tableDroite;
    private TableView tableCommandeClient;
    private TableView tableAvance;
    @FXML
    private Label lbl_erreur_avance;

        Utils appUtils = new Utils();
    
    private Clients client = null;
    private ObservableList<CommandeFinal> listeCommandeFinal = null;
    private CommandeService commandeServ = new CommandeService();
    private CommandeFinal commandeFinal = null;
    private FacturationService factureServ = new FacturationService();
    private boolean commandeListEstAfficher = true;

    public CommandeFinal getCommandeFinal() {
        return commandeFinal;
    }

    public void setCommandeFinal(CommandeFinal commandeFinal) {
        this.commandeFinal = commandeFinal;
    }

    private Class classInitial;

    /**
     * @param classInitial the classInitial to set
     */
    public void setClassInitial(Class classInitial) {
        this.classInitial = classInitial;
    }

    @FXML
    private void backToInfoClient(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        Stage stage = (Stage) node_source.getScene().getWindow();
        Parent parent = null;
        if(classInitial == ClientController.class){
            parent = FXMLLoader.load(getClass().getResource("/fxml/facture/info-client.fxml"));
            stage.setTitle("FACTURATION");
        
        }else if(classInitial == Commande2Controlleur.class){
            parent = FXMLLoader.load(getClass().getResource("/fxml/commande/info-client.fxml"));
      stage.setTitle("COMMANDE");
        }
        content_liste_commande.getChildren().removeAll();
        content_liste_commande.getChildren().setAll(parent);
    }
    private double avanceProposer = 0;

    @FXML
    private void payementAvance(ActionEvent actionEvent)throws IOException{
        FactureAvance factureAvance = new FactureAvance();
        factureAvance.setDateFacturation(LocalDate.now());
        factureAvance.setPrixAvance(avanceProposer);     
        try {
            factureAvance.ajout(factureServ, commandeFinal);
            afficheListeAvance();
            avanceProposer = 0;
            txt_avance.setText("");
        } catch (Erreur e) {
            appUtils.warningAlertDialog("AVERTISSEMENT",e.getMessage().toUpperCase());
        }
    }


      @FXML
      private void handleFacturerTout(ActionEvent actionEvent) throws IOException {
                FactureAvance factureAvance = new FactureAvance();
        factureAvance.setDateFacturation(LocalDate.now());   
        try {
            factureAvance.ajoutFactureFinal(factureServ, commandeFinal);
            afficheListeAvance();
            avanceProposer = 0;
            txt_avance.setText("");
        } catch (Erreur e) {
            appUtils.warningAlertDialog("AVERTISSEMENT",e.getMessage().toUpperCase());
        }
        }
    private void verificationReste(){
        if(commandeFinal.getReste() == 0){
            lbl_payement_avance.setVisible(false);
            hbox_payement_avance.setVisible(false);
            bt_facture_tout.setVisible(false);
        }else{
            if(commandeServ.presenceFactureFinale(commandeFinal) == true){
                lbl_payement_avance.setVisible(false);
                hbox_payement_avance.setVisible(false);
                bt_facture_tout.setVisible(false);
            }else{
                lbl_payement_avance.setVisible(true);
            hbox_payement_avance.setVisible(true);
            bt_facture_tout.setVisible(true);    
        
            }
        }
    }
    private void ecritureAvance(){
        txt_avance.textProperty().addListener((observable, oldValue, newValue) -> {
              if(newValue.isEmpty()){
                avanceProposer = 0;
              }else{
                avanceProposer = Double.parseDouble(newValue);
              }
            verificationAvance();
        });
  }
  private void verificationAvance(){
    if(commandeFinal.getReste() < avanceProposer){
                    lbl_erreur_avance.setText(" Avance très élevée ");
                    txt_avance.setStyle("-fx-border-color:red;");
                    lbl_erreur_avance.setVisible(true);
              }else{
                    txt_avance.setStyle("-fx-border-color:white;");
                    lbl_erreur_avance.setVisible(false);      
              }
  }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        creationTablePourListeCommandeClient();
        creationTablePourListeAvance();
        lbl_erreur_avance.setVisible(false);
        val_prix_avance.setAlignment(Pos.CENTER_RIGHT);
        val_prix_total.setAlignment(Pos.CENTER_RIGHT);
        val_reste.setAlignment(Pos.CENTER_RIGHT);
        datePicker_commande.setEditable(false);
        datePicker_livraison.setEditable(false);
        ecritureAvance();
        
    }
    public void initializeTableCommande(){
        TableColumn<CommandeFinal, String> codeCommande 
              = new TableColumn<CommandeFinal, String>("Code commande");
        TableColumn<CommandeFinal, String> dateCom 
              = new TableColumn<CommandeFinal, String>("Date commande");
        TableColumn<CommandeFinal, String> dateLivr 
              = new TableColumn<CommandeFinal, String>("Date livraison");
        TableColumn<CommandeFinal, String> total 
              = new TableColumn<CommandeFinal, String>("Prix total");
      
        codeCommande.setCellValueFactory(new PropertyValueFactory<>("code"));
        dateCom.setCellValueFactory(new PropertyValueFactory<>("dateCommande"));
        dateLivr.setCellValueFactory(new PropertyValueFactory<>("dateLivraison"));
        total.setCellValueFactory(new PropertyValueFactory<>("prixTotalAffiche"));
        System.out.println(listeCommandeFinal);
        tableCommande.setItems(listeCommandeFinal);
        tableCommande.getColumns().addAll(codeCommande,dateCom,dateLivr,total);
        afficheDetailsCommandeFinal();
    } 

    public void creationTablePourListeAvance(){
        TableColumn<FactureAvance,String> numFact = new TableColumn<FactureAvance,String>("Code Facture");
        TableColumn<FactureAvance,String> avanceColumn = new TableColumn<FactureAvance,String>("Avance");
        TableColumn<FactureAvance,String> date_facture = new TableColumn<FactureAvance,String>("Date Facturation");
        TableColumn<FactureAvance,Button> button_facture = new TableColumn<FactureAvance,Button>("");
        TableColumn<FactureAvance,Button> button_supprimer = new TableColumn<FactureAvance,Button>("");
        TableColumn<FactureAvance,Button> button_payer = new TableColumn<FactureAvance,Button>("");
        TableColumn<FactureAvance,String> type = new TableColumn<FactureAvance,String>("type");
        
        numFact.setCellValueFactory(new PropertyValueFactory<>("numeroFacture"));
        avanceColumn.setCellValueFactory(new PropertyValueFactory<>("prixAvanceAffiche"));
        date_facture.setCellValueFactory(new PropertyValueFactory<>("dateFacturation"));
        button_facture.setCellValueFactory(new PropertyValueFactory<>("buttonFacturer"));
        button_supprimer.setCellValueFactory(new PropertyValueFactory<>("button_supprimer"));
        button_payer.setCellValueFactory(new PropertyValueFactory<>("valider"));
        type.setCellValueFactory(new PropertyValueFactory<>("afficheType"));

        tableAvance = new TableView<>();
        tableAvance.getColumns().addAll(numFact,type,date_facture,avanceColumn,button_facture,button_supprimer,button_payer);
        
    }


      public void creationTablePourListeCommandeClient(){
            TableColumn<CommandeClient, String> nomArticle 
              = new TableColumn<CommandeClient, String>("Nom article");
        TableColumn<CommandeClient, String> nombre 
              = new TableColumn<CommandeClient, String>("Nombre");
        TableColumn<CommandeClient, String> service 
              = new TableColumn<CommandeClient, String>("Service");
        TableColumn<CommandeClient, String> prixUnitaire 
              = new TableColumn<CommandeClient, String>("Prix Unitaire");
        TableColumn<CommandeClient, String> total 
              = new TableColumn<CommandeClient, String>("Prix total");
        nomArticle.setCellValueFactory(new PropertyValueFactory<>("nomArticle"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        service.setCellValueFactory(new PropertyValueFactory<>("service"));
        prixUnitaire.setCellValueFactory(new PropertyValueFactory<>("prixUnitaireAffiche"));
        total.setCellValueFactory(new PropertyValueFactory<>("prixTotalAffiche"));
        tableCommandeClient = new TableView<>();
        tableCommandeClient.getColumns().addAll(nomArticle,service,nombre,prixUnitaire,total);

      }

      @FXML
      private void handleSelectedCommandeFinal(){
        commandeFinal = (CommandeFinal)tableCommande.getSelectionModel().getSelectedItem();
        afficheDetailsCommandeFinal();
        verificationAvance();
        }



      private void afficheDetailsCommandeFinal(){
        if(commandeFinal != null){
            paneD1.setVisible(true);
            paneD2.setVisible(true);
            paneD3.setVisible(true);
            datePicker_commande.setValue(commandeFinal.getDateCommande());
            datePicker_commande.setEditable(false);
            datePicker_livraison.setValue(commandeFinal.getDateLivraison());
            datePicker_livraison.setEditable(false);
            txt_code.setText(commandeFinal.getCode());
            txt_code.setEditable(false);
            affichageListeCommandeClient();
            val_prix_total.setText(NumberFormat.getInstance(java.util.Locale.FRENCH).format(commandeFinal.getPrixTotal())+" Ar ");
            val_prix_avance.setText(NumberFormat.getInstance(java.util.Locale.FRENCH).format(commandeFinal.getAvance())+" Ar ");
            val_reste.setText(NumberFormat.getInstance(java.util.Locale.FRENCH).format(commandeFinal.getReste())+" Ar ");
            verificationReste();
        }else{
            paneD1.setVisible(false);
            paneD2.setVisible(false);
            paneD3.setVisible(false);
        }
      }
      @FXML
      private void clickAfficheListeAvance(ActionEvent actionEvent) throws IOException {
        afficheListeAvance();
      }
      public void afficheListeAvance(){
        commandeFinal.setListeFactureAvance(factureServ.getListeFactureAvance(commandeFinal));
        tableDroite.getColumns().clear();
        tableDroite.getColumns().addAll(tableAvance.getColumns());
        tableDroite.getItems().clear();
        tableDroite.setItems(commandeFinal.getListeFactureAvance());
        val_prix_total.setText(NumberFormat.getInstance(java.util.Locale.FRENCH).format(commandeFinal.getPrixTotal())+" Ar ");
        val_prix_avance.setText(NumberFormat.getInstance(java.util.Locale.FRENCH).format(commandeFinal.getAvance())+" Ar ");
        val_reste.setText(NumberFormat.getInstance(java.util.Locale.FRENCH).format(commandeFinal.getReste())+" Ar ");        
        verificationReste();
        commandeListEstAfficher = false;
      }
      
      private FactureAvance factureAvanceSelected;
      @FXML
      private void handleSelectedListeAvance(){
        if (commandeListEstAfficher == false) {
            System.out.println("-----------------------------------------");
            if(factureAvanceSelected != null){
                factureAvanceSelected.nonAfficheModification();
            }

            factureAvanceSelected = (FactureAvance)tableDroite.getSelectionModel().getSelectedItem();
            if(factureAvanceSelected != null){
                factureAvanceSelected.modifierFactureAvance(this);
            }
        }
      }

      private void affichageListeCommandeClient(){
        try {
            commandeFinal.setListeCommandeClient(commandeServ.getListCommandeClient(commandeFinal));
        } catch (Erreur e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ObservableList<CommandeClient> list = commandeFinal.getListeCommandeClient();
        System.out.println(" affichageListeCommandeClient = "+list);
        tableDroite.getColumns().clear();
        tableDroite.getColumns().addAll(tableCommandeClient.getColumns());
        tableDroite.getItems().clear();
        tableDroite.setItems(list);
        commandeListEstAfficher = true;
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
        this.listeCommandeFinal = commandeServ.getListeCommmandeFinal(client);
        lbl_client.setText("Clients : "+client.getNom_client() +"  "+ client.getPrenom_client());
        lbl_client_adresse.setText("Adresse : "+client.getAdresse_client_1()+" | "+client.getAdresse_client_2());
        lbl_client_contact.setText("Contact : "+client.getContact_client_1()+" | "+client.getContact_client_2());
    
    }

}
