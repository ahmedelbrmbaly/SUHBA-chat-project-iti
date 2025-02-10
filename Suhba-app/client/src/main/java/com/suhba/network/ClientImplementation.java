package com.suhba.network;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.suhba.controllers.ChatScreenController;
import com.suhba.database.entities.Message;
import com.suhba.services.controllers.ChatScreenService;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ClientImplementation extends UnicastRemoteObject implements ClientInterface {

    ChatScreenService chatScreenService;
    public ClientImplementation()throws RemoteException  {
        super();
        chatScreenService = new ChatScreenService();
    }

    // public ClientImplementation() throws RemoteException {
    //     super();
    // }

    @Override
    public boolean receiveMessage(Message msg) throws RemoteException {
        System.out.println("Received Msg: "+ msg);
        chatScreenService.sendNewMessageToUi(msg);
        return true;
    }

}
