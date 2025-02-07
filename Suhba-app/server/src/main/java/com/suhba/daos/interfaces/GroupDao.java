package com.suhba.daos.interfaces;

import com.suhba.database.entities.Group;

import java.sql.SQLException;
import java.util.List;


public interface GroupDao {
    // Group createGroup(Group group, long creatorId);
    Group createGroup(Group group);

    Group getGroupById(long groupId);
    Group getGroupByChatId(long chatId);
    List<Group> getAllGroups();
    List<Group> getGroupsByUserId(long userId) throws SQLException;

    boolean updateGroup(Group group);
    boolean deleteGroup(long groupId);

    boolean addUserToGroup(long groupId, long userId);
    boolean removeUserFromGroup(long groupId, long userId);
    boolean isUserInGroup(long groupId, long userId);
    List<Long> getGroupMembers(long groupId);

}