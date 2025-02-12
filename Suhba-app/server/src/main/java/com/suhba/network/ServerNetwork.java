package com.suhba.network;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

public class ServerNetwork {

    private static Registry registry; // Store the registry instance
    private static boolean isRunning = false; // Track server status
    private static final int RMI_PORT = 2000;
    private static final String SERVICE_NAME = "SuhbaService";

    public static void start() {
        if (!isRunning) {
            try {
                stop(); // Ensure previous instance is stopped before starting a new one

                // Attempt to create the RMI registry
                try {
                    registry = LocateRegistry.createRegistry(RMI_PORT);
                    System.out.println("RMI registry started on port " + RMI_PORT);
                } catch (ExportException e) {
                    System.out.println("RMI registry already exists. Using the existing registry.");
                    registry = LocateRegistry.getRegistry(RMI_PORT);
                }

                ServerClientServicesImpl serviceObject = new ServerClientServicesImpl();

                // Check if the service is already bound before rebinding
                try {
                    registry.lookup(SERVICE_NAME);
                    System.out.println("Service '" + SERVICE_NAME + "' is already registered.");
                } catch (NotBoundException e) {
                    registry.rebind(SERVICE_NAME, serviceObject);
                    System.out.println("Service '" + SERVICE_NAME + "' registered successfully.");
                }

                isRunning = true;
                System.out.println("RMI is running..");

                // Display running services
                String[] services = registry.list();
                System.out.println("Running services:");
                for (String service : services) {
                    System.out.println(" - " + service);
                }
            } catch (RemoteException e) {
                System.err.println("Error starting server: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Server is already running.");
        }
    }

    public static void stop() {
        if (isRunning) {
            try {
                // Notify connected clients about shutdown
                if (registry != null) {
                    try {
                        ServerClientServices service = (ServerClientServices) registry.lookup(SERVICE_NAME);
                        service.notifyClientsShutdown(); // Notify clients (Implement this in ServerClientServices)
                    } catch (NotBoundException e) {
                        System.out.println("Service not bound, skipping client notification.");
                    }
                }

                // Unbind and stop the server
                registry.unbind(SERVICE_NAME);
                System.out.println("Service '" + SERVICE_NAME + "' unbound successfully.");

            } catch (RemoteException | NotBoundException e) {
                System.err.println("Error stopping server: " + e.getMessage());
            } finally {
                registry = null;
                isRunning = false;
                System.out.println("Server stopped successfully.");
            }
        } else {
            System.out.println("Server is not running.");
        }
    }

}
