package com.suhba;

import java.util.List;

import com.suhba.daos.implementation.ContactDAOImpl;
import com.suhba.database.entities.User;
import com.suhba.network.ServerClientServicesImpl;
import com.suhba.services.client.implementaions.ContactServiceImpl;

public class Main {

    public static void main(String[] args) throws Exception{
        ServerClientServicesImpl hi= new ServerClientServicesImpl();

        System.out.println(hi.getUserById(1));
        
        
    }
}
