package com.suhba.controllers;

import com.suhba.daos.interfaces.UserDAO;
import com.suhba.database.entities.User;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;

public class ProfileSettingsScreenController {

    private final UserDAO userDAO;
    private User currentUser;

    public ProfileSettingsScreenController(UserDAO userDAO, User user) {
        this.userDAO = userDAO;
        this.currentUser = user;
    }

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

    @FXML
    public void initialize() {
        if (currentUser != null) {
            loadUserData();
            countryComboBox.getItems().setAll(Country.values());
            genderComboBox.getItems().setAll(Gender.values());
        } else {
            showAlert("Error","Error: No user data provided!");
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
            try {
                Image image = new Image(currentUser.getPicture().getBinaryStream());
                userProfilePic.setImage(image);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveUser() {
        if (!validateUserInput()) return;

        currentUser.setDisplayName(fullNameField.getText());
        currentUser.setUserEmail(emailField.getText());
        currentUser.setPhone(phoneField.getText());
        currentUser.setBio(bioField.getText());
        currentUser.setBirthday(birthdayDataPicker.getValue());
        currentUser.setGender(genderComboBox.getValue());
        currentUser.setCountry(countryComboBox.getValue());

        boolean isUpdated = userDAO.updateUserProfile(currentUser);
        if (isUpdated) {
            showAlert("Success","User profile updated successfully.");
        }else{
            showAlert("Error","Failed to update user profile.");
        }
    }

    @FXML
    void handleEditAction(ActionEvent event) {
        saveUser();
    }

    @FXML
    void handleChangePicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png;*.jpg;*.jpeg;*.bmp;*.gif;*.tiff;*.tif;*.webp;*.ico;*.jfif;*.svg;*.heic;*.heif"));
        File file = fileChooser.showOpenDialog(userProfilePic.getScene().getWindow());

        if (file != null) {
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] imageData = fis.readAllBytes();
                Blob imageBlob = new SerialBlob(imageData);
                currentUser.setPicture(imageBlob);
                saveUser();

                Image image = new Image(file.toURI().toString());
                userProfilePic.setImage(image);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load image.");
            }
        }
    }

    private boolean validateUserInput() {
        if (fullNameField.getText().trim().isEmpty()) {
            showAlert("Error","Full Name cannot be empty.");
            return false;
        }
        if (!emailField.getText().matches("^(.+)@(.+)$")) {
            showAlert("Error","Invalid email format.");
            return false;
        }
        if (!phoneField.getText().matches("^\\+?[0-9]{7,15}$")) {
            showAlert("Error","Invalid phone number.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}