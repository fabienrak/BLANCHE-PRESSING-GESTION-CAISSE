package org.app.bp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.app.bp.controller.InitController;

public class Init extends Application {

    private static Stage primaryStage = null;
    public static Scene primaryScene = null;

    @Override
    public void init() {
        InitController initPreloader = new InitController();
        initPreloader.checkFunction();
    }

    public static void main(String[] args) {
        System.setProperty("javafx.preloader", AppPreloader.class.getCanonicalName());
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Init.primaryStage = stage;
        stage.getIcons().add(new Image(Init.class.getResourceAsStream("/img/logo_icon.png")));
        stage.setX(200);
        stage.setY(200);
    }
}
