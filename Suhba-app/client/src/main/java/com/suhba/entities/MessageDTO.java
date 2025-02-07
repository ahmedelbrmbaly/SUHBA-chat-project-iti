public class MessageDTO {
    private long messageId;
    private int senderId;
    private String senderName; // Derived from UserDTO.displayName
    private String content;
    private Map<String, String> formatAttributes; // Parsed JSON (font, color, etc.)
    private LocalDateTime timestamp;
    private String messageStatus; // From MessageStatus enum
    private String attachmentUrl; // URL for downloaded files

    // Getters and Setters
    public long getMessageId() { return messageId; }
    public void setMessageId(long messageId) { this.messageId = messageId; }

    public int getSenderId() { return senderId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Map<String, String> getFormatAttributes() { return formatAttributes; }
    public void setFormatAttributes(Map<String, String> formatAttributes) { 
        this.formatAttributes = formatAttributes; 
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getMessageStatus() { return messageStatus; }
    public void setMessageStatus(String messageStatus) { this.messageStatus = messageStatus; }

    public String getAttachmentUrl() { return attachmentUrl; }
    public void setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; }
}