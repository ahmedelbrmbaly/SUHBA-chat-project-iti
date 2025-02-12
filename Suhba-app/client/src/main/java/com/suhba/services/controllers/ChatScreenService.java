package com.suhba.services.controllers;

import java.io.IOException;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.suhba.controllers.ChatScreenController;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.network.ClientImplementation;
import com.suhba.network.ClientInterface;
import com.suhba.network.ServerService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;


public class ChatScreenService {

    ClientInterface client;
    ChatScreenController controller;

    private String getMacAddress () throws SocketException, RemoteException {
        return ServerService.getInstance().getMacAddress();
    }



    public void logoutService () throws IOException {
        System.out.println("In logout");
        System.out.println(getCurUser().getUserId());
        ServerService.getInstance().logout(getMacAddress(), getCurUser().getUserId());
    }

    public User getCurUser () {
        if (SignIn1Service.curUser != null) {
            System.out.println("If from login: The cur user id = " + SignIn1Service.curUser.getUserId());
            return SignIn1Service.curUser;
        }
        else if (SignUp2Service.curRegisterdUser != null) {
            System.out.println("If from signup: The cur user id = " + SignUp2Service.curRegisterdUser.getUserId());
            return SignUp2Service.curRegisterdUser;
        }
        return null;
    }

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

    public User getUserInfoById(long userId) throws RemoteException{
        return ServerService.getInstance().getUserById(userId);
    }
    public void unregister(long currentUserId) throws RemoteException {
        ServerService.getInstance().unregisterToReceive(currentUserId);
        System.out.println("Unregistered! ");
    }

}
