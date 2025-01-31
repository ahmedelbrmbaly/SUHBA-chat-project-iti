package com.suhba.daos.interfaces;

import com.suhba.entities.Group;
import com.suhba.exceptions.DAOException;

public interface GroupDAO {
    // Group Management
    long createGroup(Group group) throws DAOException; // Returns new groupId
    Group getGroupById(long groupId) throws DAOException;
    void updateGroup(Group group) throws DAOException; // Name, description, category
    void deleteGroup(long groupId) throws DAOException;
    
    // Members
    void addUserToGroup(long groupId, int userId) throws DAOException;
    void removeUserFromGroup(long groupId, int userId) throws DAOException;
    List<Group> getGroupsByCategory(String category) throws DAOException; // E.g., "Friends"
}