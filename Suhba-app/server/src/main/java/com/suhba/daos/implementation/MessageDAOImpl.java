package com.suhba.daos.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.suhba.daos.DatabaseConnection;
import com.suhba.daos.interfaces.MessageDAO;
import com.suhba.database.entities.Message;
import com.suhba.database.enums.MessageStatus;

public class MessageDAOImpl implements MessageDAO {

    Connection connection ;

    public MessageDAOImpl() { try {
        connection = DatabaseConnection.getInstance();
    } catch (ClassNotFoundException | SQLException e) {
        System.out.println("Error in Connection");
        e.printStackTrace();
    } }

    @Override
    public Message sendMessage(Message message) throws SQLException {
        String sql = """
            INSERT INTO Messages (senderId, chatId, content, messageStatus, attachment)
            VALUES (?, ?, ?, ?, ?)
            """;

            try(PreparedStatement statement = connection.prepareStatement(sql,
            Statement.RETURN_GENERATED_KEYS))
            {
                statement.setLong(1, message.getSenderId());
                statement.setLong(2,message.getChatId());
                statement.setString(3, message.getContent());
                statement.setString(4, message.getMessageStatus().name());
                statement.setString(5, message.getAttachment());

                statement.executeUpdate();

                try(ResultSet rs = statement.getGeneratedKeys()){
                    if(rs.next()){
                        long generatedId = rs.getLong(1);
                        message.setMessageId(generatedId);
                        String selectSQL = "SELECT timeStamp FROM Messages WHERE messageId = ?";
                        try (PreparedStatement selectStmt = connection.prepareStatement(selectSQL)) {
                            selectStmt.setLong(1, generatedId);
                            try (ResultSet rs2 = selectStmt.executeQuery()) {
                                if (rs2.next()) {
                                    message.setTimeStamp(rs2.getTimestamp("timeStamp"));
                                }
                            }
                        }
                    }
                }
            }

        return message;
    }

    @Override
    public List<Message> getChatMessages(long chatId) throws SQLException {
        String sql = """
            SELECT * FROM Messages 
            WHERE chatId = ?
            ORDER BY timeStamp DESC
            """;
            
        List<Message> messages = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, chatId);
            try(ResultSet rs = statement.executeQuery()) {
                while(rs.next()) {
                    messages.add(mapMessage(rs));
                }
            }
        }

        return messages;
    }

    // Helper Method: Map ResultSet to Message
    private Message mapMessage(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setMessageId(rs.getLong("messageId"));
        message.setSenderId(rs.getInt("senderId"));
        message.setChatId(rs.getLong("chatId"));
        message.setContent(rs.getString("content"));
        message.setTimeStamp(rs.getTimestamp("timeStamp"));
        message.setMessageStatus(
            MessageStatus.valueOf(rs.getString("messageStatus"))
        );
        message.setAttachment(rs.getString("attachment"));
        return message;
    }

    @Override
    public void updateMessageStatus(long messageId, MessageStatus status) throws SQLException {
        String sql = "UPDATE Messages SET messageStatus = ? WHERE messageId = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, status.name());
            statement.setLong(2, messageId);
            int numOfUpd = statement.executeUpdate();
            if(numOfUpd >0){
                System.out.println("Updated Successfully");
            }
            else{
                System.out.println("Updating failed");
            }
        }   
    }

    @Override
    public int getUnreadCount(long userId, long chatId) throws SQLException {
        int count=0;
        String sql = "select count(*) from messages " + 
                        "where chatId = ? "+
                        "AND senderId <> ? "+
                        "AND messageStatus <> 'Read'";
        
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, chatId);
            statement.setLong(2, userId);

            try(ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    count = rs.getInt(1);
                }
            }
        }

        return count;
    }

    


    
}
