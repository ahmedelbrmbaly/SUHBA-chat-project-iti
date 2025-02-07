package com.suhba;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;

import com.suhba.daos.DatabaseConnection;
import com.suhba.daos.implementation.ChatDAOImpl;

import com.suhba.daos.implementation.GroupDaoImpl;
import com.suhba.daos.implementation.MessageDAOImpl;
import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.daos.interfaces.MessageDAO;
import com.suhba.database.entities.Chat;

import com.suhba.database.entities.Group;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.database.enums.ChatType;
import com.suhba.database.enums.MessageStatus;
import com.suhba.services.client.implementaions.ChatServiceImpl;

public class Main {

    public static void main(String[] args) throws Exception {
        

        User u = (new ChatServiceImpl() ).getUserById(1);
        // System.out.println(u);

        // Chat ch = (new ChatServiceImpl().getDirectChatBetween(1, 2));
        // System.out.println(ch);

        // Map<User,Message> map = new ChatServiceImpl().getUserChats(u.getUserId());
        // for(Map.Entry<User,Message> entry: map.entrySet()) {
        //     System.out.println("Key= "+ entry.getKey());
        //     System.out.println(" Val= "+ entry.getValue());
        // }
        // if(map.isEmpty()) System.out.println("empty");

        // MessageDAOImpl impl = new MessageDAOImpl();
        
        // for(Message m: impl.getChatMessages(ch.getChatId())){
        //     System.out.println(m);
        // }

        // Message msg = new ChatServiceImpl().sendMessage(new Message(1,13,"last msg in group",MessageStatus.Sent,null));
        // System.out.println(msg);

        // Map<Group,Message> map = new ChatServiceImpl().getUserGroups(u.getUserId());
        // for(Map.Entry<Group,Message> entry: map.entrySet()) {
        //     System.out.println("Key= "+ entry.getKey());
        //     System.out.println(" Val= "+ entry.getValue());
        // }
        // if(map.isEmpty()) System.out.println("empty");

        // long id = new ChatServiceImpl().createPrivateChat(1, 3);
        // System.out.println(id);

        // User user = new ChatServiceImpl().getPrivateUserPartnerByChat(6, 1);
        // System.out.println(user);



        // List<Long> ids= new ArrayList<>();
        // ids.add((long)4);
        // // Group newGroup = new ChatServiceImpl().createGroupChat(new Group("v"), ids);
        // boolean added = new ChatServiceImpl().addUsersToGroup(1, ids);
        // System.out.println(added);

        // List<User> members = new ChatServiceImpl().getGroupMembers(2);
        // for(User user : members){
        //     System.out.println(user);
        // }

        Group g = new ChatServiceImpl().getGroupByChat(12);
        System.out.println(g);
        
    }
}
