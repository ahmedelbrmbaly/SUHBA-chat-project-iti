package com.suhba.database.entities;

public class Group {
    private long groupId;
    private String groupName;
    private String groupPhoto;
    private String groupDescription;
    private String category;
    private long chatId;


    public Group(String groupName) {
        this.groupName = groupName;

    }

    // Constructor without groupId (for insertion)
    public Group(String groupName, String groupPhoto, String groupDescription, String category) {
        this.groupName = groupName;
        this.groupPhoto = groupPhoto;
        this.groupDescription = groupDescription;
        this.category = category;

    }

    // Constructor with all fields
    public Group(long groupId, String groupName, String groupPhoto, String groupDescription, String category, long chatId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupPhoto = groupPhoto;
        this.groupDescription = groupDescription;
        this.category = category;
        this.chatId = chatId;
    }

    // Getters and Setters
    public long getGroupId() { return groupId; }
    public void setGroupId(long groupId) { this.groupId = groupId; } // no need to use maybe removed
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public String getGroupPhoto() { return groupPhoto; }
    public void setGroupPhoto(String groupPhoto) { this.groupPhoto = groupPhoto; }
    public String getGroupDescription() { return groupDescription; }
    public void setGroupDescription(String groupDescription) { this.groupDescription = groupDescription; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public long getChatId() { return chatId; }
    public void setChatId(long chatId) { this.chatId = chatId; }
}
