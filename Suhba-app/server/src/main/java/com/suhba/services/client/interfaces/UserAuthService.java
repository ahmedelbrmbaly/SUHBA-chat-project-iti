package com.suhba.services.client.interfaces;

import com.suhba.database.entities.User;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import com.suhba.exceptions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

public interface UserAuthService {

    public User signup(User user) throws InvalidPhoneException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException, InvalidPasswordException, NoSuchAlgorithmException, RemoteException;
    
    // >> Remeber me XML SESSION >> To BE REVIEWED
    public boolean isPhoneRegistered(String phoneNumber) throws RemoteException;

    boolean isEmailRegistered(String Email);

    long getUserIdByEmail(String Email);

    long getUserIdByPhone(String Phone);

    public User login(String phoneNumber, String password) throws NoSuchAlgorithmException, UserNotFoundException, IncorrectPasswordException, IOException, RemoteException;

    public String getMacAddress() throws RemoteException, SocketException;

    public boolean isSessionActive(String macAddress, long userId) throws IOException, RemoteException;

    // forget password >> To BE REVIEWED (bouns)

    // Logout >> To BE REVIEWED
    void logout(String macAddress, long userId) throws IOException, RemoteException;

    // Exit >> To BE REVIEWED
    public boolean exit() throws IOException, RemoteException;

    public boolean saveFirstPart(String phone, String email, String password) throws RemoteException, InvalidPhoneException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException, InvalidPasswordException, NoSuchAlgorithmException;

    public void saveLastPart(String name, Gender gender, LocalDate DOB, Country country, byte[] picture) throws RemoteException;
    public User getUserByPhoneNumber (String phoneNumber) throws RemoteException;
    public boolean isPasswordMatchUser (long userId, String password) throws RemoteException, NoSuchAlgorithmException;
    public List<Long> getUserIdsByPhones(List<String> phones);
    //public long registerAndGetUserId(String phone, String email, String password) throws InvalidPhoneException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException, InvalidPasswordException, NoSuchAlgorithmException;
}

