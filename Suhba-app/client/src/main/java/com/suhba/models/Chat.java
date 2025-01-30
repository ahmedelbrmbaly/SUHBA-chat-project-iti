package com.suhba.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


// Model for Chat Data
// Each chat have : id, Participant, messages, unreadCount,

public class Chat {
    private final StringProperty id = new SimpleStringProperty();
    private final ObjectProperty<User> participant = new SimpleObjectProperty<>();
    private final ObservableList<Message> messages = FXCollections.observableArrayList();
    private final IntegerProperty unreadCount = new SimpleIntegerProperty(0);

    public Chat(String id, User participant) {
        this.id.set(id);
        this.participant.set(participant);
        
        // Listen for participant's online status changes
        participant.onlineStatusProperty().addListener((obs, oldStatus, newStatus) -> {
            System.out.println("Participant " + participant.getUsername() + 
                             " changed status from " + oldStatus + " to " + newStatus);
        });
    }

    // ID property
    public String getId() { return id.get(); }
    public StringProperty idProperty() { return id; }

    // Participant property
    public User getParticipant() { return participant.get(); }
    public ObjectProperty<User> participantProperty() { return participant; }

    // Messages list
    public ObservableList<Message> getMessages() { return messages; }

    // Unread count property
    public int getUnreadCount() { return unreadCount.get(); }
    public IntegerProperty unreadCountProperty() { return unreadCount; }

    public void incrementUnreadCount() {
        unreadCount.set(unreadCount.get() + 1);
    }

    public void resetUnreadCount() {
        unreadCount.set(0);
    }
}