package com.suhba.daos.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.suhba.database.entities.*;
import com.suhba.database.enums.MessageStatus;

public interface MessageDAO {


	// boolean addNewMessage(Message);

// 	public interface MessageDAO {
//     // Message Handling
//     Message sendMessage(Message message) throws SQLException;
//     List<Message> getChatMessages(long chatId, int limit, int offset) throws SQLException;
//     void updateMessageStatus(long messageId, MessageStatus status) throws SQLException;
//     void deleteMessage(long messageId) throws SQLException;
    
//     // Attachments
//     void addAttachment(long messageId, String filePath) throws SQLException;
    
//     // Status Tracking
//     int getUnreadCount(int userId, long chatId) throws SQLException;
// }
	
	List<Message> getAllMessagesByChatId(long chatId); 

	Message sendMessage(Message msg);

	void updateMessageStatus(long msgId, MessageStatus status);

	
	// List<Message> getAllMessagedByGroupId(int groupId);

	// Message getLastMessageByChatId(int chatId);
	// Message getLastMessageByGroupId(int groupId);  //content+time

	// String getMessageStatusById(int id);
	// List<Message> getUnreadMessages();
	
	
	//Future Work:
	// Delete Messages+Update

	
}