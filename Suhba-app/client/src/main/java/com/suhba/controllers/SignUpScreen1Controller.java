package com.suhba.controllers;

import com.suhba.exceptions.*;
import com.suhba.services.controllers.SignUp1Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.net.URL;

public class SignUpScreen1Controller {

    @FXML
    private TextField phoneSignUpField;

    @FXML
    private PasswordField confirmPassSignUpField;

    @FXML
    private Button continueSignUpBtn;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passSignUpField;

    @FXML
    private Label signIpLabel;

    @FXML
    private BorderPane signUpScreen1;

    SignUp1Service myServices = new SignUp1Service();

    @FXML
    void handleContinueSignUpBtn(ActionEvent event) throws InvalidPhoneException, InvalidPasswordException, IOException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException {
        if (phoneSignUpField.getText().isEmpty()) {
            myServices.showErrorAlert("Phone number is required!");
            return;
        }

        if (emailField.getText().isEmpty()) {
            myServices.showErrorAlert("Email is required!");
            return;
        }

        if (passSignUpField.getText().isEmpty()) {
            myServices.showErrorAlert("Password is required!");
            return;
        }

        if (confirmPassSignUpField.getText().isEmpty()) {
            myServices.showErrorAlert("Confirm password is required!");
            return;
        }

        System.out.println(passSignUpField.getText() + "\n" + confirmPassSignUpField);

        if (!passSignUpField.getText().equals(confirmPassSignUpField.getText())) {
            myServices.showErrorAlert("Passwords do not match!");
            return;
        }


        if (myServices.checkInfo(phoneSignUpField.getText(), emailField.getText(), passSignUpField.getText())){
            myServices.moveToNextPage(event, "signUpPage2.fxml");
        }
    }

    @FXML
    void handleSignIn(MouseEvent event) {
        myServices.moveToNextPage(event, "signInPage1.fxml");
    }
}

