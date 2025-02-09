package com.suhba;

import com.suhba.network.ServerClientServicesImpl;

public class Main {

    public static void main(String[] args) throws Exception{
        ServerClientServicesImpl hi= new ServerClientServicesImpl();
        hi.createPrivateChat(1, 4);
        
    }
}
