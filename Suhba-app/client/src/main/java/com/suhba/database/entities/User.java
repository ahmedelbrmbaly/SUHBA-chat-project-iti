package com.suhba.database.entities;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import com.suhba.database.enums.UserStatus;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
    // Attributes
    private long userId;
    private String phone;
    private String displayName;
    private String userEmail;
    private byte[] picture;
    private String password;
    private Gender gender;
    private Country country;
    private LocalDate birthday;
    private String bio;
    private UserStatus userStatus;
    private boolean isChatBotActive; // New field


    // Getters and Setters
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public byte[] getPicture() { return picture; }
    public void setPicture(byte[] picture) { this.picture = picture; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }
    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public UserStatus getUserStatus() { return userStatus; }
    public void setUserStatus(UserStatus userStatus) { this.userStatus = userStatus; }
    public boolean isChatBotActive() { return isChatBotActive; }
    public void setChatBotActive(boolean chatBotActive) { isChatBotActive = chatBotActive; }

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