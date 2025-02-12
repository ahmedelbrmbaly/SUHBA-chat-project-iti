package com.suhba.services.client.interfaces;

import com.suhba.database.entities.User;
import com.suhba.database.enums.UserStatus;
import com.suhba.exceptions.*;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public interface UserSettingService {
    
    // Settings Screen
    public boolean updateUserProfile(User user) throws RemoteException, InvalidPhoneException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException;
    public boolean updateUserPassword(long userId, String newPassword) throws RemoteException, InvalidPasswordException, NoSuchAlgorithmException; // Password must be hashed
    public boolean updateUserStatus(long userId, UserStatus newStatus);
} 