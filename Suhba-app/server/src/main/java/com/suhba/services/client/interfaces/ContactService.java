package com.suhba.services.client.interfaces;

import java.util.List;

import com.suhba.database.entities.User;
import com.suhba.database.enums.ContactStatus;

public interface ContactService {
    
    // Contact Screen
    public boolean sendFriendRequest(String phoneNumber);
    public boolean sendFriendRequest(long userId);

    public boolean sendFriendRequests(List<String> phoneNumber);
    public boolean sendFriendRequestsById(List<Long> userId);

    public List<User> getAllPendingRequests(long userId);
    public List<User> getAllFriends(long userId);

    public boolean updateRequestStatus(long userId, ContactStatus status );

    public boolean deleteContact(long userId);
}
