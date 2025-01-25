import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ContactDAO {
    boolean addContact(Contact contact);
    boolean updateContactStatus(long userId2, String status);
    List<Contact> getContactsByUserId1(long userId1);
    Contact getContactByUserId2(long userId2);
}

public class ContactDAOImpl implements ContactDAO {
    private Connection connection;

    public ContactDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addContact(Contact contact) {
        String sql = "INSERT INTO Contacts (userId2, contactStatus) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, contact.getUserId2());
            stmt.setString(2, contact.getContactStatus());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        contact.setUserId1(rs.getLong(1));
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
    public List<Contact> getContactsByUserId1(long userId1) {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM Contacts WHERE userId1 = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId1);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setUserId1(rs.getLong("userId1"));
                contact.setUserId2(rs.getLong("userId2"));
                contact.setContactStatus(rs.getString("contactStatus"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    // Implement other methods...
}