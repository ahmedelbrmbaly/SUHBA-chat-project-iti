package com.suhba;

import com.suhba.network.ServerNetwork;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 730, 600);
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setMinWidth(1280);
        stage.setMinHeight(720);
        stage.setTitle("Suhba: Admin Login");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/images/logo-blue.png")));
        stage.setScene(scene);
        ServerNetwork.stop();
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();


    }

}