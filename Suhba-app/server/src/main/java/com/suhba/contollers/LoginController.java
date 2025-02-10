package com.suhba.contollers;

import com.suhba.utils.SessionManager;
import com.suhba.daos.implementation.AdminDAOImpl;
import com.suhba.database.entities.Admin;
import com.suhba.daos.interfaces.AdminDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private final AdminDao adminDao = new AdminDAOImpl();

    @FXML
    private TextField loginEmail;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button adminLoginBtn;

    @FXML
    private void adminLogin() {
        String email = loginEmail.getText().trim();
        String password = loginPassword.getText().trim();

        if (adminDao.isAdminCradentialsValid(email, password)) {
            Admin admin = adminDao.getAdminByEmail(email);
            if (admin != null) {
                // Store the logged-in admin in SessionManager
                SessionManager.setAdmin(admin);
                System.out.println("Login successful. Admin ID: " + SessionManager.getAdminId());

                loadBroadcastScreen();
            } else {
                System.err.println("Error: Could not retrieve admin details.");
            }
        } else {
            showLoginError();
        }
    }

    private void loadBroadcastScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/suhba/serverManagement.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) adminLoginBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load Broadcast.fxml: " + e.getMessage());
        }
    }

    private void showLoginError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText("Invalid Credentials");
        alert.setContentText("Wrong Email or Password. Please try again.");
        alert.showAndWait();
    }
}
