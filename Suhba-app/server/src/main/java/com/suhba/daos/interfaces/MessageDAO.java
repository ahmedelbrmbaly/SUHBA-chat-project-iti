package com.suhba.daos.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.suhba.database.entities.*;
import com.suhba.database.enums.MessageStatus;

public interface MessageDAO {

	Message sendMessage(Message message) throws SQLException;
	List<Message> getChatMessages(long chatId) throws SQLException;
	void updateMessageStatus(long messageId, MessageStatus status) throws SQLException;
	int getUnreadCount(long userId, long chatId) throws SQLException;
	
	Message getLastMessageInChat(long chatId) throws SQLException; // Optional


	//Future Work:
	// Delete Messages+Update
	// getAllMessagesWithAttachments

	
}
