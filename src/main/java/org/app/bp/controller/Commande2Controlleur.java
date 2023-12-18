package org.app.bp.controller;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.app.bp.models.Articles;
import org.app.bp.models.Clients;
import org.app.bp.models.CommandeClient;
import org.app.bp.models.CommandeFinal;
import org.app.bp.models.FactureAvance;
import org.app.bp.models.Service;
import org.app.bp.services.CommandeService;
import org.app.bp.services.MarchandisesServices;
import org.app.bp.services.ServiceServices;
import org.app.bp.utils.Erreur;
import org.app.bp.utils.Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Commande2Controlleur implements Initializable{

    
    @FXML
    private ComboBox cbx_type_article;
    @FXML
    private ComboBox cbx_type_services;
    @FXML
    private TextField txt_prix_total;
    private double prixTotal;
    @FXML
    private TextField txt_prix_avance;
    private double prixAvance;
    @FXML
    private TextField txt_prix_reste;
    private double prixReste;
    @FXML
    private Label lbl_client;
    @FXML
    private TextField txt_nbr_article;
    @FXML
    private DatePicker datePicker_commande;
    @FXML
    private DatePicker datePicker_livraison;
    @FXML
    private TextField txt_code;
    @FXML
    private Label lbl_client_adresse;
    @FXML
    private Label lbl_client_contact;
    @FXML
    private TableView tableCommande;
    @FXML
    private Label lbl_erreur_avance;
    @FXML
    private AnchorPane content_nouveau_commande;
    private Clients clients = null;
    private ObservableList<Articles> listeArticle;
    private ObservableList<Service> listeService;
    private MarchandisesServices marchandisesServices = new MarchandisesServices();
    private ServiceServices serviceService = new ServiceServices();
    private CommandeService commandeServ = new CommandeService();
    private ObservableList<CommandeClient> listeCommande ;
    private CommandeFinal commande = new CommandeFinal();
        Utils appUtils = new Utils();
    /**
 * @return the clients
 */
public Clients getClients() {
      return clients;
}

/**
 * @param clients the clients to set
 */
public void setClients(Clients clients) {
      this.clients = clients;
        initializeClientInfo();
}

private void calculPrixTotal(){
      int i = 0;
      prixTotal = 0;
      for(i = 0 ; i < this.listeCommande.size() ; i++){
            prixTotal = prixTotal + listeCommande.get(i).getPrixTotal();
      }
      txt_prix_total.setText(NumberFormat.getInstance(java.util.Locale.FRENCH).format(prixTotal));
}


public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("commande milay be");
        txt_prix_reste.setEditable(false);
        txt_prix_total.setEditable(false);
        lbl_erreur_avance.setVisible(false);
        ecritureAvance();
        //initializeTableCommande();
}
public void initializeComboboxArticle(){
      javafx.concurrent.Service<List<Articles>> articleService = marchandisesServices.getAllArticleData();
        articleService.setOnSucceeded(event -> {
            List<Articles> data = articleService.getValue();
            System.out.println(data);
                  listeArticle = FXCollections.observableArrayList(data);
                  cbx_type_article.setItems(listeArticle);
            });
      articleService.start();
}
@FXML
private void generationDeFactureAvance(ActionEvent actionEvent) throws IOException {
      enregistrementCommande();
      FactureAvance factureAvance = generateFactureAvance();
      //PdfService.generationFactureAccompte(factureAvance);;      
      //PdfService.generationDeFactureFinal(commande);
}

private FactureAvance generateFactureAvance(){
      FactureAvance factureAvance =  new FactureAvance();
      factureAvance.setCommande(commande);
      //factureAvance.setNumeroFacture("15");
      factureAvance.setDateFacturation(LocalDate.now());
      factureAvance.setPrixAvance(prixAvance);
      if(prixAvance > 0){
            return factureAvance;      
      }else{
            return null;
      }
}
private void enregistrementCommande(){
      try {      
            if(datePicker_livraison.getValue() == null){
                  throw new Erreur("Veuiller remplir le date de livraison");
                  
            }
            commande.setDateCommande(datePicker_commande.getValue());
            commande.setDateLivraison(datePicker_livraison.getValue());
            commande.setClient(clients);
            commande.setListeCommandeClient(listeCommande);
            commandeServ.nouveauCommande(commande);
      } catch (Erreur e) {
                  // TODO Auto-generated catch block
            //      e.printStackTrace();
            appUtils.warningAlertDialog("AVERTISSEMENT",e.getMessage().toUpperCase());
            }
}

