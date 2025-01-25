public interface ChatDAO {
	
	List<Chat> getAllChatsByUserId( int id );
	String getChatName(int chatId);
	
	boolean addNewChat(User u1, User u2);
	int getUnreadMessagesCountByChatId(int chatId)
		/*{
		getUnreadMessages // From MessageDAO
		.filter(chatId)
		.count()
		}*/

}