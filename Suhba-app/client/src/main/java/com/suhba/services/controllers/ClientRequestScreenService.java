package com.suhba.services.controllers;

import com.suhba.database.entities.User;
import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;

import java.rmi.RemoteException;
import java.util.List;

public class ClientRequestScreenService {
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

    public List<User> showPendingRequests () throws RemoteException {
        return serverService.getAllPendingRequests(getCurUser().getUserId());
    }
}
