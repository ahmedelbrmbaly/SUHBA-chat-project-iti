package com.suhba.models;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class Message {
    // Unique message identifier
    private final StringProperty id = new SimpleStringProperty();
    
    // Message sender (User object)
    private final ObjectProperty<User> sender = new SimpleObjectProperty<>();
    
    // Message content
    private final StringProperty content = new SimpleStringProperty();
    
    // Read-only timestamp (set at creation)
    private final ReadOnlyObjectWrapper<LocalDateTime> timestamp = 
        new ReadOnlyObjectWrapper<>(LocalDateTime.now());

    // Constructors
    public Message(String id, User sender, String content) {
        this.id.set(id);
        this.sender.set(sender);
        this.content.set(content);
    }

    public Message(User sender, String content) {
        this.sender.set(sender);
        this.content.set(content);
    }

    public Message(String content) {
        this.content.set(content);
    }

    // Property accessors
    public String getId() { return id.get(); }
    public StringProperty idProperty() { return id; }
    public void setId(String id) { this.id.set(id); }

    public User getSender() { return sender.get(); }
    public ObjectProperty<User> senderProperty() { return sender; }
    public void setSender(User sender) { this.sender.set(sender); }

    public String getContent() { return content.get(); }
    public StringProperty contentProperty() { return content; }
    public void setContent(String content) { this.content.set(content); }

    public LocalDateTime getTimestamp() { return timestamp.get(); }
    public ReadOnlyObjectProperty<LocalDateTime> timestampProperty() { 
        return timestamp.getReadOnlyProperty(); 
    }
}