package com.suhba.services.client.interfaces;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.suhba.database.entities.Chat;
import com.suhba.database.entities.Group;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.database.enums.ChatType;
import com.suhba.network.ClientInterface;

public interface ChatService {

    //////////////////////////////////////////////////////////////////
    public void registerToReceiveMessages(long userId, ClientInterface client );
    public void unregisterToReceive(long userId);
    
    public Chat getChatById(long chatId) throws Exception;
    /////////////////////////////////////////////////////////////////

    //Common
    public List<Message> getMessages(long chatId) throws RemoteException; //(done)
    public Message sendMessage (Message msg) throws RemoteException; // (done)
    // File trasnfer handling >> To Be Searched

    // CHAT SCREEN METHODS
    
    public long createPrivateChat(long userId1, long userId2) throws Exception, RemoteException;  //(done)
    
    public Map <User, Message> getUserChats(long userId) throws RemoteException ; //(done)
    public User getUserById(long userId) throws RemoteException; //(done)
    public Chat getDirectChatBetween(long currentUserId, long otherUserId) throws RemoteException; //(done)
    public User getPrivateUserPartnerByChat(long chatId, long userId) throws Exception, RemoteException;  // (done)


    // GROUP SCREEEN
    public Group createGroupChat(Group group, List<Long> userId) throws Exception, RemoteException; //(done)

    public Map<Group, Message> getUserGroups(long userId) throws RemoteException; // (done)
    public List<User> getGroupMembers(long groupId) throws Exception, RemoteException; // (done)
    public Group getGroupByChat(long chatId) throws Exception, RemoteException; // (done)
    public Group getGroupByGroupId(long groupId) throws RemoteException;

    public boolean addUsersToGroup(long groupId, List<Long> userId) throws Exception, RemoteException; //(done)
    public boolean removeUsersFromGroup(long chatId, List<Long> userId) throws RemoteException;

    // public boolean leaveGroup(long userId, long chatId);

    // public List<Chat> getUserChats(long userId) ;
    // public long createPrivateChat(long userId);  ---> I need the userId and the other

}
