package org.app.bp.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import org.app.bp.services.CaisseService;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CaisseJourControlleur implements Initializable{

    @FXML
    private Label lbl_titre;
    @FXML
    private Label lbl_avance;
    @FXML
    private Label lbl_livraison;
    @FXML
    private Label lbl_total;
    private CaisseService caisseServ = new CaisseService();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'initialize'");
        lbl_avance.setAlignment(Pos.BASELINE_RIGHT);

        lbl_livraison.setAlignment(Pos.BASELINE_RIGHT);
        lbl_total.setAlignment(Pos.BASELINE_RIGHT);
        
        double l = caisseServ.getTotalLivraisonCaisse(LocalDate.now());
        double a = caisseServ.getTotalAvanceCaisse(LocalDate.now());
        lbl_livraison.setText(NumberFormat.getInstance(Locale.FRENCH).format(l)+" Ar ");
        lbl_avance.setText(NumberFormat.getInstance(Locale.FRENCH).format(a) +" Ar ");
        lbl_total.setText(NumberFormat.getInstance(Locale.FRENCH).format(l+a) + " Ar "); 
        Date androany = new Date();
        DateFormat fullDateFormat = DateFormat.getDateInstance(DateFormat.FULL);
            
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO, event -> lbl_titre.setText(fullDateFormat.format(androany).toUpperCase()+" "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    
    }
    private void setStyle(Label label){
        

    }
     @FXML
    private void backToInfoClient(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        Stage stage = (Stage) node_source.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home/dashboard.fxml"));
        Parent premiereSceneParent = loader.load();
        Scene premiereScene = new Scene(premiereSceneParent);
        stage.setScene(premiereScene);
        stage.show();       
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

    
}
