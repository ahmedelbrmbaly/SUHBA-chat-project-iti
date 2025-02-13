package com.suhba.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientService extends Remote {

    void showAnnouncement(String announcement) throws RemoteException;
    void onServerShutdown() throws RemoteException ;
}
