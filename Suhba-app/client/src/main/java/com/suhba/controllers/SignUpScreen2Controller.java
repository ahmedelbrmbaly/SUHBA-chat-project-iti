package com.suhba.controllers;

import com.suhba.database.enums.Gender;
import com.suhba.services.controllers.SignUp2Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class SignUpScreen2Controller {

    @FXML
    private Button chooseImageBtn;

    @FXML
    private ComboBox<?> countryComboBox;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private Label signInLabel;

    @FXML
    private Button signUpBtn;

    @FXML
    private BorderPane signUpScreen2;

    @FXML
    private CheckBox termsCheckBox;

    @FXML
    private TextField usernameField;

    @FXML
    private RadioButton maleRadiobtn, femaleRadiobtn;

    SignUp2Service myServices = new SignUp2Service();

    @FXML
    void handleChooseImage(ActionEvent event) {

    }

    @FXML
    void handleCountrySelection(ActionEvent event) {

    }

    @FXML
    void handleDateOfBirth(ActionEvent event) {

    }

    @FXML
    void handleSignUp(ActionEvent event) {
        Gender gender = null;
        if (maleRadiobtn.isSelected())  gender = Gender.valueOf(maleRadiobtn.getText());
        else if (femaleRadiobtn.isSelected())  gender = Gender.valueOf(femaleRadiobtn.getText());
        else  myServices.showErrorAlert("It is not required to ");
    }

    @FXML
    void navigateToSignIn(MouseEvent event) {

    }

}
