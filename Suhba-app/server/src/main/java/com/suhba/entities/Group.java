public class Group {
    private long groupId;
    private String groupName;
    private String groupPhoto;
    private String groupDescription;
    private String category;
    private long chatId;

    // Getters
    public long getGroupId() { return groupId; }
    public String getGroupName() { return groupName; }
    public String getGroupPhoto() { return groupPhoto; }
    public String getGroupDescription() { return groupDescription; }
    public String getCategory() { return category; }
    public long getChatId() { return chatId; }

    // Setters
    public void setGroupId(long groupId) { this.groupId = groupId; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public void setGroupPhoto(String groupPhoto) { this.groupPhoto = groupPhoto; }
    public void setGroupDescription(String groupDescription) { 
        this.groupDescription = groupDescription; 
    }
    public void setCategory(String category) { this.category = category; }
    public void setChatId(long chatId) { this.chatId = chatId; }
}