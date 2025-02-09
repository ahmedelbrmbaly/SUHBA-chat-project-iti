package com.suhba.controllers;

import com.suhba.daos.interfaces.UserDAO;
import com.suhba.database.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.security.MessageDigest;
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

    private final UserDAO userDAO;
    private User currentUser;

    public PasswordSettingsScreenController(UserDAO userDAO, User user) {
        this.userDAO = userDAO;
        this.currentUser = user;
    }

    @FXML
    private void handleEditAction() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmNewPasswordField.getText();

        if (!validateCurrentPassword(currentPassword)) {
            showAlert("Error", "Current password is incorrect.");
            return;
        }

        if (!isValidPassword(newPassword)) {
            showAlert("Error", "New password must be at least 8 characters long.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert("Error", "New passwords do not match.");
            return;
        }

        // Hash the new password before saving
        String hashedPassword = hashPassword(newPassword);
        if (userDAO.updateUserPassword(currentUser.getUserId(), hashedPassword)) {
            showAlert("Success", "Password updated successfully.");
            clearFields();
        } else {
            showAlert("Error", "Failed to update password. Try again.");
        }
    }

    private boolean validateCurrentPassword(String inputPassword) {
        return hashPassword(inputPassword).equals(currentUser.getPassword()); // Compare hashed versions
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    private void clearFields() {
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmNewPasswordField.clear();
    }

    private String hashPassword(String password) {
        try {
            // Using a more secure hashing algorithm would be a better practice
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Log error securely in production
            showAlert("Error", "Error hashing the password.");
            return "";
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
