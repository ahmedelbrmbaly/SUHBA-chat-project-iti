package com.suhba.services.controllers;

import com.suhba.database.entities.User;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import com.suhba.exceptions.*;
import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;

public class SignUp2Service {
    ServerClientServices serverService = ServerService.getInstance();
    public static User curRegisterdUser = null;

    public void moveToNextPage(MouseEvent event, String destinationPage) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/com/suhba/" + destinationPage));
        } catch (IOException e) {
            System.out.println("Page not found!");
        }
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void moveToNextPage (ActionEvent event, String destinationPage) throws RemoteException {
        curRegisterdUser = serverService.getUserByPhoneNumber(SignUp1Service.curRegisteredPhone);
        System.out.println("curRegisterdUser" + curRegisterdUser);
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/com/suhba/" + destinationPage));
        } catch (IOException e) {
            System.out.println("Page not found!");
        }
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public boolean checkInfo (String name, Gender gender, LocalDate DOB, Country country, byte[] picture) {
        try {
            serverService.saveLastPart(name, gender, DOB, country, picture);
        } catch (RemoteException e) {
            System.out.println("A problem in BLOB!!");
        }
        return true;
    }
}
