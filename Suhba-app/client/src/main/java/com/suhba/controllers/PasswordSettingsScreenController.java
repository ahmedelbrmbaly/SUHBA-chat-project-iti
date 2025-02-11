package com.suhba.controllers;

import com.suhba.database.entities.User;
import com.suhba.exceptions.InvalidPasswordException;
import com.suhba.services.controllers.PasswordSettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public class PasswordSettingsScreenController {

    @FXML
    private PasswordField currentPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmNewPasswordField;
    @FXML
    private Button editBtn;

    private final PasswordSettingsService myServices = new PasswordSettingsService();

    private User currentUser;

    @FXML
    private void handleEditAction() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmNewPasswordField.getText();

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            myServices.showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
            return;
        }

        try {
            currentUser = myServices.getUserById();
            if (currentUser == null) {
                myServices.showAlert(Alert.AlertType.ERROR, "Error", "User not found.");
                return;
            }

            if (!myServices.checkIfMatch(currentPassword)) {
                myServices.showAlert(Alert.AlertType.ERROR, "Error", "Current password is incorrect.");
                return;
            }

            if (!isValidPassword(newPassword)) {
                myServices.showAlert(Alert.AlertType.ERROR, "Error", "New password must be at least 8 characters long.");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                myServices.showAlert(Alert.AlertType.ERROR, "Error", "New passwords do not match.");
                return;
            }

            if (myServices.updatePassword(newPassword)) {
                myServices.showAlert(Alert.AlertType.INFORMATION, "Success", "Password updated successfully.");
                clearFields();
            } else {
                myServices.showAlert(Alert.AlertType.ERROR, "Error", "Failed to update password. Try again.");
            }

        } catch (RemoteException e) {
            myServices.showAlert(Alert.AlertType.ERROR, "Remote Error", "Connection to server failed.");
        } catch (NoSuchAlgorithmException | InvalidPasswordException e) {
            myServices.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    private void clearFields() {
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmNewPasswordField.clear();
    }
}
