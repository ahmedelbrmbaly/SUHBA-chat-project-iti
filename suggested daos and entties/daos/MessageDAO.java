import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public interface MessageDAO {
    boolean sendMessage(Message message);
    List<Message> getMessagesBySender(long senderId);
    List<Message> getMessagesByReceiver(long receiverId);
}

public class MessageDAOImpl implements MessageDAO {
    private Connection connection;

    public MessageDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean sendMessage(Message message) {
        String sql = "INSERT INTO Messages (senderId, content, timeStamp, messageStatus, attachment) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, message.getSenderId());
            stmt.setString(2, message.getContent());
            stmt.setTimestamp(3, message.getTimeStamp());
            stmt.setString(4, message.getMessageStatus());
            stmt.setString(5, message.getAttachment());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        message.setMessageId(rs.getLong(1));
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Message> getMessagesBySender(long senderId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM Messages WHERE senderId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, senderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Message message = new Message();
                message.setMessageId(rs.getLong("messageId"));
                message.setSenderId(rs.getLong("senderId"));
                message.setContent(rs.getString("content"));
                message.setTimeStamp(rs.getTimestamp("timeStamp"));
                message.setMessageStatus(rs.getString("messageStatus"));
                message.setAttachment(rs.getString("attachment"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    // Implement getMessagesByReceiver...
}