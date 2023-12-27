/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app.bp.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.app.bp.models.Utilisateur;
import org.app.bp.utils.DBUtils;
import org.app.bp.utils.Erreur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Cocone
 */
public class UtilisateurDAO {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DBUtils.getConnection();
    public void insert (Utilisateur utilisateur) throws SQLException{
        String sql="insert into utilisateur (nom,mot_de_passe,role,nom_utilisateur,prenom,contact) values(?,?,?,?,?)";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getMot_de_passe());
            preparedStatement.setInt(3, utilisateur.getRole());
            preparedStatement.setString(4, utilisateur.getNom_utilisateur());
            preparedStatement.setString(5, utilisateur.getPrenom());
            preparedStatement.setString(6, utilisateur.getContact());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException){
            throw sqlException;
        }
    }
    
    public void update (Utilisateur utilisateur) throws SQLException{
        String sql="update  utilisateur set nom=? ,mot_de_passe=?,role=?,nom_utilisateur=? ,prenom=?,contact=? where id_utilisateur=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getMot_de_passe());
            preparedStatement.setInt(3, utilisateur.getRole());
            preparedStatement.setString(4, utilisateur.getNom_utilisateur());
            preparedStatement.setString(5, utilisateur.getPrenom());
            preparedStatement.setString(6, utilisateur.getContact());
            preparedStatement.setInt(7, utilisateur.getId_utilisateur());
            preparedStatement.executeUpdate();;
        } catch (SQLException sqlException){
            throw sqlException;
        }
    }
    public ObservableList<Utilisateur> getAll() {
        String query = "SELECT * FROM utilisateur where role = 1";
        System.out.println(query);
        ObservableList<Utilisateur> data = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Utilisateur utilisa = new Utilisateur();
                utilisa.setId_utilisateur(resultSet.getInt("id_utilisateur"));
                utilisa.setNom(resultSet.getString("nom"));
                utilisa.setMot_de_passe(resultSet.getString("mot_de_passe"));
                utilisa.setRole(resultSet.getInt("role"));
                utilisa.setPrenom(resultSet.getString("prenom"));
                utilisa.setNom_utilisateur(resultSet.getString("nom_utilisateur"));
                utilisa.setContact(resultSet.getString("contact"));
                utilisa.generateTextField(this);
                data.add(utilisa);
            }
        } catch (SQLException sqlException) {

            sqlException.printStackTrace();
        }catch (Erreur er){
            er.printStackTrace();
        }
        return data;
    }
    public ObservableList<Utilisateur> findBy(Utilisateur utilisateur) throws SQLException{
        String query = "SELECT * FROM utilisateur where nom_utilisateur='"+utilisateur.getNom_utilisateur()+"' and mot_de_passe='"+utilisateur.getMot_de_passe()+"' ";
        System.out.println(query);
        ObservableList<Utilisateur> data = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Utilisateur utilisa = new Utilisateur();
                utilisa.setId_utilisateur(resultSet.getInt("id_utilisateur"));
                utilisa.setNom(resultSet.getString("nom"));
                utilisa.setMot_de_passe(resultSet.getString("mot_de_passe"));
                utilisa.setRole(resultSet.getInt("role"));
                utilisa.setPrenom(resultSet.getString("prenom"));
                utilisa.setNom_utilisateur(resultSet.getString("nom_utilisateur"));
                data.add(utilisa);
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        }catch (Erreur er){
            er.printStackTrace();
        }
        return data;

    }
    private static Utilisateur utilisateur = null;
    /**
     * @return the utilisateur
     */
    public static Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * @param utilisateur the utilisateur to set
     */
    public static void setUtilisateur(Utilisateur utilisateur) {
        UtilisateurDAO.utilisateur = utilisateur;
    }

    public void login(Utilisateur login) throws Erreur{
         ObservableList<Utilisateur> liste=null;
            try {
                liste=findBy(login);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(liste.size()==0){
                throw new Erreur("Erreur de mot de Passe ou nom d'utilisateur");
            }else{
                utilisateur=liste.get(0);
            }
        
    }
    public void delete(Utilisateur utilisateur) throws SQLException{
        String sql="delete from utilisateur where id_utilisateur=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, utilisateur.getId_utilisateur());
            preparedStatement.executeUpdate();;
        } catch (SQLException sqlException){
            throw sqlException;
        }
    }
    
    
}
