package com.suhba.daos.implementation;

import com.suhba.daos.interfaces.GroupDao;
import com.suhba.database.entities.Group;

import java.util.List;
import java.util.Optional;

import java.sql.*;
import java.util.ArrayList;

public class GroupDaoImpl implements GroupDao {
    private final Connection connection;

    public GroupDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Optional<Group> createGroup(Group group) {
        String sql = "INSERT INTO Groups (groupName, groupPhoto, groupDescription, category, chatId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, group.getGroupName());
            stmt.setString(2, group.getGroupPhoto());
            stmt.setString(3, group.getGroupDescription());
            stmt.setString(4, group.getCategory());
            stmt.setLong(5, group.getChatId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    group.setGroupId(generatedKeys.getLong(1));
                    return Optional.of(group);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }

    @Override
    public Optional<Group> getGroupById(long groupId) {
        String sql = "SELECT groupId,groupName, groupPhoto, groupDescription,category, chatId FROM Groups WHERE groupId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, groupId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToGroup(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT groupId,groupName, groupPhoto, groupDescription,category, chatId FROM Groups";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                groups.add(mapRowToGroup(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    @Override
    public boolean updateGroup(Group group) {
        String sql = "UPDATE Groups SET groupName = ?, groupPhoto = ?, groupDescription = ?, category = ?, chatId = ? WHERE groupId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, group.getGroupName());
            stmt.setString(2, group.getGroupPhoto());
            stmt.setString(3, group.getGroupDescription());
            stmt.setString(4, group.getCategory());
            stmt.setLong(5, group.getChatId());
            stmt.setLong(6, group.getGroupId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteGroup(long groupId) {
        String sql = "DELETE FROM Groups WHERE groupId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, groupId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addUserToGroup(long groupId, long userId) {
       // must call the chatUser Dao
        String sql = "INSERT INTO Chats_Users (userId, chatId) " +
                "VALUES (?, (SELECT chatId FROM Groups WHERE groupId = ?))";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, groupId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    @Override
    public boolean removeUserFromGroup(long groupId, long userId) {
        String sql = "DELETE FROM Chats_Users " +
                "WHERE userId = ? AND chatId = (SELECT chatId FROM Groups WHERE groupId = ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, groupId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    @Override
    public boolean isUserInGroup(long groupId, long userId) {
        String sql = "SELECT COUNT(*) AS count FROM Chats_Users " +
                "WHERE userId = ? AND chatId = (SELECT chatId FROM Groups WHERE groupId = ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, groupId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            return false;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    private void handleSQLException(SQLException e) {
        // Replace with proper logging
        System.err.println("SQL Error: " + e.getMessage());
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Error Code: " + e.getErrorCode());
    }

    private Group mapRowToGroup(ResultSet rs) throws SQLException {
        return new Group(
                rs.getLong("groupId"),
                rs.getString("groupName"),
                rs.getString("groupPhoto"),
                rs.getString("groupDescription"),
                rs.getString("category"),
                rs.getLong("chatId")
        );
    }
}


