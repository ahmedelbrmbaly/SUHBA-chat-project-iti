public class Contact {
    private int userId1; // Owner of the contact list
    private int userId2; // Contact user ID
    private String contactStatus; // Relationship status (e.g., "active", "blocked")
    private String contactDisplayName; // Contact's display name
    private String contactPhone; // Contact's phone number
    private String contactEmail; // Contact's email address

    // Constructor
    public Contact(int userId1, int userId2, String contactStatus, String contactDisplayName, String contactPhone, String contactEmail) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.contactStatus = contactStatus;
        this.contactDisplayName = contactDisplayName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
    }

    // Getters and Setters
    public int getUserId1() { return userId1; }
    public void setUserId1(int userId1) { this.userId1 = userId1; }

    public int getUserId2() { return userId2; }
    public void setUserId2(int userId2) { this.userId2 = userId2; }

    public String getContactStatus() { return contactStatus; }
    public void setContactStatus(String contactStatus) { this.contactStatus = contactStatus; }

    public String getContactDisplayName() { return contactDisplayName; }
    public void setContactDisplayName(String contactDisplayName) { this.contactDisplayName = contactDisplayName; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
}
