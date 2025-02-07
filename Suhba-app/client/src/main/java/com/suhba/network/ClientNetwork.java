package com.suhba.network;

import com.suhba.database.entities.Chat;
import com.suhba.database.entities.User;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientNetwork {
    public static void main (String[] args){
        Registry reg;
        ServerClientServices serverRef;
        {
            try {
                reg = LocateRegistry.getRegistry("127.0.0.1",2000);
                serverRef = (ServerClientServices) reg.lookup("SuhbaService");
                long chatId = serverRef.createPrivateChat(5, 6);
                System.out.println(chatId);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
