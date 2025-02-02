public interface ContactDAO {
    void addContact(int userId1, int userId2, String contactStatus); // Add a new contact
    void updateContactStatus(int userId1, int userId2, String contactStatus); // Update contact status (e.g., block/unblock)
    void setContactEmail(int userId1, int userId2, String contactEmail); // Update a contact's email
    void deleteContact(int userId1, int userId2); // Remove a contact
    List<Contact> getContactsForUser(int userId); // Retrieve all contacts for a user
}
