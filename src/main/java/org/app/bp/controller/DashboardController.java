package org.app.bp.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.app.bp.models.Sites;
import org.app.bp.models.Utilisateur;
import org.app.bp.services.SiteServices;
import org.app.bp.services.UtilisateurDAO;
import org.app.bp.utils.Utils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DashboardController {

    @FXML
    private Label label_date;

    @FXML
    private Label label_time;

    @FXML
    private AnchorPane content_home;

    @FXML
    private AnchorPane content_total;

    @FXML
    private Button btn_g_users;

    @FXML
    private Button btn_site;

    @FXML
    private Button btn_g_marchandise;

    @FXML
    private Button btn_configuration;

    @FXML
    private Button btn_commande;
    @FXML
    private Button btn_facturation;
    @FXML 
    private Button btn_historique;

    @FXML
    private Label lbl_site;

    private Stage stage;

    Utils appUtils = new Utils();
    SiteServices siteServ = new SiteServices();
    public void affichageSite(){
        Sites sites = siteServ.getSites();
        lbl_site.setText("Site : "+sites.getLieu());
    }
    @FXML
    public void initialize(){
        Utilisateur utilisateur = UtilisateurDAO.getUtilisateur();
        if(utilisateur.getRole() == 1){
            System.out.println("utilisateur = "+utilisateur.getRole());
            btn_configuration.setVisible(false);
            btn_g_marchandise.setVisible(false);
            btn_g_users.setVisible(false);
            btn_site.setVisible(false);

            btn_commande.setLayoutX(btn_g_users.getLayoutX());
            btn_commande.setLayoutY(btn_g_users.getLayoutY());

            btn_facturation.setLayoutX(btn_site.getLayoutX());
            btn_facturation.setLayoutY(btn_site.getLayoutY());

            btn_historique.setLayoutX(btn_g_marchandise.getLayoutX());
            btn_historique.setLayoutY(btn_g_marchandise.getLayoutY());
        }
        Date androany = new Date();
        DateFormat fullDateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        label_date.setText(fullDateFormat.format(androany).toUpperCase());
        affichageSite();
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO, event -> label_time.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void sceneGestionUtilisateur(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/users/gestion-users.fxml"));
        stage.setTitle("GESTION UTILISATEUR");
        content_home.getChildren().removeAll();
        content_home.getChildren().setAll(parent);
    }

    @FXML
    private void sceneGestionService(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/service/gestion-service.fxml"));
        stage.setTitle("GESTION SERVICE");
        content_home.getChildren().removeAll();
        content_home.getChildren().setAll(parent);
    }

     @FXML
    private void scenehISTORIQUE(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        stage = (Stage) node_source.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/historique/historique-livraison.fxml"));
        Parent parent = loader.load();
        ListeCommande listeCommande = loader.getController();
        listeCommande.setLicteLivrasion(LocalDate.now());
        listeCommande.initializeTableCommande();
        listeCommande.setClassInitial(getClass());
        stage.setTitle("HISTORIQUE");
        content_total.getChildren().removeAll();
        content_total.getChildren().setAll(parent);
    }

    @FXML
    private void sceneGestionMarchandises(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/marchandises/gestion-marchandise.fxml"));
        stage.setTitle("GESTION MARCHANDISES");
        content_home.getChildren().removeAll();
        content_home.getChildren().setAll(parent);
    }

    @FXML
    private void sceneGestionSite(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        stage = (Stage) node_source.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sites/gestion-sites.fxml"));
            Parent parent = loader.load();
        SitesController sitesController = loader.getController();
        sitesController.setDashboard(this);
        stage.setTitle("GESTION SITES");
        content_home.getChildren().removeAll();
        content_home.getChildren().setAll(parent);
    }

    @FXML
    private void sceneCommande(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/commande/info-client.fxml"));
//        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/commande/gestion-commande.fxml"));
        stage.setTitle("GESTION COMMANDE");
        content_home.getChildren().removeAll();
        content_home.getChildren().setAll(parent);
    }

    @FXML
    private void sceneFacturation(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/facture/info-client.fxml"));
//        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/commande/gestion-commande.fxml"));
        stage.setTitle("FACTURATION");
        content_home.getChildren().removeAll();
        content_home.getChildren().setAll(parent);
    }


    @FXML
    private void sceneConfiguration(ActionEvent actionEvent) throws IOException  {
         Node node_source = (Node) actionEvent.getSource();
        stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/statistique/state-annee.fxml"));
//        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/commande/gestion-commande.fxml"));
        stage.setTitle("STATISTIQUE");
        content_total.getChildren().removeAll();
        content_total.getChildren().setAll(parent);
    }
}
