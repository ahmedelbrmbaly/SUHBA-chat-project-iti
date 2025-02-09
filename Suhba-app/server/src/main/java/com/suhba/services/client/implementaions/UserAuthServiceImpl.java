package com.suhba.services.client.implementaions;

import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.daos.implementation.UserSessionDAOImpl;
import com.suhba.database.entities.User;
import com.suhba.database.entities.UserSession;
import com.suhba.exceptions.*;
import com.suhba.services.client.interfaces.UserAuthService;
import com.suhba.utils.Hashing;
import com.suhba.utils.Validation;

import java.io.*;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

public class UserAuthServiceImpl implements UserAuthService {
    UserDAOImpl myObj;
    Validation myValidation;
    Hashing myHashing;
    User myUser;
    UserSession userSession;
    UserSessionDAOImpl userSessionDAOImpl;

    public UserAuthServiceImpl() {
        this.myObj = new UserDAOImpl();
        this.myValidation = new Validation();
        this.myHashing = new Hashing();
        this.myUser = new User();
        this.userSession = new UserSession();
        this.userSessionDAOImpl = new UserSessionDAOImpl();
    }

    @Override
    public User signup(User user) throws InvalidPhoneException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException, InvalidPasswordException, NoSuchAlgorithmException {
        String passowrd = user.getPassword();
        if (myValidation.validatePassword(passowrd)){
            passowrd = myHashing.doHashing(passowrd);
            user.setPassword(passowrd);
        }

        if (myValidation.validatePhone((user.getPhone())))  user.setPhone(user.getPhone());
        user.setDisplayName(user.getDisplayName());
        if (myValidation.validateEmail((user.getUserEmail())))  user.setUserEmail(user.getUserEmail());
        user.setPicture(null);
        user.setGender(user.getGender());
        user.setCountry(user.getCountry());
        user.setBirthday(user.getBirthday()); //LocalDate.parse("2002-02-02")
        user.setBio(user.getBio());
        user.setUserStatus(user.getUserStatus());

        myObj.addNewUser(user);
        return user;
    }

    @Override
    public boolean isPhoneRegistered(String phoneNumber) {
        return (myObj.getUserByPhone(phoneNumber) != null ? true : false);
    }

    @Override
    public User login(String phoneNumber, String password) throws NoSuchAlgorithmException, UserNotFoundException, IncorrectPasswordException, IOException {
        myUser = myObj.getUserByPhone(phoneNumber);

        if (myUser == null)  throw new UserNotFoundException("User is not found!");
        if(!myUser.getPassword().equals(myHashing.doHashing(password)))  throw new IncorrectPasswordException("Incorrect password!");

        String macAddress = getMacAddress();
        if (macAddress == null)  throw new IOException("Unable to reach the mac address of your device");
        userSessionDAOImpl.updateIsActiveByUserIdAndMacAddress(myUser.getUserId(), macAddress, true);
        writeSession(macAddress, phoneNumber);

        return myUser;
    }

    public String getMacAddress() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();

        while (networkInterfaceEnumeration.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
            byte[] macBytes = networkInterface.getHardwareAddress();
            if (macBytes != null) {
                StringBuilder macAddress = new StringBuilder();
                for (int i = 0; i < (int) macBytes.length; i++)
                    macAddress.append(String.format("%02X", macBytes[i]) + (i != (int)macBytes.length - 1 ? '-' : ""));
                return macAddress.toString();
            }
        }
        return null;
    }

    // To add the current user session to the sessionFile
    public void writeSession (String macAddress, String phoneNumber) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("sessionFile.txt"))){ //append is false by default
            bufferedWriter.write(macAddress + " --> " + phoneNumber);
        }
    }

    @Override
    public boolean isSessionActive(String macAddress, long userId) throws IOException {
        return (findMacAddressInFile(macAddress) && userSessionDAOImpl.getIsActiveByUserIdAndMacAddress(userId, macAddress));
    }

    // To check whether the current user session (mac address) is registered in the sessionFile
    public boolean findMacAddressInFile (String macAddress) throws IOException {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("sessionFile.txt"))) {
            String curLine = null, storedMacAddress = null;
            while ((curLine = bufferedReader.readLine()) != null) {
                String[] lineParts = curLine.split("-->");
                storedMacAddress = lineParts[0].trim();
                if (storedMacAddress.equals(macAddress))  return true;
            }
        }
        return false;
    }

    @Override
    public void logout(String macAddress, long userId) throws IOException {
        if (isSessionActive(macAddress, userId)) {
            userSessionDAOImpl.updateIsActiveByUserIdAndMacAddress(userId, macAddress, false);
            clearSessionFile();
        }
    }

    // To delete the current user session from sessionFile
    public void clearSessionFile () throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("sessionFile.txt"))){
            bufferedWriter.write("");
        }
    }

    // To check whether the user is still in the system (he just exits the app)
    @Override
    public boolean exit() throws IOException {
        String macAddress = getMacAddress();
        return (macAddress != null && findMacAddressInFile(macAddress));
    }
}
