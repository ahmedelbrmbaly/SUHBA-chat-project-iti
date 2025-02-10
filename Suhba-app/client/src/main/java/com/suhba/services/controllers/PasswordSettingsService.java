package com.suhba.services.controllers;

import com.suhba.database.entities.User;
import com.suhba.exceptions.InvalidPasswordException;
import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;
import javafx.scene.control.Alert;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public class PasswordSettingsService {
    ServerClientServices serverService = ServerService.getInstance();
    public static User curUser = null;

    public void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public boolean checkIfMatch(String password) throws NoSuchAlgorithmException, RemoteException {
        if (curUser == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No user is logged in.");
            return false;
        }
        return serverService.isPasswordMatchUser(curUser.getUserId(), password);
    }

    public boolean updatePassword(String newPassword) throws NoSuchAlgorithmException, RemoteException, InvalidPasswordException {
        if (curUser == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No user is logged in.");
            return false;
        }
        return serverService.updateUserPassword(curUser.getUserId(), newPassword);
    }
}
