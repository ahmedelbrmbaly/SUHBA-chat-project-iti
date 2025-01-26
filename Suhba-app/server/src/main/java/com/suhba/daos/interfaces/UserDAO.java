package com.suhba.daos.interfaces;

import com.suhba.entities.User;
import com.suhba.exceptions.DAOException;
import com.suhba.enums.UserStatus;

public interface UserDAO {
    // CRUD Operations
    void createUser(User user) throws DAOException;
    User getUserById(int userId) throws DAOException;
    User getUserByPhone(String phone) throws DAOException;
    User getUserByEmail(String email) throws DAOException;
    void updateUser(User user) throws DAOException;
    void updateUserStatus(int userId, UserStatus status) throws DAOException;
    void deleteUser(int userId) throws DAOException;

    // Utility
    List<User> searchUsers(String query) throws DAOException; // For contact search
}