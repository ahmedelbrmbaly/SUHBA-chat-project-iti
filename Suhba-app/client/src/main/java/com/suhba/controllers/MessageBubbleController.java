package com.suhba.controllers;

import com.suhba.models.Message;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.time.format.DateTimeFormatter;

public class MessageBubbleController {
    private static final DateTimeFormatter TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("MMM dd, HH:mm");

    @FXML private HBox messageContainer;
    @FXML private Label messageContent;
    @FXML private Label messageTime;

    public void setMessage(Message message, boolean isSentByCurrentUser) {
        // Set message content
        messageContent.setText(message.getContent());
        
        // Format timestamp
        messageTime.setText(message.getTimestamp().format(TIME_FORMATTER));

        // Configure styling based on message origin
        if (isSentByCurrentUser) {
            configureSentMessage();
        } else {
            configureReceivedMessage();
        }
    }

    private void configureSentMessage() {
        messageContainer.setAlignment(Pos.CENTER_RIGHT);
        messageContent.setStyle("-fx-background-color: #112D4E; -fx-background-radius: 10;");
        
    }

    private void configureReceivedMessage() {
        messageContainer.setAlignment(Pos.CENTER_LEFT);
        messageContent.setStyle("-fx-background-color: #3F72AF; -fx-background-radius: 10;");
    }
}