package com.suhba.services.controllers;

import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;
import javafx.scene.control.Alert;

public class SignUp2Service {
    ServerClientServices serverService = ServerService.getInstance();

    public void showErrorAlert (String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }


}
