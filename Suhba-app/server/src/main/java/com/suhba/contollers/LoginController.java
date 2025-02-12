package com.suhba.contollers;

import com.suhba.daos.implementation.AdminDAOImpl;
import com.suhba.database.entities.Admin;
import com.suhba.utils.ScreenNavigator;
import com.suhba.services.server.implementations.ServerServiceImpl;
import com.suhba.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class LoginController {

    private ServerServiceImpl serverService = new ServerServiceImpl();

    @FXML
    private TextField loginEmail;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button adminLoginBtn;


    @FXML
    private void adminLogin(ActionEvent event) {
        String email = loginEmail.getText().trim();
        String password = loginPassword.getText().trim();

        // Attempt login
        Admin loggedInAdmin = serverService.login(email, password);

        if (loggedInAdmin != null) {
            // ✅ Store logged-in admin in SessionManager
            SessionManager.setAdmin(loggedInAdmin);
            System.out.println("Login successful. Admin ID: " + loggedInAdmin.getAdminId());

            // ✅ Navigate to the server management screen
            ScreenNavigator.loadScreen(event, "serverManagement.fxml");
        } else {
            System.out.println("Login failed");

            // Clear input fields
            loginEmail.clear();
            loginPassword.clear();

            // Show login error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("Wrong email or password. Please try again.");
            alert.showAndWait();
        }
    }


    @FXML
    void handleAdminLoginBtn(MouseEvent event) {
        ScreenNavigator.loadScreen(event, "serverManagement.fxml");
    }
}
