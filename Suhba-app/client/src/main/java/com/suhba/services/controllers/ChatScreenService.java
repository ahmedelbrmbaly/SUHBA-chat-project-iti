package com.suhba.services.controllers;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.suhba.controllers.ChatScreenController;
import com.suhba.controllers.components.ChatUserBoxController;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.network.ClientImplementation;
import com.suhba.network.ClientInterface;
import com.suhba.network.ServerService;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ChatScreenService {

    // ServerService serverService;
    ClientInterface client;
    ChatScreenController controller;

    // public ChatScreenService() throws IOException {
    //     try {
    //         FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/suhba/ClientChatScreen.fxml"));
    //         Parent root = loader.load();
    //         controller = loader.getController();
    //         client = (ClientInterface) new ClientImplementation(controller);
    //     } catch (RemoteException e) {
    //         e.printStackTrace();
    //     }
    // }
    public ChatScreenService (ChatScreenController chatScreenController){
        try{
            client = (ClientInterface) new ClientImplementation();
            controller= chatScreenController;
            System.out.println("controler ==="+ controller);
        } catch (RemoteException e) {
                    e.printStackTrace();
                }

    }
    public ChatScreenService (){
        controller = ChatScreenController.getInstance();
    }
 

    public void registerToReceive(long userId) throws RemoteException {
        System.out.println("Ready To Receive Messages!");
        ServerService.getInstance().registerToReceiveMessages(userId, client);
        System.out.println("c= "+ controller);
    }

    public void unregisterToReceive(long userId) throws RemoteException {
        ServerService.getInstance().unregisterToReceive(userId);
        System.out.println("Unregistered");
    }

    public ObservableMap<User, Message> loadUserChats(long currentUserId) throws RemoteException {
        Map<User, Message> allChats = ServerService.getInstance().getUserChats(currentUserId);

        Map<User, Message> sortedMap = allChats.entrySet()
                .stream()
                .sorted(Map.Entry.<User, Message>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));

        // for (Map.Entry<User, Message> entry : allChats.entrySet()) {
        // System.out.println("User: " + entry.getKey() + ", Message: " +
        // entry.getValue());
        // }
        ObservableMap<User, Message> observChats = FXCollections.observableMap(sortedMap);
        return observChats;
    }

    public User getSelectedUserInfo(long currentChatId, long userId) throws Exception {
        return ServerService.getInstance().getPrivateUserPartnerByChat(currentChatId, userId);
    }

    public List<Message> getSelectedUserMessages(long currentChatId) throws Exception {
        List<Message> msgs = ServerService.getInstance().getMessages(currentChatId);
        // for(Message msg: msgs){
        // System.out.println(msg);
        // }
        Collections.sort(msgs);
        return msgs;
    }

    @SuppressWarnings("unused")
    public Message sendMessageToUser(Message msg) throws Exception {
        if (msg.getAttachment() != null) {

        }
        if (msg == null) {
            System.out.println("Message is null at chat screen service");
        }
        return ServerService.getInstance().sendMessage(msg);
    }

    
    public void sendNewMessageToUi(Message msg) {
        if(controller==null){
            System.out.println("controller is null");
        }
        controller.receiveNewMessage(msg);
    }

}
