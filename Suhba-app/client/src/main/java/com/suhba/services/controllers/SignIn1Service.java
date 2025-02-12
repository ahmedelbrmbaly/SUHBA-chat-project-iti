package com.suhba.services.controllers;

import com.suhba.database.entities.User;
import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class SignIn1Service {
    ServerClientServices serverService = ServerService.getInstance();
    public static User curUser = null;

    public boolean checkIfExist (String phone) throws RemoteException {
        curUser = serverService.getUserByPhoneNumber(phone);
        return !(curUser == null);
    }

    public long getCurUserId () {
        return curUser == null ? -1 : curUser.getUserId();
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

    public void moveToNextPage(MouseEvent event, String destinationPage) {
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