private void ecritureAvance(){
      txt_prix_avance.textProperty().addListener((observable, oldValue, newValue) -> {
            definisseReste(newValue);
            if(prixTotal < prixAvance){
                  lbl_erreur_avance.setText(" Avance très élevé ");
                  txt_prix_avance.setStyle("-fx-border-color:red;");
                  lbl_erreur_avance.setVisible(true);
            }else{
                  txt_prix_avance.setStyle("-fx-border-color:white;");
                  lbl_erreur_avance.setVisible(false);      
            }
      });
}

private void definisseReste(String newValue){
      if(newValue.isEmpty()==true){
            prixAvance = 0;
      }else{
            prixAvance = Double.parseDouble(newValue);
      }
      
      if(prixTotal >= 0){
            prixReste = prixTotal - prixAvance;
            txt_prix_reste.setText(String.valueOf(prixReste));
      }
      txt_prix_avance.setText(String.valueOf(newValue));
}

@FXML
private void enregistrementCommandeArticle(ActionEvent actionEvent) throws IOException {
      if (txt_nbr_article.getText().isEmpty() || cbx_type_article.getValue() == null || cbx_type_services.getValue() == null){
            appUtils.warningAlertDialog("AVERTISSEMENT","VEUILLEZ COMPLETEZ TOUS LES CHAMPS");
      }else{
            CommandeClient commandeClient = new CommandeClient();
            commandeClient.setNombre(Integer.parseInt(txt_nbr_article.getText()));
            commandeClient.setArticle((Articles)cbx_type_article.getValue());
            commandeClient.setService((Service)cbx_type_services.getValue());
            commandeClient.generateButtonSupprimer(this, listeCommande);
            listeCommande.add(commandeClient);
            miseAJourTableViewCommande();
      }
}
public void miseAJourTableViewCommande(){
            tableCommande.setItems(listeCommande);
            calculPrixTotal();
            definisseReste(txt_prix_avance.getText());
}

public void initializeComboboxService(){
      javafx.concurrent.Service<List<Service>> serviceS = serviceService.getAllArticleData();
      serviceS.setOnSucceeded(event->{
            List<Service> data = serviceS.getValue();
            listeService = FXCollections.observableArrayList(data);
            cbx_type_services.setItems(listeService);
      });
      serviceS.start();
}
@FXML
private void backToInfoClient(ActionEvent actionEvent) throws IOException {
      Node node_source = (Node) actionEvent.getSource();
      Stage stage = (Stage) node_source.getScene().getWindow();
      Parent parent = FXMLLoader.load(getClass().getResource("/fxml/commande/info-client.fxml"));
      stage.setTitle("COMMANDE");
      content_nouveau_commande.getChildren().removeAll();
      content_nouveau_commande.getChildren().setAll(parent);
}

private void initializeClientInfo(){
      lbl_client.setText("Clients : "+clients.getNom_client() +"  "+ clients.getPrenom_client());
      lbl_client_adresse.setText("Adresse : "+clients.getAdresse_client_1()+" | "+clients.getAdresse_client_2());
      lbl_client_contact.setText("Contact : "+clients.getContact_client_1()+" | "+clients.getContact_client_2());
      datePicker_commande.setValue(LocalDate.now());
      txt_code.setEditable(false);
      txt_code.setText(commande.getCode());
}


    public void initializeTableCommande(){
     listeCommande = FXCollections.observableArrayList();
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
      
        TableColumn<CommandeClient, Button> boutonSupp 
              = new TableColumn<CommandeClient, Button>("");

        nomArticle.setCellValueFactory(new PropertyValueFactory<>("nomArticle"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        service.setCellValueFactory(new PropertyValueFactory<>("service"));
        prixUnitaire.setCellValueFactory(new PropertyValueFactory<>("prixUnitaireAffiche"));
        total.setCellValueFactory(new PropertyValueFactory<>("prixTotalAffiche"));
        boutonSupp.setCellValueFactory(new PropertyValueFactory<>("bouttonSupprimer"));
        //ObservableList<Service> list = getServiceList();
        tableCommande.setItems(listeCommande);
        tableCommande.getColumns().addAll(nomArticle,nombre,service,prixUnitaire,total,boutonSupp);
      } 
}
