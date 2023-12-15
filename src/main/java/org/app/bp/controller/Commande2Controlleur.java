package org.app.bp.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.app.bp.models.modelAffichage.Commande;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Commande2Controlleur implements Initializable{
    
    @FXML
    private TableView tableCommande;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("commande milay be");
        initializeTableCommande();
    }
    private void initializeTableCommande(){
        TableColumn<Commande, String> nomArticle 
              = new TableColumn<Commande, String>("Nom article");
        TableColumn<Commande, String> nombre 
              = new TableColumn<Commande, String>("Nombre");
        TableColumn<Commande, String> service 
              = new TableColumn<Commande, String>("Service");
        TableColumn<Commande, String> prixUnitaire 
              = new TableColumn<Commande, String>("Prix Unitaire");
        TableColumn<Commande, String> total 
              = new TableColumn<Commande, String>("Prix total");
                

        nomArticle.setCellValueFactory(new PropertyValueFactory<>("nomArticle"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        service.setCellValueFactory(new PropertyValueFactory<>("service"));
        prixUnitaire.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        total.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));
        //ObservableList<Service> list = getServiceList();
        //tableService.setItems(list);
        tableCommande.getColumns().addAll(nomArticle,nombre,service,prixUnitaire,total);
    } 
}
