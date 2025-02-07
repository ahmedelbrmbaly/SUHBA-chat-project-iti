package com.suhba.services.client.implementaions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.suhba.daos.implementation.ChatDAOImpl;
import com.suhba.daos.implementation.GroupDaoImpl;
import com.suhba.daos.implementation.MessageDAOImpl;
import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.database.entities.Chat;
import com.suhba.database.entities.Group;
import com.suhba.database.entities.Message;
import com.suhba.database.entities.User;
import com.suhba.database.enums.ChatType;
import com.suhba.services.client.interfaces.ChatService;

public class ChatServiceImpl implements ChatService {

    ChatDAOImpl chatDAO = new ChatDAOImpl();
    MessageDAOImpl messageDAOImpl = new MessageDAOImpl();
    UserDAOImpl userDAOImpl = new UserDAOImpl();
    GroupDaoImpl groupDaoImpl = new GroupDaoImpl();

    @Override
    public List<Message> getMessages(long chatId) {
        List<Message> chatMessages = new ArrayList<>();
        if (userDAOImpl.getUserById(chatId) != null) {
            try {
                chatMessages = messageDAOImpl.getChatMessages(chatId);
            } catch (SQLException e) {
                System.out.println("Getting Messages Service Exception!");
                e.printStackTrace();
            }
        }
        return chatMessages;
    }

    @Override
    public Message sendMessage(Message msg) {
        Message newMsg = null;
        if (msg.getChatId() > 0 && msg.getSenderId() > 0 && msg.getContent() != null) {
            try {
                newMsg = messageDAOImpl.sendMessage(msg);
                if (msg.getAttachment() != null) {
                    // Handle if there is a File Transfer
                }
            } catch (SQLException e) {
                System.out.println("Send Messages Service Exception!");
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
            Group group= groupDaoImpl.getGroupById(groupId);
            if(group==null) throw new Exception("No such group");
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
        if (count<1) throw new Exception("users already in group");
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
        if(group==null) throw new Exception("No group chat like that");
        return group;
    }

}
