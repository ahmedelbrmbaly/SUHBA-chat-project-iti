package com.suhba.network;

import javax.swing.text.AbstractDocument;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientService extends Remote {

    void showAnnouncement(String announcement) throws RemoteException;
}
