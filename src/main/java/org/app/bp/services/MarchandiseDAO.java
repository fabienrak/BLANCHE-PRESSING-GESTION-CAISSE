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

import org.app.bp.models.Marchandise;

/**
 *
 * @author Cocone
 */
public class MarchandiseDAO {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = null;
    public void delete(Marchandise marchandise) throws SQLException{
        String sql="update marchandise set etat=1 where id_marchandise=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, marchandise.getId_marchandise());
            preparedStatement.executeUpdate();;
        } catch (SQLException sqlException){
            throw sqlException;
        }
    }
    public void update(Marchandise marchandise) throws SQLException{
        String sql="update marchandise set nom=?,code=?,prix=? where id_marchandise=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
             preparedStatement.setString(1, marchandise.getNom());
              preparedStatement.setString(2, marchandise.getCode());
               preparedStatement.setDouble(3, marchandise.getPrix());
            preparedStatement.setInt(4, marchandise.getId_marchandise());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException){
            throw sqlException;
        }
    }
    
}
