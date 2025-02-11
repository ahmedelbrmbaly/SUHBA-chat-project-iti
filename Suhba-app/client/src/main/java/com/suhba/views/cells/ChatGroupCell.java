package com.suhba.views.cells;

import java.io.IOException;

import com.suhba.controllers.components.ChatGroupController;
import com.suhba.controllers.components.ChatUserBoxController;
import com.suhba.database.entities.Group;
import com.suhba.database.entities.Message;

import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public class ChatGroupCell extends ListCell<Group> {

    ObservableMap<Group, Message> allChats ;

    public ChatGroupCell(ObservableMap<Group, Message> allChats){
        this.allChats = allChats; 
    }

    @Override
    protected void updateItem(Group group, boolean empty) {
        super.updateItem(group, empty);
        if (empty || group == null) {
            setText(null);
            setGraphic(null);
        } else {
            Message lastMessage = allChats.get(group);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/suhba/GroupBox.fxml"));
                Parent root = loader.load();
                ChatGroupController controller = (ChatGroupController) loader.getController();
                controller.setUserGroup(group, lastMessage);
                setGraphic(root);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Exception in load each single group");
            }
        }
    }

    
}
