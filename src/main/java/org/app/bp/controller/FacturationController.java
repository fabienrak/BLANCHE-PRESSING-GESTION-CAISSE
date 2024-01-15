package org.app.bp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FacturationController {

    @FXML
    private AnchorPane content_facturation;

    @FXML
    private void backToInfoClient(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        Stage stage = (Stage) node_source.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/commande/marchandises.fxml"));
        stage.setTitle("GESTION COMMANDE");
        content_facturation.getChildren().removeAll();
        content_facturation.getChildren().setAll(parent);
    }
}
