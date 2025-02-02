import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAOImpl implements ContactDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/SUHBA_Chat_Project"; 
    private static final String DB_USER = "root"; // Update your MySQL username
    private static final String DB_PASSWORD = ""; // Update your MySQL password

    @Override
    public void addContact(int userId1, int userId2, String contactStatus) {
        String query = "INSERT INTO Contacts (userId1, userId2, contactStatus) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId1);
            preparedStatement.setInt(2, userId2);
            preparedStatement.setString(3, contactStatus);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateContactStatus(int userId1, int userId2, String contactStatus) {
        String query = "UPDATE Contacts SET contactStatus = ? WHERE userId1 = ? AND userId2 = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, contactStatus);
            preparedStatement.setInt(2, userId1);
            preparedStatement.setInt(3, userId2);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setContactEmail(int userId1, int userId2, String contactEmail) {
        String query = """
            UPDATE Users
            SET userEmail = ?
            WHERE userId = (
                SELECT userId2 
                FROM Contacts 
                WHERE userId1 = ? AND userId2 = ?
            )
        """;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, contactEmail);
            preparedStatement.setInt(2, userId1);
            preparedStatement.setInt(3, userId2);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteContact(int userId1, int userId2) {
        String query = "DELETE FROM Contacts WHERE userId1 = ? AND userId2 = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId1);
            preparedStatement.setInt(2, userId2);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Contact> getContactsForUser(int userId) {
        List<Contact> contacts = new ArrayList<>();
        String query = """
            SELECT c.userId1, c.userId2, c.contactStatus, u.displayName AS contactDisplayName, 
                   u.phone AS contactPhone, u.userEmail AS contactEmail
            FROM Contacts c
            JOIN Users u ON u.userId = c.userId2
            WHERE c.userId1 = ?
        """;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                contacts.add(new Contact(
                        resultSet.getInt("userId1"),
                        resultSet.getInt("userId2"),
                        resultSet.getString("contactStatus"),
                        resultSet.getString("contactDisplayName"),
                        resultSet.getString("contactPhone"),
                        resultSet.getString("contactEmail")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }
}
