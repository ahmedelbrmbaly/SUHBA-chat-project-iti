package com.suhba;

import java.util.List;

import com.suhba.daos.implementation.ContactDAOImpl;
import com.suhba.database.entities.User;
import com.suhba.network.ServerClientServicesImpl;
import com.suhba.services.client.implementaions.ContactServiceImpl;

public class Main {

    public static void main(String[] args) throws Exception{
        ServerClientServicesImpl hi= new ServerClientServicesImpl();
        // hi.createPrivateChat(1, 4);

        ContactServiceImpl contactDAOImpl = new ContactServiceImpl();
        List<User> contacts = contactDAOImpl.getAllFriends(1);

        for(User user: contacts){
            System.out.println(user);
        }
        
        
    }
}
