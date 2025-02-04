package com.suhba.network;

import java.util.List;

import com.suhba.database.entities.Chat;
import com.suhba.database.entities.Group;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;

public interface ServerServices {
    // 1- Signup (Take User Info and return the user id)
    public int signup(User user);
    
    // 2- Log in
    public User login(String phoneNumder , String password) ;

    // 3- Logout
    public void logout();

/////////////////////////////////////////////////////////////////////////////////

    // 4- Load user chats
    public List<Chat> getUserChats(long userId) ;

    // 5- Load user Groups
    public List<Group> getUserGroups(long userId);

    // 6- Open user-user Chat and load Messages
    public List<Message> getChatMessages(long chatId);

    // 7- Open user-group Chat and load Messages
    public List<Message> getGroupChatMessages(long groupId);

//////////////////////////////////////////////////////////////////////////////

    // 8- Send Message user-user 
    public long sendChatMessage (Message msg);

    // 9- Send Message user-group
    public long sendGroupMessage(Message msg) ;

//////////////////////////////////////////////////////////////////////////////

    // 10- Create chat with user
    public long createPrivateChat(Chat chat);//?

    // 11- Create chat with group
    public long createGroupChat(Chat chat, List<User> users);

    // 12- Add users to group
    public void addUsersToGroup(long chatId, List<User> users);

//////////////////////////////////////////////////////////////////////////////

    // 13- get chat by id
    public Chat getChat(long chatId);


    // 14- get another user by chat id
    public User getPrivateUserPartnerByChat(long chatId);

//////////////////////////////////////////////////////////////////////////////

    // 15- get group by chat id
    public Group getGroupByChat(long chatId);

    // 16- get group members by group id (Or by chat id?)
    public List<User> getGroupMembers(long groupId);

    // 17- get 

}
