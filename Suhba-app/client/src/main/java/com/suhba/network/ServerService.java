package com.suhba.network;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
            long chatId = serverService.createPrivateChat(5, 6);
            System.out.println("Chat ID: " + chatId);
        } catch (RemoteException e) {
            throw new RuntimeException("Error creating private chat", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
