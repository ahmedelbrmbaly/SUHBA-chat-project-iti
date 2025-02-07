package com.suhba.network;

import com.suhba.database.entities.User;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.suhba.database.entities.User;

public class ServerService {
    private static ServerClientServices instance;

    private ServerService() {}

    public static ServerClientServices getInstance() {
        if (instance == null) {
            synchronized (ServerService.class) {
                if (instance == null) {
                    try {
                        Registry reg = LocateRegistry.getRegistry("127.0.0.1", 2000);
                        instance = (ServerClientServices) reg.lookup("SuhbaService");
                    } catch (RemoteException | NotBoundException e) {
                        throw new RuntimeException("Error connecting to the server", e);
                    }
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        ServerClientServices serverService = ServerService.getInstance();
        try {
        } catch (RemoteException e) {
            throw new RuntimeException("Error creating private chat", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
