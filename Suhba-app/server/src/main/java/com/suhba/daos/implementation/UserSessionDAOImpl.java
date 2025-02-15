package com.suhba.daos.implementation;

import com.suhba.daos.DatabaseConnection;
import com.suhba.daos.interfaces.UserSessionDAO;
import com.suhba.database.entities.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSessionDAOImpl implements UserSessionDAO {
    Connection connection;

    public UserSessionDAOImpl() {
        try {
            connection = DatabaseConnection.getInstance();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error in Connection");
            e.printStackTrace();
        }
    }

    @Override
    public UserSession getUserSessionByUserIdAndMacAddress(long userId, String macAddress) {
        String sql = "SELECT * FROM UserSessions WHERE userId = ? and macAddress = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setString(2, macAddress);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserSession userSession = new UserSession();
                userSession.setUserId(rs.getLong("userId"));
                userSession.setMacAddress(rs.getString("macAddress"));
                userSession.setIsActive(rs.getBoolean("isActive"));
                userSession.setStartDate(rs.getDate("startDate") != null ? rs.getDate("startDate").toLocalDate() : null);
                userSession.setStartDate(rs.getDate("lastLoginDate") != null ? rs.getDate("lastLoginDate").toLocalDate() : null);
                userSession.setStartDate(rs.getDate("endDate") != null ? rs.getDate("endDate").toLocalDate() : null);
                return userSession;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean getIsActiveByUserIdAndMacAddress(long userId, String macAddress) {
        String sql = "SELECT isActive FROM UserSessions WHERE userId = ? and macAddress = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setString(2, macAddress);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())  return rs.getBoolean("isActive");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateIsActiveByUserIdAndMacAddress(long userId, String macAddress, boolean isActive) {
        String sql = "UPDATE UserSessions SET isActive = ? WHERE userId = ? and macAddress = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, isActive);
            stmt.setLong(2, userId);
            stmt.setString(3, macAddress);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
