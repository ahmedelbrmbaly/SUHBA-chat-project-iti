package com.suhba.services.controllers;

import com.suhba.database.entities.User;
import com.suhba.exceptions.*;
import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;
import javafx.scene.control.Alert;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public class ProfileSettingsService {
    private final ServerClientServices serverService = ServerService.getInstance();

    public boolean updateUserProfile(User user) {
        try {
            return serverService.updateUserProfile(user);
        } catch (RemoteException e) {
            showErrorAlert("Connection error: Unable to update profile. Please try again later.");
            e.printStackTrace();
        } catch (InvalidPhoneException e) {
            showErrorAlert("Invalid phone number. Please enter a valid phone number.");
        } catch (InvalidEmailException e) {
            showErrorAlert("Invalid email address. Please enter a valid email.");
        } catch (RepeatedPhoneException e) {
            showErrorAlert("This phone number is already in use. Try another one.");
        } catch (RepeatedEmailException e) {
            showErrorAlert("This email is already in use. Try another one.");
        } catch (InvalidPasswordException | NoSuchAlgorithmException e) {
            showErrorAlert("Unexpected error: Invalid password configuration.");
        }
        return false;
    }

    public void showSuccessAlert(String message) {
        showAlert(Alert.AlertType.INFORMATION, "Success", message);
    }

    public void showErrorAlert(String message) {
        showAlert(Alert.AlertType.ERROR, "Error", message);
    }

    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
