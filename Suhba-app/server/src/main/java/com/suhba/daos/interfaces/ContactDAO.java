package com.suhba.daos.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.suhba.database.entities.Contact;
import com.suhba.database.enums.ContactStatus;

public interface ContactDAO {

    public boolean addContact(Contact contact);
    
    public boolean updateContactStatus(Contact contact, ContactStatus newStatus); 
    
    public boolean deleteContact(Contact contact);

    public List<Contact> getContactsByUserId(long userId);

    public List<Contact> getContactsByUserId1AndUserId2(long userId1, long userId2);

    public List<Long> getUserId1ByUserId2(long userId2, ContactStatus contactStatus);

    public List<Long> getAcceptedFriends(long userId) throws SQLException;

    // public List<User> getAllUsersInContactByUserID(long userId);

}
