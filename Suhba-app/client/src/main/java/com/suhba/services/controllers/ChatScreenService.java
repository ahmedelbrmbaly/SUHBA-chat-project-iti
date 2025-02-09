package com.suhba.services.controllers;

import java.io.IOException;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.Map;

import com.suhba.controllers.ChatUserBoxController;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChatScreenService {

    ServerClientServices serverService = ServerService.getInstance();

    public ObservableMap<User,Message> loadUserChats(long currentUserId) throws RemoteException{
        Map<User,Message> allChats = ServerService.getInstance().getUserChats(currentUserId);
        for (Map.Entry<User, Message> entry : allChats.entrySet()) {
            System.out.println("User: " + entry.getKey() + ", Message: " + entry.getValue());
        }        
        ObservableMap<User,Message> observChats = FXCollections.observableMap(allChats);
        return observChats;
    }

    private String getMacAddress () throws SocketException, RemoteException {
        return serverService.getMacAddress();
    }

    public void logoutService () throws IOException {
        System.out.println("In logout");
        System.out.println("The cur user id = " + SignIn1Service.curUser.getUserId());
        serverService.logout(getMacAddress(), SignIn1Service.curUser.getUserId());
    }

    public void moveToNextPage(MouseEvent event, String destinationPage) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/com/suhba/" + destinationPage));
        } catch (IOException e) {
            System.out.println("Page not found!");
        }
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void loadSingleChatBox(User user, Message lastMessage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/suhba/ChatUserBox.fxml"));
        Parent root = loader.load();
        ChatUserBoxController controller = (ChatUserBoxController) loader.getController();
        controller.setUserChat(user,lastMessage);
        
    }

}
