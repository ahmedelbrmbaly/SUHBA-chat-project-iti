package com.suhba.services.controllers;

import com.suhba.database.entities.Chat;
import com.suhba.database.entities.Contact;
import com.suhba.database.entities.User;
import com.suhba.database.enums.ContactStatus;
import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class FriendRequestBoxService {
    ServerClientServices serverService = ServerService.getInstance();
    Contact contact;

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

    public boolean acceptInvitation (String senderPhoneNumber) throws SQLException, RemoteException {
        User user = serverService.getUserByPhoneNumber(senderPhoneNumber);
        if (user != null) {
            long senderId = user.getUserId();
            Contact contact = new Contact(senderId, /*getCurUser().getUserId()*/ 12, ContactStatus.ACCEPTED);
            return (serverService.acceptRequest(contact) != null);
        }
        return false;
    }

    public boolean rejectInvitation (String senderPhoneNumber) throws RemoteException {
        User user = serverService.getUserByPhoneNumber(senderPhoneNumber);
        if (user != null) {
            long senderId = user.getUserId();
            Contact contact = new Contact(senderId, /*getCurUser().getUserId()*/ 12, ContactStatus.ACCEPTED);
            return serverService.updateRequestStatusFromPendingToDeclined(contact, ContactStatus.DECLINED);
        }
        return false;
    }
}
