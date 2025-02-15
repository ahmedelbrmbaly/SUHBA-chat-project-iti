package com.suhba.daos.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.suhba.daos.DatabaseConnection;
import com.suhba.daos.interfaces.ContactDAO;
import com.suhba.database.entities.Contact;
import com.suhba.database.entities.User;
import com.suhba.database.enums.ContactStatus;
import com.suhba.database.enums.UserStatus;

public class ContactDAOImpl implements ContactDAO {
    Connection connection ;

    public ContactDAOImpl() { try {
        connection = DatabaseConnection.getInstance();
    } catch (ClassNotFoundException | SQLException e) {
        System.out.println("Error in Connection");
        e.printStackTrace();
    } }

    @Override
    public boolean addContact(Contact contact) {
        if (!getContactsByUserId1AndUserId2(contact.getUserId1(), contact.getUserId2()).isEmpty() || !getContactsByUserId1AndUserId2(contact.getUserId2(), contact.getUserId1()).isEmpty())  return false;
        System.out.println("In addContact method");
        String query = "INSERT INTO Contacts (userId1, userId2, contactStatus) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1,contact.getUserId1());
            preparedStatement.setLong(2, contact.getUserId2());
            preparedStatement.setString(3, contact.getContactStatus().name());
            int added = preparedStatement.executeUpdate();
            if(added>0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("In addContact method");
        return false;
    }

    @Override
    public boolean updateContactStatus(Contact contact, ContactStatus newStatus) {
        String query = "UPDATE Contacts SET contactStatus = ? WHERE userId1 = ? AND userId2 = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newStatus.name());
            preparedStatement.setLong(2, contact.getUserId1());
            preparedStatement.setLong(3, contact.getUserId2());
            int updated = preparedStatement.executeUpdate();
            if(updated>0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteContact(Contact contact) {
        String query = "DELETE FROM Contacts WHERE userId1 = ? AND userId2 = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1,contact.getUserId1());
            preparedStatement.setLong(2, contact.getUserId2());
            int deleted = preparedStatement.executeUpdate();
            if(deleted>0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Contact> getContactsByUserId(long userId) {
        List<Contact> contacts = new ArrayList<>();

        String query = """
            SELECT c.userId1, c.userId2, c.contactStatus AS contactDisplayName, 
                   u.phone AS contactPhone, u.userEmail AS contactEmail
            FROM Contacts c
            JOIN Users u ON u.userId = c.userId2
            WHERE c.userId1 = ?
        """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long user1 = resultSet.getLong(1);
                long user2 = resultSet.getLong(2);
                ContactStatus status = ContactStatus.valueOf(resultSet.getString(2));
                Contact c = new Contact(user1,user2,status);
                contacts.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    @Override
    public List<Contact> getContactsByUserId1AndUserId2(long userId1, long userId2) {
        List<Contact> contacts = new ArrayList<>();

        String query = """
            SELECT c.userId1, c.userId2, c.contactStatus AS contactDisplayName, 
                   u.phone AS contactPhone, u.userEmail AS contactEmail
            FROM Contacts c
            JOIN Users u ON u.userId = c.userId2
            WHERE c.userId1 = ? and userId2 = ?
        """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId1);
            preparedStatement.setLong(2, userId2);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long user1 = resultSet.getLong(1);
                long user2 = resultSet.getLong(2);
                ContactStatus status = ContactStatus.valueOf(resultSet.getString(2));
                Contact c = new Contact(user1,user2,status);
                contacts.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    @Override
    public List<Long> getUserId1ByUserId2(long userId2, ContactStatus contactStatus) {
        String sql = "SELECT userId1 FROM Contacts WHERE userId2 = ? and contactStatus = ?";
        List<Long> ids = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId2);
            stmt.setString(2, ContactStatus.PENDING.name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next())  ids.add(rs.getLong("userId1"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return ids;
    }

    @Override
    public List<User> getAllUsersInContactByUserID(long userId) {
        List<User> contacts = new ArrayList<>();
        String query = "SELECT u.userId FROM Users u " +
                "JOIN Contacts c ON (c.userId1 = u.userId OR c.userId2 = u.userId) " +
                "WHERE (c.userId1 = ? OR c.userId2 = ?) AND c.contactStatus = 'Accepted' AND u.userId <> ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, userId);
            stmt.setLong(2, userId);
            stmt.setLong(3, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new UserDAOImpl().getUserById(rs.getLong(1));
                    contacts.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }

    @Override
    public List<Long> getAcceptedFriends(long userId) throws SQLException {
        
        List<Long> friends = new ArrayList<>();
        String sql = "SELECT CASE WHEN userId1 = ? THEN userId2 ELSE userId1 END AS friendId " +
                     "FROM contacts " +
                     "WHERE (userId1 = ? OR userId2 = ?) AND contactStatus = 'ACCEPTED'";
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
    
            stmt.setLong(1, userId);
            stmt.setLong(2, userId);
            stmt.setLong(3, userId);
    
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                friends.add(rs.getLong("friendId"));
            }
        }
        return friends;
    }
    
}
