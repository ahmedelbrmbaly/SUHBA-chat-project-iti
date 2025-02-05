package com.suhba.daos.interfaces;

import java.util.List;

import com.suhba.database.entities.Contact;
import com.suhba.database.enums.ContactStatus;

public interface ContactDAO {

    public boolean addContact(Contact contact);
    
    public boolean updateContactStatus(Contact contact, ContactStatus newStatus); 
    
    public boolean deleteContact(Contact contact);

    public List<Contact> getContactsByUserId(long userId);

    // public List<User> getAllUsersInContactByUserID(long userId);

}
