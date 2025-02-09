package com.suhba.contollers;

import com.suhba.daos.interfaces.UserDAO;
import com.suhba.database.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class SettingsController {

    @FXML
    private Label brodcastingLabel;

    @FXML
    private PasswordField confirmNewPasswordField;

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private Button enableBtn;

    @FXML
    private Label logOutLabel;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private TextField primaryEmailField;

    @FXML
    private TextField primaryPhoneField;

    @FXML
    private Button saveBtn;

    @FXML
    private Label serverManagementLabel;

    @FXML
    private Label settingsLabel;

    @FXML
    private Label staticsLabel;

    @FXML
    private TextField statusField;

    @FXML
    private Button updatePassBtn;

    @FXML
    private Label userManagmentLabel;

    private final UserDAO userDAO;
    private User currentUser;

    public SettingsController(UserDAO userDAO, User user) {
        this.userDAO = userDAO;
        this.currentUser = user;
    }

    @FXML
    public void initialize() {
        if (currentUser != null) {
            primaryEmailField.setText(currentUser.getUserEmail());
            primaryPhoneField.setText(currentUser.getPhone());
            statusField.setText(currentUser.getUserStatus().toString());
        }
    }

    @FXML
    void handleEnableBtn(ActionEvent event) {
        // Handle enabling some settings feature
    }

    @FXML
    void handleLogOut(MouseEvent event) {
        // Handle user logout
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

        String hashedPassword = hashPassword(newPassword);
        if (userDAO.updateUserPassword(currentUser.getUserId(), hashedPassword)) {
            showAlert("Success", "Password updated successfully.");
            clearFields();
        } else {
            showAlert("Error", "Failed to update password. Try again.");
        }
    }

    private boolean validateCurrentPassword(String inputPassword) {
        return hashPassword(inputPassword).equals(currentUser.getPassword());
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
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
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

    @FXML
    void handleSaveBtn(ActionEvent event) {saveUser();}

    private boolean validateUserInput() {
        if (!primaryEmailField.getText().matches("^(.+)@(.+)$")) {
            showAlert("Error","Invalid email format.");
            return false;
        }
        if (!primaryPhoneField.getText().matches("^\\+?[0-9]{7,15}$")) {
            showAlert("Error","Invalid phone number.");
            return false;
        }
        return true;
    }

    private void saveUser() {
        if (!validateUserInput()) {
            showAlert("Error", "Can not Save.");
            return;
        }

        currentUser.setUserEmail(primaryEmailField.getText());
        currentUser.setPhone(primaryPhoneField.getText());

        boolean isUpdated = userDAO.updateUserProfile(currentUser);
        if (isUpdated) {
            showAlert("Success","Admin profile updated successfully.");
        }else{
            showAlert("Error","Failed to update Admin profile.");
        }
    }
}
