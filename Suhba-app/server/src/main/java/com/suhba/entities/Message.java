public class Message {
    private long messageId;
    private int senderId;
    private long chatId;
    private String content;
    private String formatAttributes;
    private Timestamp timeStamp;
    private MessageStatus messageStatus;
    private String attachment;

    // Getters
    public long getMessageId() { return messageId; }
    public int getSenderId() { return senderId; }
    public long getChatId() { return chatId; }
    public String getContent() { return content; }
    public String getFormatAttributes() { return formatAttributes; }
    public Timestamp getTimeStamp() { return timeStamp; }
    public MessageStatus getMessageStatus() { return messageStatus; }
    public String getAttachment() { return attachment; }

    // Setters
    public void setMessageId(long messageId) { this.messageId = messageId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }
    public void setChatId(long chatId) { this.chatId = chatId; }
    public void setContent(String content) { this.content = content; }
    public void setFormatAttributes(String formatAttributes) { 
        this.formatAttributes = formatAttributes; 
    }
    public void setTimeStamp(Timestamp timeStamp) { this.timeStamp = timeStamp; }
    public void setMessageStatus(MessageStatus messageStatus) { 
        this.messageStatus = messageStatus; 
    }
    public void setAttachment(String attachment) { this.attachment = attachment; }
}