package org.app.bp.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import org.app.bp.models.SatistiqueParMoi;
import org.app.bp.models.StatStiqueAnnee;
import org.app.bp.models.Statisitique;
import org.app.bp.services.PdfCaisse;
import org.app.bp.services.StatDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StatistiqueControlleur implements Initializable{

    @FXML
    private Pane pane_state;
    @FXML
    private TableView table_state;
    @FXML
    private Label lbl_titre;
    @FXML
    private Label lbl_tab;
    @FXML
    private ComboBox cbx_search;
    @FXML
    private Label lbl_total;

    private CategoryAxis xAxis = null;  
    private NumberAxis yAxis = null;
    private StatDAO dao = new StatDAO();
    private int affiche = 0;
    private double total = 0;


    public void afficheTotal(){
        lbl_total.setText("TOTAL : "+NumberFormat.getInstance(java.util.Locale.FRENCH).format(total));   
    }

    /* Statistique Par Jour */
    public void afficheStatistiqueParJour(SatistiqueParMoi moi){
        moisSelect = moi;
        affiche = 3;
        try {
            ObservableList<Statisitique> jour = dao.getParJOUR(moi);
            calculTotalPrixParJour(jour);
            afficheTotal();
            System.out.println("--------------------------"+jour);
            afficheTitreAndChoiseParJour("par Jour "+moi.getMoi()+" "+moi.getAnnee(), jour);
            creationStatistiqueParJour(moi, jour);
            creationTableauStatistiqueParJour(moi, jour);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void calculTotalPrixParJour(ObservableList<Statisitique> jour){
        total = 0;
        int i = 0;
        total = 0;
        for( i = 0 ; i < jour.size() ; i++){
            total = total + jour.get(i).getPrix();
        }
    }
    private void afficheTitreAndChoiseParJour(String titre,ObservableList<Statisitique> list){
            lbl_titre.setText("Statistique "+titre);
            lbl_tab.setText("Tableau "+titre);
            cbx_search.getItems().clear();
    }
    private void creationStatistiqueParJour(SatistiqueParMoi mois,ObservableList<Statisitique> state){
        xAxis = new CategoryAxis();
        xAxis.setLabel("jour "+mois.getMoi());
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Montant ( Ariary )");
        
        //Creating the Bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis); 
        barChart.setTitle("Statistique caisse par Jour "+mois.getMoi()+" "+mois.getAnnee());
            
        //Prepare XYChart.Series objects by setting data       
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Montant total");
        int i = 0;
        for (i = 0 ; i < state.size() ; i++) {
            series1.getData().add(new XYChart.Data<>(String.valueOf(state.get(i).getDate().toString()), state.get(i).getPrix()));
        }
        barChart.getData().clear();
        barChart.getData().addAll(series1);
        pane_state.getChildren().clear();
        pane_state.getChildren().add(barChart);
    }

    private void creationTableauStatistiqueParJour(SatistiqueParMoi mois,ObservableList<Statisitique> state){
        TableColumn<Statisitique, String> semaine
              = new TableColumn<Statisitique, String>("Jour "+mois.getMoi());      
              semaine.setCellValueFactory(new PropertyValueFactory<>("date"));
        
         TableColumn<Statisitique, String> prix 
              = new TableColumn<Statisitique, String>("Montant total");      
              prix.setCellValueFactory(new PropertyValueFactory<>("affichePrix"));
        table_state.getColumns().clear();
            table_state.getItems().clear();
              table_state.getColumns().addAll(semaine,prix);
        table_state.setItems(state);
    }





    /* Statistique Par Semaine */
    public void afficheStatistiqueParSemaine(SatistiqueParMoi moi){
        try {
            moisSelect = moi;
            affiche = 2;
            ObservableList<Statisitique> semaine = dao.getStatParSemaine(moi);
            calculTotalParSemaine(semaine);
            afficheTotal();
            afficheTitreAndChoiseParSemaine(" par semaine "+moi.getMoi()+" "+moi.getAnnee(), semaine);
            creationStatistiqueParSemaine(moi, semaine);
            creationTableauStatistiqueParSemaine(moi, semaine);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void calculTotalParSemaine(ObservableList<Statisitique> jour){
        total = 0;
        int i = 0;
        total = 0;
        for( i = 0 ; i < jour.size() ; i++){
            total = total + jour.get(i).getPrix();
        }
    }
    private void afficheTitreAndChoiseParSemaine(String titre,ObservableList<Statisitique> list){
            lbl_titre.setText("Statistique "+titre);
            lbl_tab.setText("Tableau "+titre);
            cbx_search.getItems().clear();
            cbx_search.setItems(FXCollections.observableArrayList("PAR JOUR"));
    }
    private void creationStatistiqueParSemaine(SatistiqueParMoi mois,ObservableList<Statisitique> state){
        xAxis = new CategoryAxis();
        xAxis.setLabel("semaines "+mois.getMoi());
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Montant ( Ariary )");
        
        //Creating the Bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis); 
        barChart.setTitle("Statistique caisse par semaine "+mois.getMoi()+" "+mois.getAnnee());
            
        //Prepare XYChart.Series objects by setting data       
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Montant total");
        int i = 0;
        for (i = 0 ; i < state.size() ; i++) {
            series1.getData().add(new XYChart.Data<>(String.valueOf(state.get(i).getNum_semaine_moi()), state.get(i).getPrix()));
        }
        barChart.getData().clear();
        barChart.getData().addAll(series1);
        pane_state.getChildren().clear();
        pane_state.getChildren().add(barChart);
    }

    private void creationTableauStatistiqueParSemaine(SatistiqueParMoi mois,ObservableList<Statisitique> state){
        TableColumn<Statisitique, String> semaine
              = new TableColumn<Statisitique, String>("Numero Semaine "+mois.getMoi());      
              semaine.setCellValueFactory(new PropertyValueFactory<>("num_semaine_moi"));
        
         TableColumn<Statisitique, String> prix 
              = new TableColumn<Statisitique, String>("Montant total");      
              prix.setCellValueFactory(new PropertyValueFactory<>("affichePrix"));
        table_state.getColumns().clear();
            table_state.getItems().clear();
              table_state.getColumns().addAll(semaine,prix);
        table_state.setItems(state);
    }

    private int anneeSelect = 0;

    /* Statistique Par mois */
    public void afficheStatistiqueParMois(int annee){
        try {
            anneeSelect = annee;
            affiche = 1;
            ObservableList<SatistiqueParMoi> stateMois = dao.getStatParMOI(annee);
            calculTotalParMois(stateMois);
            afficheTotal();
            afficheTitreAndChoiseParMois(" par mois de l'année "+annee, stateMois);
            System.out.println("pi = "+stateMois);
            creationStatistiqueParMois(annee, stateMois);
            creationTableauStatistiqueParMois(annee, stateMois);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void calculTotalParMois(ObservableList<SatistiqueParMoi> jour){
        total = 0;
        int i = 0;
        total = 0;
        for( i = 0 ; i < jour.size() ; i++){
            total = total + jour.get(i).getPrix();
        }
    }
    private void afficheTitreAndChoiseParMois(String titre,ObservableList<SatistiqueParMoi> list){
            lbl_titre.setText("Statistique "+titre);
            lbl_tab.setText("Tableau "+titre);
            cbx_search.getItems().clear();
            cbx_search.setItems(list);
        }

    private void creationStatistiqueParMois(int annee,ObservableList<SatistiqueParMoi> stateMois){
        xAxis = new CategoryAxis();
        xAxis.setLabel("mois");
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Montant ( Ariary )");
        
        //Creating the Bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis); 
        barChart.setTitle("Statistique caisse "+annee);
            
        //Prepare XYChart.Series objects by setting data       
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Montant total");
        int i = 0;
        for (i = 0 ; i < stateMois.size() ; i++) {
            series1.getData().add(new XYChart.Data<>(String.valueOf(stateMois.get(i).getMoi()), stateMois.get(i).getPrix()));
        }
        barChart.getData().clear();
        barChart.getData().addAll(series1);
        pane_state.getChildren().clear();
        pane_state.getChildren().add(barChart);
      
    }

    private void creationTableauStatistiqueParMois(int annee,ObservableList<SatistiqueParMoi> stateMois){
        TableColumn<SatistiqueParMoi, String> mois 
              = new TableColumn<SatistiqueParMoi, String>("MOIS");      
              mois.setCellValueFactory(new PropertyValueFactory<>("moi"));
        
         TableColumn<SatistiqueParMoi, String> prix 
              = new TableColumn<SatistiqueParMoi, String>("Montant total");      
              prix.setCellValueFactory(new PropertyValueFactory<>("affichePrix"));
        table_state.getColumns().clear();
            table_state.getItems().clear();
              table_state.getColumns().addAll(mois,prix);
        table_state.setItems(stateMois);
    }

    /*  Statistique Par Annee */
    public void afficheStatistiqueParAnnee(){
        try {
            affiche = 0;
            ObservableList<StatStiqueAnnee> state = dao.getStatParANNEE();
            calculTotalParAnnee(state);
            afficheTotal();
            afficheTitreAndChoiseParAnnee(" par année", state);
            creationStatistiqueParAnnee(state);
            creationTableauStatiqueParAnnee(state);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void calculTotalParAnnee(ObservableList<StatStiqueAnnee> jour){
        total = 0;
        int i = 0;
        total = 0;
        for( i = 0 ; i < jour.size() ; i++){
            total = total + jour.get(i).getPrix();
        }
    }
    
    

    private void afficheTitreAndChoiseParAnnee(String titre,ObservableList<StatStiqueAnnee> list){
        lbl_titre.setText("Statistique "+titre);
        lbl_tab.setText("Tableau "+titre);
            cbx_search.getItems().clear();
        cbx_search.setItems(list);
    }
    private void creationStatistiqueParAnnee(ObservableList<StatStiqueAnnee> state){
        xAxis = new CategoryAxis();
        xAxis.setLabel("année");
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Montant ( Ariary )");
        
        //Creating the Bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis); 
        barChart.setTitle("Statistique caisse par année");
            
        //Prepare XYChart.Series objects by setting data       
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Montant total");
        int i = 0;
        for (i = 0 ; i < state.size() ; i++) {
            series1.getData().add(new XYChart.Data<>(String.valueOf(state.get(i).getAnnee()), state.get(i).getPrix()));
        }

        barChart.getData().addAll(series1);
        pane_state.getChildren().clear();
        pane_state.getChildren().add(barChart);
      }
      private void creationTableauStatiqueParAnnee(ObservableList<StatStiqueAnnee> state){
         TableColumn<StatStiqueAnnee, String> annee 
              = new TableColumn<StatStiqueAnnee, String>("annee");      
              annee.setCellValueFactory(new PropertyValueFactory<>("annee"));
        
         TableColumn<StatStiqueAnnee, String> prix 
              = new TableColumn<StatStiqueAnnee, String>("Montant total");      
              prix.setCellValueFactory(new PropertyValueFactory<>("affichePrix"));
            table_state.getColumns().clear();
            table_state.getItems().clear();
              table_state.getColumns().addAll(annee,prix);
        table_state.setItems(state);
      }
      private SatistiqueParMoi moisSelect;
     @FXML
    private void backToInfoClient(ActionEvent actionEvent) throws IOException {
        Node node_source = (Node) actionEvent.getSource();
        Stage stage = (Stage) node_source.getScene().getWindow();
            if(affiche == 0){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home/dashboard.fxml"));
                Parent premiereSceneParent = loader.load();
                Scene premiereScene = new Scene(premiereSceneParent);
                stage.setScene(premiereScene);
                stage.show();
            }else if(affiche == 1){
                afficheStatistiqueParAnnee();        
            }else if(affiche == 2){
                afficheStatistiqueParMois(anneeSelect);
            }else if(affiche == 3){
                afficheStatistiqueParSemaine(moisSelect);
            }
        }
    @FXML
    private void generatePDF(){
        PdfCaisse.generatePdf(lbl_tab.getText(), table_state);
    }
    @FXML
     private void changementScene(){
        Object newValue = cbx_search.getSelectionModel().getSelectedItem();
        if(newValue == null){
            cbx_search.setPromptText("Choisir");
        }else{
            System.out.println("affffff "+affiche);
            if(affiche == 0){
                StatStiqueAnnee annee = (StatStiqueAnnee)newValue;
                afficheStatistiqueParMois(annee.getAnnee());
                affiche = 1;
            }else if(affiche == 1){
                SatistiqueParMoi mois = (SatistiqueParMoi) newValue;
                afficheStatistiqueParSemaine(mois);
            }else if(affiche == 2){
                afficheStatistiqueParJour(moisSelect);
            }
        }
        //cbx_search.valueProperty().addListener((observable, oldValue, newValue) -> {
           
        //});
     }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afficheStatistiqueParAnnee();
        //afficheStatistiqueParMois(LocalDate.now().getYear());
        //changementScene();
        }

    
}
