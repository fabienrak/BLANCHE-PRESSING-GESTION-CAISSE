package org.app.bp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommandeController implements Initializable {

    private int currentStep = 1;

    private Scene scene1, scene2, scene3;

    @FXML
    private AnchorPane content_commande;
    @FXML
    Button btn_suivant;

    @FXML
    private void retourVersPremiereScene(ActionEvent event) throws IOException {
        // Utiliser la référence au contrôleur précédent pour revenir à la première scène
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home/dashboard.fxml"));
        Parent premiereSceneParent = loader.load();
        Scene premiereScene = new Scene(premiereSceneParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(premiereScene);
        stage.show();
    }


    @FXML
    private void changeScene1(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/commande/etape1.fxml"));
        Parent premiereSceneParent = loader.load();
//        Scene premiereScene = new Scene(premiereSceneParent);
        scene1 = new Scene(premiereSceneParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        /*stage.setScene(scene1);
        stage.show();*/
        content_commande.getChildren().removeAll();
        content_commande.getChildren().setAll(premiereSceneParent);
    }

    @FXML
    private void changeScene2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/commande/etape2.fxml"));
        Parent premiereSceneParent = loader.load();
        scene2 = new Scene(premiereSceneParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        /*stage.setScene(scene2);
        stage.show();*/
        content_commande.getChildren().removeAll();
        content_commande.getChildren().setAll(premiereSceneParent);
    }

    @FXML
    private void changeScene3(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/commande/etape3.fxml"));
        Parent premiereSceneParent = loader.load();
        scene3 = new Scene(premiereSceneParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        /*stage.setScene(scene3);
        stage.show();*/
        content_commande.getChildren().removeAll();
        content_commande.getChildren().setAll(premiereSceneParent);
    }


    private void goToNextscene() throws IOException {
        if (currentStep == 1) {
            changeScene2(new ActionEvent());
            currentStep = 2;
        } else if (currentStep == 2) {
            changeScene3(new ActionEvent());
            currentStep = 3;
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_suivant.setOnAction((event) -> {
            try {
                goToNextscene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
