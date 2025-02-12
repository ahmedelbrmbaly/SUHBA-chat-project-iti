package com.suhba.views.cells;

import java.io.IOException;
import java.rmi.RemoteException;

import com.suhba.controllers.components.GroupMessageBubbleController;
import com.suhba.controllers.components.MessageBubbleController;
import com.suhba.database.entities.Message;
import com.suhba.services.UserService;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public class GroupMessageBubble extends ListCell<Message> {
    private FXMLLoader loader;
    private GroupMessageBubbleController controller;
    private long currentUserId;
    private UserService userService;
    private Parent root;

    public GroupMessageBubble(long currentUserId) {
        this.currentUserId = currentUserId;
        this.userService = new UserService();
        loadFXML(); 
    }

    private void loadFXML() {
        try {
            loader = new FXMLLoader(getClass().getResource("/com/suhba/GroupBubbleMessage.fxml"));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(Message msg, boolean empty) {
        super.updateItem(msg, empty);
        if (empty || msg == null) {
            setText(null);
            setGraphic(null);
            setStyle("-fx-background-color: transparent;");
        } else {
            if (controller != null) {
                if (msg.getSenderId() == currentUserId) {
                    controller.setMessage(msg, true);
                } else {
                    String name = getUserName(msg);
                    controller.setMessage(msg, false, name);
                }
                setGraphic(root); 
            }
        }
    }

    private String getUserName(Message msg) {
        try {
            return userService.getUserInfoById(msg.getSenderId()).getDisplayName();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
