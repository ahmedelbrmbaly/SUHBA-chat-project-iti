import java.sql.Timestamp;

public class Message {
    private long messageId;
    private long senderId;
    private String content;
    private Timestamp timeStamp;
    private String messageStatus;
    private String attachment;

    // Getters and Setters
    public long getMessageId() { return messageId; }
    public void setMessageId(long messageId) { this.messageId = messageId; }
    public long getSenderId() { return senderId; }
    public void setSenderId(long senderId) { this.senderId = senderId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Timestamp getTimeStamp() { return timeStamp; }
    public void setTimeStamp(Timestamp timeStamp) { this.timeStamp = timeStamp; }
    public String getMessageStatus() { return messageStatus; }
    public void setMessageStatus(String messageStatus) { this.messageStatus = messageStatus; }
    public String getAttachment() { return attachment; }
    public void setAttachment(String attachment) { this.attachment = attachment; }
}