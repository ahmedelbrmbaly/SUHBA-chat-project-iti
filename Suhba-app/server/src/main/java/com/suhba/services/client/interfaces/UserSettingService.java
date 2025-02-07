package com.suhba.services.client.interfaces;

import com.suhba.database.entities.User;

import java.rmi.RemoteException;

public interface UserSettingService {
    
    // Settings Screen
    public boolean updateUserProfile(User user) throws RemoteException;
    public boolean updateUserPassword(long userId, String newPassword) throws RemoteException; // Password must be hashed
} 