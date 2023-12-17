package org.app.bp.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.app.bp.models.Service;
import org.app.bp.utils.DBUtils;

import javafx.concurrent.Task;

public class ServiceServices {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DBUtils.getConnection();

    public javafx.concurrent.Service<List<Service>> getAllArticleData(){
        javafx.concurrent.Service<List<Service>> serviceService = new javafx.concurrent.Service(){
            @Override
            protected Task<List<Service>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Service> call() {
                        String query = "SELECT id_service,nom_service FROM service";
                        List<Service> data_service = new ArrayList<>();
                        try {
                            preparedStatement = connection.prepareStatement(query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                Service service = new Service(resultSet.getInt("id_service"), resultSet.getString("nom_service"));
                                System.out.println(service);
                                data_service.add(service);
                            }
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                        return data_service;
                    }
                };
            }
        };
        return serviceService;
    };
}
