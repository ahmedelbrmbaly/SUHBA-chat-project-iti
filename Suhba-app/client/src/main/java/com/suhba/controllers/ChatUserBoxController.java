package com.suhba.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class ChatUserBoxController{

    @FXML
    private Circle statusCircle;

    @FXML
    private ImageView userChatImg;

    @FXML
    private Label userChatName;

    @FXML
    private Label userLastMessageLabel;

    private String username;
    private String lastMessage;
    private Image userImage;
    
    public ChatUserBoxController(String username, String lastMessage, Image userImage ){
        this.username = username;
        this.lastMessage = lastMessage;
        this.userImage = userImage;
    }


    public void initialize() {
        userChatName.setText(username);
        userChatImg.setImage(userImage);
        userLastMessageLabel.setText(lastMessage);
        Circle circle = new Circle(25,25,25);
        userChatImg.setClip(circle);
    
    }


}
