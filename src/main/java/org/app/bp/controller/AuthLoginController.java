package org.app.bp.controller;

import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.app.bp.models.Articles;
import org.app.bp.models.Sites;
import org.app.bp.services.SiteServices;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AuthLoginController implements Initializable {

    @FXML
    private Button btn_login;
    private Stage stage;
    private Parent parent;
    @FXML
    private ComboBox cbx_site;
    SiteServices siteServices = new SiteServices();

    @FXML
    private void switchToDashboard() {
        try {
            stage = (Stage) btn_login.getScene().getWindow();
            parent = FXMLLoader.load(getClass().getResource("/fxml/home/dashboard.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Service<List<Sites>> listeSites = siteServices.getSitesData();
        listeSites.setOnSucceeded(s -> {
            List<String> site_data = new ArrayList<>();
            for (Sites sites : listeSites.getValue()){
                site_data.add(sites.getNom_site());
            }
            cbx_site.getItems().addAll(site_data);
        });
        listeSites.start();
    }
}
