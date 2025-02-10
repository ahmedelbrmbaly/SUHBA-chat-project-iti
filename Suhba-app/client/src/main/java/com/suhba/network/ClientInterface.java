package com.suhba.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.suhba.database.entities.Message;

public interface ClientInterface extends Remote {
    
    boolean receiveMessage(Message msg) throws RemoteException;
    
}