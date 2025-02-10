package com.suhba.services.client.implementaions;

import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.daos.implementation.UserSessionDAOImpl;
import com.suhba.database.entities.User;
import com.suhba.database.entities.UserSession;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import com.suhba.exceptions.*;
import com.suhba.services.client.interfaces.UserAuthService;
import com.suhba.utils.Hashing;
import com.suhba.utils.Validation;

import java.io.*;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.Enumeration;

public class UserAuthServiceImpl implements UserAuthService {
    UserDAOImpl myObj;
    Validation myValidation;
    Hashing myHashing;
    User myUser;
    UserSession userSession;
    UserSessionDAOImpl userSessionDAOImpl;
    User userTobeInserted;

    public UserAuthServiceImpl() {
        this.myObj = new UserDAOImpl();
        this.myValidation = new Validation();
        this.myHashing = new Hashing();
        this.myUser = new User();
        this.userSession = new UserSession();
        this.userSessionDAOImpl = new UserSessionDAOImpl();
        this.userTobeInserted = new User();
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

    @Override
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

    @Override
    public boolean saveFirstPart(String phone, String email, String password) throws InvalidPhoneException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException, InvalidPasswordException, NoSuchAlgorithmException {
        System.out.println("saveFirstPart method in UserAuthServiceImpl is called");
        if (myValidation.validatePhone(phone))  userTobeInserted.setPhone(phone);
        if (myValidation.validateEmail(email))  userTobeInserted.setUserEmail(email);
        if (myValidation.validatePassword(password))  userTobeInserted.setPassword(myHashing.doHashing(password));
        System.out.println("Phone: " + userTobeInserted.getPhone());
        System.out.println("Email: " + userTobeInserted.getUserEmail());
        System.out.println("Password: " + userTobeInserted.getPassword());
        return true;
    }

   /* @Override
    public long registerAndGetUserId(String phone, String email, String password) throws InvalidPhoneException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException, InvalidPasswordException, NoSuchAlgorithmException {
        boolean isValid = saveFirstPart(phone, email, password);
        if (isValid) {
            long userId = myObj.getUserIdByPhone(phone);
            System.out.println("User registered with ID: " + userId);
            return userId;
        }
        return -1;
    }*/


    @Override
    public void saveLastPart(String name, Gender gender, LocalDate DOB, Country country, byte[] picture) {
        System.out.println("saveLastPart method in UserAuthServiceImpl is called");
        userTobeInserted.setDisplayName(name);
        userTobeInserted.setGender(gender);
        userTobeInserted.setBirthday(DOB);
        userTobeInserted.setCountry(country);
        userTobeInserted.setPicture(picture == null ? null : picture);
        myObj.addNewUser(userTobeInserted);
        System.out.println("Name: " + userTobeInserted.getDisplayName());
        System.out.println("Gender: " + userTobeInserted.getGender());
        System.out.println("DOB: " + userTobeInserted.getBirthday());
        System.out.println("Country: " + userTobeInserted.getCountry());
        System.out.println("Picture: " + (userTobeInserted.getPicture() != null ? "Exists" : "Null"));
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return myObj.getUserByPhone(phoneNumber);
    }

    @Override
    public boolean isPasswordMatchUser(long userId, String password) throws RemoteException, NoSuchAlgorithmException {
        return myObj.getUserById(userId).getPassword().equals(myHashing.doHashing(password));
    }


}
