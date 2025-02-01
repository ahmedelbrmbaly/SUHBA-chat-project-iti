public interface MessageDAO {
	boolean addNewMessage(Message);
	
	List<Message> getAllMessagesByChatId(int chatId); 
	List<Message> getAllMessagedByGroupId(int groupId);

	Message getLastMessageByChatId(int chatId);
	Message getLastMessageByGroupId(int groupId);  //content+time

	String getMessageStatusById(int id);
	List<Message> getUnreadMessages();
	
	
	//Future Work:
	// Delete Messages+Update

	
}