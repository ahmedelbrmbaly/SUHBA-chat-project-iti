public class Chat {
    private long chatId;
    private ChatType chatType;

    // Getters
    public long getChatId() { return chatId; }
    public ChatType getChatType() { return chatType; }

    // Setters
    public void setChatId(long chatId) { this.chatId = chatId; }
    public void setChatType(ChatType chatType) { this.chatType = chatType; }
}