package com.suhba.daos.interfaces;

import java.util.List;

import com.suhba.database.entities.User;

public interface UserDAO {  //CRUD
    //Create
    boolean addNewUser(User user);

    //Read
    User getUserById(long userId);
    User getUserByPhone(String phone);
    String getUserStatusById(long userId);
    String getUserDisplayNameById(long userId);
    List<User> getUsersByCountry(String country);
    List<User> getUsersByStatus(String status);
    List<User> getUsersByGender(String gender);
    List<User> getUsersByEmail(String email);
    List<User> getAllUsers();

    //Update
    boolean updateUser(User user);
    boolean updateUserProfileById(long userId);
    boolean updateUserPasswordById(long userId);

    //Delete
    boolean deleteUserById(long userId);
}

