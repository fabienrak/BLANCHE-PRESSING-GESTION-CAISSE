package org.app.bp.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.app.bp.Init;
import org.app.bp.utils.DBUtils;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class InitController implements Initializable {

    @FXML
    public Label load_label;

    @FXML
    public ImageView logo_label;

    @FXML
    public static Label lbl_loading;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_loading = load_label;
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2.9), logo_label);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
    
    
        
    }

    public String checkFunction(){

        final String[] message = {""};
        Thread thread_function_load_database = new Thread(() -> {
            message[0] = "Connexion a la base de donnee ...";
            Platform.runLater(() -> lbl_loading.setText(message[0]));
            try {
                Thread.sleep(3000);
                Connection connection = DBUtils.getConnection();
                connection.getClientInfo();
                message[0] = "CLIENT : " + connection.getClientInfo();
                //Thread.currentThread();
            } catch (InterruptedException | SQLException e) {
                Logger.getLogger(InitController.class.getName()).log(Level.SEVERE, "ERREUR",e);
            }
        });

        Thread thread_function_b = new Thread(() -> {
            message[0] = "Fonction Faharoa";
            Platform.runLater(() -> lbl_loading.setText(message[0]));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Logger.getLogger(InitController.class.getName()).log(Level.SEVERE, "ERREUR",e);
            }
        });

        Thread thread_function_login = new Thread(() -> {
            message[0] = "Page de connexion ...";
            Platform.runLater(
                    () -> {
                        lbl_loading.setText(message[0]);
                        try {
                            Thread.sleep(2000);
                            Stage stage = new Stage();
                            FXMLLoader fxmlLoader = new FXMLLoader(Init.class.getResource("/fxml/auth/auth-login.fxml"));
                            Scene scene = new Scene(fxmlLoader.load());
                            scene.getStylesheets().add(this.getClass().getResource("/css/style.css").toExternalForm());
                            stage.setTitle("BLANCHE PRESSING");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.show();
                        } catch (InterruptedException e) {
                            Logger.getLogger(InitController.class.getName()).log(Level.SEVERE, "ERREUR THREAD",e);
                        } catch (IOException e) {
                            Logger.getLogger(InitController.class.getName()).log(Level.SEVERE, "ERREUR IO",e);
                        }
                    }
            );
        });

        try {
            thread_function_load_database.start();
            thread_function_load_database.join();
            /*thread_function_b.start();
            thread_function_b.join();*/
            thread_function_login.start();
            thread_function_login.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return message[0];
    }


}
