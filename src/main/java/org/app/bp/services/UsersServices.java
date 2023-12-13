package org.app.bp.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.app.bp.models.Users;
import org.app.bp.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersServices {

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DBUtils.getConnection();
    public Service<Boolean> addNewUsers(Users new_user){
        return new Service<Boolean>(){
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>(){
                    @Override
                    protected Boolean call() throws Exception {
                        String ajout_user_query = "INSERT INTO users (username, nom, prenom, matricule, poste, contact, sites) VALUES (?,?,?,?,?,?,?)";
                        try{
                            preparedStatement = connection.prepareStatement(ajout_user_query);
                            preparedStatement.setString(1, new_user.getUsername());
                            preparedStatement.setString(2, new_user.getNom());
                            preparedStatement.setString(3, new_user.getPrenom());
                            preparedStatement.setString(4, new_user.getMatricule());
                            preparedStatement.setString(5, new_user.getPoste());
                            preparedStatement.setString(6, new_user.getContact());
                            preparedStatement.setString(7, new_user.getSites());
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

    public Service<Integer> getLastIdFromUsersTable(){
        return new Service<Integer>(){
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        String last_id_query = "SELECT seq FROM sqlite_sequence WHERE name = 'users'";
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

    public Service<List<Users>> getUsersData(){
        Service<List<Users>> users_service = new Service(){
            @Override
            protected Task<List<Users>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Users> call() {
                        String liste_users_query = "SELECT * FROM users";
                        List<Users> data_users = new ArrayList<>();
                        try {
                            preparedStatement = connection.prepareStatement(liste_users_query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {

                                Users new_users = new Users(
                                        resultSet.getInt("id_user"),
                                        resultSet.getString("username"),
                                        resultSet.getString("matricule"),
                                        resultSet.getString("nom"),
                                        resultSet.getString("prenom"),
                                        resultSet.getString("contact"),
                                        resultSet.getString("poste"),
                                        resultSet.getString("sites")
                                );

                                data_users.add(new_users);
                            }
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                        return data_users;
                    }
                };
            }
        };
        return users_service;
    };

    public Service<Boolean> deleteUsers(Users users){
        return new Service<Boolean>(){
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>(){
                    @Override
                    protected Boolean call() throws Exception {
                        String delete_user_query = "DELETE FROM users WHERE id_user = ?";
                        try{
                            preparedStatement = connection.prepareStatement(delete_user_query);
                            preparedStatement.setInt(1,users.getId_user());
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
