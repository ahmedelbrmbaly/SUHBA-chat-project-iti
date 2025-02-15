package com.suhba.contollers;

import com.suhba.daos.implementation.AdminDAOImpl;
import com.suhba.utils.SessionManager;
import com.suhba.daos.interfaces.AdminDao;
import com.suhba.database.entities.Admin;
import com.suhba.utils.ScreenNavigator;
import com.suhba.utils.Validation;
import com.suhba.exceptions.InvalidEmailException;
import com.suhba.exceptions.InvalidPasswordException;
import com.suhba.exceptions.RepeatedEmailException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

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
    private final Validation validation = new Validation();

    public SettingsController() {
        this.currentAdmin = SessionManager.getAdmin();
        if (currentAdmin == null) {
            System.err.println("Error: No logged-in admin.");
        }    }

    @FXML
    public void initialize() {
        // Move session retrieval here, not in constructor
        this.currentAdmin = SessionManager.getAdmin();
        if (currentAdmin == null) {
            System.err.println("Error: No logged-in admin.");
        } else {
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

        try {
            validation.validatePassword(newPassword);
        } catch (InvalidPasswordException e) {
            showAlert("Error", e.getMessage());
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert("Error", "New passwords do not match.");
            return;
        }

        currentAdmin.setPassword(newPassword);
        if (adminDao.updateAdmin(currentAdmin)) {
            showAlert("Success", "Password updated successfully.");
            clearFields();
        } else {
            showAlert("Error", "Failed to update password. Try again.");
        }
    }

    private boolean validateCurrentPassword(String inputPassword) {
        return inputPassword.equals(currentAdmin.getPassword());
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

    private void saveAdmin() {
        String newEmail = primaryEmailField.getText();

        // 1. **Check if the new email is the same as the current one**
        if (newEmail.equals(currentAdmin.getAdminEmail())) {
            showAlert("Error", "The email you entered is the same as your current email.");
            return;
        }

        // 2. **Check if the new email matches the required format**
        if (!Pattern.matches("^[a-z][a-z0-9.]*@gmail\\.com", newEmail)) {
            showAlert("Error", "The email you entered is invalid.");
            return;
        }

        // 3. **Check if another admin already has this email**
        if (adminDao.getAdminByEmail(newEmail) != null) {
            showAlert("Error", "The email you entered already exists.");
            return;
        }

        // 4. **Proceed with saving the new email**
        currentAdmin.setAdminEmail(newEmail);
        boolean isUpdated = adminDao.updateAdmin(currentAdmin);

        if (isUpdated) {
            showAlert("Success", "Admin profile updated successfully.");
        } else {
            showAlert("Error", "Failed to update admin profile.");
        }
    }





    @FXML
    void goToBrodcastingScreen(MouseEvent event) {
        ScreenNavigator.loadScreen(event, "Broadcast.fxml");
    }

    @FXML
    void goToServerManagmentScreen(MouseEvent event) {
        ScreenNavigator.loadScreen(event, "serverManagement.fxml");
    }

    @FXML
    void goToSettingsScreen(MouseEvent event) {
        ScreenNavigator.loadScreen(event, "settings.fxml");
    }

    @FXML
    void goToStatisticsScreen(MouseEvent event) {
        ScreenNavigator.loadScreen(event, "Statics.fxml");
    }

    @FXML
    void goToUserManagmentScreen(MouseEvent event) {
        ScreenNavigator.loadScreen(event, "User Management.fxml");
    }

    @FXML
    void handleLogOut(MouseEvent event) {
        SessionManager.clearSession(); // ✅ Ensures admin is logged out
        ScreenNavigator.loadScreen(event, "login.fxml");
    }
}
