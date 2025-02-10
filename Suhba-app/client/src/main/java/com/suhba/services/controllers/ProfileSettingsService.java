package com.suhba.services.controllers;

import com.suhba.database.entities.User;
import com.suhba.exceptions.*;
import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;
import javafx.scene.control.Alert;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public class ProfileSettingsService {
    private ServerClientServices serverService = ServerService.getInstance();

    public boolean updateUserProfile(User user) throws InvalidPhoneException, InvalidEmailException, RepeatedPhoneException, RepeatedEmailException {
        try {
            return serverService.updateUserProfile(user);
        } catch (RemoteException | InvalidPasswordException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            showErrorAlert("Failed to update user profile. Please try again later.");
            return false;
        }
    }

    public void showSuccessAlert(String content) {
        showAlert(Alert.AlertType.INFORMATION, "Success", content);
    }

    public void showErrorAlert(String content) {
        showAlert(Alert.AlertType.ERROR, "Error", content);
    }

    public void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
