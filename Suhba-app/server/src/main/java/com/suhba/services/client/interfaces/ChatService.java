package com.suhba.services.client.interfaces;

import java.util.List;
import java.util.Map;

import com.suhba.database.entities.Group;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;

public interface ChatService {

    //Common
    public List<Message> getMessages(long chatId); 
    public Message sendMessage (Message msg); 
    // File trasnfer handling >> To Be Searched

    // CHAT SCREEN METHODS
    // public List<Chat> getUserChats(long userId) ;
    public long createPrivateChat(long userId);
    public  Map <User, Message> getUserChats(long userId) ; // Message Oject needs sender name >>>
    public  User getUserById(long userId);
    public User getPrivateUserPartnerByChat(long chatId);

    // GROUP SCREEEN
    public Map<Group, Message> getUserGroups(long userId);
    public List<User> getGroupMembers(long groupId);
    public Group createGroupChat(Group group, List<Long> userId);
    public Group getGroupByChat(long chatId);
    public boolean addUsersToGroup(long chatId, List<Long> userId);
    public boolean removeUsersFromGroup(long chatId, List<Long> userId);

}
