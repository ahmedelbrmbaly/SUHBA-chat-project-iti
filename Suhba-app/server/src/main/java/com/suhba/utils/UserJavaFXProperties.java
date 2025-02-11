package com.suhba.utils;

import com.suhba.database.entities.User;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import com.suhba.database.enums.UserStatus;
import javafx.beans.property.*;

import java.sql.Blob;
import java.time.LocalDate;

public class UserJavaFXProperties {
    private final LongProperty userId = new SimpleLongProperty();
    private final StringProperty phone = new SimpleStringProperty();
    private final StringProperty displayName = new SimpleStringProperty();
    private final StringProperty userEmail = new SimpleStringProperty();
    private final ObjectProperty<Blob> picture = new SimpleObjectProperty<>();
    private final StringProperty password = new SimpleStringProperty();
    private final ObjectProperty<Gender> gender = new SimpleObjectProperty<>();
    private final ObjectProperty<Country> country = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> birthday = new SimpleObjectProperty<>();
    private final StringProperty bio = new SimpleStringProperty();
    private final ObjectProperty<UserStatus> userStatus = new SimpleObjectProperty<>();

    public UserJavaFXProperties(long userId, String phone, String displayName, String userEmail, Blob picture, String password, Gender gender, Country country, LocalDate birthday, String bio, UserStatus userStatus) {
        this.userId.set(userId);
        this.phone.set(phone);
        this.displayName.set(displayName);
        this.userEmail.set(userEmail);
        this.picture.set(picture);
        this.password.set(password);
        this.gender.set(gender);
        this.country.set(country);
        this.birthday.set(birthday);
        this.bio.set(bio);
        this.userStatus.set(userStatus);
    }

    // JavaFX Property Getters
    public LongProperty userIdProperty() { return userId; }
    public StringProperty phoneProperty() { return phone; }
    public StringProperty displayNameProperty() { return displayName; }
    public StringProperty userEmailProperty() { return userEmail; }
    public ObjectProperty<Blob> pictureProperty() { return picture; }
    public StringProperty passwordProperty() { return password; }
    public ObjectProperty<Gender> genderProperty() { return gender; }
    public ObjectProperty<Country> countryProperty() { return country; }
    public ObjectProperty<LocalDate> birthdayProperty() { return birthday; }
    public StringProperty bioProperty() { return bio; }
    public ObjectProperty<UserStatus> userStatusProperty() { return userStatus; }

    // Regular Getters
    public long getUserId() { return userId.get(); }
    public String getPhone() { return phone.get(); }
    public String getDisplayName() { return displayName.get(); }
    public String getUserEmail() { return userEmail.get(); }
    public Blob getPicture() { return picture.get(); }
    public String getPassword() { return password.get(); }
    public Gender getGender() { return gender.get(); }
    public Country getCountry() { return country.get(); }
    public LocalDate getBirthday() { return birthday.get(); }
    public String getBio() { return bio.get(); }
    public UserStatus getUserStatus() { return userStatus.get(); }

    // Regular Setters
    public void setUserId(long userId) { this.userId.set(userId); }
    public void setPhone(String phone) { this.phone.set(phone); }
    public void setDisplayName(String displayName) { this.displayName.set(displayName); }
    public void setUserEmail(String userEmail) { this.userEmail.set(userEmail); }
    public void setPicture(Blob picture) { this.picture.set(picture); }
    public void setPassword(String password) { this.password.set(password); }
    public void setGender(Gender gender) { this.gender.set(gender); }
    public void setCountry(Country country) { this.country.set(country); }
    public void setBirthday(LocalDate birthday) { this.birthday.set(birthday); }
    public void setBio(String bio) { this.bio.set(bio); }
    public void setUserStatus(UserStatus userStatus) { this.userStatus.set(userStatus); }

    public static UserJavaFXProperties fromUser(User user) {
        return new UserJavaFXProperties(
                user.getUserId(),
                user.getPhone(),
                user.getDisplayName(),
                user.getUserEmail(),
                user.getPicture(),
                user.getPassword(),
                user.getGender(),
                user.getCountry(),
                user.getBirthday(),
                user.getBio(),
                user.getUserStatus()
        );
    }

}



