package com.suhba.services.client.interfaces;

import com.suhba.database.entities.User;

public interface UserSettingService {
    
    // Settings Screen
    public boolean updateUserProfile(User user);
    public boolean updateUserPassword(String newPassword); // Password must be hashed
    
} 