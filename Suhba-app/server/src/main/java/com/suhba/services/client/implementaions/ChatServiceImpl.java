package com.suhba.services.client.implementaions;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.suhba.daos.implementation.ChatDAOImpl;
import com.suhba.daos.implementation.ContactDAOImpl;
import com.suhba.daos.implementation.GroupDaoImpl;
import com.suhba.daos.implementation.MessageDAOImpl;
import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.daos.interfaces.ContactDAO;
import com.suhba.database.entities.Chat;
import com.suhba.database.entities.Group;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.database.enums.ChatType;
import com.suhba.database.enums.UserStatus;
import com.suhba.network.ClientInterface;
import com.suhba.services.client.interfaces.ChatService;

public class ChatServiceImpl implements ChatService {

    ChatDAOImpl chatDAO = new ChatDAOImpl();
    MessageDAOImpl messageDAOImpl = new MessageDAOImpl();
    UserDAOImpl userDAOImpl = new UserDAOImpl();
    GroupDaoImpl groupDaoImpl = new GroupDaoImpl();
    Map<Long, ClientInterface> clients = new HashMap<>();
    ContactDAOImpl contactDAO = new ContactDAOImpl();

    @Override
    public List<Message> getMessages(long chatId) {
        List<Message> chatMessages = new ArrayList<>();
        try {
            if (chatDAO.getChatById(chatId) != null) {
                chatMessages = messageDAOImpl.getChatMessages(chatId);
            }
        } catch (SQLException e) {
            System.out.println("Getting Messages Service Exception!");
            e.printStackTrace();
        }

        return chatMessages;
    }
    @Override
    public Message sendMessage(Message msg) {
        Message newMsg = null;
        if (msg.getChatId() > 0 && msg.getSenderId() > 0 && msg != null) {
            try {
                // Save the message to the database
                newMsg = messageDAOImpl.sendMessage(msg);
                if (msg.getMessageId() == 0) {
                    System.out.println("No message added to DB!");
                } else {
                    System.out.println("msg after added: " + newMsg);

                    // Check if the chat is a direct chat
                    if (chatDAO.getChatById(msg.getChatId()).getChatType() == ChatType.Direct) {
                        long receiverId = chatDAO.getDirectChatPartner(msg.getChatId(), msg.getSenderId()).getUserId();
                        System.out.println("Receiver id:" + receiverId);

                        // Check if the receiver is online
                        if (clients.containsKey(receiverId)) {
                            ClientInterface receiverClient = clients.get(receiverId);
                            System.out.println("ClientInterface: " + receiverClient);
                            if (receiverClient != null) {
                                // Deliver the message to the receiver
                                receiverClient.receiveMessage(newMsg, ChatType.Direct);
                            } else {
                                System.out.println("Client with ID " + receiverId + " is not online.");
                            }
                        } else {
                            // Receiver is offline, check if chatbot is active
                            User receiver = userDAOImpl.getUserById(receiverId);
                            System.out.println(receiver.isChatBotActive());
                            if (receiver.getUserStatus() == UserStatus.Offline && receiver.isChatBotActive()) {

                                System.out.println("Chat bot is active and user is offline");

                                // Notify the sender's client to handle the chatbot response
                                if (clients.containsKey(msg.getSenderId())) {
                                    ClientInterface senderClient = clients.get(msg.getSenderId());
                                    if (senderClient != null) {
                                        // Trigger the chatbot response on the sender's client
                                        senderClient.chatBotMessage(receiverId, msg.getSenderId(), msg);
                                    }
                                }
                            } else {
                                System.out.println("Client with ID " + receiverId + " is offline and chatbot is not active.");
                            }
                        }
                    } else {
                        // Handle group messages
                        List<User> members = chatDAO.getChatParticipants(msg.getChatId());
                        for (User member : members) {
                            if (member.getUserId() == msg.getSenderId()) continue; // Skip the sender
                            if (clients.containsKey(member.getUserId())) {
                                ClientInterface client = clients.get(member.getUserId());
                                System.out.println("ClientInterface: " + client);
                                if (client != null) {
                                    client.receiveMessage(newMsg, ChatType.Group);
                                } else {
                                    System.out.println("Client with ID " + member.getUserId() + " is not online.");
                                }
                            }
                        }
                    }
                }

                // Handle file attachments (if any)
                if (msg.getAttachment() != null) {
                    // Handle file transfer logic here
                }
            } catch (SQLException e) {
                System.out.println("Send Messages Service Exception!");
                e.printStackTrace();
            } catch (RemoteException e) {
                System.out.println("Exception at receiving !");
                e.printStackTrace();
            }
        }
        return newMsg;
    }
    @Override
    public long createPrivateChat(long userId1, long userId2) throws Exception {
        Chat newChat;
        User user = userDAOImpl.getUserById(userId1);
        User user2 = userDAOImpl.getUserById(userId2);
        if (user != null && user2 != null && userId1 != userId2) {
            if (chatDAO.hasDirectChatBetween(userId1, userId2)) {
                throw new Exception("already have chat");
            }
            try {
                newChat = chatDAO.createDirectChat(user, user2);
                return newChat.getChatId();
            } catch (SQLException e) {
                System.out.println("Create Private Chat Exception!");
                e.printStackTrace();
            }
        } else {
            throw new Exception("Invalid Parameters for creating !");
        }
        return 0;
    }

