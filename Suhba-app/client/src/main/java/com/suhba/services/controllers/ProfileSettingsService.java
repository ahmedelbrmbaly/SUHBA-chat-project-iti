package com.suhba.services.controllers;

import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.SocketException;
import java.rmi.RemoteException;

public class ProfileSettingsService {
    ServerClientServices serverService = ServerService.getInstance();

    private String getMacAddress () throws SocketException, RemoteException {
        return serverService.getMacAddress();
    }

    public void logoutService () throws IOException {
        System.out.println("In logout");
        System.out.println(SignIn1Service.curUser.getUserId());
        serverService.logout(getMacAddress(), SignIn1Service.curUser.getUserId());
    }

    public void moveToNextPage(MouseEvent event, String destinationPage) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/com/suhba/" + destinationPage));
        } catch (IOException e) {
            System.out.println("Page not found!");
        }
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
