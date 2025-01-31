package com.suhba.daos.interfaces;

import com.suhba.entities.User;
import com.suhba.exceptions.DAOException;
import java.util.Map;
import java.util.List;

public interface AdminDAO {
    // User Management
    List<User> getAllUsers() throws DAOException;
    void deactivateUser(int userId) throws DAOException;
    
    // Statistics
    Map<String, Integer> getUserStatistics() throws DAOException; // Online/offline counts
    Map<String, Integer> getGenderStatistics() throws DAOException;
    Map<String, Integer> getCountryStatistics() throws DAOException;
}