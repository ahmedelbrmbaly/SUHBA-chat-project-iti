package com.suhba.controllers;

import com.suhba.database.entities.User;
import com.suhba.exceptions.InvalidPasswordException;
import com.suhba.services.UserService;
import com.suhba.services.controllers.ChatScreenService;
import com.suhba.services.controllers.PasswordSettingsService;
import com.suhba.utils.LoadingFXML;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class PasswordSettingsScreenController implements Initializable {

    @FXML
    private PasswordField currentPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmNewPasswordField;
    @FXML
    private Button editBtn;
    @FXML
    private ImageView userProfilePic;
    @FXML
    private Label userNameLabel;

    UserService userService;



    private final PasswordSettingsService myServices = new PasswordSettingsService();

    private User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUserInfo();
    }

    @FXML
    private void handleEditAction() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmNewPasswordField.getText();

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            myServices.showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
            return;
        }

        try {
            currentUser = myServices.getUserById();
            System.out.println("Current user: " + currentUser);
            if (currentUser == null) {
                myServices.showAlert(Alert.AlertType.ERROR, "Error", "User not found.");
                return;
            }

            if (!myServices.checkIfMatch(currentPassword)) {
                myServices.showAlert(Alert.AlertType.ERROR, "Error", "Current password is incorrect.");
                return;
            }

            if (!isValidPassword(newPassword)) {
                myServices.showAlert(Alert.AlertType.ERROR, "Error", "New password must be at least 8 characters long.");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                myServices.showAlert(Alert.AlertType.ERROR, "Error", "New passwords do not match.");
                return;
            }

            if (myServices.updatePassword(newPassword)) {
                myServices.showAlert(Alert.AlertType.INFORMATION, "Success", "Password updated successfully.");
                clearFields();
            } else {
                myServices.showAlert(Alert.AlertType.ERROR, "Error", "Failed to update password. Try again.");
            }

        } catch (RemoteException e) {
            myServices.showAlert(Alert.AlertType.ERROR, "Remote Error", "Connection to server failed.");
        } catch (NoSuchAlgorithmException | InvalidPasswordException e) {
            myServices.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    private void clearFields() {
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmNewPasswordField.clear();
    }

    public void setUserInfo() {
        try {
            userService = new UserService();
            User currentUser = userService.getUserInfoById(myServices.getCurUser().getUserId());
            userNameLabel.setText(currentUser.getDisplayName());

            byte[] userPhoto = currentUser.getPicture();
            if (userPhoto != null && userPhoto.length > 0) {
                Image image = new Image(new ByteArrayInputStream(userPhoto));
                userProfilePic.setImage(image);
            } else {
                userProfilePic.setImage(new Image(getClass().getResourceAsStream("/images/defaultUser.png")));
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleToChats(MouseEvent event) {
        LoadingFXML.moveToNextPage(event, "ClientChatScreen.fxml");
    }

    @FXML
    void handleToContacts(MouseEvent event) {
        LoadingFXML.moveToNextPage(event, "ClientContactScreen.fxml");
    }

    @FXML
    void handleToGroups(MouseEvent event) {
        LoadingFXML.moveToNextPage(event, "ClientGroupScreen.fxml");
    }

    @FXML
    void handleToLogout(MouseEvent event) throws IOException {
        new ChatScreenService().unregister(myServices.getCurUser().getUserId());
        myServices.logoutService();
        LoadingFXML.moveToNextPage(event, "signInPage1.fxml");
    }

    @FXML
    void handleToProfile(MouseEvent event) {
        LoadingFXML.moveToNextPage(event, "ProfileSettingsScreen.fxml");
    }

}