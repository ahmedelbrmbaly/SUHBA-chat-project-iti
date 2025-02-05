package com.suhba.services.client.interfaces;

import com.suhba.database.entities.User;

public interface UserAuthService {

    public User signup(User user);
    
    // >> Remeber me XML SESSION >> To BE REVIEWED
    public boolean isPhoneRegistered(String phoneNumder);

    public User login(String phoneNumder , String password) ;

    public boolean isSessionActive(String macAddress, long userId);

    // forget password >> To BE REVIEWED (bouns)

    // Logout >> To BE REVIEWED
    public boolean logout();

    // Exit >> To BE REVIEWED
    public boolean exit();

}