public class ContactDTO {
    private int contactUserId; // ID of the contact (from Users.userId)
    private String contactName; // UserDTO.displayName of the contact
    private String contactPhone; // UserDTO.phone of the contact
    private String contactStatus; // From ContactStatus enum

    // Getters and Setters
    public int getContactUserId() { return contactUserId; }
    public void setContactUserId(int contactUserId) { this.contactUserId = contactUserId; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getContactStatus() { return contactStatus; }
    public void setContactStatus(String contactStatus) { this.contactStatus = contactStatus; }
}