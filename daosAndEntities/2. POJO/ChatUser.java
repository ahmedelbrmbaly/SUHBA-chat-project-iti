public class ChatUser {
    private long chatId;
    private long userId;

    // Constructors
    public ChatUser() {} // Default constructor

    public ChatUser(int userId, long chatId) {
        this.userId = userId;
        this.chatId = chatId;
    }

    // Getters
    public long getUserId() {
        return userId;
    }

    public long getChatId() {
        return chatId;
    }

    // Setters
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    // toString() for debugging
    @Override
    public String toString() {
        return "ChatUser{" +
                "userId=" + userId +
                ", chatId=" + chatId +
                '}';
    }
}
