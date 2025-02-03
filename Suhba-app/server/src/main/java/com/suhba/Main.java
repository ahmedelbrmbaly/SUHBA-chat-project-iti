package com.suhba;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.suhba.daos.implementation.ChatDAOImpl;
import com.suhba.daos.implementation.MessageDAOImpl;
import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.daos.interfaces.MessageDAO;
import com.suhba.database.entities.Chat;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.database.enums.ChatType;
import com.suhba.database.enums.MessageStatus;

public class Main {
    public static void main(String[] args) throws SQLException{
        UserDAOImpl userDao = new UserDAOImpl();
        User u1 = userDao.getUserById(1);
        User u2 = userDao.getUserById(3);

        ChatDAOImpl chatDAOImpl = new ChatDAOImpl();

        MessageDAOImpl messageDAOImpl = new MessageDAOImpl();

        

    }

    public void testChat(){
        //1- Create Chat (done)
        // Chat c = chatDAOImpl.createDirectChat(u1, u2);

        // System.out.println(c);
        // System.out.println(ChatType.Direct.name());

        // 2- Is Direct Chat exist ? (done)
        // System.out.println(chatDAOImpl.hasDirectChatBetween(u1, u2));

        // 3- get Direct Chat Partner (done)
        // User partner = chatDAOImpl.getDirectChatPartner(6, u1);
        // System.out.println(partner);

        // 4- get Chat by id (done)
        // Chat c = chatDAOImpl.getChatById(6);
        // System.out.println(c);

        // 5-get user chats (done)
        // List<Chat> chats = chatDAOImpl.getChatsByUser(1);
        // for(Chat c: chats){
        //     System.out.println(c);
        // }

        // 6- is user in chat ? (done)
        // System.out.println(chatDAOImpl.isUserInChat(3, 7));

        // 7- get Chat Participants (done)
        // List<User> users = chatDAOImpl.getChatParticipants(7);
        // for(User u : users) {
        //     System.out.println(u);
        // }

        // 8- Add users to Chat (done)
        // List<Long> ids = new ArrayList<>();
        // ids.add((long)4);
        // chatDAOImpl.addUsersToChat(5,ids);
    }

    public void testMessage(){
        // 1- send Message (done)

        // Message msg = new Message(2,5,"hi test 1", MessageStatus.Sent, null);
        // Message after = messageDAOImpl.sendMessage(msg);
        // System.out.println(after);

        // 2- get Chat Messages (done)
        // List<Message> messages = messageDAOImpl.getChatMessages(5);
        // for (Message m: messages) {
        //     System.out.println(m);
        // }

        // 3- Update Message Status (done)
        // messageDAOImpl.updateMessageStatus(1, MessageStatus.Delivered);

        // 4- get Unread count: (done)
        // int count = messageDAOImpl.getUnreadCount(4, 5);
        // System.out.println(count);

    }
}
