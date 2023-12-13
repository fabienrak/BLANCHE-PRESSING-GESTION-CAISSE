package org.app.bp.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.app.bp.models.Clients;
import org.app.bp.models.Users;
import org.app.bp.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientServices {

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DBUtils.getConnection();

    /**
     *  Ajout nouveau client
     * @param new_client
     * */
    public Service<Boolean> addNewClient(Clients new_client){
        return new Service<Boolean>(){
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>(){
                    @Override
                    protected Boolean call() throws Exception {
                        String ajout_client_query = "INSERT INTO clients (nom_client, prenom_client, " +
                                "contact_client_1, contact_client_2, adresse_client_1, adresse_client_2) VALUES (?,?,?,?,?,?)";
                        try{
                            preparedStatement = connection.prepareStatement(ajout_client_query);
                            preparedStatement.setString(1, new_client.getNom_client());
                            preparedStatement.setString(2, new_client.getPrenom_client());
                            preparedStatement.setString(3, new_client.getContact_client_1());
                            preparedStatement.setString(4, new_client.getContact_client_2());
                            preparedStatement.setString(5, new_client.getAdresse_client_1());
                            preparedStatement.setString(6, new_client.getAdresse_client_2());
                            preparedStatement.executeUpdate();
                            return true;

                        } catch (SQLException sqlException){
                            sqlException.printStackTrace();
                            return false;
                        }
                    }
                };
            }
        };
    }

    /**
     *  Recuperer la dernier enregistrement
     * */
    public Service<Integer> getLastIdFromClientTable(){
        return new Service<Integer>(){
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        String last_id_query = "SELECT seq FROM sqlite_sequence WHERE name = 'clients'";
                        int _return_value = -1;
                        try {
                            preparedStatement = connection.prepareStatement(last_id_query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                _return_value = resultSet.getInt("seq");
                            }
                        } catch (SQLException sqlException){
                            sqlException.printStackTrace();
                        }
                        return _return_value;
                    }
                };
            }
        };
    }

    /**
     * Recuperer client enregistrer
     * */
    public Service<List<Clients>> getClientsData(){
        Service<List<Clients>> client_service = new Service(){
            @Override
            protected Task<List<Clients>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Clients> call() {
                        String liste_client_query = "SELECT * FROM clients";
                        List<Clients> data_client = new ArrayList<>();
                        try {
                            preparedStatement = connection.prepareStatement(liste_client_query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                Clients nouveau_client = new Clients(
                                        resultSet.getInt("id_client"),
                                        resultSet.getString("nom_client"),
                                        resultSet.getString("prenom_client"),
                                        resultSet.getString("contact_client_1"),
                                        resultSet.getString("contact_client_2"),
                                        resultSet.getString("adresse_client_1"),
                                        resultSet.getString("adresse_client_2")
                                );
                                data_client.add(nouveau_client);
                            }
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                        return data_client;
                    }
                };
            }
        };
        return client_service;
    };

    /**
     * Supprimer client
     * @param client
     */
    public Service<Boolean> deleteClient(Clients client){
        return new Service<Boolean>(){
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>(){
                    @Override
                    protected Boolean call() throws Exception {
                        String delete_user_query = "DELETE FROM clients WHERE id_client = ?";
                        try{
                            preparedStatement = connection.prepareStatement(delete_user_query);
                            preparedStatement.setInt(1,client.getId_client());
                            preparedStatement.executeUpdate();
                            return true;
                        } catch (SQLException sqlException){
                            sqlException.printStackTrace();
                            return false;
                        }
                    }
                };
            }
        };
    }
}
