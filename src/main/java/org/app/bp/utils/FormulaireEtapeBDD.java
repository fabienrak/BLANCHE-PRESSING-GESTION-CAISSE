package org.app.bp.utils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormulaireEtapeBDD extends Application {

    private Stage primaryStage;
    private Scene scene1, scene2, scene3;
    private int currentStep = 1;

    // Modèle de données
    private FormData formData = new FormData();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Étape 1
        VBox step1Layout = new VBox(10);
        TextField textFieldStep1 = new TextField();
        step1Layout.getChildren().addAll(new Label("Étape 1"), textFieldStep1, new Button("Suivant"));
        scene1 = new Scene(step1Layout, 300, 200);

        // Étape 2
        VBox step2Layout = new VBox(10);
        TextArea textAreaStep2 = new TextArea();
        step2Layout.getChildren().addAll(new Label("Étape 2"), textAreaStep2, new Button("Suivant"), new Button("Précédent"));
        scene2 = new Scene(step2Layout, 300, 200);

        // Étape 3
        VBox step3Layout = new VBox(10);
        CheckBox checkBoxStep3 = new CheckBox("Terminé");
        step3Layout.getChildren().addAll(new Label("Étape 3"), checkBoxStep3, new Button("Précédent"), new Button("Enregistrer"));
        scene3 = new Scene(step3Layout, 300, 200);

        // Configuration des boutons de navigation et enregistrement
        configureButtons(textFieldStep1, textAreaStep2, checkBoxStep3);

        // Affichage de la première scène
        primaryStage.setScene(scene1);
        primaryStage.setTitle("Formulaire à étapes multiples avec BDD");
        primaryStage.show();
    }

    private void configureButtons(TextField textField, TextArea textArea, CheckBox checkBox) {
        Button nextButton1 = new Button("Suivant");
        nextButton1.setOnAction(e -> {
            formData.setField1(textField.getText());
            goToNextScene();
        });

        Button nextButton2 = new Button("Suivant");
        nextButton2.setOnAction(e -> {
            formData.setField2(textArea.getText());
            goToNextScene();
        });

        Button prevButton2 = new Button("Précédent");
        prevButton2.setOnAction(e -> goToPreviousScene());

        Button prevButton3 = new Button("Précédent");
        prevButton3.setOnAction(e -> goToPreviousScene());

        Button saveButton = new Button("Enregistrer");
        saveButton.setOnAction(e -> {
            formData.setField3(checkBox.isSelected());
            saveDataToDatabase();
        });

        // Ajout des actions aux boutons
        ((VBox) scene1.getRoot()).getChildren().add(nextButton1);
        ((VBox) scene2.getRoot()).getChildren().addAll(nextButton2, prevButton2);
        ((VBox) scene3.getRoot()).getChildren().addAll(prevButton3, saveButton);
    }

    private void goToNextScene() {
        if (currentStep == 1) {
            primaryStage.setScene(scene2);
            currentStep = 2;
        } else if (currentStep == 2) {
            primaryStage.setScene(scene3);
            currentStep = 3;
        }
    }

    private void goToPreviousScene() {
        if (currentStep == 2) {
            primaryStage.setScene(scene1);
            currentStep = 1;
        } else if (currentStep == 3) {
            primaryStage.setScene(scene2);
            currentStep = 2;
        }
    }

    private void saveDataToDatabase() {
        try {
            // Utilisation de JDBC pour la connexion à la base de données (changez les détails selon votre base de données)
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/votre_base_de_donnees", "votre_utilisateur", "votre_mot_de_passe");

            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            Connection connection = DBUtils.getConnection();

            // Insertion des données dans la base de données
            String query = "INSERT INTO votre_table (field1, field2, field3) VALUES (?, ?, ?)";
            /*PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, formData.getField1());
            preparedStatement.setString(2, formData.getField2());
            preparedStatement.setBoolean(3, formData.isField3());
            preparedStatement.executeUpdate();*/

            // Fermeture des ressources
            preparedStatement.close();
            connection.close();

            System.out.println("Données enregistrées avec succès dans la base de données.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Classe de modèle pour les données du formulaire
class FormData {
    private String field1;
    private String field2;
    private boolean field3;

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public boolean isField3() {
        return field3;
    }

    public void setField3(boolean field3) {
        this.field3 = field3;
    }
}
