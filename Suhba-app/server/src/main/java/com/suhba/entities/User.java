public class User {
    private int userId;
    private String phone;
    private String displayName;
    private String userEmail;
    private String password;
    private Gender gender;
    private String country;
    private Date birthday;
    private String bio;
    private UserStatus userStatus;

    // Getters
    public int getUserId() { return userId; }
    public String getPhone() { return phone; }
    public String getDisplayName() { return displayName; }
    public String getUserEmail() { return userEmail; }
    public String getPassword() { return password; }
    public Gender getGender() { return gender; }
    public String getCountry() { return country; }
    public Date getBirthday() { return birthday; }
    public String getBio() { return bio; }
    public UserStatus getUserStatus() { return userStatus; }

    // Setters
    public void setUserId(int userId) { this.userId = userId; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public void setPassword(String password) { this.password = password; }
    public void setGender(Gender gender) { this.gender = gender; }
    public void setCountry(String country) { this.country = country; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }
    public void setBio(String bio) { this.bio = bio; }
    public void setUserStatus(UserStatus userStatus) { this.userStatus = userStatus; }
}