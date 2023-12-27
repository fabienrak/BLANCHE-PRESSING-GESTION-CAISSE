package org.app.bp.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.app.bp.models.Utilisateur;
import org.app.bp.services.UtilisateurDAO;
import org.app.bp.utils.Erreur;
import org.app.bp.utils.Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthLoginController implements Initializable {

    @FXML
    private Button btn_login;
    private Stage stage;
    private Parent parent;
    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_password;
    @FXML
    private ComboBox cbx_site;
    private UtilisateurDAO dao = new UtilisateurDAO();
    Utils appUtils = new Utils();

    public void afficheERREUR(String er){
        appUtils.warningAlertDialog("AVERTISSEMENT",er);
        
    }   
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

    @FXML 
    private void connection(ActionEvent actionEvent)throws IOException{
        Utilisateur utilisateur = new Utilisateur();
        try {
            utilisateur.setNom_utilisateur(txt_username.getText());
            utilisateur.setMot_de_passe(txt_password.getText());
            dao.login(utilisateur);
            switchToDashboard();
        } catch (Erreur e) {
            // TODO Auto-generated catch block
            afficheERREUR(e.getMessage().toString());
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
