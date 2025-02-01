package com.suhba.daos.interfaces;

import java.util.List;

import com.suhba.database.entities.*;
import com.suhba.database.enums.ChatType;

public interface ChatDAO {
	
	//Create New Chat 
	Chat createChat(ChatType type);

	// Get Chats for user By Id
	List<Chat> getChatsByUserId( int userId );

	// Get User in chat
	User getChatParticipant(User user1, long chatId );

	// Get chat By id
	Chat getChatById(long chatId);

	

	// String getChatName(int chatId);
	
	// boolean addNewChat(User u1, User u2);
	// int getUnreadMessagesCountByChatId(int chatId);
	// 	/*{
	// 	getUnreadMessages // From MessageDAO
	// 	.filter(chatId)
	// 	.count()
	// 	}*/

}