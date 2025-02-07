package com.suhba.services.client.interfaces;

import java.util.List;
import java.util.Map;

import com.suhba.database.entities.Chat;
import com.suhba.database.entities.Group;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;

public interface ChatService {

    //Common
    public List<Message> getMessages(long chatId); //(done)
    public Message sendMessage (Message msg); // (done)
    // File trasnfer handling >> To Be Searched

    // CHAT SCREEN METHODS
    
    public long createPrivateChat(long userId1, long userId2) throws Exception;  //(done)
    
    public Map <User, Message> getUserChats(long userId) ; //(done)
    public User getUserById(long userId); //(done)
    public Chat getDirectChatBetween(long currentUserId, long otherUserId); //(done)
    public User getPrivateUserPartnerByChat(long chatId, long userId) throws Exception;  // (done)


    // GROUP SCREEEN
    public Group createGroupChat(Group group, List<Long> userId) throws Exception; //(done)

    public Map<Group, Message> getUserGroups(long userId); // (done)
    public List<User> getGroupMembers(long groupId) throws Exception; // (done)
    public Group getGroupByChat(long chatId) throws Exception; // (done)
    public Group getGroupByGroupId(long groupId);

    public boolean addUsersToGroup(long groupId, List<Long> userId) throws Exception; //(done)
    public boolean removeUsersFromGroup(long chatId, List<Long> userId);

    // public boolean leaveGroup(long userId, long chatId);

    // public List<Chat> getUserChats(long userId) ;
    // public long createPrivateChat(long userId);  ---> I need the userId and the other

}
