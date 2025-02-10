package com.suhba.network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.suhba.database.entities.Message;
import com.suhba.database.enums.ChatType;
import com.suhba.database.enums.UserStatus;
import com.suhba.services.controllers.ChatScreenService;
import com.suhba.services.controllers.GroupScreenService;


public class ClientImplementation extends UnicastRemoteObject implements ClientInterface {

    ChatScreenService chatScreenService;
    GroupScreenService groupScreenService;

    public ClientImplementation()throws RemoteException  {
        super();
        chatScreenService = new ChatScreenService();
        groupScreenService = new GroupScreenService();
    }

    // public ClientImplementation() throws RemoteException {
    //     super();
    // }

    @Override
    public boolean receiveMessage(Message msg, ChatType type) throws RemoteException {
        System.out.println("Received Msg: "+ msg);
        if(type== ChatType.Direct){
            chatScreenService.sendNewMessageToUi(msg);
        }else{
            groupScreenService.sendNewMessageToUi(msg);
        }
        
        return true;
    }

    @Override
    public void notifyUserStatusChanged(long userId, UserStatus newStatus) throws RemoteException {
       System.out.println("Getting new update for user : "+ userId);
       // Call method to handle in gui
    }

    

}
