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

import java.rmi.RemoteException;

public class ProfileSettingsScreenController {
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

    @FXML
    private Button chatBtn;

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
            setUserInfo();
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
