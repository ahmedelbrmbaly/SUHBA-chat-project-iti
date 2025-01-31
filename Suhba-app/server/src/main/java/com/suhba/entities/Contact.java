public class Contact {
    private int userId1;
    private int userId2;
    private ContactStatus contactStatus;

    // Getters
    public int getUserId1() { return userId1; }
    public int getUserId2() { return userId2; }
    public ContactStatus getContactStatus() { return contactStatus; }

    // Setters
    public void setUserId1(int userId1) { this.userId1 = userId1; }
    public void setUserId2(int userId2) { this.userId2 = userId2; }
    public void setContactStatus(ContactStatus contactStatus) { 
        this.contactStatus = contactStatus; 
    }
}