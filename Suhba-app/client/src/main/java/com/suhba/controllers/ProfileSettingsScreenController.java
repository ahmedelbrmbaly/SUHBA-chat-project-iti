package com.suhba.controllers;

import com.suhba.database.entities.User;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import com.suhba.services.controllers.ProfileSettingsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

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
    private Label userNameLabel;
    @FXML
    private ImageView userProfilePic;

    private byte[] imageBytes;
    private Image userImage;
    private User currentUser;

    ProfileSettingsService myProfServices = new ProfileSettingsService();

    @FXML
    public void initialize() {
        if (currentUser != null) {
            loadUserData();
            countryComboBox.getItems().setAll(Country.values());
            genderComboBox.getItems().setAll(Gender.values());
        } else {
            myProfServices.showAlert(Alert.AlertType.ERROR, "Error", "No user data provided!");
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
        userNameLabel.setText(currentUser.getDisplayName());

        if (currentUser.getPicture() != null) {
            userImage = new Image(currentUser.getPicture());
            userProfilePic.setImage(userImage);
        }
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

        if (imageBytes != null) {
            currentUser.setPicture(imageBytes);
        }

        boolean isUpdated = myProfServices.updateUserProfile(currentUser);
        if (isUpdated) {
            myProfServices.showAlert(Alert.AlertType.INFORMATION, "Success", "User profile updated successfully.");
        } else {
            myProfServices.showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user profile.");
        }
    }

    @FXML
    void handleEditAction(ActionEvent event) {
        saveUser();
    }

    @FXML
    void handleChangePicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));

        File selectedFile = fileChooser.showOpenDialog(userProfilePic.getScene().getWindow());
        if (selectedFile != null) {
            try {
                userImage = new Image(selectedFile.toURI().toString());
                userProfilePic.setImage(userImage);
                imageBytes = Files.readAllBytes(selectedFile.toPath());
                System.out.println("Image uploaded successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                myProfServices.showAlert(Alert.AlertType.ERROR, "Error", "Failed to load image.");
            }
        } else {
            System.out.println("No file selected!");
        }
    }

    private boolean validateUserInput() {
        if (fullNameField.getText().isEmpty() || emailField.getText().isEmpty() || phoneField.getText().isEmpty()) {
            myProfServices.showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill in all required fields.");
            return false;
        }
        return true;
    }
}
