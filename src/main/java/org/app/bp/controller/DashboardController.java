package org.app.bp.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.app.bp.utils.Utils;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashboardController {

    @FXML
    private Label label_date;

    @FXML
    private Label label_time;

    @FXML
    private AnchorPane content_home;

    @FXML
    private Button btn_g_users;

    @FXML
    private Button btn_commande;

    @FXML
    private Button btn_g_marchandise;

    @FXML
    private Button btn_configuration;

    private Stage stage;

    Utils appUtils = new Utils();


    @FXML
    public void initialize(){

        Date androany = new Date();
        DateFormat fullDateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        label_date.setText(fullDateFormat.format(androany).toUpperCase());

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
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/sites/gestion-sites.fxml"));
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
    private void sceneConfiguration() {
        btn_configuration.setOnAction(event -> {
            appUtils.warningAlertDialog("AVERTISSEMENT","BIENTOT DISPONIBLE");
        });
    }
}
