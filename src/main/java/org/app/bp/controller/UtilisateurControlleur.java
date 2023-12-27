package org.app.bp.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.app.bp.models.Role;
import org.app.bp.models.Utilisateur;
import org.app.bp.services.UtilisateurDAO;
import org.app.bp.utils.Erreur;
import org.app.bp.utils.Utils;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UtilisateurControlleur implements Initializable{

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_username;
    
    @FXML
    private TextField txt_contact;
    
    @FXML
    private TextField txt_prenom;
    
    @FXML
    private TextField txt_mdp;

    @FXML
    private ComboBox cbx_role;
    
    Utils appUtils = new Utils();

    public void afficheERREUR(String er){
        appUtils.warningAlertDialog("AVERTISSEMENT",er);
        
    }    

    @FXML
    private TableView table_users;
    private UtilisateurDAO dao = new UtilisateurDAO();
    private Utilisateur utilisateurSelect = null;

    private void initilizationForm(){
        txt_contact.setText("");
        txt_mdp.setText("");
        txt_nom.setText("");
        txt_prenom.setText("");
        txt_username.setText("");
        cbx_role.setItems(Role.getAllRole());
    }

    public void entreeDonneeDansTableUser(){
        table_users.getItems().clear();
        table_users.setItems(dao.getAll());
    }
    @FXML
    private void ajout(ActionEvent actionEvent)throws IOException{
        Utilisateur utilisateur = new Utilisateur();
        try {
            utilisateur.setContact(txt_contact.getText());
        utilisateur.setNom_utilisateur(txt_username.getText());
        utilisateur.setNom(txt_nom.getText());
        utilisateur.setPrenom(txt_prenom.getText());
        utilisateur.setMot_de_passe(txt_mdp.getText());
        Role role = (Role)cbx_role.getSelectionModel().getSelectedItem();
        if(role == null){
            throw new Erreur("Veuiller choisir le role");
        }
        utilisateur.setRole(role.getValue());
            dao.insert(utilisateur);
            initilizationForm();
            entreeDonneeDansTableUser();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch(Erreur er){
            afficheERREUR(er.getMessage().toUpperCase());
        }
    }
     @FXML
    private void retourVersPremiereScene(ActionEvent event) throws IOException {
        // Utiliser la référence au contrôleur précédent pour revenir à la première scène
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home/dashboard.fxml"));
        Parent premiereSceneParent = loader.load();
        Scene premiereScene = new Scene(premiereSceneParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(premiereScene);
        stage.show();
    }
    @FXML
    private void handleSelectedTableUser(){
        if(utilisateurSelect != null){
            utilisateurSelect.deselectionner();
        }
        utilisateurSelect = (Utilisateur)table_users.getSelectionModel().getSelectedItem();
        if(utilisateurSelect != null){
                utilisateurSelect.selectionneer(this);
        }
    }
    public void creationTableUsers(){
        TableColumn<Utilisateur, TextField> nom 
              = new TableColumn<Utilisateur, TextField>("Nom");      
              nom.setCellValueFactory(new PropertyValueFactory<>("txt_nom"));
        TableColumn<Utilisateur, TextField> prenom 
              = new TableColumn<Utilisateur, TextField>("Prenom");      
              prenom.setCellValueFactory(new PropertyValueFactory<>("txt_prenom"));
        TableColumn<Utilisateur, TextField> nom_utilisateur 
              = new TableColumn<Utilisateur, TextField>("Nom d'Utilisateur");      
              nom_utilisateur.setCellValueFactory(new PropertyValueFactory<>("txt_nom_utilisateur"));
        TableColumn<Utilisateur, TextField> mot_de_Passe 
              = new TableColumn<Utilisateur, TextField>("Mot de Passe");      
              mot_de_Passe.setCellValueFactory(new PropertyValueFactory<>("txt_mot_de_passe"));
        TableColumn<Utilisateur, TextField> contact 
              = new TableColumn<Utilisateur, TextField>("Contact");      
              contact.setCellValueFactory(new PropertyValueFactory<>("txt_contact"));

        TableColumn<Utilisateur, Button> modifier 
              = new TableColumn<Utilisateur, Button>("");      
              modifier.setCellValueFactory(new PropertyValueFactory<>("modifier"));
        
         TableColumn<Utilisateur, Button> delete 
              = new TableColumn<Utilisateur, Button>("");      
              delete.setCellValueFactory(new PropertyValueFactory<>("delete"));
        
              table_users.getColumns().addAll(nom,prenom,nom_utilisateur,contact,mot_de_Passe,modifier,delete);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        creationTableUsers();        
        entreeDonneeDansTableUser();
        initilizationForm();
    }
    
}
