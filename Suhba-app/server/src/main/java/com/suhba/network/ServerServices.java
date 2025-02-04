package com.suhba.network;

import java.util.List;

import com.suhba.database.entities.Chat;
import com.suhba.database.entities.Group;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;

public interface ServerServices {
    // 1- Signup (Take User Info and return the user id)
    //DTOS
    // Upload the photo picture  >> how

    public User signup(User user);
    
    // 2- Log in
    // >> Remeber me XML SESSION >> To BE REVIEWED
    public boolean isPhoneRegistered(String phoneNumder);

    public User login(String phoneNumder , String password) ;

    // forget password >> To BE REVIEWED (bouns)

    // 3- Logout >> To BE REVIEWED
    public boolean logout();

    // Exit >> To BE REVIEWED
    public boolean exit();

/////////////////////////////////////////////////////////////////////////////////

// CHAT SCREEN METHODS

    // 4- Load user chats
//    public List<Chat> getUserChats(long userId) ;
    public long createPrivateChat(Long userId);

    public  Map <User, Message> getUserChats(long userId) ;
    // 5- Load user Groups


    // 6- Open user-user Chat and load Messages
    // Message Oject needs sender name >>>
    public List<Message> getMessages(long chatId);
    public  User getUserById(long userId);
    public User getPrivateUserPartnerByChat(long chatId);

    // 8- Send Message user-user
    // File trasnfer handling >> To Be Searched
    public Message sendMessage (Message msg);



//////////////////////////////////////////////////////////////////////////////

    // GRUUP SCREEEN

    public Map <Group, Message> getUserGroups(long userId);
    public List<User> getGroupMembers(long groupId);
//    public List<Message> getMessages(long chatId); >> Method defined in chat above
    //public Message sendMessage (Message msg); >> Method defined in chat above
    public Group createGroupChat(Group group, List<Long> userID);
    public Group getGroupByChat(long chatId);
    public boolean addUsersToGroup(long chatId, List<Long> userID);
    public boolean removeUsersFromGroup(long chatId, List<Long> userID);

//////////////////////////////////////////////////////////////////////////////




    // 10- Create chat with user
    //?

    // 11- Create chat with group


    // 12- Add users to group


//////////////////////////////////////////////////////////////////////////////




    // 14- get another user by chat id


//////////////////////////////////////////////////////////////////////////////

    // 15- get group by chat id


    // 16- get group members by group id (Or by chat id?)


    // 17- get 

}
