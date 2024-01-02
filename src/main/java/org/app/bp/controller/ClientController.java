package org.app.bp.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.app.bp.models.Clients;
import org.app.bp.services.ClientServices;
import org.app.bp.utils.Utils;
import org.controlsfx.control.MaskerPane;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientController implements Initializable {

    @FXML
    private TextField txt_nom_client;
    @FXML
    private TextField txt_prenom_client;
    @FXML
    private TextField txt_contact_client_1;
    @FXML
    private TextField txt_contact_client_2;
    @FXML
    private TextField txt_adresse_client_1;
    @FXML
    private TextField txt_adresse_client_2;
    @FXML
    private Button btn_enregistrer_client;
    @FXML
    private Button btn_suivant;
    @FXML
    private TableView<Clients> table_client;
    @FXML
    private TableColumn<Clients, Integer> COL_NUMERO_CLIENT;
    @FXML
    private TableColumn<Clients, String> COL_NOM_CLIENT;
    @FXML
    private TableColumn<Clients, String> COL_PRENOM_CLIENT;
    @FXML
    private TableColumn<Clients, String> COL_CONTACT_CLIENT_1;
    @FXML
    private TableColumn<Clients, String> COL_CONTACT_CLIENT_2;
    @FXML
    private TableColumn<Clients, String> COL_ADRESSE_CLIENT_1;
    @FXML
    private TableColumn<Clients, String> COL_ADRESSE_CLIENT_2;
    @FXML
    private TextField txt_search_client;
    @FXML
    private MaskerPane table_client_loading;
    @FXML
    private Label lbl_nom_client;
    @FXML
    private Label lbl_prenom_client;
    @FXML
    private Label lbl_contact_client_1;
    @FXML
    private Label lbl_contact_client_2;
    @FXML
    private Label lbl_adresse_client_1;
    @FXML
    private Label lbl_adresse_client_2;
    private ClientServices clientServices = new ClientServices();
    private Utils appUtils = new Utils();
    private Clients clientSelected = null;
    @FXML
    private AnchorPane content_info_client;

    public boolean handleValidateForm(){
        boolean is = true;
        if (txt_nom_client.getText().isEmpty()){
            appUtils.warningAlertDialog("AVERTISSEMENT","VEUILLEZ ENTRER LE NOM DU CLIENT");
            is = false;
        }
        if (txt_contact_client_1.getText().isEmpty()){
            appUtils.warningAlertDialog("AVERTISSEMENT","VEUILLEZ ENTRER LE CONTACT 1 DU CLIENT");
            is = false;
        }
        return true;
    }

    public void clearForm(){
        txt_nom_client.clear();
        txt_prenom_client.clear();
        txt_contact_client_1.clear();
        txt_contact_client_2.clear();
        txt_adresse_client_1.clear();
        txt_adresse_client_2.clear();
    }

    @FXML
    private void handleSaveClientButton(ActionEvent actionEvent){
        if (handleValidateForm()){
            Clients nouveau_client = new Clients(
                txt_nom_client.getText().trim(),
                txt_prenom_client.getText().trim(),
                txt_contact_client_1.getText().trim(),
                txt_contact_client_2.getText().trim(),
                txt_adresse_client_1.getText().trim(),
                txt_contact_client_2.getText().trim()
            );

            Service<Boolean> addNewClientService = clientServices.addNewClient(nouveau_client);
            addNewClientService.setOnSucceeded(onSucceededEvent -> {
                if (addNewClientService.getValue()){
                    Service<Integer> getLastIdClients = clientServices.getLastIdFromClientTable();
                    getLastIdClients.setOnSucceeded((t) -> {
                        nouveau_client.setId_client(getLastIdClients.getValue());
                        try {
                            clientSelected = nouveau_client;
                            detailsClientPourNouveauCommande(actionEvent);

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        //table_client.getItems().add(nouveau_client);
                        
                        //appUtils.successAlertDialog("SUCCESS",nouveau_client.getNom_client() + " Ajouter ");
                        //clearForm();
                    });
                    getLastIdClients.start();
                } else {
                    appUtils.erreurAlertDialog("ERREUR","Erreur lors de l'ajout de nouveau club");
                    clearForm();
                }
            });
            addNewClientService.start();
        }
    }
    @FXML
    private void handleDeleteMenu(){
        Clients client_effacer = table_client.getSelectionModel().getSelectedItem();
        Service<Boolean> deleteClient = clientServices.deleteClient(client_effacer);
        deleteClient.setOnSucceeded((onSucceededEvent) -> {
            if(deleteClient.getValue()){
                table_client.getItems().remove(client_effacer);
                appUtils.successAlertDialog("SUCCESS","Club " + client_effacer.getNom_client() + " Effacer");
            } else {
                appUtils.erreurAlertDialog("ERREUR","Erreur lors de la suppression du club " + client_effacer.getNom_client());
            }
        });
        deleteClient.setOnFailed(setOnFailedEvent -> {
            deleteClient.getException().getMessage();
        });
        deleteClient.start();
    }
    @FXML
    private void backACCEUIL(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        Stage stage = (Stage) node_source.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home/dashboard.fxml"));
        Parent premiereSceneParent = loader.load();
        Scene premiereScene = new Scene(premiereSceneParent);
        stage.setScene(premiereScene);
        stage.show();       
    }

    @FXML
    private void retourVersPremiereScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home/dashboard.fxml"));
        Parent premiereSceneParent = loader.load();
        Scene premiereScene = new Scene(premiereSceneParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(premiereScene);
        stage.show();
    }
    @FXML
    private void handleSelectedClient(){
        clientSelected = table_client.getSelectionModel().getSelectedItem();
        if(clientSelected != null){

            lbl_nom_client.setVisible(true);
            lbl_nom_client.setText(clientSelected.getNom_client());

            lbl_prenom_client.setVisible(true);
            lbl_prenom_client.setText(clientSelected.getPrenom_client());

            lbl_contact_client_1.setVisible(true);
            lbl_contact_client_1.setText(clientSelected.getContact_client_1());

            lbl_contact_client_2.setVisible(true);
            lbl_contact_client_2.setText(clientSelected.getContact_client_2());

            lbl_adresse_client_1.setVisible(true);
            lbl_adresse_client_1.setText(clientSelected.getAdresse_client_1());

            lbl_adresse_client_2.setVisible(true);
            lbl_adresse_client_2.setText(clientSelected.getAdresse_client_2());

            btn_suivant.setVisible(true);
        } else {
            appUtils.warningAlertDialog("AVERTISSEMENT","VEUILLEZ CHOISIR OU AJOUTER UN(E) CLIENT(E) ");
        }
    }


    @FXML
    private void nextSceneCommande(ActionEvent actionEvent) throws IOException {
            Node node_source = (Node) actionEvent.getSource();
            Stage stage = (Stage) node_source.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("/fxml/commande/marchandises.fxml"));
            stage.setTitle("MARCHANDISES");

            MarchandisesController marchandisesController = MarchandisesController.getInstance();
            if (marchandisesController != null){
                marchandisesController.setClientData(clientSelected);
            } else {
                appUtils.erreurAlertDialog("ERREUR","UNE ERREUR EST SURVENUE, VEUILLEZ CHOISIR OU AJOUTER UN CLIENT");
            }

            content_info_client.getChildren().removeAll();
            content_info_client.getChildren().setAll(parent);
    }

    @FXML
    private void detailsClientPourNouveauCommande(ActionEvent event) throws IOException {
            Node node_source = (Node) event.getSource();
            Stage stage = (Stage) node_source.getScene().getWindow();
            System.out.println("Nom = "+clientSelected.getNom_client());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/commande/nouveau-commande.fxml"));
            Parent parent = loader.load();
            System.out.println("Contact = "+clientSelected.getNom_client());
            Commande2Controlleur commande2Controlleur = loader.getController();
            commande2Controlleur.setClients(clientSelected);
            commande2Controlleur.initializeComboboxArticle();
            commande2Controlleur.initializeComboboxService();
            commande2Controlleur.initializeTableCommande();
            stage.setTitle("NOUVEAU COMMANDE");
            content_info_client.getChildren().removeAll();
            content_info_client.getChildren().setAll(parent);  
    }


    @FXML
    private void detailsClientCommande(ActionEvent event) throws IOException {
            Node node_source = (Node) event.getSource();
            Stage stage = (Stage) node_source.getScene().getWindow();
            System.out.println("Nom = "+clientSelected.getNom_client());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/facture/liste-commande.fxml"));
            Parent parent = loader.load();
            System.out.println("Contact = "+clientSelected.getNom_client());
            ListeCommande listeCommande = loader.getController();
            listeCommande.setClient(clientSelected);
            listeCommande.initializeTableCommande();
            listeCommande.setClassInitial(getClass());
            stage.setTitle("FACTURATION");
            content_info_client.getChildren().removeAll();
            content_info_client.getChildren().setAll(parent);  
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_suivant.setVisible(false);
        lbl_nom_client.setVisible(false);
        lbl_prenom_client.setVisible(false);
        lbl_contact_client_1.setVisible(false);
        lbl_contact_client_2.setVisible(false);
        lbl_adresse_client_1.setVisible(false);
        lbl_adresse_client_2.setVisible(false);

        COL_NUMERO_CLIENT.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        COL_NOM_CLIENT.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
        COL_PRENOM_CLIENT.setCellValueFactory(new PropertyValueFactory<>("prenom_client"));
        COL_CONTACT_CLIENT_1.setCellValueFactory(new PropertyValueFactory<>("contact_client_1"));
        COL_CONTACT_CLIENT_2.setCellValueFactory(new PropertyValueFactory<>("contact_client_2"));
        COL_ADRESSE_CLIENT_1.setCellValueFactory(new PropertyValueFactory<>("adresse_client_1"));
        COL_CONTACT_CLIENT_2.setCellValueFactory(new PropertyValueFactory<>("adresse_client_2"));

        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit_menu = new MenuItem("EDITER");
        MenuItem delete_menu = new MenuItem("EFFACER");
        contextMenu.getItems().addAll(edit_menu, delete_menu);
        table_client.setContextMenu(contextMenu);

        delete_menu.setOnAction((event) -> {
            handleDeleteMenu();
        });

        ClientServices clientServices = new ClientServices();

        Platform.runLater(() -> {
            table_client_loading.setDisable(false);
            table_client_loading.toFront();
            Service<List<Clients>> getClientDataService = clientServices.getClientsData();
            getClientDataService.setOnSucceeded(onSucceededEvent -> {
                table_client.getItems().setAll(getClientDataService.getValue());
                table_client_loading.setDisable(true);
                table_client_loading.toBack();
            });
            getClientDataService.start();
        });
    }
}
