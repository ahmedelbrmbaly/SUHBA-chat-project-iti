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


    // TESTED >> WORKING
    @Override
    public Optional<Group> createGroup(Group group, long creatorId) {

        String createChatSQL = "INSERT INTO `Chats` (chatType) VALUES ('Group')";
        String addUserToChatSQL = "INSERT INTO `Chats_Users` (userId, chatId) VALUES (?, ?)";
        String createGroupSQL = "INSERT INTO `Groups` (groupName, groupPhoto, groupDescription, category, chatId) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement createChatStmt = connection.prepareStatement(createChatSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement addUserToChatStmt = connection.prepareStatement(addUserToChatSQL);
             PreparedStatement createGroupStmt = connection.prepareStatement(createGroupSQL, Statement.RETURN_GENERATED_KEYS)) {

            connection.setAutoCommit(false);

            // Step 1: Create a chat entry
            createChatStmt.executeUpdate();
            ResultSet chatKeys = createChatStmt.getGeneratedKeys();
            if (!chatKeys.next()) {
                connection.rollback();
                return Optional.empty();
            }
            long chatId = chatKeys.getLong(1);

            // Step 2: Add the creator to the chat
            addUserToChatStmt.setLong(1, creatorId);
            addUserToChatStmt.setLong(2, chatId);
            addUserToChatStmt.executeUpdate();

            // Step 3: Create the group
            createGroupStmt.setString(1, group.getGroupName());
            createGroupStmt.setString(2, group.getGroupPhoto());
            createGroupStmt.setString(3, group.getGroupDescription());
            createGroupStmt.setString(4, group.getCategory());
            createGroupStmt.setLong(5, chatId);

            int affectedRows = createGroupStmt.executeUpdate();
            if (affectedRows == 0) {
                connection.rollback();
                return Optional.empty();
            }

            ResultSet groupKeys = createGroupStmt.getGeneratedKeys();
            if (groupKeys.next()) {
                group.setGroupId(groupKeys.getLong(1));
                group.setChatId(chatId);
                connection.commit();
                return Optional.of(group);
            }

            connection.rollback();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            handleSQLException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                handleSQLException(autoCommitEx);
            }
        }
        return Optional.empty();
    }

    // TESTED >> WORKING
    @Override
    public Optional<Group> getGroupById(long groupId) {
        String sql = "SELECT groupId, groupName, groupPhoto, groupDescription,category, chatId FROM `Groups` WHERE groupId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, groupId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToGroup(rs));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return Optional.empty();
    }

    // TESTED >> WORKING
    @Override
    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT groupId,groupName, groupPhoto, groupDescription,category, chatId FROM `Groups`";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                groups.add(mapRowToGroup(rs));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return groups;
    }

    // TESTED >> WORKING
    @Override
    public List<Group> getGroupsByUserId(long userId) {
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT g.groupId, g.groupName, g.groupPhoto, g.groupDescription, g.category, g.chatId " +
                "FROM `Groups` g " +
                "JOIN Chats_Users cu ON g.chatId = cu.chatId " +
                "WHERE cu.userId = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    groups.add(mapRowToGroup(rs));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e); // Consider logging this properly
        }
        return groups;
    }


    // TESTED >> WORKING
    @Override
    public boolean updateGroup(Group group) {
        String sql = "UPDATE `Groups` SET groupName = ?, groupPhoto = ?, groupDescription = ?, category = ? WHERE groupId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, group.getGroupName());
            stmt.setString(2, group.getGroupPhoto());
            stmt.setString(3, group.getGroupDescription());
            stmt.setString(4, group.getCategory());
            stmt.setLong(5, group.getGroupId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }
    // TESTED >> WORKING
    @Override
    public boolean deleteGroup(long groupId) {
        String sql = "DELETE FROM `Groups` WHERE groupId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, groupId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    // TESTED >> WORKING
    @Override
    public boolean addUserToGroup(long groupId, long userId) {

        String sql = "INSERT INTO Chats_Users (userId, chatId) " +
                "VALUES (?, (SELECT chatId FROM `Groups` WHERE groupId = ?))";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, groupId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    // TESTED >> WORKING
    @Override
    public boolean removeUserFromGroup(long groupId, long userId) {
        String sql = "DELETE FROM Chats_Users " +
                "WHERE userId = ? AND chatId = (SELECT chatId FROM `Groups` WHERE groupId = ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, groupId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    // TESTED >> WORKING
    @Override
    public boolean isUserInGroup(long groupId, long userId) {
        String sql = "SELECT COUNT(*) AS count FROM Chats_Users " +
                "WHERE userId = ? AND chatId = (SELECT chatId FROM `Groups` WHERE groupId = ?)";
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

    // TESTED >> WORKING
    @Override
    public List<Long> getGroupMembers(long groupId) {
        List<Long> members = new ArrayList<>();
        String sql = "SELECT cu.userId FROM Chats_Users cu " +
                "JOIN `Groups` g ON cu.chatId = g.chatId " +
                "WHERE g.groupId = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, groupId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    members.add(rs.getLong("userId"));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return members;
    }


    private void handleSQLException(SQLException e) {
        // Replace with proper logging
        System.err.println("SQL Error: " + e.getMessage());
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Error Code: " + e.getErrorCode());
    }

    // TESTED >> WORKING
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


