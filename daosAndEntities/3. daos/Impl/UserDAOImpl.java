package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private Connectivity connectivity = new Connectivity();
    Connection connection = null;

    public UserDAOImpl() { connection = connectivity.getConnection(); }

    public boolean addNewUser(User user) {
        String sql = "INSERT INTO Users (phone, displayName, userEmail, picture, password, gender, country, birthday, bio, userStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getPhone());
            stmt.setString(2, user.getDisplayName());
            stmt.setString(3, user.getUserEmail());
            stmt.setBlob(4, user.getPicture());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getCountry());
            stmt.setDate(8, user.getBirthday() != null ? Date.valueOf(user.getBirthday()) : null);
            stmt.setString(9, user.getBio());
            stmt.setString(10, user.getUserStatus());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserId(rs.getLong(1));
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUserById(long userId) {
        String sql = "SELECT * FROM Users WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("userId"));
                user.setPhone(rs.getString("phone"));
                user.setDisplayName(rs.getString("displayName"));
                user.setUserEmail(rs.getString("userEmail"));
                user.setPicture(rs.getBlob("picture"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender"));
                user.setCountry(rs.getString("country"));
                user.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
                user.setBio(rs.getString("bio"));
                user.setUserStatus(rs.getString("userStatus"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByPhone(String phone) {
        String sql = "SELECT * FROM Users WHERE phone = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phone);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getLong("userId"));
                    user.setPhone(rs.getString("phone"));
                    user.setDisplayName(rs.getString("displayName"));
                    user.setUserEmail(rs.getString("userEmail"));
                    user.setPicture(rs.getBlob("picture"));
                    user.setPassword(rs.getString("password"));
                    user.setGender(rs.getString("gender"));
                    user.setCountry(rs.getString("country"));
                    user.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
                    user.setBio(rs.getString("bio"));
                    user.setUserStatus(rs.getString("userStatus"));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getUserStatusById(long userId) {
        String sql = "SELECT userStatus FROM Users WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())  return rs.getString("userStatus");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String getUserDisplayNameById(long userId) {
        String sql = "SELECT displayName FROM Users WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())  return rs.getString("displayName");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public List<User> getUsersByCountry(String country) {
        String sql = "SELECT * FROM Users WHERE country = ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, country);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getLong("userId"));
                    user.setPhone(rs.getString("phone"));
                    user.setDisplayName(rs.getString("displayName"));
                    user.setUserEmail(rs.getString("userEmail"));
                    user.setPicture(rs.getBlob("picture"));
                    user.setPassword(rs.getString("password"));
                    user.setGender(rs.getString("gender"));
                    user.setCountry(rs.getString("country"));
                    user.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
                    user.setBio(rs.getString("bio"));
                    user.setUserStatus(rs.getString("userStatus"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> getUsersByStatus(String status) {
        String sql = "SELECT * FROM Users WHERE userStatus = ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getLong("userId"));
                    user.setPhone(rs.getString("phone"));
                    user.setDisplayName(rs.getString("displayName"));
                    user.setUserEmail(rs.getString("userEmail"));
                    user.setPicture(rs.getBlob("picture"));
                    user.setPassword(rs.getString("password"));
                    user.setGender(rs.getString("gender"));
                    user.setCountry(rs.getString("country"));
                    user.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
                    user.setBio(rs.getString("bio"));
                    user.setUserStatus(rs.getString("userStatus"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> getUsersByGender(String gender) {
        String sql = "SELECT * FROM Users WHERE gender = ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, gender);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getLong("userId"));
                    user.setPhone(rs.getString("phone"));
                    user.setDisplayName(rs.getString("displayName"));
                    user.setUserEmail(rs.getString("userEmail"));
                    user.setPicture(rs.getBlob("picture"));
                    user.setPassword(rs.getString("password"));
                    user.setGender(rs.getString("gender"));
                    user.setCountry(rs.getString("country"));
                    user.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
                    user.setBio(rs.getString("bio"));
                    user.setUserStatus(rs.getString("userStatus"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM Users";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("userId"));
                user.setPhone(rs.getString("phone"));
                user.setDisplayName(rs.getString("displayName"));
                user.setUserEmail(rs.getString("userEmail"));
                user.setPicture(rs.getBlob("picture"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender"));
                user.setCountry(rs.getString("country"));
                user.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
                user.setBio(rs.getString("bio"));
                user.setUserStatus(rs.getString("userStatus"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET phone = ?, displayName = ?, userEmail = ?, picture = ?, password = ?, gender = ?, country = ?, birthday = ?, bio = ?, userStatus = ? WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getPhone());
            stmt.setString(2, user.getDisplayName());
            stmt.setString(3, user.getUserEmail());
            stmt.setBlob(4, user.getPicture());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getCountry());
            stmt.setDate(8, user.getBirthday() != null ? Date.valueOf(user.getBirthday()) : null);
            stmt.setString(9, user.getBio());
            stmt.setString(10, user.getUserStatus());
            stmt.setLong(11, user.getUserId());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUserProfileById(long userId) {
        String sql = "UPDATE Users SET phone = ?, displayName = ?, userEmail = ?, picture = ?, gender = ?, country = ?, birthday = ?, bio = ? WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "1234567890");
            stmt.setString(2, "New Display Name");
            stmt.setString(3, "example@gmail.com");
            stmt.setBlob(4, (Blob) null);
            stmt.setString(5, "Male");
            stmt.setString(6, "New country");
            stmt.setDate(7, Date.valueOf("2002-02-10"));
            stmt.setString(8, "New Bio");
            stmt.setLong(9, userId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUserPasswordById(long userId) {
        String sql = "UPDATE Users SET password = ? WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "New password");
            stmt.setLong(2, userId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUserById(long userId) {
        String sql = "DELETE FROM Users WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}