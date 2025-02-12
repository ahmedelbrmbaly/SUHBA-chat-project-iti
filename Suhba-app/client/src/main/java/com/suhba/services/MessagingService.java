package com.suhba.services;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;


import com.suhba.database.entities.Message;
import com.suhba.database.enums.ChatType;
import com.suhba.network.ServerService;

public class MessagingService {

    public List<Message> getSelectedChatMessages(long currentChatId) throws Exception {
        List<Message> msgs = ServerService.getInstance().getMessages(currentChatId);
        // for(Message msg: msgs){
        // System.out.println(msg);
        // }
        Collections.sort(msgs);
        return msgs;
    }

    public ChatType getChatType(Message msg) throws RemoteException{
        return ServerService.getInstance().getChatById(msg.getChatId()).getChatType();
    }

    @SuppressWarnings("unused")
    public Message sendMessageToUser(Message msg) throws Exception {
        if (msg.getAttachment() != null) {
        }
        if (msg == null) {
            System.out.println("Message is null at chat screen service");
        }
        return ServerService.getInstance().sendMessage(msg);
    }

}
