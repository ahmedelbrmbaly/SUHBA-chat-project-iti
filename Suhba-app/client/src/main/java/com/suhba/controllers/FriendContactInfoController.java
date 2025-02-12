package com.suhba.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class FriendContactInfoController {

    @FXML
    private ImageView friendImage;

    @FXML
    private Circle friendStatus;

    @FXML
    private Label friendName;

    @FXML
    private Label friendBio;

    @FXML
    private Label friendEmail;

    @FXML
    private Label friendPhone;

    public void setNewFriendData(Color color, String name, String bio, String email, String phoneNumber) {
        //imageFriend = new ImageView(new Image(imageURL));
        friendStatus.setFill(color);
        friendName.setText(name);
        friendBio.setText(bio);
        friendEmail.setText(email);
        friendPhone.setText(phoneNumber);
    }
}
