package com.suhba.contollers;

import com.suhba.daos.implementation.AdminDAOImpl;
import com.suhba.services.server.implementations.ServerServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    private ServerServiceImpl serverService = new ServerServiceImpl();

    @FXML
    private TextField loginEmail;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button adminLoginBtn;


    @FXML
    private void adminLogin() {
        String email = loginEmail.getText();
        String password = loginPassword.getText();

        // Logging email and password for debugging
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        if (serverService.login(email, password) != null) {
            System.out.println("Login successful");
            // Proceed with the successful login workflow (e.g., navigate to another view)
        } else {
            System.out.println("Login failed");
            // Clear input fields
            loginEmail.setText("");
            loginPassword.setText("");

            // Show an alert to the user about the failed login
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("Wrong Email or Password. Please try again.");
            alert.showAndWait(); // Show the alert and wait for user interaction
        }
    }
}
