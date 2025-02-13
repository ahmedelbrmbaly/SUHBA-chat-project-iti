package com.suhba.controllers;

import com.suhba.App;
import com.suhba.database.entities.User;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import com.suhba.database.enums.UserStatus;
import com.suhba.services.UserService;
import com.suhba.services.controllers.ChatScreenService;
import com.suhba.services.controllers.ProfileSettingsService;
import com.suhba.utils.LoadingFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ProfileSettingsScreenController implements Initializable {

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
    private ImageView profileImage, userProfilePic;

    Image myImage;
    private byte[] imageBytes;
    UserService userService;

    private User currentUser;
    ProfileSettingsService profileService = new ProfileSettingsService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUserInfo();
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
        birthdayDataPicker.setValue(currentUser.getBirthday());
        countryComboBox.setValue(currentUser.getCountry());
        genderComboBox.setValue(currentUser.getGender());
        statusComboBox.setValue(currentUser.getUserStatus());
        bioField.setText(currentUser.getBio());

        // Get picture --> To display the user picture on the image view
        byte[] userPhoto = currentUser.getPicture();
        if (userPhoto != null && userPhoto.length > 0) {
            Image image = new Image(new ByteArrayInputStream(userPhoto));
            profileImage.setImage(image);
        } else {
            profileImage.setImage(new Image(getClass().getResourceAsStream("/images/defaultUser.png")));
        }
    }

    public void setUserInfo() {
        try {
            userService = new UserService();
            User currentUser = userService.getUserInfoById(profileService.getCurUser().getUserId());
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

    public void saveUser() {
        currentUser.setDisplayName(fullNameField.getText());
        currentUser.setUserEmail(emailField.getText());
        currentUser.setPhone(phoneField.getText());
        currentUser.setBirthday(birthdayDataPicker.getValue());
        currentUser.setCountry(countryComboBox.getValue());
        currentUser.setGender(genderComboBox.getValue());
        currentUser.setUserStatus(statusComboBox.getValue());
        currentUser.setBio(bioField.getText());
      //  currentUser.setPicture(profileImage.getImage());

        Image image = profileImage.getImage();
        if (image != null) {
            try {
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();
                PixelReader pixelReader = image.getPixelReader();

                int[] pixelData = new int[width * height];
                pixelReader.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixelData, 0, width);

                BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                bufferedImage.setRGB(0, 0, width, height, pixelData, 0, width);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", outputStream);
                byte[] imageBytes = outputStream.toByteArray();
                currentUser.setPicture(imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
                profileService.showErrorAlert("Failed to convert image to byte array.");
                return;
            }
        } else {
            currentUser.setPicture(null);
        }

        boolean isUpdated = profileService.updateUserProfile(currentUser);
        if (isUpdated) {
            profileService.showSuccessAlert("User profile updated successfully.");
        } else {
            profileService.showErrorAlert("Failed to update user profile.");
            loadUserProfile();
        }
    }

    @FXML
    void handleEditAction(ActionEvent event) {
        saveUser();
    }

    @FXML
    void handleChooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));

        File selectedFile = fileChooser.showOpenDialog(App.myStage);
        if (selectedFile != null) {
            try {
                myImage = new Image(selectedFile.toURI().toString());
                profileImage.setImage(myImage);
                imageBytes = Files.readAllBytes(selectedFile.toPath());
                System.out.println("Image uploaded successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected!");
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
        new ChatScreenService().unregister(profileService.getCurUser().getUserId());
        profileService.logoutService();
        LoadingFXML.moveToNextPage(event, "signInPage1.fxml");
    }

    @FXML
    void handleToPassword(MouseEvent event) {
        LoadingFXML.moveToNextPage(event, "PasswordSettingsScreen.fxml");
    }
}
