package com.suhba.daos.interfaces;

import com.suhba.database.entities.Group;

import java.util.List;
import java.util.Optional;

public interface GroupDao {
    Optional<Group> createGroup(Group group, long creatorId);

    Optional<Group> getGroupById(long groupId);
    List<Group> getAllGroups();
    List<Group> getGroupsByUserId(long userId);

    boolean updateGroup(Group group);
    boolean deleteGroup(long groupId);

    boolean addUserToGroup(long groupId, long userId);
    boolean removeUserFromGroup(long groupId, long userId);
    boolean isUserInGroup(long groupId, long userId);
    List<Long> getGroupMembers(long groupId);

}