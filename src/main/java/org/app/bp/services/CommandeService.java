package org.app.bp.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.app.bp.models.Articles;
import org.app.bp.models.Clients;
import org.app.bp.models.CommandeClient;
import org.app.bp.models.CommandeFinal;
import org.app.bp.models.Service;
import org.app.bp.utils.DBUtils;
import org.app.bp.utils.Erreur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommandeService {

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DBUtils.getConnection();
    
    private int getNombreCommande(){
        int t = 0;
        String liste_site_query = "SELECT count(*) from commande";
        try {
            preparedStatement = connection.prepareStatement(liste_site_query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                t = resultSet.getInt("count(*)");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return t;
    }
    public void nouveauCommande(CommandeFinal commande){
        addCommandeFinal(commande);
            int i = 0;
            for( i = 0 ; i < commande.getListeCommandeClient().size() ; i++){
                addCommandeClient(commande.getListeCommandeClient().get(i), commande);
                }
        //addcom.start();
    }
    private void addCommandeClient(CommandeClient commande,CommandeFinal cf){
        String ajout_client_query = "INSERT INTO commande_client (id_commande,nom_article,nom_service,prix_unitaire,nombre_article) VALUES (?,?,?,?,?)";
        try{
            preparedStatement = connection.prepareStatement(ajout_client_query);
            preparedStatement.setInt(1, cf.getIdCommande());
            preparedStatement.setString(2, commande.getArticle().getNom_article());
            preparedStatement.setString(3, commande.getService().getNomService());
            preparedStatement.setDouble(4, commande.getArticle().getPrix());
            preparedStatement.setInt(5, commande.getNombre());
            System.out.println(commande.getArticle().getPrix());
            preparedStatement.executeUpdate();;
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public ObservableList<CommandeFinal> getListeCommmandeFinal(Clients client){
        String query = "SELECT * FROM commande where id_clients="+client.getId_client();
        System.out.println(query);
        ObservableList<CommandeFinal> data = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            System.out.println("commande = "+ resultSet.getRow());
            while (resultSet.next()) {
                CommandeFinal commandeFinal = new CommandeFinal();
                commandeFinal.setClient(client);
                commandeFinal.setCode(resultSet.getString("code_commande"));
                System.out.println(commandeFinal.getCode());
                commandeFinal.setIdCommande(resultSet.getInt("id_commande"));
                commandeFinal.setDateCommande(LocalDate.parse(resultSet.getString("date_commande")));
                commandeFinal.setDateLivraison(LocalDate.parse(resultSet.getString("date_livraison")));
                commandeFinal.setPrixTotal(getPrixTotalCommandeFinal(commandeFinal));
                commandeFinal.setAvance(getPrixTotalAvance(commandeFinal));
                data.add(commandeFinal);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }catch(Erreur er){

        }
        return data;
    
    }

    public double getPrixTotalAvance(CommandeFinal commande){
        double t = 0;
        String liste_site_query = "SELECT sum(prix) from facturation where etat=1 and id_commande="+commande.getIdCommande();
        System.out.println(liste_site_query);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(liste_site_query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                t = resultSet.getDouble("sum(prix)");
                System.out.println(t);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return t;
    }

    public boolean presenceFactureFinale(CommandeFinal commande){
        double t = 0;
        boolean is = false;
        String liste_site_query = "SELECT * from facturation where type=1 and id_commande="+commande.getIdCommande();
        System.out.println(liste_site_query);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(liste_site_query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                t = 1;
                System.out.println(t);
                is = true;
                break;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return is;
    }

    public double getPrixTotalCommandeFinal(CommandeFinal commande){
        double t = 0;
        String liste_site_query = "SELECT sum(prix_unitaire * nombre_article) from commande_client where id_commande="+commande.getIdCommande();
        System.out.println(liste_site_query);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(liste_site_query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                t = resultSet.getDouble("sum(prix_unitaire * nombre_article)");
                System.out.println(t);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return t;
    }

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
    
    private void addCommandeFinal(CommandeFinal commande){
        commande.setIdCommande(getNombreCommande()+1);
        String ajout_client_query = "INSERT INTO commande (id_commande,date_commande,date_livraison,code_commande,id_clients) VALUES (?,?,?,?,?)";
        try{
            preparedStatement = connection.prepareStatement(ajout_client_query);
            preparedStatement.setInt(1, commande.getIdCommande());
            preparedStatement.setString(2, commande.getDateCommande().toString());
            preparedStatement.setString(3, commande.getDateLivraison().toString());
            preparedStatement.setString(4, commande.getCode());
            preparedStatement.setInt(5, commande.getClient().getId_client());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        
    }
}
