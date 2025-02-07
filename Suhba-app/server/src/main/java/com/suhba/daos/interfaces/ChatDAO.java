package com.suhba.daos.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.suhba.database.entities.*;

public interface ChatDAO {


	// Direct Chat Operations
    Chat createDirectChat(User user1, User user2) throws SQLException;
    boolean hasDirectChatBetween(User user1, User user2) throws SQLException;
	boolean hasDirectChatBetween(long user1, long user2) throws SQLException;
    User getDirectChatPartner(long chatId, long currentUser) throws SQLException;
	Chat getDirectChatPartnerByUserId(long userId, long secondUserId) throws SQLException;
	// boolean isDirectChat(long chatId);

	// Common Operations
	Chat getChatById(long chatId) throws SQLException;
	List<Chat> getChatsByUser(long userId) throws SQLException;
	boolean isUserInChat(long userId, long chatId) throws SQLException;
	List<User> getChatParticipants(long chatId) throws SQLException;
	List<Chat> getAllDirectChatsByUserId(long userId) throws SQLException;
	List<Chat> getAllGroupChatsByUserId(long userId) throws SQLException;

	// Group Chat Operations
	// 	Chat createGroupChat(Group group) throws SQLException;
	boolean addUsersToChat(long chatId, List<Long> userIds) throws SQLException;
	// 	void removeUserFromChat(long chatId, int userId) throws SQLException;
    
}

