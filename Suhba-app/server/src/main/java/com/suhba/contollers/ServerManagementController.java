package com.suhba.contollers;

import com.suhba.network.ServerNetwork;
import com.suhba.utils.ScreenNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ServerManagementController {

    @FXML
    private Button startServer;

    @FXML
    private Button stopServer;

    private boolean isServerRunning = false; // Track server status

    @FXML
    private Button startButton; // Reference to the button

    @FXML
    void handleStartServer(ActionEvent event) {
        if (!isServerRunning) {
            try {
                ServerNetwork.start();
                isServerRunning = true;
                System.out.println("Server started.");
                startServer.setStyle("-fx-background-color:  #3F72AF; -fx-text-fill: #ffffff; -fx-cursor:  hand;");
                stopServer.setStyle("-fx-background-color: #ffffff; -fx-text-fill:  #3F72AF; -fx-cursor:  hand;");
            } catch (Exception e) {
                System.out.println("Error starting server.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Server is already running.");
        }
    }


    @FXML
    void handleStopServer(ActionEvent event) {
        if (isServerRunning) {
            try {
                ServerNetwork.stop();
                isServerRunning = false;
                System.out.println("Server stopped.");
                stopServer.setStyle("-fx-background-color:  #3F72AF; -fx-text-fill: white; -fx-cursor:  hand;");
                startServer.setStyle("-fx-background-color: #ffffff; -fx-text-fill:  #3F72AF; -fx-cursor:  hand;");
            } catch (Exception e) {
                System.out.println("Error stopping server.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Server is not running.");
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
        ScreenNavigator.loadScreen(event, "login.fxml");
    }
}
