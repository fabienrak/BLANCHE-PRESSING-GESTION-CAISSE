package org.app.bp.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.app.bp.models.Articles;
import org.app.bp.models.Clients;
import org.app.bp.models.Services;
import org.app.bp.services.MarchandisesServices;
import org.app.bp.utils.Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MarchandisesController implements Initializable {

    @FXML
    private Button btn_retour;
    @FXML
    private AnchorPane content_enregistrement;

    @FXML
    private ComboBox cbx_type_article;
    @FXML
    private ComboBox cbx_type_services;
    @FXML
    private Label lbl_prix_unitaire;
    @FXML
    private Label lbl_client;
    @FXML
    private Label lbl_client_info;
    @FXML
    private Label lbl_id_client;
    @FXML
    private TextField txt_nbr_article;
    @FXML
    private TableColumn<Articles, Integer> COL_PRIX_UNITAIRE;
    @FXML
    private Button btn_generer_code;
    @FXML
    private TextField txt_code_article;

    //  Info Article
    @FXML
    private Label lbl_type_article;
    @FXML
    private Label lbl_nbr_article;
    @FXML
    private Label lbl_code_article;
    @FXML
    private Label lbl_service_article;
    @FXML
    private Label lbl_prix_unitaire_article;
    @FXML
    private Label lbl_prix_total;
    Utils appUtils = new Utils();
    MarchandisesServices marchandisesServices = new MarchandisesServices();
    private static MarchandisesController instance;
    public MarchandisesController(){
        instance = this;
    }
    public static MarchandisesController getInstance(){
        return instance;
    }
    public void afficheNomClient(String nom_client){
        lbl_client.setText(nom_client);
    }

    public void setClientData(Clients clients){
        lbl_client.setText(clients.getNom_client() + " " + clients.getPrenom_client());
        lbl_client_info.setText(clients.getAdresse_client_1() + " - " + clients.getContact_client_1());
        System.out.println("*-*-*-*-*-*-* ID CLIENT : " + clients.getId_client());
    }

    /**
     * Bouton Retour vers scene information client
     * */
    @FXML
    private void backToInfoClient(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        Stage stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/commande/info-client.fxml"));
        stage.setTitle("COMMANDE");
        content_enregistrement.getChildren().removeAll();
        content_enregistrement.getChildren().setAll(parent);
    }

    /**
     * Bouton suivant vers facturation
     * */
    @FXML
    private void goToFacturation(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        Stage stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/commande/facturation.fxml"));

        stage.setTitle("FACTURATION");
        content_enregistrement.getChildren().removeAll();
        content_enregistrement.getChildren().setAll(parent);
    }

    /**
     * Validation formulaire
     * */
    public boolean handleValidateForm(){
        if (txt_nbr_article.getText().isEmpty() || cbx_type_article.getValue() == null || cbx_type_services.getValue() == null){
            appUtils.warningAlertDialog("AVERTISSEMENT","VEUILLEZ COMPLETEZ TOUS LES CHAMPS");
            return false;
        }
        return true;
    }

    /**
     * Enregistrer article, commande client
     * */
    @FXML
    private void enregistrerCommandeClient(){
        if(handleValidateForm()){
            String articleClient = cbx_type_article.getValue().toString();
            String[] articleInfo = appUtils.getArticleEtCode(articleClient);
            Service<Integer> getPrixArticle = marchandisesServices.getPrixArticle(articleInfo[0], articleInfo[1]);
            getPrixArticle.setOnSucceeded((t) -> {
                System.out.println(" ############## PRIX UNITAIRE : " + getPrixArticle.getValue());
                int prixTotal = getPrixArticle.getValue() * Integer.parseInt(txt_nbr_article.getText());
                System.out.println(" [+] ############## PRIX TOTAL : "+ prixTotal);
            });
            getPrixArticle.start();
        }
    }

    /**
     * Generer code article
     * */
    private void generationCodeArticle(){
        btn_generer_code.setOnAction(event -> {
            String nom;
            String article;
            if (cbx_type_article.getValue() != null){
                nom = lbl_client.getText();
                article = cbx_type_article.getValue().toString();
                String code = appUtils.genererCodeArticle(nom,article);
                System.out.println("++++++++ Code Client genere : "+ code);
                txt_code_article.setText(code);
                btn_generer_code.setDisable(true);
            } else {
                appUtils.warningAlertDialog("AVERTISSEMENT","VEUILLEZ CHOISIR UN ARTICLE");
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lbl_type_article.setVisible(false);
        lbl_nbr_article.setVisible(false);
        lbl_code_article.setVisible(false);
        lbl_service_article.setVisible(false);
        lbl_prix_unitaire_article.setVisible(false);
        lbl_prix_total.setVisible(false);

        generationCodeArticle();
        ObservableList<Services> type_match = FXCollections.observableArrayList(Services.values());
        cbx_type_services.getItems().addAll(type_match);

        Service<List<Articles>> listeArticle = marchandisesServices.getAllArticleData();
        listeArticle.setOnSucceeded(s -> {
            List<String> article_data = new ArrayList<>();
            int prix_unitaire = 0;
            for (Articles articles : listeArticle.getValue()){
//                article_data.add(articles.getNom_article() + " - " + articles.getPrefix_code() + " - " + articles.getPrix() + " Ar");
                article_data.add(articles.getNom_article() + " - " + articles.getPrefix_code());
                prix_unitaire = articles.getPrix();
                System.out.println("*-*-*-*-*-*-*-* ARTICLE AZO : " + article_data);
            }
            cbx_type_article.getItems().addAll(article_data);

        });
        listeArticle.start();
    }
}
