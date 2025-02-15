package com.suhba.network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.suhba.database.entities.Message;
import com.suhba.database.enums.ChatType;
import com.suhba.database.enums.UserStatus;
import com.suhba.services.chatbot;
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

    public ClientImplementation(ChatScreenService chatScreenService2) throws RemoteException {
        super();
        this.chatScreenService=chatScreenService2;
    }

    public ClientImplementation(GroupScreenService groupService) throws RemoteException {
        super();
        this.groupScreenService = groupService;
    }

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
    public void chatBotMessage(long senderId, long receiverId, Message oldMessage) throws RemoteException {
        // Create a new thread to handle the chatbot response
        new Thread(() -> {
            try {
                // Prepare the prompt for the chatbot
                String prompt = "Act as a representer of offline user and reply to this message on behave of them with a clear declartion that you are a bot and that's auto reply mode: ";
                prompt += oldMessage.getContent();

                // Get the chatbot's response (this is a blocking call)
                String reply = chatbot.getBotResponse(prompt);

                // Update the message with the chatbot's response
                oldMessage.setContent(reply);
                oldMessage.setSenderId(senderId);

                // Send the chatbot's response back to the server
                chatScreenService.sendMessageToUser(oldMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start(); // Start the thread
    }


}
