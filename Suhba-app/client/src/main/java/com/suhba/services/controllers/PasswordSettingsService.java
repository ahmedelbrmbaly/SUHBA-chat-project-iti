package com.suhba.services.controllers;

import com.suhba.database.entities.User;
import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;

import java.rmi.RemoteException;

public class PasswordSettingsService {
    ServerClientServices serverService = ServerService.getInstance();
    public void fun () {
        try {
            User user = serverService.getUserById(5);
            System.out.println(user);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
