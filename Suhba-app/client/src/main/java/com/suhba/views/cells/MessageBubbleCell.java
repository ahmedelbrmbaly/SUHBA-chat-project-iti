package com.suhba.views.cells;

import java.io.IOException;
import java.rmi.RemoteException;

import com.suhba.controllers.components.MessageBubbleController;
import com.suhba.database.entities.Message;
import com.suhba.database.enums.ChatType;
import com.suhba.services.MessagingService;
import com.suhba.services.UserService;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public class MessageBubbleCell extends ListCell<Message> {

    private FXMLLoader loader;
    private MessageBubbleController controller;
    private long currentUserId;

    public MessageBubbleCell(long currentUserId) {
        this.currentUserId = currentUserId;
    }

    @Override
    protected void updateItem(Message msg, boolean empty) {
        super.updateItem(msg, empty);
        if (empty || msg == null) {
            setText(null);
            setGraphic(null);
            setStyle("-fx-background-color: transparent;");
        } else {
            if (loader == null) {
                try {
                    loader = new FXMLLoader(getClass().getResource("/com/suhba/MessageBubble.fxml"));
                    Parent root = loader.load();
                    controller = loader.getController();
                    setGraphic(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (controller != null) {
                if (msg.getSenderId() == currentUserId) {
                    controller.setMessage(msg, true);
                } else {
                    controller.setMessage(msg, false);

                }
            }

        }

    }
}
