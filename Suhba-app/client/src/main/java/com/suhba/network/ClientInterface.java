package com.suhba.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.suhba.database.entities.Message;
import com.suhba.database.enums.ChatType;
import com.suhba.database.enums.UserStatus;

public interface ClientInterface extends Remote {
    
    boolean receiveMessage(Message msg, ChatType type) throws RemoteException;
    void notifyUserStatusChanged(long userId, UserStatus newStatus) throws RemoteException;
}