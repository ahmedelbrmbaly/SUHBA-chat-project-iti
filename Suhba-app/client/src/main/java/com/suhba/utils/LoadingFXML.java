package com.suhba.utils;

import java.io.IOException;
import java.net.URL;

import com.suhba.controllers.AddNewGroupController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
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
            
            loader.setControllerFactory(param->{
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
}
