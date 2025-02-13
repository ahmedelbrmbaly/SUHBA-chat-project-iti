package com.suhba.services.controllers;

import com.suhba.database.entities.User;
import com.suhba.exceptions.InvalidPasswordException;
import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public class PasswordSettingsService {
    private final ServerClientServices serverService;
    //private final long userId = 5; // Pre-set userId

    public PasswordSettingsService() {
        serverService = ServerService.getInstance();
        if (serverService == null) {
            showAlert(Alert.AlertType.ERROR, "Connection Error", "Unable to connect to the server.");
        }
    }

    public User getCurUser () {
        if (SignIn1Service.curUser != null) {
            System.out.println("If from login: The cur user id = " + SignIn1Service.curUser.getUserId());
            return SignIn1Service.curUser;
        }
        else if (SignUp2Service.curRegisterdUser != null) {
            System.out.println("If from signup: The cur user id = " + SignUp2Service.curRegisterdUser.getUserId());
            return SignUp2Service.curRegisterdUser;
        }
        return null;
    }

    public void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public boolean checkIfMatch(String password) throws NoSuchAlgorithmException, RemoteException {
        return serverService != null && serverService.isPasswordMatchUser(getCurUser().getUserId(), password);
    }

    public boolean updatePassword(String newPassword) throws NoSuchAlgorithmException, RemoteException, InvalidPasswordException {
        return serverService != null && serverService.updateUserPassword(getCurUser().getUserId(), newPassword);
    }

    public User getUserById() throws RemoteException {
        return serverService != null ? serverService.getUserById(getCurUser().getUserId()) : null;
    }

    private String getMacAddress () throws SocketException, RemoteException {
        return ServerService.getInstance().getMacAddress();
    }

    public void logoutService () throws IOException {
        System.out.println("In logout");
        System.out.println(getCurUser().getUserId());
        ServerService.getInstance().logout(getMacAddress(), getCurUser().getUserId());
    }
}