package com.suhba.services.controllers;

import com.suhba.database.entities.Contact;
import com.suhba.database.entities.User;
import com.suhba.exceptions.InvalidEmailException;
import com.suhba.exceptions.InvalidPhoneException;
import com.suhba.network.ServerService;

import java.rmi.RemoteException;

public class ContactsServices {


    ServerService serverService ;

    public User getUserByPhone(String phone) throws RemoteException, InvalidPhoneException {
        return serverService.getInstance().getUserByPhone(phone);
    }

    public boolean sendFriendRequest(Contact contact) throws RemoteException{

        return serverService.getInstance().sendFriendRequest(contact);
    }




}
