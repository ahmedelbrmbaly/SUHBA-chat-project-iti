package com.suhba.models;

import com.suhba.models.enums.OnlineStatus;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

// Model for User Data
// User have (name , image, status) That we want to be up to date with it
public class User {
    private final StringProperty username = new SimpleStringProperty();
    private final ObjectProperty<Image> avatar = new SimpleObjectProperty<>();
    private final ObjectProperty<OnlineStatus> onlineStatus = new SimpleObjectProperty<>(OnlineStatus.OFFLINE);

    public User(String username, Image avatar) {
        this.username.set(username);
        this.avatar.set(avatar);
    }

    public User(String username) {
        this.username.set(username);
    }
    // Username property
    public String getUsername() { return username.get(); }
    public void setUsername(String username) { this.username.set(username); }
    public StringProperty usernameProperty() { return username; }

    // Avatar property
    public Image getAvatar() { return avatar.get(); }
    public void setAvatar(Image avatar) { this.avatar.set(avatar); }
    public ObjectProperty<Image> avatarProperty() { return avatar; }

    // Online status property
    public OnlineStatus getOnlineStatus() { return onlineStatus.get(); }
    public void setOnlineStatus(OnlineStatus status) { this.onlineStatus.set(status); }
    public ObjectProperty<OnlineStatus> onlineStatusProperty() { return onlineStatus; }

    // Helper method for UI binding
    public Paint getStatusColor() {
        return Color.web(onlineStatus.get().getColorCode());
    }
}