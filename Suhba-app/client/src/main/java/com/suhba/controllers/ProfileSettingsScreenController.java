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
}
