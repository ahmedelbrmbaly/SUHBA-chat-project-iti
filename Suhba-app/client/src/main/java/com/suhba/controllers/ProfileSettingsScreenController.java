package com.suhba.controllers;

import com.suhba.services.controllers.ProfileSettingsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ProfileSettingsScreenController {

    @FXML
    private TextArea bioField;

    @FXML
    private DatePicker birthdayDataPicker;

    @FXML
    private VBox chatBoxBar;

    @FXML
    private ImageView chatIconView;

    @FXML
    private Label chatLabel;

    @FXML
    private VBox chatbotBoxBar;

    @FXML
    private ImageView chatbotIconView;

    @FXML
    private Label chatbotLabel;

    @FXML
    private VBox contactsBoxBar;

    @FXML
    private ImageView contactsIconView;

    @FXML
    private Label contactsLabel;

    @FXML
    private ComboBox<?> countryComboBox;

    @FXML
    private Button editBtn;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fullNameField;

    @FXML
    private ComboBox<?> genderComboBox;

    @FXML
    private VBox groupBoxBar;

    @FXML
    private ImageView groupIconView;

    @FXML
    private Label groupsLabel;

    @FXML
    private VBox logoutBoxBar;

    @FXML
    private ImageView logoutIconView;

    @FXML
    private Label logoutLabel;

    @FXML
    private TextField phoneField;

    @FXML
    private Button photoBtn;

    @FXML
    private ImageView profileImage;

    @FXML
    private VBox settingBoxBar;

    @FXML
    private ImageView settingIconView;

    @FXML
    private Label settingLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private ImageView userProfilePic;

    ProfileSettingsService myServices = new ProfileSettingsService();

    @FXML
    void handleBirthdaySelect(ActionEvent event) {

    }

    @FXML
    void handleChangePicture(ActionEvent event) {

    }

    @FXML
    void handleCountrySelect(ActionEvent event) {

    }

    @FXML
    void handleEditAction(ActionEvent event) {

    }

    @FXML
    void handleEmailField(ActionEvent event) {

    }

    @FXML
    void handleFullNameField(ActionEvent event) {

    }

    @FXML
    void handleGenderSelect(ActionEvent event) {

    }

    @FXML
    void handlePhoneField(ActionEvent event) {

    }

    @FXML
    void handleLogout(MouseEvent event) throws IOException {
        myServices.logoutService();
        myServices.moveToNextPage(event, "signInPage1.fxml");
    }

}
