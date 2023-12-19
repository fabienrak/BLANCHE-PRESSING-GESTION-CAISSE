package org.app.bp.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.app.bp.services.SiteServices;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

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
        
    }
}
