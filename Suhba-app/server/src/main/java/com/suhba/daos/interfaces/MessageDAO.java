package com.suhba.daos.interfaces;

import com.suhba.entities.Message;
import com.suhba.exceptions.DAOException;
import com.suhba.enums.MessageStatus;

public interface MessageDAO {
    // Messaging
    void sendMessage(Message message) throws DAOException;
    Message getMessageById(long messageId) throws DAOException;
    List<Message> getMessagesByChatId(long chatId) throws DAOException; // Chat history
    
    // Status Updates
    void updateMessageStatus(long messageId, MessageStatus status) throws DAOException;
    void deleteMessage(long messageId) throws DAOException;
    
    // Offline Support
    List<Message> getPendingMessages(int userId) throws DAOException; // Undelivered messages
}