    @Override
    public Map<User, Message> getUserChats(long userId) {
        User currentUser = userDAOImpl.getUserById(userId);
        Map<User, Message> m = new HashMap<>();
        if (currentUser == null)
            return Collections.emptyMap();
        try {
            List<Chat> chats = chatDAO.getAllDirectChatsByUserId(userId);
            for (Chat c : chats) {
                User otherUser = chatDAO.getDirectChatPartner(c.getChatId(), userId);
                Message lastMessage = messageDAOImpl.getLastMessageInChat(c.getChatId());
                if (otherUser != null && lastMessage != null)
                    m.put(otherUser, lastMessage);
            }

        } catch (SQLException e) {
            System.out.println("get User Chats Exception!");
            e.printStackTrace();
        }
        return m;
    }

    @Override
    public User getUserById(long userId) {
        return userDAOImpl.getUserById(userId);
    }

    @Override
    public User getPrivateUserPartnerByChat(long chatId, long userId) throws Exception { // Must Know the Client who
        if (chatDAO.isUserInChat(userId, chatId)) {
            return chatDAO.getDirectChatPartner(chatId, userId);
        }
        return null;
    }

    // ************************************GROUP***************************************
    // //

    public Map<Group, Message> getUserGroups(long userId) {
        User currentUser = userDAOImpl.getUserById(userId);
        Map<Group, Message> m = new HashMap<>();
        if (currentUser == null)
            return Collections.emptyMap();
        try {
            List<Chat> chats = chatDAO.getAllGroupChatsByUserId(userId);
            for (Chat c : chats) {
                Group group = groupDaoImpl.getGroupByChatId(c.getChatId());
                Message lastMessage = messageDAOImpl.getLastMessageInChat(c.getChatId());
                if (group != null && lastMessage != null)
                    m.put(group, lastMessage);
            }

        } catch (SQLException e) {
            System.out.println("get User Group Chats Exception!");
            e.printStackTrace();
        }
        return m;
    }

    @Override
    public List<User> getGroupMembers(long groupId) throws Exception {
        try {
            Group group = groupDaoImpl.getGroupById(groupId);
            if (group == null)
                throw new Exception("No such group");
            return chatDAO.getChatParticipants(group.getChatId());
        } catch (SQLException e) {
            System.out.println("get group users Exception!");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Group createGroupChat(Group group, List<Long> userId) throws Exception {
        if (userId.size() < 3) {
            throw new Exception("Can not create group : less than boundry");
        }
        Group newGroup = groupDaoImpl.createGroup(group);
        if (newGroup == null)
            return null;
        try {
            chatDAO.addUsersToChat(newGroup.getChatId(), userId);
        } catch (SQLException e) {
            System.out.println("Exception at Adding users to group");
            e.printStackTrace();
        }
        return newGroup;
    }

    @Override
    public Group getGroupByGroupId(long groupId) {
        Group group = groupDaoImpl.getGroupById(groupId);
        return group;
    }

    @Override
    public boolean addUsersToGroup(long groupId, List<Long> userId) throws Exception {
        if (userId.size() < 1)
            throw new Exception("Can not add");
        int count = 0;
        for (Long u : userId) {
            if (!groupDaoImpl.isUserInGroup(groupId, u)) {
                groupDaoImpl.addUserToGroup(groupId, u);
                count++;
            }
        }
        if (count < 1)
            throw new Exception("users already in group");
        return true;

    }

    @Override
    public boolean removeUsersFromGroup(long chatId, List<Long> userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeUsersFromGroup'");
    }

    @Override
    public Chat getDirectChatBetween(long currentUserId, long otherUserId) {
        try {
            if (chatDAO.hasDirectChatBetween(currentUserId, otherUserId)) {
                return chatDAO.getDirectChatPartnerByUserId(currentUserId, otherUserId);
            }
        } catch (SQLException e) {
            System.out.println("Get Direct chat Partner Exception!");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Group getGroupByChat(long chatId) throws Exception {
        Group group = groupDaoImpl.getGroupByChatId(chatId);
        if (group == null)
            throw new Exception("No group chat like that");
        return group;
    }

    @Override
    public void registerToReceiveMessages(long userId, ClientInterface client) {
        clients.put(userId, client);
        userDAOImpl.updateUserStatus(userId, UserStatus.Available);
        // Notify My friends + Update GUI for online friends
        List<User> friends = contactDAO.getAllUsersInContactByUserID(userId);

        for (User friend : friends) {
            if (clients.containsKey(friend.getUserId())) {
                ClientInterface friendClient = clients.get(friend.getUserId());
                try {
                    if (friendClient != null)
                        friendClient.notifyUserStatusChanged(userId, UserStatus.Available);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(clients.get(userId));
        System.out.println(userId + " is online");
    }

    @Override
    public void unregisterToReceive(long userId) {
        clients.remove(userId);
        userDAOImpl.updateUserStatus(userId, UserStatus.Offline);
        System.out.println(userId + " is offline now");
    }

    @Override
    public Chat getChatById(long chatId) {
        Chat chat = null;
        try {
            chat = chatDAO.getChatById(chatId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chat;
    }

}
