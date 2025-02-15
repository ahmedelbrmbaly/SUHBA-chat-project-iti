package com.suhba.services.controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.suhba.controllers.ClientGroupScreenController;
import com.suhba.database.entities.Group;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.network.ClientImplementation;
import com.suhba.network.ClientInterface;
import com.suhba.network.ServerService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class GroupScreenService {

    ClientInterface client;
    ClientGroupScreenController controller;
    
    public GroupScreenService(ClientGroupScreenController controller) {
        try {
            client = (ClientInterface) new ClientImplementation(this);
            this.controller = controller;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public GroupScreenService() {
        controller = ClientGroupScreenController.getInstance();
    }

    // Service To return the current logged in user :
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

    public ObservableMap<Group, Message> loadUserGroups(long currentUserId) throws RemoteException {
        Map<Group, Message> allGroups = ServerService.getInstance().getUserGroups(currentUserId);

        Map<Group, Message> sortedMap = allGroups.entrySet()
                .stream()
                .sorted(Map.Entry.<Group, Message>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));

        ObservableMap<Group, Message> observGroups = FXCollections.observableMap(sortedMap);
        return observGroups;
    }

    public Group getSelectedGroupInfo(long currentChatId) throws RemoteException, Exception {
        return ServerService.getInstance().getGroupByChat(currentChatId);
    }

    public List<Message> getSelectedUserMessages(long currentChatId) throws Exception {
        List<Message> msgs = ServerService.getInstance().getMessages(currentChatId);
        // for(Message msg: msgs){
        // System.out.println(msg);
        // }
        Collections.sort(msgs);
        return msgs;
    }

    public void registerToReceive(long currentUserId) throws RemoteException {
        System.out.println("Ready To Receive Messages!");
        ServerService.getInstance().registerToReceiveMessages(currentUserId, client);
        // System.out.println("c= "+ controller);
    }

    public String getGroupMembers(long groupId) throws RemoteException, Exception {
        List<User> members = ServerService.getInstance().getGroupMembers(groupId);
        String groupMembersNames = "";
        for(User member: members){
            groupMembersNames += member.getDisplayName();
            groupMembersNames +=",";
        }
        return groupMembersNames;
    }

    public void sendNewMessageToUi(Message msg) {
        if(controller==null){
            System.out.println("controller is null");
        }
        controller.receiveNewMessage(msg);
    }

    public List<User> getMyFriends(long userId) {
        try {
            return ServerService.getInstance().getAllFriends(userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Group createNewGroup(Group group , List<Long> ids) {
        try {
            return ServerService.getInstance().createGroupChat(group, ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
