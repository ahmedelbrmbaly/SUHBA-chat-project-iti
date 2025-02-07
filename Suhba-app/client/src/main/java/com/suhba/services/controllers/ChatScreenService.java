package com.suhba.services.controllers;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Map;

import com.suhba.controllers.ChatUserBoxController;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.network.ServerService;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ChatScreenService {

    ServerService serverService ;

    public ObservableMap<User,Message> loadUserChats(long currentUserId) throws RemoteException{
        Map<User,Message> allChats = ServerService.getInstance().getUserChats(currentUserId);
        for (Map.Entry<User, Message> entry : allChats.entrySet()) {
            System.out.println("User: " + entry.getKey() + ", Message: " + entry.getValue());
        }        
        ObservableMap<User,Message> observChats = FXCollections.observableMap(allChats);
        return observChats;
    }

    public void loadSingleChatBox(User user, Message lastMessage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/suhba/ChatUserBox.fxml"));
        Parent root = loader.load();
        ChatUserBoxController controller = (ChatUserBoxController) loader.getController();
        controller.setUserChat(user,lastMessage);
        
    }

}
