package com.suhba.network;

import com.suhba.database.entities.*;
import com.suhba.database.enums.ContactStatus;
import com.suhba.exceptions.*;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public interface ServerClientServices extends Remote {
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

    // Contact Screen
    public boolean sendFriendRequest(String phoneNumber) throws RemoteException;
    public boolean sendFriendRequest(long userId) throws RemoteException;

    public boolean sendFriendRequests(List<String> phoneNumber) throws RemoteException;
    public boolean sendFriendRequestsById(List<Long> userId) throws RemoteException;

    public List<User> getAllPendingRequests(long userId) throws RemoteException;
    public List<User> getAllFriends(long userId) throws RemoteException;

    boolean updateRequestStatusFromPendingToAccepted(Contact contact, ContactStatus status) throws RemoteException;

    boolean updateRequestStatusFromPendingToDeclined(Contact contact, ContactStatus status) throws RemoteException;

    public boolean deleteContact(Contact contact) throws RemoteException;


    public User signup(User user) throws InvalidPhoneException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException, InvalidPasswordException, NoSuchAlgorithmException, RemoteException;

    // >> Remeber me XML SESSION >> To BE REVIEWED
    public boolean isPhoneRegistered(String phoneNumber) throws RemoteException;

    public User login(String phoneNumber, String password) throws NoSuchAlgorithmException, UserNotFoundException, IncorrectPasswordException, IOException, RemoteException;

    public boolean isSessionActive(String macAddress, long userId) throws IOException, RemoteException;

    // forget password >> To BE REVIEWED (bouns)

    // Logout >> To BE REVIEWED
    void logout(String macAddress, long userId) throws IOException, RemoteException;

    // Exit >> To BE REVIEWED
    public boolean exit() throws IOException, RemoteException;


    // Settings Screen
    public boolean updateUserProfile(User user) throws RemoteException, InvalidPhoneException, InvalidPasswordException, NoSuchAlgorithmException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException;
    public boolean updateUserPassword(long userId, String newPassword) throws RemoteException, InvalidPasswordException, NoSuchAlgorithmException; // Password must be hashed
}
