package com.suhba.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Chat {
    // Unique identifier for the chat
    private final StringProperty id = new SimpleStringProperty();
    
    // The other participant in the chat
    private final ObjectProperty<User> participant = new SimpleObjectProperty<>();
    
    // List of messages in the chat
    private final ObservableList<Message> messages = FXCollections.observableArrayList();
    
    // Count of unread messages
    private final IntegerProperty unreadCount = new SimpleIntegerProperty(0);
    
    // The last message in the chat (read-only for external access)
    private final ReadOnlyObjectWrapper<Message> lastMessage = new ReadOnlyObjectWrapper<>();
    
    // Track if this chat is currently active/selected
    private final BooleanProperty isActive = new SimpleBooleanProperty(false);

    public Chat(String id, User participant) {
        this.id.set(id);
        this.participant.set(participant);

        // Listen for message list changes
        messages.addListener((ListChangeListener.Change<? extends Message> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    // Update last message
                    lastMessage.set(messages.get(messages.size() - 1));
                    
                    // Increment unread count if chat isn't active
                    if (!isActive.get()) {
                        incrementUnreadCount();
                    }
                }
            }
        });

        // Listen for participant's online status changes
        participant.onlineStatusProperty().addListener((obs, oldStatus, newStatus) -> {
            System.out.println("Participant " + participant.getUsername() + 
                             " changed status from " + oldStatus + " to " + newStatus);
        });
    }

    // Property accessors
    public String getId() { return id.get(); }
    public StringProperty idProperty() { return id; }

    public User getParticipant() { return participant.get(); }
    public ObjectProperty<User> participantProperty() { return participant; }

    public ObservableList<Message> getMessages() { return messages; }

    public int getUnreadCount() { return unreadCount.get(); }
    public IntegerProperty unreadCountProperty() { return unreadCount; }

    public Message getLastMessage() { return lastMessage.get(); }
    public ReadOnlyObjectProperty<Message> lastMessageProperty() { return lastMessage.getReadOnlyProperty(); }

    public boolean isActive() { return isActive.get(); }
    public BooleanProperty isActiveProperty() { return isActive; }
    public void setActive(boolean active) { isActive.set(active); }

    // Business logic methods
    public void incrementUnreadCount() {
        unreadCount.set(unreadCount.get() + 1);
    }

    public void resetUnreadCount() {
        unreadCount.set(0);
    }
}