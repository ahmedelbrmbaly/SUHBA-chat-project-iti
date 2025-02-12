package com.suhba.daos.implementation;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.suhba.daos.DatabaseConnection;
import com.suhba.daos.interfaces.UserDAO;
import com.suhba.database.entities.*;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import com.suhba.database.enums.UserStatus;
import com.suhba.database.entities.User;



public class UserDAOImpl implements UserDAO {
    Connection connection;

    public UserDAOImpl() {
        try {
            connection = DatabaseConnection.getInstance();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error in Connection");
            e.printStackTrace();
        }
    }

    public boolean addNewUser(User user) {
        String sql = "INSERT INTO Users (phone, displayName, userEmail, picture, password, gender, country, birthday, bio, userStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getPhone());
            stmt.setString(2, user.getDisplayName());
            stmt.setString(3, user.getUserEmail());
            stmt.setBytes(4, user.getPicture() == null ? null : user.getPicture());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getGender() == null ? null : String.valueOf(user.getGender()));
            stmt.setString(7, user.getCountry() == null ? null : String.valueOf(user.getCountry()));
            stmt.setDate(8, user.getBirthday() == null ? null : Date.valueOf(user.getBirthday()));
            stmt.setString(9, user.getBio());
            stmt.setString(10, user.getUserStatus() == null ? null : String.valueOf(user.getUserStatus()));
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
                user.setPicture(rs.getBlob("picture") == null ? null : rs.getBytes("picture"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender") == null ? null : Gender.valueOf(rs.getString("gender")));
                user.setCountry(rs.getString("country") == null ? null : Country.valueOf(rs.getString("country")));
                user.setBirthday(rs.getDate("birthday") == null ? null : rs.getDate("birthday").toLocalDate());
                user.setBio(rs.getString("bio"));
                user.setUserStatus(rs.getString("userStatus") == null ? null : UserStatus.valueOf(rs.getString("userStatus")));
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
                    user.setPicture(rs.getBlob("picture") == null ? null : rs.getBytes("picture"));
                    user.setPassword(rs.getString("password"));
                    user.setGender(rs.getString("gender") == null ? null : Gender.valueOf(rs.getString("gender")));
                    user.setCountry(rs.getString("country") == null ? null : Country.valueOf(rs.getString("country")));
                    user.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
                    user.setBio(rs.getString("bio"));
                    user.setUserStatus(rs.getString("userStatus") == null ? null : UserStatus.valueOf(rs.getString("userStatus")));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // look here
    @Override
    public long getUserIdByEmailDAO(String phone) {
        return 0;
    }
    // look here
    @Override
    public long getUserIdByPhoneDAO(String phone) {
        return 0;
    }

    @Override
    public long getUserIdByPhone(String phone) {
        String sql = "SELECT userId FROM Users WHERE phone = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phone);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())  return rs.getLong("userId");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public UserStatus getUserStatusById(long userId) {
        String sql = "SELECT userStatus FROM Users WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())  return UserStatus.valueOf(rs.getString("userStatus"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return UserStatus.valueOf("");
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
    public List<User> getUsersByCountry(Country country) {
        String sql = "SELECT * FROM Users WHERE country = ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, String.valueOf(country));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getLong("userId"));
                    user.setPhone(rs.getString("phone"));
                    user.setDisplayName(rs.getString("displayName"));
                    user.setUserEmail(rs.getString("userEmail"));
                    user.setPicture(rs.getBlob("picture") == null ? null : rs.getBytes("picture"));
                    user.setPassword(rs.getString("password"));
                    user.setGender(rs.getString("gender") == null ? null : Gender.valueOf(rs.getString("gender")));
                    user.setCountry(rs.getString("country") == null ? null : Country.valueOf(rs.getString("country")));
                    user.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
                    user.setBio(rs.getString("bio"));
                    user.setUserStatus(rs.getString("userStatus") == null ? null : UserStatus.valueOf(rs.getString("userStatus")));
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
    public List<User> getUsersByStatus(UserStatus status) {
        String sql = "SELECT * FROM Users WHERE userStatus = ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, String.valueOf(status));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getLong("userId"));
                    user.setPhone(rs.getString("phone"));
                    user.setDisplayName(rs.getString("displayName"));
                    user.setUserEmail(rs.getString("userEmail"));
                    user.setPicture(rs.getBlob("picture") == null ? null : rs.getBytes("picture"));
                    user.setPassword(rs.getString("password"));
                    user.setGender(rs.getString("gender") == null ? null : Gender.valueOf(rs.getString("gender")));
                    user.setCountry(rs.getString("country") == null ? null : Country.valueOf(rs.getString("country")));
                    user.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
                    user.setBio(rs.getString("bio"));
                    user.setUserStatus(rs.getString("userStatus") == null ? null : UserStatus.valueOf(rs.getString("userStatus")));
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
    public List<User> getUsersByGender(Gender gender) {
        String sql = "SELECT * FROM Users WHERE gender = ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, String.valueOf(gender));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getLong("userId"));
                    user.setPhone(rs.getString("phone"));
                    user.setDisplayName(rs.getString("displayName"));
                    user.setUserEmail(rs.getString("userEmail"));
                    user.setPicture(rs.getBlob("picture") == null ? null : rs.getBytes("picture"));
                    user.setPassword(rs.getString("password"));
                    user.setGender(rs.getString("gender") == null ? null : Gender.valueOf(rs.getString("gender")));
                    user.setCountry(rs.getString("country") == null ? null : Country.valueOf(rs.getString("country")));
                    user.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
                    user.setBio(rs.getString("bio"));
                    user.setUserStatus(rs.getString("userStatus") == null ? null : UserStatus.valueOf(rs.getString("userStatus")));
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
    public List<User> getUsersByEmail(String userEmail) {
        String sql = "SELECT * FROM Users WHERE userEmail = ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getLong("userId"));
                    user.setPhone(rs.getString("phone"));
                    user.setDisplayName(rs.getString("displayName"));
                    user.setUserEmail(rs.getString("userEmail"));
                    user.setPicture(rs.getBlob("picture") == null ? null : rs.getBytes("picture"));
                    user.setPassword(rs.getString("password"));
                    user.setGender(rs.getString("gender") == null ? null : Gender.valueOf(rs.getString("gender")));
                    user.setCountry(rs.getString("country") == null ? null : Country.valueOf(rs.getString("country")));
                    user.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
                    user.setBio(rs.getString("bio"));
                    user.setUserStatus(rs.getString("userStatus") == null ? null : UserStatus.valueOf(rs.getString("userStatus")));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return users.isEmpty() ? null : users;
    }

    /**
     * @return
     */
    @Override
    public Map<Country, Long> getUsersCountries() {
        String sql = "SELECT country, COUNT(*) AS count FROM Users GROUP BY country";
        Map<Country, Long> countryCounts = new HashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String countryName = rs.getString("country");
                Country country = Country.valueOf(countryName.toUpperCase());
                long count = rs.getLong("count");

                countryCounts.put(country, count);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

        return countryCounts;
    }


    /**
     * @return
     */
    @Override
    public Map<UserStatus, Long> getUsersStatus() {
        String sql = "SELECT userStatus, COUNT(*) AS count FROM Users GROUP BY userStatus";
        Map<UserStatus, Long> statusCounts = new HashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String statusName = rs.getString("userStatus").trim(); // Trim for any hidden spaces
                long count = rs.getLong("count");

                try {
                    UserStatus status = UserStatus.valueOf(statusName); // Direct mapping without case change
                    statusCounts.put(status, count);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid user status value from database: " + statusName);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

        return statusCounts;
    }


    /**
     * @return
     */
    @Override
    public Map<Gender, Long> getUsersGenders() {
        String sql = "SELECT gender, COUNT(*) AS count FROM Users GROUP BY gender";
        Map<Gender, Long> genderCounts = new HashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String genderName = rs.getString("gender").trim(); // Trim for hidden spaces
                long count = rs.getLong("count");

                try {
                    Gender gender = Gender.valueOf(genderName); // Keep the exact case as defined in the enum
                    genderCounts.put(gender, count);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid gender value from database: " + genderName);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

        return genderCounts;
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
                user.setPicture(rs.getBlob("picture") == null ? null : rs.getBytes("picture"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender") == null ? null : Gender.valueOf(rs.getString("gender")));
                user.setCountry(rs.getString("country") == null ? null : Country.valueOf(rs.getString("country")));
                user.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
                user.setBio(rs.getString("bio"));
                user.setUserStatus(rs.getString("userStatus") == null ? null : UserStatus.valueOf(rs.getString("userStatus")));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<Long> getUserIdsByPhones(List<String> phones) {
        List<Long> userIds = new ArrayList<>();
        String sql = "SELECT userId FROM Users WHERE phone = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (String phone : phones) {
                stmt.setString(1, phone);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        userIds.add(rs.getLong("userId"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

        return userIds;
    }

    @Override
    public List<User> getUsersById(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return List.of();
        }

        // Construct the SQL query with the appropriate number of placeholders
        String placeholders = userIds.stream().map(id -> "?").collect(Collectors.joining(","));
        String sql = "SELECT * FROM Users WHERE userId IN (" + placeholders + ")";

        List<User> users = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Set the user IDs in the prepared statement
            for (int i = 0; i < userIds.size(); i++) {
                stmt.setLong(i + 1, userIds.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getLong("userId"));
                    user.setPhone(rs.getString("phone"));
                    user.setDisplayName(rs.getString("displayName"));
                    user.setUserEmail(rs.getString("userEmail"));
                    user.setPicture(rs.getBlob("picture") == null ? null : rs.getBytes("picture"));
                    user.setPassword(rs.getString("password"));
                    user.setGender(rs.getString("gender") == null ? null : Gender.valueOf(rs.getString("gender")));
                    user.setCountry(rs.getString("country") == null ? null : Country.valueOf(rs.getString("country")));
                    user.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
                    user.setBio(rs.getString("bio"));
                    user.setUserStatus(rs.getString("userStatus") == null ? null : UserStatus.valueOf(rs.getString("userStatus")));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
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
            stmt.setBytes(4, user.getPicture() == null ? null : user.getPicture());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getGender() == null ? null : String.valueOf(user.getGender()));
            stmt.setString(7, user.getCountry() == null ? null : String.valueOf(user.getCountry()));
            stmt.setDate(8, user.getBirthday() != null ? Date.valueOf(user.getBirthday()) : null);
            stmt.setString(9, user.getBio());
            stmt.setString(10, user.getUserStatus() == null ? null : String.valueOf(user.getUserStatus()));
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
    public boolean updateUserProfile(User user) {
        String sql = "UPDATE Users SET phone = ?, displayName = ?, userEmail = ?, picture = ?, gender = ?, country = ?, birthday = ?, bio = ? WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Debugging: Print values before execution
            System.out.println("🔄 Updating User Profile...");
            System.out.println("User ID: " + user.getUserId());
            System.out.println("Phone: " + user.getPhone());
            System.out.println("Display Name: " + user.getDisplayName());
            System.out.println("Email: " + user.getUserEmail());
            System.out.println("Picture: " + (user.getPicture() != null ? "Has Picture" : "No Picture"));
            System.out.println("Gender: " + user.getGender());
            System.out.println("Country: " + user.getCountry());
            System.out.println("Birthday: " + user.getBirthday());
            System.out.println("Bio: " + user.getBio());

            stmt.setString(1, user.getPhone());
            stmt.setString(2, user.getDisplayName());
            stmt.setString(3, user.getUserEmail());
            stmt.setBytes(4, user.getPicture() == null ? null : user.getPicture());
            stmt.setString(5, user.getGender() == null ? null : String.valueOf(user.getGender()));
            stmt.setString(6, user.getCountry() == null ? null : String.valueOf(user.getCountry()));
            stmt.setDate(7, user.getBirthday() != null ? Date.valueOf(user.getBirthday()) : null);
            stmt.setString(8, user.getBio());
            stmt.setLong(9, user.getUserId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("✅ User updated successfully!");
                return true;
            } else {
                System.out.println("❌ No rows updated. Possible reasons: Wrong userId, no changes.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean updateUserPassword(long userId, String newPassword) {
        String sql = "UPDATE Users SET password = ? WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
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
    public boolean updateUserStatus(long userId, UserStatus newStatus) {
        String query = "UPDATE users SET userStatus = ? WHERE userId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, newStatus.name());
            statement.setLong(2, userId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}