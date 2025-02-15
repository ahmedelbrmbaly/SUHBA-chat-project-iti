package com.suhba.daos.interfaces;

import java.util.List;
import java.util.Map;

import com.suhba.database.entities.User;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import com.suhba.database.enums.UserStatus;

public interface UserDAO {  //CRUD
    //Create
    boolean addNewUser(User user);

    //Read
    User getUserById(long userId);
    User getUserByPhone(String phone);

    long getUserIdByEmailDAO(String phone);

    long getUserIdByPhoneDAO(String phone);

    long getUserIdByPhone(String phone);
    UserStatus getUserStatusById(long userId);
    String getUserDisplayNameById(long userId);
    List<User> getUsersByCountry(Country country);
    List<User> getUsersByStatus(UserStatus status);
    List<User> getUsersByGender(Gender gender);
    List<User> getUsersByEmail(String email);
    List<User> getAllUsers();
    List<User> getUsersById(List<Long> userIds);
    List<Long> getUserIdsByPhones(List<String> phones);

    public Map<Country, Long> getUsersCountries();
    public Map<UserStatus, Long> getUsersStatus();
    public Map<Gender, Long> getUsersGenders();

    //Update
    boolean updateUser(User user);
    boolean updateUserProfile(User user);
    boolean updateUserPassword(long userId, String newPassword);

    //Delete
    boolean deleteUserById(long userId);

    public boolean isChatBotActive(User user);
    public void setChatBotActive(User user,boolean chatBotActive);



}

