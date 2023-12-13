package org.app.bp.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.app.bp.models.Clients;
import org.app.bp.models.Sites;
import org.app.bp.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SiteServices {

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DBUtils.getConnection();

    public Service<List<Sites>> getSitesData(){
        Service<List<Sites>> site_service = new Service(){
            @Override
            protected Task<List<Sites>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Sites> call() {
                        String liste_site_query = "SELECT * FROM sites";
                        List<Sites> data_site = new ArrayList<>();
                        try {
                            preparedStatement = connection.prepareStatement(liste_site_query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                Sites liste_site = new Sites(
                                        resultSet.getInt("id_site"),
                                        resultSet.getString("nom_site"),
                                        resultSet.getString("lieu"),
                                        resultSet.getString("contact"),
                                        resultSet.getString("code_site")
                                );
                                data_site.add(liste_site);
                                System.out.println("TAILLE SITE : " + data_site.size());
                            }
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                        return data_site;
                    }
                };
            }
        };
        return site_service;
    };
}
