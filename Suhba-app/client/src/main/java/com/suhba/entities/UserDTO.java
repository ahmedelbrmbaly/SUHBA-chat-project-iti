public class UserDTO {
    private int userId;
    private String phone;
    private String displayName;
    private String userStatus; // From UserStatus enum

    // Constructor
    public UserDTO() {}

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getUserStatus() { return userStatus; }
    public void setUserStatus(String userStatus) { this.userStatus = userStatus; }
}