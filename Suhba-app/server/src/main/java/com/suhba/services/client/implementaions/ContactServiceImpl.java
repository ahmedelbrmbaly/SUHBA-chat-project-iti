package com.suhba.services.client.implementaions;

import com.suhba.daos.implementation.ContactDAOImpl;
import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.database.entities.Contact;
import com.suhba.database.entities.User;
import com.suhba.database.enums.ContactStatus;
import com.suhba.services.client.interfaces.ContactService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactServiceImpl implements ContactService {
    //userId1 --> Sender (me)  VS  userId2 --> Receiver  VS  contactStatus --> ACCEPTED, PENDING, or DECLINED

    ContactDAOImpl myContactDao;
    User myUser;
    UserDAOImpl myUserDao;

    public ContactServiceImpl() {
        this.myContactDao = new ContactDAOImpl();
        myUser = new User();
        myUserDao = new UserDAOImpl();
    }

    @Override
    public boolean sendFriendRequest(String phoneNumber) {
        return myContactDao.addContact(new Contact(myUser.getUserId(), myUserDao.getUserIdByPhone(phoneNumber), ContactStatus.PENDING));
    }

    @Override
    public boolean sendFriendRequest(long userId) {
        return myContactDao.addContact(new Contact(myUser.getUserId(), userId, ContactStatus.PENDING));
    }

    @Override
    public boolean sendFriendRequests(List<String> phoneNumber) {
        for (String curPhoneNumber: phoneNumber) {
            if (!sendFriendRequest(curPhoneNumber))  return false;
        }
        return true;
    }

    @Override
    public boolean sendFriendRequestsById(List<Long> userId) {
        for (long curUserId: userId) {
            if (!sendFriendRequest(curUserId))  return false;
        }
        return true;
    }

    @Override
    public List<User> getAllPendingRequests(long userId) {  //the receiver
        List<Long> sendersIds = myContactDao.getUserId1ByUserId2(userId, ContactStatus.PENDING);
        return myUserDao.getUsersById(sendersIds);
    }

    @Override
    public List<User> getAllFriends(long userId) {  //the receiver
        List<Long> sendersIds = new ArrayList<>();
        try {
            sendersIds = myContactDao.getAcceptedFriends(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myUserDao.getUsersById(sendersIds);
    }

    @Override
    public boolean updateRequestStatusFromPendingToAccepted(Contact contact, ContactStatus status) {
        return myContactDao.updateContactStatus(contact, ContactStatus.ACCEPTED);
    }

    @Override
    public boolean updateRequestStatusFromPendingToDeclined(Contact contact, ContactStatus status) {
        return myContactDao.updateContactStatus(contact, ContactStatus.DECLINED);
    }

    @Override
    public boolean deleteContact(Contact contact) {
        return myContactDao.deleteContact(contact);
    }
}
