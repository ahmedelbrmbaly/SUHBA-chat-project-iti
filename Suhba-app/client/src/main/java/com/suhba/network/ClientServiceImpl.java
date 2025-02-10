package com.suhba.network;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientServiceImpl extends UnicastRemoteObject implements ClientService {
    public ClientServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void showAnnouncement(String announcement) throws RemoteException {
        System.out.println("Announcement received: " + announcement);

        // Ensure UI updates happen on the JavaFX thread
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("New Announcement");
            alert.setHeaderText(null);
            alert.setContentText(announcement);
            alert.showAndWait();
        });
    }
}
