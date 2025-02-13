package com.suhba.services.controllers;

import com.suhba.database.entities.User;
import com.suhba.exceptions.InvalidPasswordException;
import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;
import javafx.scene.control.Alert;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public class PasswordSettingsService {
    private final ServerClientServices serverService;
    private final long userId = 1; // Pre-set userId

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
        return serverService != null && serverService.isPasswordMatchUser(userId, password);//edit it here by getCurUser().getUserId()
    }

    public boolean updatePassword(String newPassword) throws NoSuchAlgorithmException, RemoteException, InvalidPasswordException {
        return serverService != null && serverService.updateUserPassword(userId, newPassword);//edit it here by getCurUser().getUserId()
    }

    public User getUserById() throws RemoteException {
        System.out.println("getUserById");
        return serverService != null ? serverService.getUserById(userId) : null;//edit it here by getCurUser().getUserId()
    }


}