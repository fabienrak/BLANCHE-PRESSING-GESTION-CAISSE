package org.app.bp.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.app.bp.models.Articles;
import org.app.bp.models.CommandeClient;
import org.app.bp.models.CommandeFinal;
import org.app.bp.models.Service;
import org.app.bp.utils.DBUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class HistoriqueService{
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DBUtils.getConnection();

    public ObservableList<CommandeClient> getListCommandeClient(CommandeFinal commande){
        String query = "SELECT * FROM commande_client where id_commande="+commande.getIdCommande();
        ObservableList<CommandeClient> data = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            System.out.println("commande client = "+ resultSet.getRow());

            while (resultSet.next()) {
                CommandeClient commandeClient = new CommandeClient();
                commandeClient.setNomArticle(resultSet.getString("nom_article"));
                commandeClient.setArticle(new Articles(commandeClient.getNomArticle()));
                commandeClient.setNombre(resultSet.getInt("nombre_article"));
                commandeClient.setService(new Service(0, resultSet.getString("nom_service")));
                commandeClient.setPrixUnitaire(resultSet.getDouble("prix_unitaire"));
            
                data.add(commandeClient);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return data;
    }
    public ObservableList<CommandeClient> getTousLesCommandeDunDateDonnee(ObservableList<CommandeFinal> commandeFinal){
        String query = "SELECT nom_article,sum(nombre_article) as nombre_article from commande_client where 1=1 and(";
        for (int i = 0; i < commandeFinal.size(); i++) {
            if(i>0){
                query=query+" or ";
            }
            query=query+ "  id_commande="+commandeFinal.get(i).getIdCommande();
            
        }
        query=query+" )group by nom_article";
        System.out.println("queerrryyy"+query);
        ObservableList<CommandeClient> data = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            System.out.println("commande client = "+ resultSet.getRow());

            while (resultSet.next()) {
                CommandeClient commandeClient = new CommandeClient();
                commandeClient.setNomArticle(resultSet.getString("nom_article"));
                commandeClient.setArticle(new Articles(commandeClient.getNomArticle()));
                commandeClient.setNombre(resultSet.getInt("nombre_article"));
                System.out.println(commandeClient.getNomArticle());
                data.add(commandeClient);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return data;
    }
    
    
}