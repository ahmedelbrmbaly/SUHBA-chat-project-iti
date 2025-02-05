package com.suhba.network;

import java.util.List;
import java.util.Map;

import com.suhba.database.entities.Group;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.database.enums.ContactStatus;

public interface ServerClientServices {
    // 1- Signup (Take User Info and return the user id)
    //DTOS
    // Upload the photo picture  >> how

    public User signup(User user);
    
    // >> Remeber me XML SESSION >> To BE REVIEWED
    public boolean isPhoneRegistered(String phoneNumder);

    public User login(String phoneNumder , String password) ;

    public boolean isSessionActive(String macAddress, long userId);

    // forget password >> To BE REVIEWED (bouns)

    // Logout >> To BE REVIEWED
    public boolean logout();

    // Exit >> To BE REVIEWED
    public boolean exit();

/////////////////////////////////////////////////////////////////////////////////

    // CHAT SCREEN METHODS

    // public List<Chat> getUserChats(long userId) ;
    public long createPrivateChat(long userId);

    public  Map <User, Message> getUserChats(long userId) ;


    // Message Oject needs sender name >>>
    public List<Message> getMessages(long chatId);
    public  User getUserById(long userId);
    public User getPrivateUserPartnerByChat(long chatId);

    // File trasnfer handling >> To Be Searched
    public Message sendMessage (Message msg);



//////////////////////////////////////////////////////////////////////////////

    // GROUP SCREEEN

    public Map<Group, Message> getUserGroups(long userId);
    public List<User> getGroupMembers(long groupId);
//    public List<Message> getMessages(long chatId); >> Method defined in chat above
    //public Message sendMessage (Message msg); >> Method defined in chat above
    public Group createGroupChat(Group group, List<Long> userId);
    public Group getGroupByChat(long chatId);
    public boolean addUsersToGroup(long chatId, List<Long> userId);
    public boolean removeUsersFromGroup(long chatId, List<Long> userId);

//////////////////////////////////////////////////////////////////////////////

    // Contact Screen
    public boolean sendFriendRequest(String phoneNumber);
    public boolean sendFriendRequest(long userId);

    public boolean sendFriendRequests(List<String> phoneNumber);
    public boolean sendFriendRequestsById(List<Long> userId);

    public List<User> getAllPendingRequests(long userId);
    public List<User> getAllFriends(long userId);

    public boolean updateRequestStatus(long userId, ContactStatus status );

    public boolean deleteContact(long userId);

//////////////////////////////////////////////////////////////////////////////

    // Settings Screen
    public boolean updateUserProfile(User user);
    public boolean updateUserPassword(String newPassword); // Password must be hashed


//////////////////////////////////////////////////////////////////////////////


}
