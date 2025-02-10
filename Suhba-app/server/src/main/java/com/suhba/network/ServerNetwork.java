package com.suhba.network;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerNetwork {
    public static void main(String[] args) throws InterruptedException, RemoteException {
        try{
            ServerClientServicesImpl serviceObject = new ServerClientServicesImpl();

            Registry reg = LocateRegistry.createRegistry(2000);

            reg.rebind("SuhbaService", serviceObject);

            System.out.println("RMI is running..");

            String[] services = reg.list();
            System.out.println("Running services:");
            for (String service : services) {
                System.out.println(" - " + service);
            }

        }catch(RemoteException e){
            System.out.println("Remote Exception");
            // e.printStackTrace();
        }


    }
}
