package com.suhba.contollers;

import com.suhba.network.ServerClientServicesImpl;
import com.suhba.utils.ScreenNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import com.suhba.network.ClientService;

import java.rmi.RemoteException;

public class BroadcastController {

    private ServerClientServicesImpl clientService;


    @FXML
    private TextArea txtArea;

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
        ScreenNavigator.loadScreen(event, "login.fxml");
    }

    @FXML
    void cancelAnnouncment(MouseEvent event) {
        // Clear the text area
        txtArea.clear();
        showAlert("Action", "Announcement has been canceled.");

    }

    @FXML
    void saveAnnouncment(MouseEvent event) {
        String text = txtArea.getText();
        if (text.isEmpty()) {
            System.out.println("No announcement to save.");
            showAlert("Action", "No Announcement to be saved.");

            return;
        }
        // Here, you would implement logic to save the announcement to a file or database
        System.out.println("Announcement saved: " + text);
        showAlert("Action", "Announcement saved as draft.");

    }

    @FXML
    void sendAnnouncment(MouseEvent event) {
        String text = txtArea.getText();
        if (text.isEmpty()) {
            showAlert("Action", "No Announcement to be sent.");
            return;
        }
        try {
             clientService = new ServerClientServicesImpl(); // Initialize with ServerClientServicesImpl
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            // Call the RMI method to broadcast the announcement

            this.clientService.showAnnouncement(text);
            txtArea.clear();
            showAlert("Action", "Announcement has been sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to send announcement.");
        }
    }
}
