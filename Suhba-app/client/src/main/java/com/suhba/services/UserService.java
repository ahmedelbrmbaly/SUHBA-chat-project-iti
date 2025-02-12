package com.suhba.services;

import java.rmi.RemoteException;

import com.suhba.database.entities.User;
import com.suhba.network.ServerService;

public class UserService {


    
    public User getUserInfoById(long userId) throws RemoteException{
        return ServerService.getInstance().getUserById(userId);
    }
}
