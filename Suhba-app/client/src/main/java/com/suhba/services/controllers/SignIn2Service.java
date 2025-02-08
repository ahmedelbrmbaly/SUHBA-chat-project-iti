package com.suhba.services.controllers;

import com.suhba.network.ServerClientServices;
import com.suhba.network.ServerService;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public class SignIn2Service {
    ServerClientServices serverService = ServerService.getInstance();
    SignIn1Service signIn1Service = new SignIn1Service();

    public boolean checkIfMatch (String password) throws NoSuchAlgorithmException, RemoteException {
        return serverService.isPasswordMatchUser(signIn1Service.getCurUserId(), password);
    }
}
