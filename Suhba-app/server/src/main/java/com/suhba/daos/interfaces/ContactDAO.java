package com.suhba.daos.interfaces;

import com.suhba.entities.Contact;
import com.suhba.exceptions.DAOException;
import com.suhba.enums.ContactStatus;

public interface ContactDAO {
    // Contact Requests
    void createContactRequest(int userId1, int userId2) throws DAOException;
    void updateContactStatus(int userId1, int userId2, ContactStatus status) throws DAOException;
    
    // Queries
    ContactStatus getContactStatus(int userId1, int userId2) throws DAOException;
    List<Contact> getContactsByUserId(int userId) throws DAOException; // All contacts for a user
    void deleteContact(int userId1, int userId2) throws DAOException;
}