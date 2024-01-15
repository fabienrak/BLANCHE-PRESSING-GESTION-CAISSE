package org.app.bp.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.app.bp.models.Articles;
import org.app.bp.models.Users;
import org.app.bp.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarchandisesServices {

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DBUtils.getConnection();

    /**
     * Creer nouveau article
     * @param new_article
     * */
    public Service<Boolean> addNewArticle(Articles new_article){
        return new Service<Boolean>(){
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>(){
                    @Override
                    protected Boolean call() throws Exception {
                        String ajout_article_query = "INSERT INTO articles (nom_article, prix, prefix_code) VALUES (?,?,?)";
                        try{
                            preparedStatement = connection.prepareStatement(ajout_article_query);
                            preparedStatement.setString(1, new_article.getNom_article());
                            preparedStatement.setInt(2, new_article.getPrix());
                            preparedStatement.setString(3, new_article.getPrefix_code());

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
     * Recuperer ID dernier enregistrement
     * */
    public Service<Integer> getLastIdFromArticleTable(){
        return new Service<Integer>(){
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        String last_id_query = "SELECT seq FROM sqlite_sequence WHERE name = 'articles'";
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
     * Recuperer liste Article
     * */
    public Service<List<Articles>> getAllArticleData(){
        Service<List<Articles>> articleService = new Service(){
            @Override
            protected Task<List<Articles>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Articles> call() {
                        String liste_article_query = "SELECT * FROM articles";
                        List<Articles> data_article = new ArrayList<>();
                        try {
                            preparedStatement = connection.prepareStatement(liste_article_query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                Articles nouveau_article = new Articles(
                                        resultSet.getInt("id_article"),
                                        resultSet.getString("nom_article"),
                                        resultSet.getInt("prix"),
                                        resultSet.getString("prefix_code")
                                );

                                data_article.add(nouveau_article);
                            }
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                        return data_article;
                    }
                };
            }
        };
        return articleService;
    };

    /**
     * Recuperer prix article
     * @param nom_article
     * @param code_article
     * */
    public Service<Integer> getPrixArticle(String nom_article, String code_article){
        return new Service<Integer>(){
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        String prix_article_query = "SELECT prix FROM articles WHERE nom_article = '"+nom_article+"' AND prefix_code='"+ code_article +"'";
                        int _return_value = -1;
                        try {
                            preparedStatement = connection.prepareStatement(prix_article_query);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                _return_value = resultSet.getInt("prix");
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
     * Supprimer article
     * @param articles
     * */
    public Service<Boolean> deleteArticle(Articles articles){
        return new Service<Boolean>(){
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>(){
                    @Override
                    protected Boolean call() throws Exception {
                        String delete_article_query = "DELETE FROM articles WHERE id_article = ?";
                        try{
                            preparedStatement = connection.prepareStatement(delete_article_query);
                            preparedStatement.setInt(1,articles.getId_article());
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
