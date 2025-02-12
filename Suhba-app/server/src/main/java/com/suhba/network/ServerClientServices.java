package com.suhba.network;

import java.io.IOException;
import java.net.SocketException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.suhba.database.entities.*;
import com.suhba.database.enums.ContactStatus;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import com.suhba.exceptions.*;


public interface ServerClientServices extends Remote {

    //////////////////////////////////////////////////////////////////
    // missing import >>>>
    public void registerToReceiveMessages(long userId, ClientInterface client )throws RemoteException;
    public void unregisterToReceive(long userId) throws RemoteException;

    public Chat getChatById(long chatId) throws RemoteException;

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



    // Contact Screen


    public boolean sendFriendRequest(long userId1, long userId2) throws RemoteException;

    public boolean sendFriendRequestsById(long userId1, List<Long> userId) throws RemoteException;



    boolean updateRequestStatusFromPendingToAccepted(Contact contact, ContactStatus status) throws RemoteException;

    boolean updateRequestStatusFromPendingToDeclined(Contact contact, ContactStatus status) throws RemoteException;

    public boolean deleteContact(Contact contact) throws RemoteException;
   // public long registerAndGetUserId(String phone, String email, String password) throws InvalidPhoneException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException, InvalidPasswordException, NoSuchAlgorithmException;

    // Signup & Signin Screens & Logout

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

    public String getMacAddress() throws RemoteException, SocketException;

    public boolean saveFirstPart(String phone,String email, String password) throws RemoteException, InvalidPhoneException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException, InvalidPasswordException, NoSuchAlgorithmException;

    public void saveLastPart(String name, Gender gender, LocalDate DOB, Country country, byte[] picture) throws RemoteException;
    public List<Long> getUserIdsByPhones(List<String> phones) throws RemoteException;

//////////////////////////////////////////////////////////////////////////////

    // Contact Screen
    public boolean sendFriendRequest(String phoneNumber) throws RemoteException;
    public boolean sendFriendRequest(long userId) throws RemoteException;

    public boolean sendFriendRequests(List<String> phoneNumber) throws RemoteException;
    public boolean sendFriendRequestsById(List<Long> userId) throws RemoteException;

    public List<User> getAllPendingRequests(long userId) throws RemoteException;
    public List<User> getAllFriends(long userId) throws RemoteException;

    public boolean updateRequestStatus(long userId, ContactStatus status ) throws RemoteException;

    public boolean deleteContact(long userId) throws RemoteException;

//////////////////////////////////////////////////////////////////////////////

    // Settings Screen

    public boolean updateUserProfile(User user) throws RemoteException, InvalidPhoneException, InvalidPasswordException, NoSuchAlgorithmException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException;
    public boolean updateUserPassword(long userId, String newPassword) throws RemoteException, InvalidPasswordException, NoSuchAlgorithmException; // Password must be hashed
    public boolean isEmailRegistered(String phoneNumber) throws RemoteException;



    public long getUserIdByEmail(String email) throws RemoteException;
    public long getUserIdByPhone(String phone) throws RemoteException ;


    public User getUserByPhoneNumber(String phoneNumber) throws RemoteException;

    public boolean isPasswordMatchUser (long userId, String password) throws RemoteException, NoSuchAlgorithmException;

    // Announcemnt

    public void showAnnouncement(String message) throws RemoteException;
    public void register(ClientService client) throws RemoteException;
    public void unregister(ClientService client) throws RemoteException;
    void notifyServerShutdown() throws RemoteException;


    void notifyClientsShutdown() throws RemoteException;
}
