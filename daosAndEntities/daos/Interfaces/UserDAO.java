package org.example;

import java.util.List;

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
    List<User> getAllUsers();

    //Update
    boolean updateUser(User user);
    boolean updateUserProfileById(long userId);
    boolean updateUserPasswordById(long userId);

    //Delete
    boolean deleteUserById(long userId);
}

