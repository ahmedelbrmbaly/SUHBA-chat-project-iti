package com.suhba.daos.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.suhba.database.entities.*;

public interface ChatDAO {


	// Direct Chat Operations
    Chat createDirectChat(User user1, User user2) throws SQLException;
    boolean hasDirectChatBetween(User user1, User user2) throws SQLException;
    User getDirectChatPartner(long chatId, User currentUser) throws SQLException;

    // Group Chat Operations
    // Chat createGroupChat(String groupName, User creator, List<User> initialParticipants) throws SQLException;
    // void addUsersToGroup(long chatId, List<User> users) throws SQLException;
    // void removeUserFromGroup(long chatId, User user) throws SQLException;
    // void updateGroupInfo(long chatId, String groupName, String description) throws SQLException;

    // Common Operations
    List<Chat> getChatsByUser(long userId) throws SQLException;
    // List<User> getChatParticipants(long chatId) throws SQLException;
    Chat getChatById(long chatId) throws SQLException;
    boolean isUserInChat(long userId, long chatId) throws SQLException;
    // void deleteChat(long chatId) throws SQLException;
	
	// //Create New Chat 
	// Chat createChat(ChatType type, User user, User user2);

	// // Get Chats for user By Id
	// List<Chat> getChatsByUserId( long userId );

	// // Get User in chat
	// User getChatParticipant(User user1, long chatId );

	// // Get chat By id
	// Chat getChatById(long chatId);

	// // Check if those users have the same chatId before or not!
	// boolean haveChatBefore(User user1, User user2);

	

	// // String getChatName(int chatId);
	
	// // boolean addNewChat(User u1, User u2);
	// // int getUnreadMessagesCountByChatId(int chatId);
	// // 	/*{
	// // 	getUnreadMessages // From MessageDAO
	// // 	.filter(chatId)
	// // 	.count()
	// // 	}*/

}