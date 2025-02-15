package com.suhba.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.input.MouseEvent;

public class ScreenNavigator {

    public static void loadScreen(MouseEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenNavigator.class.getResource("/com/suhba/" + fxmlFile));
            Parent root = loader.load();

            if (root == null) {
                System.err.println("Error: Could not load " + fxmlFile);
                return;
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load " + fxmlFile + ": " + e.getMessage());
        }
    }

    public static void loadScreen(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenNavigator.class.getResource("/com/suhba/" + fxmlFile));
            Parent root = loader.load();

            if (root == null) {
                System.err.println("Error: Could not load " + fxmlFile);
                return;
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load " + fxmlFile + ": " + e.getMessage());
        }
    }
}
