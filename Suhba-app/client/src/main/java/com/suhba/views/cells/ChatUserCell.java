package com.suhba.views.cells;

import java.io.IOException;

import com.suhba.controllers.components.ChatUserBoxController;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;

import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public class ChatUserCell extends ListCell<User> {

    ObservableMap<User, Message> allChats ;

    public ChatUserCell(ObservableMap<User, Message> allChats){
        this.allChats = allChats; 
    }

    @Override
    protected void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);
        if (empty || user == null) {
            setText(null);
            setGraphic(null);
        } else {
            Message lastMessage = allChats.get(user);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/suhba/ChatUserBox.fxml"));
                Parent root = loader.load();
                ChatUserBoxController controller = (ChatUserBoxController) loader.getController();
                controller.setUserChat(user, lastMessage);
                setGraphic(root);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Exception in load each single chat");
            }
        }
    }

    
}
