package com.suhba.services.controllers;

import com.suhba.database.entities.User;
import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;

import java.rmi.RemoteException;
import java.util.List;

public class ClientAddContactScreenService {
    ServerClientServices serverService = ServerService.getInstance();

    private User getCurUser () {
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

    public boolean sendFriendRequest (String phoneNumber) throws RemoteException {
        long receiverId = -1;
        if (serverService.getUserByPhoneNumber(phoneNumber) != null) {
            receiverId = serverService.getUserByPhoneNumber(phoneNumber).getUserId();
        }                                  /////////// ////////////////////////////////////
        if (receiverId != -1)  return serverService.sendFriendRequest(/*getCurUser().getUserId()*/ 5, receiverId);
        return true;
    }

    public boolean sendFriendRequest (List<String> phoneNumbers) throws RemoteException {
        List<Long> receiverIds = serverService.getUserIdsByPhones(phoneNumbers);
        /// //////////////////////////////////////////////////////////////////////////////
        if (receiverIds != null) return serverService.sendFriendRequestsById(/*getCurUser().getUserId()*/ 5, receiverIds);
        return true;
    }

}
