package com.suhba.services.controllers;

import com.suhba.database.entities.User;
import com.suhba.exceptions.*;
import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;
import javafx.scene.control.Alert;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public class ProfileSettingsService {
    private final ServerClientServices serverService = ServerService.getInstance();

    private static final int PRESET_USER_ID = 5;

    public User loadUserProfile() {
        User currentUser = getCurUser();
        if (currentUser != null) {
            try {
                return serverService.getUserById(currentUser.getUserId());//edit it here by
            } catch (RemoteException e) {
                showErrorAlert("Connection error: Unable to load profile. Please try again later.");
                e.printStackTrace();
            }
        } else {
            showErrorAlert("No user is currently logged in.");
        }
        return null;
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

    public boolean updateUserProfile(User user) {
        try {
            return serverService.updateUserProfile(user);
        } catch (RemoteException e) {
            showErrorAlert("Connection error: Unable to update profile. Please try again later.");
            e.printStackTrace();
        } catch (InvalidPhoneException e) {
            showErrorAlert("Invalid phone number. Please enter a valid phone number.");
        } catch (InvalidEmailException e) {
            showErrorAlert("Invalid email address. Please enter a valid email.");
        } catch (RepeatedPhoneException e) {
            showErrorAlert("This phone number is already in use. Try another one.");
        } catch (RepeatedEmailException e) {
            showErrorAlert("This email is already in use. Try another one.");
        } catch (InvalidPasswordException | NoSuchAlgorithmException e) {
            showErrorAlert("Unexpected error: Invalid password configuration.");
        }
        return false;
    }

    public void showSuccessAlert(String message) {
        showAlert(Alert.AlertType.INFORMATION, "Success", message);
    }

    public void showErrorAlert(String message) {
        showAlert(Alert.AlertType.ERROR, "Error", message);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
