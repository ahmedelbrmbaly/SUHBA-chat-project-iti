public class ChatDTO {
    private long chatId;
    private String chatType; // "Direct" or "Group"
    private String chatName; // Derived from participants (e.g., "John, Alice")
    private List<UserDTO> participants; // Members in the chat
    private MessageDTO lastMessage; // Last message preview

    // Getters and Setters
    public long getChatId() { return chatId; }
    public void setChatId(long chatId) { this.chatId = chatId; }

    public String getChatType() { return chatType; }
    public void setChatType(String chatType) { this.chatType = chatType; }

    public String getChatName() { return chatName; }
    public void setChatName(String chatName) { this.chatName = chatName; }

    public List<UserDTO> getParticipants() { return participants; }
    public void setParticipants(List<UserDTO> participants) { this.participants = participants; }

    public MessageDTO getLastMessage() { return lastMessage; }
    public void setLastMessage(MessageDTO lastMessage) { this.lastMessage = lastMessage; }
}