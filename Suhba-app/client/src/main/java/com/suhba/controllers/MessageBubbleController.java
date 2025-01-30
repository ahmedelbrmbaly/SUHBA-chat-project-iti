package com.suhba.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import com.suhba.models.Message;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MessageBubbleController {
    @FXML
    private Label messageContent;

    @FXML
    private Label messageTime;

    @FXML
    private HBox messageContainer;


    private String msgContent;

    private LocalDateTime msgTime;

    public void setMessage(Message m) {
        messageContent.setText(m.getContent());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("\"MMM d, h:mm a\"");
        messageTime.setText(m.getTimestamp().format(timeFormatter));
    }

    public void receiveMessage(Message m) {
        messageContent.setStyle("-fx-background-color:  #3F72AF;-fx-background-radius: 10;");
        messageContainer.setAlignment(Pos.CENTER_LEFT);
        messageContent.setText(m.getContent());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("\"MMM d, h:mm a\"");
        messageTime.setText(m.getTimestamp().format(timeFormatter));
    }
    
}
