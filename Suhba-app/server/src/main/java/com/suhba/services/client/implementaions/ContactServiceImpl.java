package com.suhba.services.client.implementaions;

import com.suhba.daos.implementation.ContactDAOImpl;
import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.database.entities.Contact;
import com.suhba.database.entities.User;
import com.suhba.database.enums.ContactStatus;
import com.suhba.services.client.interfaces.ContactService;

import java.rmi.RemoteException;
import java.util.List;

public class ContactServiceImpl implements ContactService {
    //userId1 --> Sender (me)  VS  userId2 --> Receiver  VS  contactStatus --> ACCEPTED, PENDING, or DECLINED

    ContactDAOImpl myContactDao;
    User myUser;
    UserDAOImpl myUserDao;
    boolean exist;

    public ContactServiceImpl() {
        this.myContactDao = new ContactDAOImpl();
        myUser = new User();
        myUserDao = new UserDAOImpl();
        exist = false;
    }

    @Override
    public boolean sendFriendRequest(String phoneNumber) {
        System.out.println("In sendFriendRequest that takes one phone number");
        return myContactDao.addContact(new Contact(myUser.getUserId(), myUserDao.getUserIdByPhone(phoneNumber), ContactStatus.PENDING));
    }

    @Override
    public boolean sendFriendRequest(long userId) {
        return myContactDao.addContact(new Contact(myUser.getUserId(), userId, ContactStatus.PENDING));
    }

    @Override
    public boolean sendFriendRequest(long userId1, long userId2) throws RemoteException {
       /* exist = false;
        if (myContactDao.getContactsByUserId1AndUserId2(userId1, userId2) != null) {
            exist = true;
            return false;
        }*/
        Contact contact = new Contact();
        contact.setUserId1(userId1);
        contact.setUserId2(userId2);
        contact.setContactStatus(ContactStatus.PENDING);
        System.out.println("In sendFriendRequest that takes userId1 & userId2");
        return myContactDao.addContact(contact);
    }

    @Override
    public boolean sendFriendRequests(List<String> phoneNumber) {
        for (String curPhoneNumber: phoneNumber) {
            System.out.println("In sendFriendRequest that takes list of phone numbers");
            if (!sendFriendRequest(curPhoneNumber))  return false;
        }
        return true;
    }

    @Override
    public boolean sendFriendRequestsById(List<Long> userId) {
        for (long curUserId: userId) {
            if (!sendFriendRequest(curUserId))  return false;
        }
        System.out.println("In sendFriendRequest that takes list of userId");
        return true;
    }

    @Override
    public boolean sendFriendRequestsById(long userId1, List<Long> userId) throws RemoteException {
        System.out.println("In sendFriendRequestsById that takes userId1 & list of userId");
        for (long curUserId: userId) {
            if (myContactDao.getContactsByUserId1AndUserId2(userId1, curUserId) != null)  continue;
            if (!sendFriendRequest(userId1, curUserId))  return false;
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
        List<Long> sendersIds = myContactDao.getUserId1ByUserId2(userId, ContactStatus.ACCEPTED);
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
