/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app.bp.models;

import java.sql.SQLException;

import org.app.bp.controller.UtilisateurControlleur;
import org.app.bp.services.UtilisateurDAO;
import org.app.bp.utils.Erreur;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author Cocone
 */
public class Utilisateur {
    int id_utilisateur;
    int role;
    String nom;
    String mot_de_passe;
    String nom_utilisateur;
    String prenom;
    String contact;

    
    TextField txt_nom;
    TextField txt_mot_de_passe;
    TextField txt_nom_utilisateur;
    TextField txt_prenom;
    TextField txt_contact;
    Button modifier;
    Button delete;
 

    /**
     * @return the delete
     */
    public Button getDelete() {
        return delete;
    }
    /**
     * @param delete the delete to set
     */
    public void setDelete(Button delete) {
        this.delete = delete;
    }

    UtilisateurControlleur utilConctrolleur;
   /**
     * @return the modifier
     */
    public Button getModifier() {
        return modifier;
    }
    /**
     * @param modifier the modifier to set
     */
    public void setModifier(Button modifier) {
        this.modifier = modifier;
    }
    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }
    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return the txt_contact
     */
    public TextField getTxt_contact() {
        return txt_contact;
    }
    /**
     * @param txt_contact the txt_contact to set
     */
    public void setTxt_contact(TextField txt_contact) {
        this.txt_contact = txt_contact;
    }
    public void generateTextField(UtilisateurDAO dao){
        txt_nom = initialiseTextField(txt_nom, nom);
        txt_mot_de_passe = initialiseTextField(txt_mot_de_passe, mot_de_passe);
        txt_mot_de_passe.setVisible(false);
        txt_nom_utilisateur = initialiseTextField(txt_nom_utilisateur, nom_utilisateur) ;
        txt_contact = initialiseTextField(txt_contact, contact);
        txt_prenom = initialiseTextField(txt_prenom, prenom);       
        
        modifier = new Button("modifier");
        modifier.setStyle("-fx-background-color: yellow;");
        modifier.setVisible(false);
        modifier.setOnAction(event->{
            this.setContact(txt_contact.getText());
            this.setNom(txt_nom.getText());
            this.setPrenom(txt_prenom.getText());
            
            try {
                this.setNom_utilisateur(txt_nom_utilisateur.getText());
            this.setMot_de_passe(txt_mot_de_passe.getText());
                dao.update(this);
                utilConctrolleur.entreeDonneeDansTableUser();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch(Erreur er){
                utilConctrolleur.afficheERREUR(er.getMessage().toUpperCase());
            }
        });
        delete = new Button("supprimer");
        delete.setStyle("-fx-background-color: red;");
        delete.setVisible(false);
        delete.setOnAction(event->{
            try {
                dao.delete(this);
                utilConctrolleur.entreeDonneeDansTableUser();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }   
    public void selectionneer(UtilisateurControlleur co){
        this.utilConctrolleur = co;
        txt_nom.setEditable(true);
        txt_mot_de_passe.setEditable(true);
        txt_mot_de_passe.setVisible(true);
        txt_contact.setEditable(true);
        txt_prenom.setEditable(true);
        txt_nom_utilisateur.setEditable(true);
        modifier.setVisible(true);
        delete.setVisible(true);
    }

    public void deselectionner(){
            txt_nom.setEditable(false);
        txt_mot_de_passe.setEditable(false);
        txt_mot_de_passe.setVisible(false);
        txt_contact.setEditable(false);
        txt_prenom.setEditable(false);
        txt_nom_utilisateur.setEditable(false);
        modifier.setVisible(false);
        delete.setVisible(false);
    }
    private TextField initialiseTextField(TextField txt,String text){
        txt = new TextField(text);
        txt.setEditable(false);
        return txt;
    } 
    /**
     * @return the txt_nom
     */
    public TextField getTxt_nom() {
        return txt_nom;
    }

    /**
     * @param txt_nom the txt_nom to set
     */
    public void setTxt_nom(TextField txt_nom) {
        this.txt_nom = txt_nom;
    }

    /**
     * @return the txt_mot_de_passe
     */
    public TextField getTxt_mot_de_passe() {
        return txt_mot_de_passe;
    }

    /**
     * @param txt_mot_de_passe the txt_mot_de_passe to set
     */
    public void setTxt_mot_de_passe(TextField txt_mot_de_passe) {
        this.txt_mot_de_passe = txt_mot_de_passe;
    }

    /**
     * @return the txt_nom_utilisateur
     */
    public TextField getTxt_nom_utilisateur() {
        return txt_nom_utilisateur;
    }

    /**
     * @param txt_nom_utilisateur the txt_nom_utilisateur to set
     */
    public void setTxt_nom_utilisateur(TextField txt_nom_utilisateur) {
        this.txt_nom_utilisateur = txt_nom_utilisateur;
    }

    /**
     * @return the txt_prenom
     */
    public TextField getTxt_prenom() {
        return txt_prenom;
    }

    /**
     * @param txt_prenom the txt_prenom to set
     */
    public void setTxt_prenom(TextField txt_prenom) {
        this.txt_prenom = txt_prenom;
    }

    /**
     * @return the nom_utilisateur
     */
    public String getNom_utilisateur() {
        return nom_utilisateur;
    }

    /**
     * @param nom_utilisateur the nom_utilisateur to set
     */
    public void setNom_utilisateur(String nom_utilisateur) throws Erreur{
        this.nom_utilisateur = nom_utilisateur;
        if(nom_utilisateur.isEmpty() == true){
            throw new Erreur("Veuiller entrer le Nom d'Utilisateur");
        }
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) throws Erreur{
        this.mot_de_passe = mot_de_passe;
        if(mot_de_passe.isEmpty() == true){
            throw new Erreur("Veuiller entrer le Mot de passe");
        }
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
