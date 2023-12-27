package org.app.bp.models;

import java.sql.SQLException;
import java.text.NumberFormat;

import org.app.bp.controller.ArticleController;
import org.app.bp.services.MarchandisesServices;
import org.app.bp.utils.Erreur;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Articles {

    private int id_article;
    private String nom_article;
    private int prix;
    private String prefix_code;
    private TextField txt_nom;
    private TextField txt_prix;
    private TextField txt_prefix_code;
    private Button modifier;
    private Button delete;

    
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
    private TextField initialiseTextField(TextField txt,String text){
        txt = new TextField(text);
        txt.setEditable(false);
        return txt;
    } 
    public void generateTextField(MarchandisesServices marchandises){
        txt_nom = initialiseTextField(txt_nom, nom_article);
        txt_prefix_code = initialiseTextField(txt_prefix_code, prefix_code);
        txt_prix = initialiseTextField(txt_prix, String.valueOf(prix));

        modifier = new Button("modifier");
        modifier.setStyle("-fx-background-color: yellow;");
        modifier.setVisible(false);
        modifier.setOnAction(event->{
            try {
                this.setNom_article(txt_nom.getText());
                this.setPrefix_code(txt_prefix_code.getText());
                this.setPrix(Integer.parseInt(txt_prix.getText()));
                marchandises.update(this);
                articleController.reinitialisation();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch(Erreur er){
                articleController.afficheERREUR(er.getMessage());
            }
        });
        delete = new Button("supprimer");
        delete.setStyle("-fx-background-color: red;");
        delete.setVisible(false);
        delete.setOnAction(event->{
            try {
                marchandises.delete(this);
                articleController.reinitialisation();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }
    private ArticleController articleController;
    public void selectionneer(ArticleController co){
        this.articleController = co;
        txt_nom.setEditable(true);
        txt_prefix_code.setEditable(true);
        txt_prix.setEditable(true);
        modifier.setVisible(true);
        delete.setVisible(true);
    }

    public void deselectionner(){
        txt_nom.setEditable(false);
        txt_prefix_code.setEditable(false);
        txt_prix.setEditable(false);
        modifier.setVisible(false);
        delete.setVisible(false);
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
     * @return the txt_prix
     */
    public TextField getTxt_prix() {
        return txt_prix;
    }

    /**
     * @param txt_prix the txt_prix to set
     */
    public void setTxt_prix(TextField txt_prix) {
        this.txt_prix = txt_prix;
    }

    /**
     * @return the txt_prefix_code
     */
    public TextField getTxt_prefix_code() {
        return txt_prefix_code;
    }

    /**
     * @param txt_prefix_code the txt_prefix_code to set
     */
    public void setTxt_prefix_code(TextField txt_prefix_code) {
        this.txt_prefix_code = txt_prefix_code;
    }

    /**
     * @param nom_article
     */
    public Articles(String nom_article) {
        this.nom_article = nom_article;
    }

    public Articles(int id_article, String nom_article, int prix, String prefix_code) {
        this.id_article = id_article;
        this.nom_article = nom_article;
        this.prix = prix;
        this.prefix_code = prefix_code;
    }

    public Articles(String nom_article, int prix, String prefix_code) {
        this.id_article = -1;
        this.nom_article = nom_article;
        this.prix = prix;
        this.prefix_code = prefix_code;
    }

    public int getId_article() {
        return id_article;
    }

    public void setId_article(int id_article) {
        this.id_article = id_article;
    }

    public String getNom_article() {
        return nom_article;
    }

    public void setNom_article(String nom_article)throws Erreur {
        this.nom_article = nom_article;
        if(nom_article.isEmpty() == true){
            throw new Erreur("Veuiller completer le nom");
        }
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getPrefix_code() {
        return prefix_code;
    }

    public void setPrefix_code(String prefix_code) throws Erreur{
        this.prefix_code = prefix_code;
        if(prefix_code.isEmpty() == true){
            throw new Erreur("Veuiller completer le code");    
        }
    }

    public String toString(){
        return this.nom_article +" ( "+NumberFormat.getInstance(java.util.Locale.FRENCH).format(this.prix)+" Ar )";
    }
}
