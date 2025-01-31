package com.suhba.daos.interfaces;

import com.suhba.entities.Chat;
import com.suhba.exceptions.DAOException;
import com.suhba.enums.ChatType;

public interface ChatDAO {
    // Chat Management
    long createChat(ChatType chatType) throws DAOException; // Returns new chatId
    Chat getChatById(long chatId) throws DAOException;
    List<Chat> getChatsByUserId(int userId) throws DAOException; // All chats for a user
    
    // Participants
    void addUserToChat(int userId, long chatId) throws DAOException;
    void removeUserFromChat(int userId, long chatId) throws DAOException;
    List<Integer> getChatParticipants(long chatId) throws DAOException; // List of userIds
}