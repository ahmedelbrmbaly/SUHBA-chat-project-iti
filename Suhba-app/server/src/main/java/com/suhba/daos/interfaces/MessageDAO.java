package com.suhba.daos.interfaces;

import java.util.List;

import com.suhba.database.entities.*;
import com.suhba.database.enums.MessageStatus;

public interface MessageDAO {


	// boolean addNewMessage(Message);
	
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