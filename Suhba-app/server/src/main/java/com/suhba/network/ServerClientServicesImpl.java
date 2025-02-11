package com.suhba.network;

import com.suhba.database.entities.*;
import com.suhba.database.enums.ContactStatus;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import com.suhba.exceptions.*;
import com.suhba.services.client.implementaions.ChatServiceImpl;
import com.suhba.services.client.implementaions.ContactServiceImpl;
import com.suhba.services.client.implementaions.UserAuthServiceImpl;
import com.suhba.services.client.implementaions.UserSettingServiceImpl;

import java.io.IOException;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ServerClientServicesImpl extends UnicastRemoteObject implements ServerClientServices {
    ChatServiceImpl myChatImpl;
    UserAuthServiceImpl myAuthImpl;
    UserSettingServiceImpl mySettingImpl;
    ContactServiceImpl myContactImpl;




    public static Vector<ClientService> clients = new Vector<>();

    public ServerClientServicesImpl() throws RemoteException {
        super();
        this.myChatImpl = new ChatServiceImpl();
        this.myAuthImpl = new UserAuthServiceImpl();
        this.mySettingImpl = new UserSettingServiceImpl();
        this.myContactImpl = new ContactServiceImpl();
        
    }

    @Override
    public List<Message> getMessages(long chatId) throws RemoteException {
        return myChatImpl.getMessages(chatId);
    }

    @Override
    public Message sendMessage(Message msg) throws RemoteException {
        return myChatImpl.sendMessage(msg);
    }

    @Override
    public long createPrivateChat(long userId1, long userId2) throws Exception, RemoteException {
        return myChatImpl.createPrivateChat(userId1, userId2);
    }

    @Override
    public Map<User, Message> getUserChats(long userId) throws RemoteException {
        return myChatImpl.getUserChats(userId);
    }

    @Override
    public User getUserById(long userId) throws RemoteException {
        return myChatImpl.getUserById(userId);
    }

    @Override
    public Chat getDirectChatBetween(long currentUserId, long otherUserId) throws RemoteException {
        return myChatImpl.getDirectChatBetween(currentUserId, otherUserId);
    }

    @Override
    public User getPrivateUserPartnerByChat(long chatId, long userId) throws Exception, RemoteException {
        return myChatImpl.getPrivateUserPartnerByChat(chatId, userId);
    }

    @Override
    public Group createGroupChat(Group group, List<Long> userId) throws Exception, RemoteException {
        return myChatImpl.createGroupChat(group, userId);
    }

    @Override
    public Map<Group, Message> getUserGroups(long userId) throws RemoteException {
        return myChatImpl.getUserGroups(userId);
    }

    @Override
    public List<User> getGroupMembers(long groupId) throws Exception, RemoteException {
        return myChatImpl.getGroupMembers(groupId);
    }

    @Override
    public Group getGroupByChat(long chatId) throws Exception, RemoteException {
        return myChatImpl.getGroupByChat(chatId);
    }

    @Override
    public Group getGroupByGroupId(long groupId) throws RemoteException {
        return myChatImpl.getGroupByGroupId(groupId);
    }

    @Override
    public boolean addUsersToGroup(long groupId, List<Long> userId) throws Exception, RemoteException {
        return myChatImpl.addUsersToGroup(groupId, userId);
    }

    @Override
    public boolean removeUsersFromGroup(long chatId, List<Long> userId) throws RemoteException {
        return myChatImpl.removeUsersFromGroup(chatId, userId);
    }

    @Override
    public boolean sendFriendRequest(String phoneNumber) throws RemoteException {
        return myContactImpl.sendFriendRequest(phoneNumber);
    }

    @Override
    public boolean sendFriendRequest(long userId) throws RemoteException {
        return myContactImpl.sendFriendRequest(userId);
    }

    @Override
    public boolean sendFriendRequest(long userId1, long userId2) throws RemoteException {
        return myContactImpl.sendFriendRequest(userId1, userId2);
    }

    @Override
    public boolean sendFriendRequests(List<String> phoneNumber) throws RemoteException {
        return myContactImpl.sendFriendRequests(phoneNumber);
    }

    @Override
    public boolean sendFriendRequestsById(List<Long> userId) throws RemoteException {
        return myContactImpl.sendFriendRequestsById(userId);
    }

    @Override
    public boolean sendFriendRequestsById(long userId1, List<Long> userId) throws RemoteException {
        return myContactImpl.sendFriendRequestsById(userId1, userId);
    }

    @Override
    public List<User> getAllPendingRequests(long userId) throws RemoteException {
        return myContactImpl.getAllPendingRequests(userId);
    }

    @Override
    public List<User> getAllFriends(long userId) throws RemoteException {
        return myContactImpl.getAllFriends(userId);
    }

    @Override
    public boolean updateRequestStatus(long userId, ContactStatus status)  throws RemoteException {
        return false;
    }

    @Override
    public boolean deleteContact(long userId)  throws RemoteException {
        return false;
    }

    @Override
    public boolean updateRequestStatusFromPendingToAccepted(Contact contact, ContactStatus status) throws RemoteException {
        return myContactImpl.updateRequestStatusFromPendingToAccepted(contact, status);
    }

    @Override
    public boolean updateRequestStatusFromPendingToDeclined(Contact contact, ContactStatus status) throws RemoteException {
        return myContactImpl.updateRequestStatusFromPendingToDeclined(contact, status);
    }

    @Override
    public boolean deleteContact(Contact contact) throws RemoteException {
        return myContactImpl.deleteContact(contact);
    }

    @Override
    public User signup(User user) throws InvalidPhoneException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException, InvalidPasswordException, NoSuchAlgorithmException, RemoteException {
        return myAuthImpl.signup(user);
    }

    @Override
    public boolean isPhoneRegistered(String phoneNumber) throws RemoteException {
        return myAuthImpl.isPhoneRegistered(phoneNumber);
    }

    @Override
    public User login(String phoneNumber, String password) throws NoSuchAlgorithmException, UserNotFoundException, IncorrectPasswordException, IOException, RemoteException {
        return myAuthImpl.login(phoneNumber, password);
    }

    @Override
    public boolean isSessionActive(String macAddress, long userId) throws IOException, RemoteException {
        return myAuthImpl.isSessionActive(macAddress, userId);
    }

    @Override
    public void logout(String macAddress, long userId) throws IOException, RemoteException {
        myAuthImpl.logout(macAddress, userId);
    }

    @Override
    public boolean exit() throws IOException, RemoteException {
        return myAuthImpl.exit();
    }

    @Override
    public String getMacAddress() throws RemoteException, SocketException {
        return myAuthImpl.getMacAddress();
    }

    @Override
    public boolean saveFirstPart(String phone, String email, String password) throws RemoteException, InvalidPhoneException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException, InvalidPasswordException, NoSuchAlgorithmException {
        return myAuthImpl.saveFirstPart(phone, email, password);
    }

    @Override
    public void saveLastPart(String name, Gender gender, LocalDate DOB, Country country, byte[] picture) throws RemoteException{
        myAuthImpl.saveLastPart(name, gender, DOB, country, picture);
    }

    @Override
    public List<Long> getUserIdsByPhones(List<String> phones) throws RemoteException {
        return myAuthImpl.getUserIdsByPhones(phones);
    }

    @Override
    public boolean updateUserProfile(User user) throws RemoteException, InvalidPhoneException, InvalidPasswordException, NoSuchAlgorithmException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException {
        return mySettingImpl.updateUserProfile(user);
    }

    @Override
    public boolean updateUserPassword(long userId, String newPassword) throws RemoteException, InvalidPasswordException, NoSuchAlgorithmException {
        return mySettingImpl.updateUserPassword(userId, newPassword);
    }

    @Override
    public void registerToReceiveMessages(long userId, ClientInterface client) {
        myChatImpl.registerToReceiveMessages(userId, client);
    }

    @Override
    public void unregisterToReceive(long userId) {
        myChatImpl.unregisterToReceive(userId);
    }

    @Override
    public Chat getChatById(long chatId){
        return myChatImpl.getChatById(chatId);
    }


    public User getUserByPhoneNumber(String phoneNumber) throws RemoteException {
        return myAuthImpl.getUserByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean isPasswordMatchUser(long userId, String password) throws RemoteException, NoSuchAlgorithmException {
        return myAuthImpl.isPasswordMatchUser(userId, password);
    }

    public synchronized void register(ClientService client) throws RemoteException {
        if (!clients.contains(client)) {
            if (client == null) {
                System.err.println("ClientService is null in BroadcastController!");
            }

            clients.add(client);
            System.out.println("New client registered.");
//            showAnnouncement("Hello");

        }
    }

    @Override
    public synchronized void unregister(ClientService client) throws RemoteException {
        clients.remove(client);
        System.out.println("Client unregistered.");
    }

    @Override
    public void showAnnouncement(String message) throws RemoteException {
        System.out.println("Broadcasting announcement: " + message);
        for (ClientService client : clients) {
            try {
                client.showAnnouncement(message);
            } catch (RemoteException e) {
                System.err.println("Failed to send announcement to a client: " + e.getMessage());
                clients.remove(client); // Remove disconnected clients
            }
        }
    }




}
