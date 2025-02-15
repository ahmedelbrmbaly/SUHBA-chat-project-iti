package com.suhba.utils;

import java.io.IOException;
import java.net.URL;

import com.suhba.controllers.AddNewFriendController;
import com.suhba.controllers.AddNewGroupController;
import com.suhba.controllers.ClientAddContactScreenController;
import com.suhba.controllers.ClientGroupScreenController;
import com.suhba.controllers.ClientRequestScreenController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadingFXML {

    public static void showPopup(Stage owner, URL fxmlURL, double width, double height) {
        try {
            // Load the popup content
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent popupContent = loader.load();
            Stage popupStage = new Stage();
            popupStage.initOwner(owner);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UTILITY);

            // Apply blur to the owner stage
            GaussianBlur blur = new GaussianBlur();
            owner.getScene().getRoot().setEffect(blur);

            Scene scene = new Scene(popupContent, width, height);
            popupStage.setScene(scene);
            popupStage.setResizable(false);

            // Remove blur effect when popup closes
            popupStage.setOnCloseRequest(ev -> owner.getScene().getRoot().setEffect(null));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showPopupWithId(Stage owner, URL fxmlURL, double width, double height, long userId) {
        try {
            // Load the popup content
            FXMLLoader loader = new FXMLLoader(fxmlURL);

            loader.setControllerFactory(param -> {
                AddNewGroupController controller = new AddNewGroupController();
                controller.setUserId(userId);
                return controller;
            });
            Parent popupContent = loader.load();

            // AddNewGroupController controller = loader.getController();
            // controller.setUserId(userId);

            Stage popupStage = new Stage();
            popupStage.initOwner(owner);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UTILITY);

            // Apply blur to the owner stage
            GaussianBlur blur = new GaussianBlur();
            owner.getScene().getRoot().setEffect(blur);

            Scene scene = new Scene(popupContent, width, height);
            popupStage.setScene(scene);
            popupStage.setResizable(false);

            // Remove blur effect when popup closes
            popupStage.setOnCloseRequest(ev -> owner.getScene().getRoot().setEffect(null));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void moveToNextPage(MouseEvent event, String destinationPage) {
        try {
            Parent parent = FXMLLoader.load(LoadingFXML.class.getResource("/com/suhba/" + destinationPage));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(" Page not found: " + destinationPage);
            e.printStackTrace();
        }
    }

    public static void moveToNextPageWithId(MouseEvent event, String destinationPage, long currentUserId) {
        try {
            // Load Groups FXML
            FXMLLoader loader = new FXMLLoader(
                    LoadingFXML.class.getClass().getResource("/com/suhba/" + destinationPage));
            // Get the GroupsController

            loader.setControllerFactory(param -> {
                ClientGroupScreenController controller = new ClientGroupScreenController();
                controller.setCurrentUserId(currentUserId);
                return controller;
            });
            Parent root = loader.load();
            // Switch Scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showSuccessMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.getDialogPane().setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        // stage.getIcons().add(new Image(LoadingFXML.class.getClass().getResourceAsStream("/images/accept.png")));
        

        alert.showAndWait();

    }

    public static void showPopupWithIdReqFriend(Stage owner, URL fxmlURL, double width, double height, long userId) {
        try {
            // Load the popup content
            FXMLLoader loader = new FXMLLoader(fxmlURL);

            loader.setControllerFactory(param -> {
                ClientRequestScreenController controller = new ClientRequestScreenController();
                // controller.setUserId(userId);
                return controller;
            });
            Parent popupContent = loader.load();

            // AddNewGroupController controller = loader.getController();
            // controller.setUserId(userId);

            Stage popupStage = new Stage();
            popupStage.initOwner(owner);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UTILITY);

            // Apply blur to the owner stage
            GaussianBlur blur = new GaussianBlur();
            owner.getScene().getRoot().setEffect(blur);

            Scene scene = new Scene(popupContent, width, height);
            popupStage.setScene(scene);
            popupStage.setResizable(false);

            // Remove blur effect when popup closes
            popupStage.setOnCloseRequest(ev -> owner.getScene().getRoot().setEffect(null));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showPopupWithIdAddFriend(Stage owner, URL fxmlURL, double width, double height, long userId) {
        try {
            // Load the popup content
            FXMLLoader loader = new FXMLLoader(fxmlURL);

            loader.setControllerFactory(param -> {
                ClientAddContactScreenController controller = new ClientAddContactScreenController();
                // controller.setUserId(userId);
                return controller;
            });
            Parent popupContent = loader.load();

            // AddNewGroupController controller = loader.getController();
            // controller.setUserId(userId);

            Stage popupStage = new Stage();
            popupStage.initOwner(owner);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UTILITY);

            // Apply blur to the owner stage
            GaussianBlur blur = new GaussianBlur();
            owner.getScene().getRoot().setEffect(blur);

            Scene scene = new Scene(popupContent, width, height);
            popupStage.setScene(scene);
            popupStage.setResizable(false);

            // Remove blur effect when popup closes
            popupStage.setOnCloseRequest(ev -> owner.getScene().getRoot().setEffect(null));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
