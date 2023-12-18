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
import org.app.bp.services.PdfService;
import org.app.bp.utils.Erreur;
import org.app.bp.utils.Utils;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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

    public CommandeFinal getCommandeFinal() {
        return commandeFinal;
    }

    public void setCommandeFinal(CommandeFinal commandeFinal) {
        this.commandeFinal = commandeFinal;
    }

    @FXML
    private void backToInfoClient(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        Stage stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/facture/info-client.fxml"));
        stage.setTitle("FACTURATION");
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
            val_prix_total.setText(NumberFormat.getInstance(java.util.Locale.FRENCH).format(commandeFinal.getPrixTotal())+" Ar ");
            val_prix_avance.setText(NumberFormat.getInstance(java.util.Locale.FRENCH).format(commandeFinal.getAvance())+" Ar ");
            val_reste.setText(NumberFormat.getInstance(java.util.Locale.FRENCH).format(commandeFinal.getReste())+" Ar ");
            avanceProposer = 0;
            txt_avance.setText("");
        } catch (Erreur e) {
            appUtils.warningAlertDialog("AVERTISSEMENT",e.getMessage().toUpperCase());
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
        
        numFact.setCellValueFactory(new PropertyValueFactory<>("numeroFacture"));
        avanceColumn.setCellValueFactory(new PropertyValueFactory<>("prixAvanceAffiche"));
        date_facture.setCellValueFactory(new PropertyValueFactory<>("dateFacturation"));
        tableAvance = new TableView<>();
        tableAvance.getColumns().addAll(numFact,avanceColumn,date_facture);
        
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


      @FXML
      private void handleFacturerTout(ActionEvent actionEvent) throws IOException {
        commandeFinal.setListeFactureAvance(factureServ.getListeFactureAvance(commandeFinal));
        if(commandeFinal.getListeCommandeClient() == null){
            try {
                commandeFinal.setListeCommandeClient(commandeServ.getListCommandeClient(commandeFinal));
            } catch (Erreur e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        PdfService.generationDeFactureFinal(commandeFinal);
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
      private void afficheListeAvance(){
        commandeFinal.setListeFactureAvance(factureServ.getListeFactureAvance(commandeFinal));
        tableDroite.getColumns().clear();
        tableDroite.getColumns().addAll(tableAvance.getColumns());
        tableDroite.getItems().clear();
        tableDroite.setItems(commandeFinal.getListeFactureAvance());
        
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
