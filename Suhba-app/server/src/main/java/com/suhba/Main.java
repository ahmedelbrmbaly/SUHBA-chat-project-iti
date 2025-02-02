package com.suhba;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.suhba.daos.implementation.ChatDAOImpl;
import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.database.entities.Chat;
import com.suhba.database.entities.User;
import com.suhba.database.enums.ChatType;

public class Main {
    public static void main(String[] args){
        UserDAOImpl userDao = new UserDAOImpl();
        User u1 = userDao.getUserById(1);
        User u2 = userDao.getUserById(2);

        // ChatDAOImpl chatDao = new ChatDAOImpl();
        // Chat chat1 = chatDao.createChat(ChatType.Direct,u1,u2);

        // System.out.println("Chat = "+ chat1 );

        // List<Chat> chats = chatDao.getChatsByUserId(1);
        // for(Chat c: chats){
        //     System.out.println(c);
        // }



    }
}
