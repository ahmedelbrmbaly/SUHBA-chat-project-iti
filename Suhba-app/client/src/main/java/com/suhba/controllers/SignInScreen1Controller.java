package com.suhba.controllers;

import com.suhba.App;
import com.suhba.services.controllers.SignIn1Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.rmi.RemoteException;

public class SignInScreen1Controller {

    @FXML
    private TextField PhoneSignInField;

    @FXML
    private Button continueSignInBtn;

    @FXML
    private BorderPane signInScreen1;

    @FXML
    private Label signUpLabel;

    SignIn1Service myServices = new SignIn1Service();

    @FXML
    void handlePhoneNumberSubmit(ActionEvent event) throws RemoteException {
        if (myServices.checkIfExist(PhoneSignInField.getText().toString())) {
            System.out.println(myServices.getCurUserId());
            myServices.moveToNextPage(event, "signInPage2.fxml");
        }
        else {
            myServices.showErrorAlert("The phone number you entered does not exist!");
            App.myStage.close();
        }
    }

    @FXML
    void navigateToSignUp(MouseEvent event) {
        myServices.moveToNextPage(event, "signUpPage1.fxml");
    }

}
