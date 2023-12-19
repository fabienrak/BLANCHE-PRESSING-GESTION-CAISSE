package org.app.bp.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.app.bp.models.CommandeFinal;
import org.app.bp.models.FactureAvance;
import org.app.bp.utils.DBUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FacturationService {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DBUtils.getConnection();

    public void nouveauFactureAvance(FactureAvance facture,CommandeFinal commande){
        String ajout_client_query = "INSERT INTO facturation (etat,id_commande,avance,date_payement,num_facture) VALUES (0,?,?,?,?)";
        try{
            preparedStatement = connection.prepareStatement(ajout_client_query);
            preparedStatement.setInt(1, commande.getIdCommande());
            preparedStatement.setDouble(2, facture.getPrixAvance());
            preparedStatement.setString(3, facture.getDateFacturation().toString());
            preparedStatement.setString(4, facture.getNumeroFacture());
            preparedStatement.executeUpdate();;
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void deleteFactureAvance(FactureAvance facture){
        String query = "DELETE FROM facturation where id_facturation="+facture.getIdFacture();
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();;
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void validerEtatFacture(FactureAvance facture){
        String query = "UPDATE  facturation set etat=1 where id_facturation="+facture.getIdFacture();
        System.out.println(query);
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();;
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public ObservableList<FactureAvance> getListeFactureAvance(CommandeFinal commande){
        String query = "SELECT * FROM facturation where id_commande="+commande.getIdCommande();
        System.out.println(query);
        ObservableList<FactureAvance> data = FXCollections.observableArrayList();
         try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                FactureAvance factureAvance = new FactureAvance();
                factureAvance.setNumeroFacture(resultSet.getString("num_facture"));
                factureAvance.setIdFacture(resultSet.getInt("id_facturation"));
                factureAvance.setDateFacturation(LocalDate.parse(resultSet.getString("date_payement")));
                factureAvance.setPrixAvance(resultSet.getDouble("avance"));
                factureAvance.setEtat(resultSet.getInt("etat"));
                factureAvance.generationButtonFacturer(commande,this);
                factureAvance.generationButtonSupprimer(commande, this);
                data.add(factureAvance);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        System.out.println("factures : "+data);
       
        return data;
    
    }
    
}
