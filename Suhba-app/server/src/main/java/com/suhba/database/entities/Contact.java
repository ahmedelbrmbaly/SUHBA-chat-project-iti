package com.suhba.database.entities;

import com.suhba.database.enums.ContactStatus;

public class Contact {
    private long userId1;
    private long userId2;
    private ContactStatus contactStatus;

    // Constructor
    public Contact(){}

    public Contact(long userId1, long userId2, ContactStatus contactStatus) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.contactStatus = contactStatus;
    }

    // Getters and Setters
    public long getUserId1() {
        return userId1;
    }

    public void setUserId1(long userId1) {
        this.userId1 = userId1;
    }

    public long getUserId2() {
        return userId2;
    }

    public void setUserId2(long userId2) {
        this.userId2 = userId2;
    }

    public ContactStatus getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(ContactStatus contactStatus) {
        this.contactStatus = contactStatus;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "userId1=" + userId1 +
                ", userId2=" + userId2 +
                ", contactStatus=" + contactStatus +
                '}';
    }
}
