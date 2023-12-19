package org.app.bp.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.app.bp.models.Sites;
import org.app.bp.services.SiteServices;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SitesController implements Initializable {

    @FXML
    private Label lbl_lieu;

    @FXML
    private Label lbl_contact;

    @FXML
    private Label lbl_email;

    @FXML
    private TextField txt_lieu;
    @FXML
    private TextField txt_contact;
    @FXML
    private TextField txt_mail;
    private Sites site;
    private SiteServices siteServ  = new SiteServices();

    @FXML
    private void retourVersPremiereScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home/dashboard.fxml"));
        Parent premiereSceneParent = loader.load();
        Scene premiereScene = new Scene(premiereSceneParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(premiereScene);
        stage.show();
    }

    private void entrerSiteDansLabel(){
        site = siteServ.getSites();
        lbl_lieu.setText(site.getLieu());
        lbl_contact.setText(site.getContact());
        lbl_email.setText(site.getEmail());
        txt_lieu.setText(site.getLieu());
        txt_contact.setText(site.getContact());
        txt_mail.setText(site.getEmail());
    }

    @FXML
    private void enregistrer(ActionEvent event) throws IOException {
        site.setLieu(txt_lieu.getText());
        site.setContact(txt_contact.getText());
        site.setEmail(txt_mail.getText());
        siteServ.modificationSite(site);
        entrerSiteDansLabel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        entrerSiteDansLabel();
    }


}
