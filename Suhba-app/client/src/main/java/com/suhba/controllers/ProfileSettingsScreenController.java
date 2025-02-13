package com.suhba.controllers;

import com.suhba.database.entities.User;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import com.suhba.database.enums.UserStatus;
import com.suhba.services.controllers.ProfileSettingsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.rmi.RemoteException;

public class ProfileSettingsScreenController {

    @FXML
    private TextField fullNameField, emailField, phoneField;
    @FXML
    private TextArea bioField;
    @FXML
    private DatePicker birthdayDataPicker;
    @FXML
    private ComboBox<Country> countryComboBox;
    @FXML
    private ComboBox<Gender> genderComboBox;
    @FXML
    private ComboBox<UserStatus> statusComboBox;
    @FXML
    private Label userNameLabel;
    @FXML
    private ImageView userProfilePic;

    @FXML
    private Button chatBtn;

    private User currentUser;
    ProfileSettingsService profileService = new ProfileSettingsService();

    @FXML
    public void initialize() {
        loadUserProfile();
    }

    private void loadUserProfile() {
        currentUser = profileService.loadUserProfile();
        if (currentUser != null) {
            loadUserData();
            countryComboBox.getItems().setAll(Country.values());
            genderComboBox.getItems().setAll(Gender.values());
            statusComboBox.getItems().setAll(UserStatus.values());
        } else {
            profileService.showErrorAlert("No user data found!");
        }
        try {
//            currentUser = myServices.getUserById(1);
            currentUser = profileService.getUserById(1);
            System.out.println(currentUser);
            boolean isActive = profileService.isChatBotActive(currentUser);
            updateChatButton(isActive);
        } catch (RemoteException e) {
            handleRemoteError("Connection to server failed.");
            chatBtn.setText("Chatbot: Error");
        } catch (Exception e) {

            profileService.showAlert(Alert.AlertType.ERROR, "Error", "Unable to load chatbot status.");
            chatBtn.setText("Chatbot: Error");
        }
    }

    private void loadUserData() {
        fullNameField.setText(currentUser.getDisplayName());
        emailField.setText(currentUser.getUserEmail());
        phoneField.setText(currentUser.getPhone());
        bioField.setText(currentUser.getBio());
        birthdayDataPicker.setValue(currentUser.getBirthday());
        genderComboBox.setValue(currentUser.getGender());
        countryComboBox.setValue(currentUser.getCountry());
        statusComboBox.setValue(currentUser.getUserStatus()); // Bind status
        userNameLabel.setText(currentUser.getDisplayName());
    }

    public void saveUser() {
        if (!validateUserInput()) return;

        currentUser.setDisplayName(fullNameField.getText());
        currentUser.setUserEmail(emailField.getText());
        currentUser.setPhone(phoneField.getText());
        currentUser.setBio(bioField.getText());
        currentUser.setBirthday(birthdayDataPicker.getValue());
        currentUser.setGender(genderComboBox.getValue());
        currentUser.setCountry(countryComboBox.getValue());
        currentUser.setUserStatus(statusComboBox.getValue()); // Save status

        boolean isUpdated = profileService.updateUserProfile(currentUser);
        if (isUpdated) {
            profileService.showSuccessAlert("User profile updated successfully.");
        } else {
            profileService.showErrorAlert("Failed to update user profile.");
        }
    }

    @FXML
    void handleEditAction(ActionEvent event) {
        saveUser();
    }

    private boolean validateUserInput() {
        if (fullNameField.getText().isEmpty() || emailField.getText().isEmpty() || phoneField.getText().isEmpty()) {
            profileService.showErrorAlert("Please fill in all required fields.");
            return false;
        }
        return true;
    }



    private void updateChatButton(boolean isActive) {
        if (isActive) {
            chatBtn.setText("Chatbot: ON");
            chatBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #3f72af;");
        } else {
            chatBtn.setText("Chatbot: OFF");
            chatBtn.setStyle("-fx-background-color: #3f72af; -fx-text-fill: white;");
        }
    }

    @FXML
    private void changeBotStatus() {
        if (currentUser == null) {
            System.out.println("Error User not found.");
            return;
        }

        try {
            boolean currentStatus = profileService.isChatBotActive(currentUser);
            boolean newStatus = !currentStatus;
            profileService.setChatBotActive(currentUser, newStatus);
            currentUser.setChatBotActive(newStatus);
            updateChatButton(newStatus);
        } catch (RemoteException e) {
            profileService.showAlert(Alert.AlertType.ERROR, "Remote Error", "Failed to toggle chatbot status.");
            try {
                boolean currentStatus = profileService.isChatBotActive(currentUser);
                updateChatButton(currentStatus);
            } catch (RemoteException ex) {
                handleRemoteError("Connection to server failed.");
                chatBtn.setText("Chatbot: Error");
            }
        }
    }

    private void handleRemoteError(String message) {
        profileService.showAlert(Alert.AlertType.ERROR, "Remote Error", message);
    }
}
