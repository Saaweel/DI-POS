package com.saaweel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("appIcon.png")));

        stage.getIcons().add(icon);

        stage.setTitle("POS");

        FXMLLoader fxmlLoader = loadFXML("main");

        scene = new Scene(fxmlLoader.load(), 640, 480);

        stage.setScene(scene);

        stage.setMaximized(true);

        stage.show();
    }

    public static FXMLLoader loadFXML(String fxml) throws IOException {
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }

    public static void main(String[] args) {
        launch();
    }
}