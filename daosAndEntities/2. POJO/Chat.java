import enums.ChatType;

public class Chat {
    private long chatId;
    private ChatType chatType;

    // Constructors
    public Chat() {} // Default

    public Chat(long chatId, ChatType chatType) {
        this.chatId = chatId;
        this.chatType = chatType;
    }

    // Getters
    public long getChatId() {
        return chatId;
    }

    public ChatType getChatType() {
        return chatType;
    }

    // Setters
    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    // toString() for debugging
    @Override
    public String toString() {
        return "Chat{" +
                "chatId=" + chatId +
                ", chatType=" + chatType +
                '}';
    }
}