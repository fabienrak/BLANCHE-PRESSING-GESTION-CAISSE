package org.app.bp.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.app.bp.models.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ServiceControlleur implements Initializable {

    @FXML
    private Label label;

    @FXML
    private TableView tableService;

    @FXML
    private Button button;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("service milay be");
        initializeTableService(tableService);
    }

    private void initializeTableService(TableView tableView){
        TableColumn<Service, String> numero 
              = new TableColumn<Service, String>("numero");
        TableColumn<Service, String> nomService 
              = new TableColumn<Service, String>("nom service");
        numero.setCellValueFactory(new PropertyValueFactory<>("idService"));
        nomService.setCellValueFactory(new PropertyValueFactory<>("nomService"));
        ObservableList<Service> list = getServiceList();
        tableService.setItems(list);
        tableService.getColumns().addAll(numero,nomService);
    
    }
    private ObservableList<Service>  getServiceList() {
         ObservableList<Service> list = FXCollections.observableArrayList(new Service(1,"LAVAGE"), new Service(2,"compression"));
      return list;
    }

    @FXML
    private void backToDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home/dashboard.fxml"));
        Parent premiereSceneParent = loader.load();
        Scene premiereScene = new Scene(premiereSceneParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(premiereScene);
        stage.show();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        label.setText("Hello World!");
    }
}
