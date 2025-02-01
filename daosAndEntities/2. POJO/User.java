
import java.sql.Blob;
import java.time.LocalDate;

public class User {
    // Attributes
    private long userId;
    private String phone;
    private String displayName;
    private String userEmail;
    private Blob picture;
    private String password;
    private String gender;
    private String country;
    private LocalDate birthday;
    private String bio;
    private String userStatus;

    // Getters and Setters
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public Blob getPicture() { return picture; }
    public void setPicture(Blob picture) { this.picture = picture; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getUserStatus() { return userStatus; }
    public void setUserStatus(String userStatus) { this.userStatus = userStatus; }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", phone='" + phone + '\'' +
                ", displayName='" + displayName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", picture=" + picture +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", country='" + country + '\'' +
                ", birthday=" + birthday +
                ", bio='" + bio + '\'' +
                ", userStatus='" + userStatus + '\'' +
                '}';
    }
}