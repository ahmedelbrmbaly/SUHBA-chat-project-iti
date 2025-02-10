package com.suhba.controllers.components;

import java.time.format.DateTimeFormatter;

import com.suhba.database.entities.Message;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GroupMessageBubbleController {

    @FXML
    private VBox messageContainer;

    @FXML
    private Label messageContent;

    @FXML
    private Label messageTime;

    @FXML
    private Label senderName;

    @FXML
    private HBox container;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, HH:mm");

    public void setMessage(Message message, boolean isSentByCurrentUser) {
        senderName.setVisible(false);
        // Set message content
        messageContent.setText(message.getContent());

        // Format timestamp
        messageTime.setText(message.getTimeStamp().toLocalDateTime().format(TIME_FORMATTER));

        // Configure styling based on message origi
        if (isSentByCurrentUser) {
            configureSentMessage();
        } else {
            configureReceivedMessage();
        }
    }

    private void configureSentMessage() {
        container.setAlignment(Pos.CENTER_RIGHT);
        messageContent.setStyle("-fx-background-color: #112D4E; -fx-background-radius: 10;");

    }

    private void configureReceivedMessage() {
        container.setAlignment(Pos.CENTER_LEFT);
        messageContent.setStyle("-fx-background-color: #3F72AF; -fx-background-radius: 10;");
    }

    public void setMessage(Message message, boolean isSentByCurrentUser, String name) {
        configureReceivedMessage();
        // Set message content
        messageContent.setText(message.getContent());

        // Format timestamp
        messageTime.setText(message.getTimeStamp().toLocalDateTime().format(TIME_FORMATTER));

        senderName.setVisible(true);
        senderName.setText(name);
    }

}
