package com.suhba.services.client.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.suhba.database.entities.Contact;
import com.suhba.database.entities.User;
import com.suhba.database.enums.ContactStatus;

public interface ContactService {
    
    // Contact Screen
    public boolean sendFriendRequest(String phoneNumber) throws RemoteException;
    public boolean sendFriendRequest(long userId) throws RemoteException;

    public boolean sendFriendRequests(List<String> phoneNumber) throws RemoteException;
    public boolean sendFriendRequestsById(List<Long> userId) throws RemoteException;

    public List<User> getAllPendingRequests(long userId) throws RemoteException;
    public List<User> getAllFriends(long userId) throws RemoteException;

    boolean updateRequestStatusFromPendingToAccepted(Contact contact, ContactStatus status) throws RemoteException;

    boolean updateRequestStatusFromPendingToDeclined(Contact contact, ContactStatus status) throws RemoteException;

    public boolean deleteContact(Contact contact) throws RemoteException;
}
