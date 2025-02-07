package com.suhba.daos.interfaces;

import java.util.List;

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
    long getUserIdByPhone(String phone);
    UserStatus getUserStatusById(long userId);
    String getUserDisplayNameById(long userId);
    List<User> getUsersByCountry(Country country);
    List<User> getUsersByStatus(UserStatus status);
    List<User> getUsersByGender(Gender gender);
    List<User> getUsersByEmail(String email);
    List<User> getAllUsers();
    List<User> getUsersById(List<Long> userIds);

    //Update
    boolean updateUser(User user);
    boolean updateUserProfileById(long userId);
    boolean updateUserPasswordById(long userId);

    //Delete
    boolean deleteUserById(long userId);
}

