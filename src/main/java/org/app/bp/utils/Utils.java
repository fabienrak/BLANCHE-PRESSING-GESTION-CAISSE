package org.app.bp.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Utils {

    /**
     * Alert Avertissement
     */
    public void warningAlertDialog(String headerText, String contentMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("AVERTISSEMENT");
        alert.setHeaderText(headerText);
        alert.setContentText(contentMessage);
        alert.setGraphic(new ImageView(this.getClass().getResource("/img/attention.png").toString()));
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("BUTTON CLIKER kjdghvhjksdgvjhsdfghjv"));
    }

    /**
     * Alert Erreur
     */
    public void erreurAlertDialog(String headerText, String contentMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("AVERTISSEMENT");
        alert.setHeaderText(headerText);
        alert.setContentText(contentMessage);
        alert.setGraphic(new ImageView(this.getClass().getResource("/img/cross.png").toString()));
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("BUTTON CLIKER kjdghvhjksdgvjhsdfghjv"));
    }

    /**
     * Alert Success
     */
    public void successAlertDialog(String headerText, String contentMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText(headerText);
        alert.setContentText(contentMessage);
        alert.setGraphic(new ImageView(this.getClass().getResource("/img/successful.png").toString()));
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("BUTTON CLIKER kjdghvhjksdgvjhsdfghjv"));
    }

    /**
     * Generation code marchandises
     * */
    public String genererCodeArticle(String prefix_1, String prefix_2){
        String prefix1 = extractPrefix(prefix_1);
        String prefix2 = extractPrefix(prefix_2);
        String nbr = generateRandomNumber(0001, 9999);
        return prefix1 + "_" + prefix2 + "_" + nbr;
    }

    /**
     * Extract prefix
     * */
    private static String extractPrefix(String prefix){
        if(prefix != null && prefix.length() >= 3){
            return prefix.substring(0,3).toUpperCase();
        } else {
            throw new IllegalArgumentException("PREFIX DOIT ETRE 3 CARACTERE");
        }
    }

    /**
     * Generer random nombre
     * */
    private static String generateRandomNumber(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt((max - min) + 1) + min;
        return String.valueOf(randomNumber);
    }

    public String[] getArticleEtCode(String article){
        String[] parts = article.split(" - ");

        int numberOfWordsToExtract = Math.min(3, parts.length);
        String[] words = new String[numberOfWordsToExtract];
        System.arraycopy(parts, 0, words, 0, numberOfWordsToExtract);

        return words;
    }


}
