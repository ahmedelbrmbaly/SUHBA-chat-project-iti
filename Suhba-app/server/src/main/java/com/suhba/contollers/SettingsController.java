package com.suhba.contollers;

import com.suhba.daos.implementation.AdminDAOImpl;
import com.suhba.daos.interfaces.AdminDao;
import com.suhba.database.entities.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SettingsController {

    @FXML
    private PasswordField confirmNewPasswordField;

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private Button saveBtn;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private TextField primaryEmailField;

    @FXML
    private Button updatePassBtn;

    private final AdminDao adminDao = new AdminDAOImpl();
    private Admin currentAdmin;

    public SettingsController() {
        // Fetch the admin from the database (Assuming admin ID is 1 for now)
        this.currentAdmin = adminDao.getAdminById(5L);
    }

    @FXML
    public void initialize() {
        if (currentAdmin != null) {
            primaryEmailField.setText(currentAdmin.getAdminEmail());
        }
    }

    @FXML
    void handleUpdatePassword(ActionEvent event) {
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

        currentAdmin.setPassword(newPassword); // Store password as plain text
        if (adminDao.updateAdmin(currentAdmin)) {
            showAlert("Success", "Password updated successfully.");
            clearFields();
        } else {
            showAlert("Error", "Failed to update password. Try again.");
        }
    }

    private boolean validateCurrentPassword(String inputPassword) {
        return inputPassword.equals(currentAdmin.getPassword()); // Compare directly
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    private void clearFields() {
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmNewPasswordField.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void handleSaveBtn(ActionEvent event) {
        saveAdmin();
    }

    private boolean validateAdminInput() {
        if (!primaryEmailField.getText().matches("^(.+)@(.+)$")) {
            showAlert("Error", "Invalid email format.");
            return false;
        }
        return true;
    }

    private void saveAdmin() {
        if (!validateAdminInput()) {
            showAlert("Error", "Cannot save admin profile.");
            return;
        }

        currentAdmin.setAdminEmail(primaryEmailField.getText());

        boolean isUpdated = adminDao.updateAdmin(currentAdmin);
        if (isUpdated) {
            showAlert("Success", "Admin profile updated successfully.");
        } else {
            showAlert("Error", "Failed to update admin profile.");
        }
    }

    @FXML
    void handleLogOut(MouseEvent event) {
        // Handle admin logout
    }
}
