package com.suhba.controllers;

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

    PasswordSettingsService myServices = new PasswordSettingsService();

    @FXML
    private void handleEditAction() throws NoSuchAlgorithmException, RemoteException, InvalidPasswordException {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmNewPasswordField.getText();

        if (!myServices.checkIfMatch(currentPassword)) { // ✅ Fix condition
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

        // ✅ Use Remote Service for updating password
        if (myServices.updatePassword(newPassword)) {
            myServices.showAlert(Alert.AlertType.INFORMATION, "Success", "Password updated successfully.");
            clearFields();
        } else {
            myServices.showAlert(Alert.AlertType.ERROR, "Error", "Failed to update password. Try again.");
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
