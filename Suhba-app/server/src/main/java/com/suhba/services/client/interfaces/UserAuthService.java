package com.suhba.services.client.interfaces;

import com.suhba.database.entities.User;
import com.suhba.exceptions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public interface UserAuthService {

    public User signup(User user) throws InvalidPhoneException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException, InvalidPasswordException, NoSuchAlgorithmException, RemoteException;
    
    // >> Remeber me XML SESSION >> To BE REVIEWED
    public boolean isPhoneRegistered(String phoneNumber) throws RemoteException;

    public User login(String phoneNumber, String password) throws NoSuchAlgorithmException, UserNotFoundException, IncorrectPasswordException, IOException, RemoteException;

    public boolean isSessionActive(String macAddress, long userId) throws IOException, RemoteException;

    // forget password >> To BE REVIEWED (bouns)

    // Logout >> To BE REVIEWED
    void logout(String macAddress, long userId) throws IOException, RemoteException;

    // Exit >> To BE REVIEWED
    public boolean exit() throws IOException, RemoteException;
}