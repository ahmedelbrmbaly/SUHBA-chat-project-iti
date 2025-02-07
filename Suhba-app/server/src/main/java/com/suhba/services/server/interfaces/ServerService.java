package com.suhba.services.server.interfaces;

import java.util.List;
import java.util.Map;

import com.suhba.database.entities.Admin;
import com.suhba.database.entities.User;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import com.suhba.database.enums.UserStatus;

public interface ServerService {

    public Admin login(String username, String password);
    public Admin addNewAdmin(Admin newAdmin);
    public boolean logout(Admin admin);
    public boolean updateAdmin(Admin admin);

    public boolean startServer();
    public boolean stopServer();
    public boolean isActive();

    public boolean addNewUser(User user);  // User(username, password [default=123], phone)

    public List<User> getAllUsers(); 
    
    // public List<Session> getAllSessions();

    public boolean sendAnnounce(String msg);

    public Map<UserStatus,Long> getUserStatus();

    public Map<Gender,Long> getGenderStatistics();

    public Map<Country,Long> getCountryStatistics();
}
