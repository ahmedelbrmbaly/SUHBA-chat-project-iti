package com.suhba.services.controllers;

import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public class SignIn2Service {
    ServerClientServices serverService = ServerService.getInstance();

    public boolean checkIfMatch (String password) throws NoSuchAlgorithmException, RemoteException {
        System.out.println(SignIn1Service.curUser.getUserId());
        return serverService.isPasswordMatchUser(SignIn1Service.curUser.getUserId(), password);
    }

    public void moveToNextPage (ActionEvent event, String destinationPage) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/com/suhba/" + destinationPage));
        } catch (IOException e) {
            System.out.println("Page not found!");
        }
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void showErrorAlert (String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
