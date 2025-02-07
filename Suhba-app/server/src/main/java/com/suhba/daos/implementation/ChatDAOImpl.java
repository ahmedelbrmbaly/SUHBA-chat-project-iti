package com.suhba.daos.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.suhba.daos.DatabaseConnection;
import com.suhba.daos.interfaces.ChatDAO;
import com.suhba.database.entities.Chat;
import com.suhba.database.entities.User;
import com.suhba.database.enums.ChatType;

public class ChatDAOImpl implements ChatDAO {

    Connection connection ;

    public ChatDAOImpl() { try {
        connection = DatabaseConnection.getInstance();
    } catch (ClassNotFoundException | SQLException e) {
        System.out.println("Error in Connection");
        e.printStackTrace();
    } }

    

    @Override
    public Chat createDirectChat(User user1, User user2) throws SQLException {
        try {
            connection.setAutoCommit(false);
            
            // Create chat
            Chat chat = new Chat();
            try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO Chats (chatType) VALUES (?)", 
                Statement.RETURN_GENERATED_KEYS
            )) {
                stmt.setString(1, ChatType.Direct.name());
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        chat.setChatId(rs.getLong(1));
                        chat.setChatType(ChatType.Direct);
                    }
                }
            }

            // Add participants
            addToChatUsers(chat.getChatId(), user1.getUserId());
            addToChatUsers(chat.getChatId(), user2.getUserId());
            
            connection.commit();
            return chat;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // Common helper method
    private void addToChatUsers(long chatId, long userId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
            "INSERT INTO Chats_Users (userId, chatId) VALUES (?, ?)"
        )) {
            stmt.setLong(1, userId);
            stmt.setLong(2, chatId);
            stmt.executeUpdate();
        }
    }

    @Override
    public boolean hasDirectChatBetween(User user1, User user2) throws SQLException {
        String sql = """
            SELECT COUNT(*) FROM Chats c
            JOIN Chats_Users cu1 ON c.chatId = cu1.chatId
            JOIN Chats_Users cu2 ON c.chatId = cu2.chatId
            WHERE c.chatType = 'Direct' 
            AND cu1.userId = ? 
            AND cu2.userId = ?""";
            
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, user1.getUserId());
            stmt.setLong(2, user2.getUserId());
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    @Override
    public User getDirectChatPartner(long chatId, long currentUser) throws SQLException {
       String sql = "SELECT cu.userId " +
                 "FROM Chats_Users cu " +
                 "JOIN Chats c ON cu.chatId = c.chatId " +
                 "WHERE cu.chatId = ? " +
                 "AND c.chatType = 'Direct' " +
                 "AND cu.userId <> ?";

             try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, chatId);
                stmt.setLong(2, currentUser);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    long partnerId = rs.getLong("userId");
                    UserDAOImpl user = new UserDAOImpl();
                    return user.getUserById(partnerId);
                }
            }
        

       return null;
    }

   

    @Override
    public List<Chat> getChatsByUser(long userId) throws SQLException {
        // Return all chats (Direct + Group)
        List<Chat> chats = new ArrayList<>();
        String sql = "select * from chats where chatId In ( select chatId from chats_users where userId = ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Chat chat = new Chat();
                chat.setChatId(rs.getLong("chatId"));
                chat.setChatType(ChatType.valueOf(rs.getString("chatType")));
                chats.add(chat);
            }
        }
        catch(SQLException e){
            System.out.println("SQL Exception at getting Chats for user");
            e.printStackTrace();
        }
        return chats;

    }

    @Override
    public boolean isUserInChat(long userId, long chatId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Chats_Users WHERE userId = ? AND chatId = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, userId);
            statement.setLong(2, chatId);

            try(ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1)>0;
                }
                return false;
            }
        }

    }

    @Override
    public Chat getChatById(long chatId) throws SQLException {
        Chat chat = new Chat();
        String sql= "select chatType from chats where chatId = ? ";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, chatId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                chat.setChatId(chatId);
                chat.setChatType(ChatType.valueOf(rs.getString(1)));
            } else{
                System.out.println("Not found chat");
            }
        }
        return chat;
    }



    @Override
    public List<User> getChatParticipants(long chatId) throws SQLException {
        List<User> users = new ArrayList<>();
        
        String sql= "select userId from chats_users where chatId = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, chatId);
            try(ResultSet rs = statement.executeQuery()){
                while(rs.next()) {
                    long userFoundId = rs.getLong(1);
                    UserDAOImpl userDAOImpl = new UserDAOImpl();
                    User user = userDAOImpl.getUserById(userFoundId);
                    users.add(user);
                    
                }
            }
        }
        return users;
    }



    @Override
    public boolean addUsersToChat(long chatId, List<Long> userIds) throws SQLException {
        String sql = "Insert into chats_users (chatId, userId) values (?,?)";
        int totalAdded=0;
        for (Long userId : userIds) {
            try(PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setLong(1, chatId);
                statement.setLong(2, userId);
                int numOfAdded = statement.executeUpdate();
                if (numOfAdded>0) {
                    System.out.println(userId + " Added successfully !");
                    totalAdded++;
                }else{
                    System.out.println("Failed to add");
                }
            }
        }
        if(totalAdded == userIds.size()){
            return true;
        }
        return false;
        
    }



    @Override
    public Chat getDirectChatPartnerByUserId(long userId, long secondUserId) throws SQLException {
        Chat chat = null;
        String sql = """
        SELECT c.chatId, c.chatType FROM Chats c
        JOIN Chats_Users cu1 ON c.chatId = cu1.chatId
        JOIN Chats_Users cu2 ON c.chatId = cu2.chatId
        WHERE c.chatType = 'Direct' 
        AND cu1.userId = ? 
        AND cu2.userId = ?""";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setLong(1, userId);
        stmt.setLong(2, secondUserId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                chat = new Chat();
                chat.setChatId(rs.getLong("chatId"));
                chat.setChatType(ChatType.Direct);
            }
        }
    }

        return chat;
    }



    @Override
    public boolean hasDirectChatBetween(long user1, long user2) throws SQLException {
        String sql = """
            SELECT COUNT(*) FROM Chats c
            JOIN Chats_Users cu1 ON c.chatId = cu1.chatId
            JOIN Chats_Users cu2 ON c.chatId = cu2.chatId
            WHERE c.chatType = 'Direct' 
            AND cu1.userId = ? 
            AND cu2.userId = ?""";
            
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, user1);
            stmt.setLong(2, user2);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }



    @Override
    public List<Chat> getAllDirectChatsByUserId(long userId) throws SQLException {
        // Return all chats (Direct)
        List<Chat> chats = new ArrayList<>();
        String sql = "select * from chats where chatId In ( select chatId from chats_users where userId = ?) and chatType = 'Direct'";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Chat chat = new Chat();
                chat.setChatId(rs.getLong("chatId"));
                chat.setChatType(ChatType.Direct);
                chats.add(chat);
            }
        }
        catch(SQLException e){
            System.out.println("SQL Exception at getting direct Chats for user");
            e.printStackTrace();
        }
        return chats;
    }



    @Override
    public List<Chat> getAllGroupChatsByUserId(long userId) throws SQLException {
       // Return all chats (Group)
       List<Chat> chats = new ArrayList<>();
       String sql = "select * from chats where chatId In ( select chatId from chats_users where userId = ?) and chatType = 'Group'";
       try(PreparedStatement statement = connection.prepareStatement(sql)){
           statement.setLong(1, userId);
           ResultSet rs = statement.executeQuery();
           while(rs.next()) {
               Chat chat = new Chat();
               chat.setChatId(rs.getLong("chatId"));
               chat.setChatType(ChatType.Group);
               chats.add(chat);
           }
       }
       catch(SQLException e){
           System.out.println("SQL Exception at getting direct Chats for user");
           e.printStackTrace();
       }
       return chats;
    }
    
}
