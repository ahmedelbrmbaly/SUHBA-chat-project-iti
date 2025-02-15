package com.suhba.services.controllers;

import com.suhba.exceptions.*;
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
import java.security.NoSuchAlgorithmException;

public class SignUp1Service {
    ServerClientServices serverService = ServerService.getInstance();
    public static String curRegisteredPhone = null;

    public void showErrorAlert (String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean checkInfo (String phone, String email, String password) throws RemoteException {
        try {
            if (serverService.saveFirstPart(phone, email, password)) {
                curRegisteredPhone = phone;
                return true;
            }
            else  return false;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (InvalidPhoneException e) {
            showErrorAlert(e.getMessage());
            return false;
        } catch (RepeatedPhoneException e) {
            showErrorAlert(e.getMessage());
            return false;
        } catch (InvalidEmailException e) {
            showErrorAlert(e.getMessage());
            return false;
        } catch (RepeatedEmailException e) {
            showErrorAlert(e.getMessage());
            return false;
        } catch (InvalidPasswordException e) {
            showErrorAlert(e.getMessage());
            return false;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void moveToNextPage (ActionEvent event, String destinationPage) {
        System.out.println("curRegisteredPhone = " + curRegisteredPhone);
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
}
