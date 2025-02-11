package com.suhba.controllers;

import com.suhba.services.controllers.FriendContactInfoService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class FriendContactInfoController {

    @FXML
    private ImageView friendImage;

    @FXML
    private Label friendName;

    @FXML
    private Label friendBio;

    @FXML
    private Label friendlastSeen;

    @FXML
    private Label friendEmail;

    @FXML
    private Label friendPhone;

    public void setNewFriendData(String name, String bio, String email, String phoneNumber) {
        //imageFriend = new ImageView(new Image(imageURL));
        friendName.setText(name);
        friendBio.setText(bio);
        friendEmail.setText(email);
        friendPhone.setText(phoneNumber);
    }
}
