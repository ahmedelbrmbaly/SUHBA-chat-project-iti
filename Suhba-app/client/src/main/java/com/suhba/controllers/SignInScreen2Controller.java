package com.suhba.controllers;

import com.suhba.services.controllers.SignIn2Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public class SignInScreen2Controller {

    @FXML
    private Label forgetPassLabel;

    @FXML
    private PasswordField passSignInField;

    @FXML
    private CheckBox rememberMeCheckBox;

    @FXML
    private Button signInBtn;

    @FXML
    private BorderPane signInScreen2;

    SignIn2Service myServices = new SignIn2Service();

    @FXML
    void handleSignInBtn(ActionEvent event) throws NoSuchAlgorithmException, RemoteException {
        if (myServices.checkIfMatch(passSignInField.getText().toString())) {
            myServices.moveToNextPage(event, "ClientChatScreen.fxml");
        }
        else {
            myServices.showErrorAlert("Incorrect password!");
        }
    }

}
