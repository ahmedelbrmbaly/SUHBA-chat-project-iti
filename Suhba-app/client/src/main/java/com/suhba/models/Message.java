package com.suhba.models;

import javafx.beans.property.*;
import java.time.LocalDateTime;

// Model for Message Data
// Each message have (id, sender, content, timestamp)
public class Message {
    private final StringProperty id = new SimpleStringProperty();
    private final ObjectProperty<User> sender = new SimpleObjectProperty<>();
    private final StringProperty content = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> timestamp = new SimpleObjectProperty<>();

    public Message(String id, User sender, String content) {
        this.id.set(id);
        this.sender.set(sender);
        this.content.set(content);
        this.timestamp.set(LocalDateTime.now());
    }

    public Message(User sender, String content){
        this.sender.set(sender);
        this.content.set(content);
        this.timestamp.set(LocalDateTime.now());
    }

    public Message(String content) {
        this.content.set(content);
        this.timestamp.set(LocalDateTime.now());
    }

    // ID property
    public String getId() { return id.get(); }
    // public void setId(String id) { this.id.set(id); }
    public StringProperty idProperty() { return id; }

    // Sender property
    public User getSender() { return sender.get(); }
    public void setSender(User sender) { this.sender.set(sender); }
    public ObjectProperty<User> senderProperty() { return sender; }

    // Content property
    public String getContent() { return content.get(); }
    public void setContent(String content) { this.content.set(content); }
    public StringProperty contentProperty() { return content; }

    // Timestamp property
    public LocalDateTime getTimestamp() { return timestamp.get(); }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp.set(timestamp); }
    public ObjectProperty<LocalDateTime> timestampProperty() { return timestamp; }
}
