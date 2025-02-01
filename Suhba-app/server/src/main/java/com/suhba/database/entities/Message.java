package com.suhba.database.entities;

import java.sql.Timestamp;

import com.suhba.database.enums.*;

public class Message {
    private int messageId;
    private long senderId;
    private long chatId;
    private String content;
    private Timestamp timeStamp;
    private MessageStatus messageStatus;
    private String attachment;

    // Constructors
    public Message() {}

    public Message(int messageId, long senderId, long chatId, String content, Timestamp timeStamp, MessageStatus messageStatus, String attachment) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.chatId = chatId;
        this.content = content;
        this.timeStamp = timeStamp;
        this.messageStatus = messageStatus;
        this.attachment = attachment;
    }

    // Getters and Setters
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", senderId=" + senderId +
                ", chatId=" + chatId +
                ", content='" + content + '\'' +
                ", timeStamp=" + timeStamp +
                ", messageStatus=" + messageStatus +
                ", attachment='" + attachment + '\'' +
                '}';
    }
}