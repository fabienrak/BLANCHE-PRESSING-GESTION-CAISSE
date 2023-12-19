package org.app.bp.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.app.bp.models.Sites;
import org.app.bp.utils.DBUtils;

public class SiteServices {

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DBUtils.getConnection();

    public void modificationSite(Sites site){
        String ajout_client_query = "UPDATE sites set lieu=? , contact=?,email=? where id_site="+site.getId_site();
        try{
            preparedStatement = connection.prepareStatement(ajout_client_query);
            preparedStatement.setString(1, site.getLieu());
            preparedStatement.setString(2, site.getContact());
            preparedStatement.setString(3,site.getEmail());
            preparedStatement.executeUpdate();;
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public Sites getSites(){
        Sites sites = new Sites();
        String liste_site_query = "select * from sites";
        System.out.println(liste_site_query);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(liste_site_query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sites.setLieu(resultSet.getString("lieu"));
                sites.setId_site(resultSet.getInt("id_site"));
                sites.setContact(resultSet.getString("contact"));
                sites.setEmail(resultSet.getString("email"));
                break;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
       
        return sites;
    }
    
}
