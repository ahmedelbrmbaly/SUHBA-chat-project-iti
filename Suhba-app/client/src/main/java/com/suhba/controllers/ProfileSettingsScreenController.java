package com.suhba.controllers;

import com.suhba.services.controllers.ProfileSettingsService;
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

    ProfileSettingsService myProfServices = new ProfileSettingsService();


    @FXML
    public void initialize() {
        if (currentUser != null) {
            loadUserData();
            countryComboBox.getItems().setAll(Country.values());
            genderComboBox.getItems().setAll(Gender.values());
        } else {
            myProfServices.showAlert(Alert.AlertType.ERROR,"Error","Error: No user data provided!");
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

    public void saveUser() {
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
            myProfServices.showAlert(Alert.AlertType.INFORMATION,"Success","User profile updated successfully.");
        }else{
            myProfServices.showAlert(Alert.AlertType.ERROR,"Error","Failed to update user profile.");
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
                myProfServices.showAlert(Alert.AlertType.ERROR,"Error", "Failed to load image.");
            }
        }
    }



}