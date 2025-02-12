package com.suhba.controllers;

import com.suhba.database.entities.User;
import com.suhba.services.controllers.ClientRequestScreenService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.LightBase;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientRequestScreen implements Initializable {

    @FXML
    private Label AddNewLAbel;

    @FXML
    private GridPane newFriend_1;

    ClientRequestScreenService myServices = new ClientRequestScreenService();

    List<User> myFriends;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myFriends = new ArrayList<>();
        try {
            myFriends = myServices.showFriends();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // Show
        System.out.println(myFriends);
    }
}