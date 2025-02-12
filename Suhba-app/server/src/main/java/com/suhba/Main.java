package com.suhba;

import com.suhba.network.ServerClientServicesImpl;
import com.suhba.services.client.implementaions.GeminiAPI;

public class Main {

    public static void main(String[] args) throws Exception{

        System.out.println(GeminiAPI.getBotResponse("Hi there"));
        
    }
}
