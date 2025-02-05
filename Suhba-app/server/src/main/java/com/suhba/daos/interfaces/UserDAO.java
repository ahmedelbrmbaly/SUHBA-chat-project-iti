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
    String getUserStatusById(long userId);
    String getUserDisplayNameById(long userId);
    List<User> getUsersByCountry(String country);
    List<User> getUsersByStatus(String status);
    List<User> getUsersByGender(String gender);

    Map<Country,Long> getUsersCountries();
    Map<UserStatus, Long> getUsersStatus();
    Map<Gender,Long> getUsersGenders();
    
    List<User> getAllUsers();

    //Update
    boolean updateUser(User user);
    boolean updateUserProfileById(long userId);
    boolean updateUserPasswordById(long userId);

    //Delete
    boolean deleteUserById(long userId);
}

