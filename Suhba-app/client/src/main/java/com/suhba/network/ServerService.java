package com.suhba.network;

import com.suhba.database.entities.User;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.suhba.database.entities.User;

public class ServerService {
    private static ServerClientServices instance;

    private ServerService() {
    }

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

   /* public static void startServerService()
    {
        try {

        } catch (Exception e) {
            throw new RuntimeException(e);

            // Get the server instance
            ServerClientServices serverService = ServerService.getInstance();

            // Create and register the client
            ClientService client = new ClientServiceImpl();
            serverService.register(client);
            System.out.println("Client registered with the server.");

        } catch (RemoteException e) {
            System.err.println("Failed to register client: " + e.getMessage());
            e.printStackTrace();


        }
    }*/
    public static void main(String[] args) {
       // startServerService();
    }
}
